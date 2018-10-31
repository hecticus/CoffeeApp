package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.responseUtils.ExceptionsUtils;
import controllers.responseUtils.PropertiesCollection;
import controllers.responseUtils.Response;
import controllers.responseUtils.ResponseCollection;
import controllers.utils.ListPagerCollection;
import controllers.utils.NsExceptionsUtils;
import io.ebean.Ebean;
import models.LogSyncApp;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import javax.inject.Inject;


public class LogSyncApps extends Controller {

    @Inject
    private FormFactory formFactory;
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
            return Response.responseExceptionCreated(e);
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
            return Response.responseExceptionUpdated(e);
        }
    }

    @CoffeAppsecurity
    public  Result delete(Long id) {
        try{
            Ebean.delete(LogSyncApp.findById(id));
            return controllers.utils.Response.deletedEntity();
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
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
            ListPagerCollection listPager = LogSyncApp.findAll();

            return ResponseCollection.foundEntity(listPager, propertiesCollection.getPathProperties(null));
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }



}
