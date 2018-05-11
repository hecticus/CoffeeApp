package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.ListPagerCollection;
import io.ebean.text.PathProperties;
import models.Store;
import models.responseUtils.ExceptionsUtils;
import models.responseUtils.PropertiesCollection;
import models.responseUtils.Response;
import models.responseUtils.ResponseCollection;
import play.libs.Json;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import java.util.List;

import static play.mvc.Controller.request;

/**
 * Created by drocha on 30/05/17.
 */
public class Stores {

    private static Store storeDao = new Store();
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public Stores(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

    @CoffeAppsecurity
    public Result create() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();


            JsonNode Name = json.get("nameStore");
            if (Name == null)
                return Response.requiredParameter("nameStore");

            int registered = storeDao.getExist(Name.asText().toUpperCase());
            if(registered==0) return  Response.messageExist("nameStore");
            if(registered==1) return  Response.messageExistDeleted("nameStore");

            JsonNode status = json.get("statusStore");
            if (status == null)
                return Response.requiredParameter("statusStore");




            // mapping object-json
            Store store = Json.fromJson(json, Store.class);


            store.save();
            return Response.createdEntity(Json.toJson(store));

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

            JsonNode id = json.get("idStore");
            if (id == null)
                return Response.requiredParameter("idStore");

            Store store =  Json.fromJson(json, Store.class);

            JsonNode Name = json.get("nameStore");
            if (Name != null)
            {
                int registered = storeDao.getExist(Name.asText().toUpperCase());
                if(registered==0) return  Response.messageExist("nameStore");
                if(registered==1) return  Response.messageExistDeleted("nameStore");

                store.setNameStore(Name.asText().toUpperCase());
            }


            store.update();
            return Response.updatedEntity(Json.toJson(store));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Store store = storeDao.findById(id);
            if(store != null) {

                store.setStatusDelete(1);
                store.update();

                return Response.deletedEntity();
            } else {
                return  Response.message("Successful no existe el registro a eliminar");
            }
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }
    /*public Result delete(Long id) {
        try {

            Store store = findById(id);
            //    storeDao.delete(id);
            return Response.deletedEntity();

        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }*/

    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            Store store = storeDao.findById(id);
            return Response.foundEntity(Response.toJson(store, Store.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

 /*   @CoffeAppsecurity
    public Result findAll(Integer index, Integer size) {
        try {
            List<Store> stores = storeDao.findAll(index, size);
            return Response.foundEntity(Json.toJson(stores));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }*/

    public Result getByStatusStore(String statusStore, String order)
    {
        String strOrder = "ASC";
        try {

            if(!order.equals("-1")) strOrder = order;

            if(!strOrder.equals("ASC") && !strOrder.equals("DESC"))
                return Response.requiredParameter("order (ASC o DESC)");


            List<Store> stores = storeDao. getByStatusStore(statusStore,strOrder);
            return Response.foundEntity(Json.toJson(stores));

        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @CoffeAppsecurity
    public Result findAll(Integer index, Integer size, String sort, String collection) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = storeDao.findAll(index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

    @CoffeAppsecurity
    public Result findAllSearch(String name, Integer index, Integer size, String sort, String collection) {
        try {

            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = storeDao.findAllSearch(name, index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


    @CoffeAppsecurity
    public Result preCreate() {


        try {
            Store store = new Store();
            return Response.foundEntity(
                    Json.toJson(store));
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }
}
