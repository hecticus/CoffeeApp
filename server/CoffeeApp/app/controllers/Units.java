package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.ListPagerCollection;
import models.ItemType;
import models.Unit;
import models.responseUtils.ExceptionsUtils;
import models.responseUtils.Response;
import models.responseUtils.ResponseCollection;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

/**
 * Created by sm21 on 10/05/18.
 */
public class Units extends Controller {

    @Inject
    private FormFactory formFactory;

    private static Unit unitDao = new Unit();
    private static ItemType itemTypeDao = new ItemType();

//    @CoffeAppsecurity
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

////    @CoffeAppsecurity
//    public Result update() {
//        try
//        {
//            JsonNode json = request().body().asJson();
//            if(json == null)
//                return Response.requiredJson();
//
//            JsonNode id = json.get("id");
//            if (id == null)
//                return Response.requiredParameter("id");
//
//            Unit unit =  Json.fromJson(json, Unit.class);
//
//            System.out.println(unit.getIdUnit());
//
//            JsonNode Name = json.get("name");
//            if (Name != null)
//            {
//                int registered = unitDao.getExist(Name.asText().toUpperCase());
//                if(registered==0) return  Response.messageExist("name");
//                if(registered==1) return  Response.messageExistDeleted("name");
//
//                unit.setNameUnit(Name.asText().toUpperCase());
//            }
//
//            unit.insert();//.update();// = unitDao.update(unit);
//            return Response.updatedEntity(Json.toJson(unit));
//
//        }catch(Exception e){
//            return Response.responseExceptionUpdated(e);
//        }
//    }

    public  Result update(){
        JsonNode json = request().body().asJson();

        if(json == null)
            return badRequest("Expecting Json data");

        if(json.get("id") == null)
            return badRequest("Missing parameter [id]");

        Unit unit  = Unit.findById(json.findPath("id").asLong());

        try {

        JsonNode status = json.findPath("status");
         if (status != null & status.isInt())
             unit.setStatusDelete(status.asInt());

        JsonNode statusUnit = json.findPath("statusUnit");
        if (statusUnit != null & statusUnit.isInt())
            unit.setStatusUnit(statusUnit.asInt());

        JsonNode nameUnit = json.findPath("name");

        if (nameUnit != null & ! nameUnit.asText().isEmpty()) {
            String name = nameUnit.asText();
            if (Unit.existName(name)) {
                System.out.println("************");
                return badRequest("There is the name, write new name");
            }
            unit.setNameUnit(nameUnit.textValue());
        }

        unit.update();
        return ok(Json.toJson(unit));

        }catch (Exception e){
            return badRequest(e.toString());
        }


    }


//    @CoffeAppsecurity
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


//    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            Unit unit = unitDao.findById(id);
            return Response.foundEntity(Response.toJson(unit, Unit.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    //@CoffeAppsecurity
    public Result findAll(String name, Integer index, Integer size, String sort, String collection,  Integer status) {
        try {
//            List<Unit> units = unitDao.findAll(index, size);
            ListPagerCollection listPager = Unit.findAll(name, index, size, sort, null, status);

            return ResponseCollection.foundEntity(listPager);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

}



