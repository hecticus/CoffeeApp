package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.JsonUtils;

import controllers.utils.NsExceptionsUtils;
import controllers.utils.Response;
import io.ebean.Ebean;
import io.ebean.PagedList;
import models.Store;

import controllers.utils.PropertiesCollection;

import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import javax.inject.Inject;

import static play.mvc.Controller.request;

/**
 * Created by drocha on 30/05/17.
 */
public class Stores {

    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    @CoffeAppsecurity
    public Result create() {
        try {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            Form<Store> form = formFactory.form(Store.class).bind(json);
            if(form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            Store store = Json.fromJson(json, Store.class);
            store.save();
            return Response.createdEntity(Json.toJson(store));
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

            Form<Store> form = formFactory.form(Store.class).bind(json);
            if(form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            Store store = Json.fromJson(json, Store.class);
            store.setId(id);
            store.update();
            return Response.updatedEntity(Json.toJson(store));

        }catch(Exception e){
            return NsExceptionsUtils.update(e);
        }
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Ebean.delete(Store.findById(id));
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

            Ebean.deleteAll(Store.class, JsonUtils.toArrayLong(json, "ids"));

            return controllers.utils.Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            return Response.foundEntity(Json.toJson(Store.findById(id)));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @CoffeAppsecurity
    public Result findAll(Integer index, Integer size, String collection,
                          String sort, String name, Long status, boolean deleted){
        try {
            PagedList pagedList = Store.findAll(index, size,  propertiesCollection.getPathProperties(collection),
                                                            sort, name, status, deleted);
            return Response.foundEntity(pagedList,  propertiesCollection.getPathProperties(collection));
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }


}
