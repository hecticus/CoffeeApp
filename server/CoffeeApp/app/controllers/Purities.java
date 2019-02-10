package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.PropertiesCollection;
import controllers.utils.Response;
import io.ebean.Ebean;
import io.ebean.PagedList;
import models.Purity;

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
public class Purities extends Controller{

    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    @CoffeAppsecurity
    public Result preCreate() {
        try {
            return Response.foundEntity(Json.toJson(new Purity()));
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }

    @CoffeAppsecurity
    public Result create() {
        try {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            Form<Purity> form = formFactory.form(Purity.class).bind(json);
            if (form.hasErrors()){
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());
            }

            Purity purity = Json.fromJson(json, Purity.class);
            purity.save();
            return Response.createdEntity(Json.toJson(purity));
        }catch(Exception e){
            return NsExceptionsUtils.create(e);
        }
    }

    @CoffeAppsecurity
    public Result update(Long id) {
        try {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            Form<Purity> form = formFactory.form(Purity.class).bind(json);
            if (form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            Purity purity = Json.fromJson(json, Purity.class);
            purity.setId(id);
            purity.update();
            return Response.updatedEntity(Json.toJson(purity));

        }catch(Exception e){
            return NsExceptionsUtils.update(e);
        }
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Ebean.delete(Purity.findById(id));
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

            Ebean.deleteAll(Purity.class, controllers.utils.JsonUtils.toArrayLong(json, "ids"));

            return controllers.utils.Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            return Response.foundEntity(Json.toJson(Purity.findById(id)));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @CoffeAppsecurity
    public Result findAll(Integer index, Integer size, String collection, String sort, String name, boolean deleted){
        try {
            PagedList pagedList = Purity.findAll(index, size, propertiesCollection.getPathProperties(collection), sort, name, deleted);
            return Response.foundEntity(pagedList, propertiesCollection.getPathProperties(collection));
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }


}