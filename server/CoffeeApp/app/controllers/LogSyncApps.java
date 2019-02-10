package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.PropertiesCollection;
import controllers.utils.Response;
import io.ebean.Ebean;
import io.ebean.PagedList;
import models.LogSyncApp;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import javax.inject.Inject;


public class LogSyncApps extends Controller {

    @Inject
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    @CoffeAppsecurity
    public Result create() {
        try{
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            LogSyncApp logSyncApp = Json.fromJson(json, LogSyncApp.class);

            logSyncApp.save();
            return  Response.createdEntity(Json.toJson(logSyncApp));

        }catch(Exception e){
            return NsExceptionsUtils.create(e);
        }
    }

    @CoffeAppsecurity
    public  Result update(Long id) {
        try {
            JsonNode json = request().body().asJson();
            if(json== null)
                return badRequest("Expecting Json data");

            LogSyncApp logSyncApp = Json.fromJson(json, LogSyncApp.class);
            logSyncApp.setId(id);
            logSyncApp.update();
            return Response.updatedEntity(Json.toJson(logSyncApp));
        }catch(Exception e){
            return NsExceptionsUtils.update(e);
        }
    }

    @CoffeAppsecurity
    public  Result delete(Long id) {
        try{
            Ebean.delete(LogSyncApp.findById(id));
            return controllers.utils.Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            LogSyncApp logSyncApp = LogSyncApp.findById(id);
            return controllers.utils.Response.foundEntity(Json.toJson(logSyncApp));
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }


    @CoffeAppsecurity
    public Result findAll(){
        try {
            PagedList pagedList = LogSyncApp.findAll();

            return Response.foundEntity(pagedList, propertiesCollection.getPathProperties(null));
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }



}
