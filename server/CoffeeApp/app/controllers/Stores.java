package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.responseUtils.Response;
import controllers.utils.ListPagerCollection;
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


//    @CoffeAppsecurity
    public Result preCreate() {
        try {
            Store store = new Store();
            return Response.foundEntity(
                    Json.toJson(store));
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

//    @CoffeAppsecurity
    public Result create() {
        try {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            Form<Store> form = formFactory.form(Store.class).bind(json);
            if(form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            // mapping object-json
            Store store = Json.fromJson(json, Store.class);
            store.save();
            return Response.createdEntity(Json.toJson(store));
        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

//    @CoffeAppsecurity
    public Result update() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            JsonNode id = json.get("idStore");
            if (id == null)
                return Response.requiredParameter("idStore");

            Form<Store> form = formFactory.form(Store.class).bind(json);
            if(form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            // mapping object-json
            Store store = Json.fromJson(json, Store.class);
            store.update();
            return Response.updatedEntity(Json.toJson(store));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

//    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Store store = Store.findById(id);
//            store.setStatusDelete(1);
            store.update();
            return Response.deletedEntity();
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }

//    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            Store store = Store.findById(id);
            return Response.foundEntity(Response.toJson(store, Store.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }


    public Result getByStatusStore(String statusStore, String order)
    {
        String strOrder = "ASC";
        try {

            if(!order.equals("-1")) strOrder = order;

            if(!strOrder.equals("ASC") && !strOrder.equals("DESC"))
                return Response.requiredParameter("order (ASC o DESC)");


            List<Store> stores = Store.getByStatusStore(statusStore,strOrder);
            return Response.foundEntity(Json.toJson(stores));

        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    //@CoffeAppsecurity
    public Result findAll(String name, Integer index, Integer size, String sort, String collection,  Integer status) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = Store.findAll(name, index, size, sort, pathProperties, status);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


}
