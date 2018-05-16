package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import controllers.parsers.queryStringBindable.Pager;
import controllers.utils.JsonUtils;
import controllers.utils.ListPagerCollection;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.Response;
import io.ebean.Ebean;
import io.ebean.text.PathProperties;
import models.Farm;
import models.responseUtils.ExceptionsUtils;
import models.responseUtils.PropertiesCollection;
import models.responseUtils.ResponseCollection;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import javax.inject.Inject;

/**
 * Created by darwin on 30/08/17.
 */
public class Farms extends Controller {

    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public Farms(){
        propertiesCollection.putPropertiesCollection("s", "(idFarm, NameFarm)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

    @CoffeAppsecurity
    public Result preCreate() {
        try {

            return  null;
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }


    @CoffeAppsecurity
    public Result create() {
        try {
            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            Form<Farm> form = formFactory.form(Farm.class).bind(request);
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());

            Farm farm = form.get();
            farm.insert();

            return Response.createdEntity(Json.toJson(farm));
        } catch (Exception e) {
            return NsExceptionsUtils.create(e);
        }
    }

    @CoffeAppsecurity
    public Result update(Long id) {
        try {
            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            Form<Farm> form = formFactory.form(Farm.class).bind(request);
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());

            Farm farm = Json.fromJson(request, Farm.class);
            farm.setIdFarm(id);
            farm.update();

            return Response.updatedEntity(Json.toJson(farm));
        } catch (Exception e) {
            return NsExceptionsUtils.update(e);
        }
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        try {
            Ebean.delete(Farm.class, id);
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
                return Response.requiredJson();

            Ebean.deleteAll(Farm.class, JsonUtils.toArrayLong(json, "ids"));

            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            Farm farm = Farm.findById(id);
            return Response.foundEntity(Json.toJson(farm));
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }


    @CoffeAppsecurity
    public Result findAllSearch(String name, Pager pager, String sort, String collection) {
        return findAll(name,pager, sort, collection);
    }

    @CoffeAppsecurity
    public Result findAll(String name, Pager pager, String sort, String collection){
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = Farm.findAll(name, pager, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }



//    @CoffeAppsecurity
//    public Result findAllSearch(String name, Pager pager, String sort, String collection) {
//        try {
//            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
//            PagedList listPager = Farm.findAll(name, pager, sort, pathProperties);
//
//            return ResponseCollection.foundEntity(listPager, pathProperties);
//        }catch(Exception e){
//            return ExceptionsUtils.find(e);
//        }
//    }


}
