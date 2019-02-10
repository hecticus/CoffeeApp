package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.JsonUtils;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.PropertiesCollection;
import controllers.utils.Response;
import io.ebean.Ebean;
import io.ebean.PagedList;
import models.Unit;

import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import javax.inject.Inject;

/**
 * Created by sm21 on 10/05/18.
 */
public class Units extends Controller {

    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    @CoffeAppsecurity
    public Result create() {
        try {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            Form<Unit> form = formFactory.form(Unit.class).bind(json);
            if(form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            Unit unit = Json.fromJson(json, Unit.class);
            unit.save();
            return Response.createdEntity(Json.toJson(unit));

        }catch(Exception e){
            return NsExceptionsUtils.create(e);
        }
    }

    @CoffeAppsecurity
    public  Result update(Long id){
        try{
            JsonNode json = request().body().asJson();
            if(json == null)
                return badRequest("Expecting Json data");
            Form<Unit> form = formFactory.form(Unit.class).bind(json);
            if(form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            Unit unit = Json.fromJson(json, Unit.class);
            unit.setId(id);
            unit.update();
            return Response.createdEntity(Json.toJson(unit));
        }catch (Exception e){
            return badRequest(e.toString());
        }
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Ebean.delete(Unit.findById(id));
            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }


    @CoffeAppsecurity
    public Result deletes() {
        try {
            JsonNode json = request().body().asJson();
            if (json == null)
                return controllers.utils.Response.requiredJson();

            Ebean.deleteAll(Unit.class, JsonUtils.toArrayLong(json, "ids"));

            return controllers.utils.Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }
    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            return Response.foundEntity(Json.toJson(Unit.findById(id)));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @CoffeAppsecurity
    public Result findAll(Integer index, Integer size, String collection,
                          String sort, String name,  boolean deleted){
        try {
            PagedList pagedList = Unit.findAll(index, size, propertiesCollection.getPathProperties(collection), sort, name,  deleted);
            return controllers.utils.Response.foundEntity(pagedList, propertiesCollection.getPathProperties(collection));
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }

}



