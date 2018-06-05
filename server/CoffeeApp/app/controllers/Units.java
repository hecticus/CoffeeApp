package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.responseUtils.PropertiesCollection;
import controllers.utils.ListPagerCollection;
import models.Unit;
import controllers.responseUtils.ExceptionsUtils;
import controllers.responseUtils.Response;
import controllers.responseUtils.ResponseCollection;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

/**
 * Created by sm21 on 10/05/18.
 */
public class Units extends Controller {

    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

//    @CoffeAppsecurity
    public Result create() {
        try {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(json.toString());
            node.set("NameUnit", json.findValue("name"));

            Form<Unit> form = formFactory.form(Unit.class).bind(json);
            if(form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            // mapping object-json
            Unit unit = Json.fromJson(node, Unit.class);
            unit.save();
            return Response.createdEntity(Json.toJson(unit));

        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

//    @CoffeAppsecurity
    public  Result update(){
        try{
            JsonNode json = request().body().asJson();

            if(json == null)
                return badRequest("Expecting Json data");

            JsonNode id = json.get("idLot");
            if(id == null )
                return badRequest("Missing parameter idLot");

            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(json.toString());
            node.set("NameUnit", json.findValue("name"));

            Form<Unit> form = formFactory.form(Unit.class).bind(json);
            if(form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            // mapping object-json
            Unit unit = Json.fromJson(node, Unit.class);
            unit.save();
            return Response.createdEntity(Json.toJson(unit));
        }catch (Exception e){
            return badRequest(e.toString());
        }
    }

//    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Unit unit = Unit.findById(id);
            unit.setStatusDelete(1);
            unit.setStatusUnit(0);
            unit.update();
            return Response.deletedEntity();
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }


//    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            Unit unit = Unit.findById(id);
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



