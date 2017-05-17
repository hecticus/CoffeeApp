package models.manager.impl;

import com.fasterxml.jackson.databind.JsonNode;
import models.dao.ItemTypeDao;
import models.dao.UnitDao;
import models.dao.impl.ItemTypeDaoImpl;
import models.dao.impl.UnitDaoImpl;
import models.domain.ItemType;
import models.domain.Unit;
import models.manager.UnitManager;
import models.manager.responseUtils.Response;
import models.manager.responseUtils.responseObject.UnitResponse;
import play.libs.Json;
import play.mvc.Result;

import java.util.List;

import static play.mvc.Controller.request;

/**
 * Created by drocha on 25/04/17.
 */
public class UnitManagerImpl   implements UnitManager {




    private static UnitDao unitDao = new UnitDaoImpl();
    private static ItemTypeDao itemTypeDao = new ItemTypeDaoImpl();

    @Override
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


            unit = unitDao.create(unit);
            return Response.createdEntity(Json.toJson(unit));

        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

    @Override
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

                unit.setName(Name.asText().toUpperCase());
            }

            unit = unitDao.update(unit);
            return Response.updatedEntity(Json.toJson(unit));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    @Override
    public Result delete(Long id) {
        try{
            Unit unit = unitDao.findById(id);
            List<ItemType> itemTypes = itemTypeDao.getOpenByUnitId(id);
            if(unit != null  && itemTypes.size()==0) {

                unit.setStatusDelete(1);
                unit = unitDao.update(unit);

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

    @Override
    public Result findById(Long id) {
        try {
            Unit unit = unitDao.findById(id);
            return Response.foundEntity(Response.toJson(unit, UnitResponse.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @Override
    public Result findAll(Integer index, Integer size) {
        try {
            List<Unit> units = unitDao.findAll(index, size);
            return Response.foundEntity(Json.toJson(units));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

}
