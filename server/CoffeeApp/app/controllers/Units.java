package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.ItemType;
import models.Unit;
import models.responseUtils.Response;
import play.libs.Json;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import java.util.List;

import static play.mvc.Controller.request;

/**
 * Created by sm21 on 10/05/18.
 */
public class Units {
    
    private static Unit unitDao = new Unit();
    private static ItemType itemTypeDao = new ItemType();

    @CoffeAppsecurity
    public Result create() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();


            JsonNode Name = json.get("name");
            if (Name == null)
                return Response.requiredParameter("name");

            int registered = unitDao.getExist(Name.asText().toUpperCase());
            if(registered==0) return  Response.messageExist("name");
            if(registered==1) return  Response.messageExistDeleted("name");

            JsonNode status = json.get("status");
            if (status == null)
                return Response.requiredParameter("status");




            // mapping object-json
            Unit unit = Json.fromJson(json, Unit.class);


            unit.save();// = unitDao.create(unit);
            return Response.createdEntity(Json.toJson(unit));

        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

    @CoffeAppsecurity
    public Result update() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            JsonNode id = json.get("id");
            if (id == null)
                return Response.requiredParameter("id");

            Unit unit =  Json.fromJson(json, Unit.class);

            JsonNode Name = json.get("name");
            if (Name != null)
            {
                int registered = unitDao.getExist(Name.asText().toUpperCase());
                if(registered==0) return  Response.messageExist("name");
                if(registered==1) return  Response.messageExistDeleted("name");

                unit.setNameUnit(Name.asText().toUpperCase());
            }

            unit.update();// = unitDao.update(unit);
            return Response.updatedEntity(Json.toJson(unit));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Unit unit = unitDao.findById(id);
            List<ItemType> itemTypes = itemTypeDao.getOpenByUnitId(id);
            if(unit != null  && itemTypes.size()==0) {

                unit.setStatusDelete(1);
                unit.update();// = unitDao.update(unit);

                return Response.deletedEntity();
            } else {

                if(unit == null)  return  Response.message("Successful no existe el registro a eliminar");
                else  return  Response.message("Successful el registro tiene facturas aun no cerradas");

            }
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }
    /*public Result delete(Long id) {
        try {

            Unit unit = findById(id);
            //    unitDao.delete(id);
            return Response.deletedEntity();

        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }*/

    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            Unit unit = unitDao.findById(id);
            return Response.foundEntity(Response.toJson(unit, Unit.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @CoffeAppsecurity
    public Result findAll(Integer index, Integer size) {
        try {
            List<Unit> units = unitDao.findAll(index, size);
            return Response.foundEntity(Json.toJson(units));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }
}
