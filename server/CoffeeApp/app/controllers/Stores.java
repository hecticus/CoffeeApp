package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.responseUtils.Response;
import controllers.utils.ListPagerCollection;
import io.ebean.Ebean;
import io.ebean.text.PathProperties;
import models.Store;
import controllers.responseUtils.ExceptionsUtils;
import controllers.responseUtils.PropertiesCollection;
import controllers.responseUtils.ResponseCollection;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import javax.inject.Inject;
import java.util.List;

import static play.mvc.Controller.request;

/**
 * Created by drocha on 30/05/17.
 */
public class Stores {

    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public Stores(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }


////@CoffeAppsecurity
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
            return Response.responseExceptionCreated(e);
        }
    }

////@CoffeAppsecurity
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
            return Response.responseExceptionUpdated(e);
        }
    }

////@CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Ebean.delete(Store.findById(id));
            return Response.deletedEntity();
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }

////@CoffeAppsecurity
    public Result findById(Long id) {
        try {
            return Response.foundEntity(Response.toJson(Store.findById(id), Store.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    //@CoffeAppsecurity
    public Result findAll(Integer index, Integer size, String collection,
                          String sort, String name, Integer status, boolean deleted){
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = Store.findAll(index, size, pathProperties, sort, name, status, deleted);
            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


}
