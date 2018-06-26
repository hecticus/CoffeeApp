package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.JsonUtils;
import controllers.utils.ListPagerCollection;
import controllers.utils.NsExceptionsUtils;
import controllers.utils.Response;
import io.ebean.Ebean;
import io.ebean.text.PathProperties;
import models.Farm;
import controllers.responseUtils.ExceptionsUtils;
import controllers.responseUtils.PropertiesCollection;
import controllers.responseUtils.ResponseCollection;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import javax.inject.Inject;

/**
 * Created by darwin on 30/08/17.
 * modify sm21 2018
 */
public class Farms extends Controller {

    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public Farms(){
        propertiesCollection.putPropertiesCollection("s", "(idFarm, NameFarm)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

//@CoffeAppsecurity
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

//@CoffeAppsecurity
    public Result update(Long id) {
        try {
            JsonNode request = request().body().asJson();
            if (request == null)
                return Response.requiredJson();

            Form<Farm> form = formFactory.form(Farm.class).bind(request);
            if (form.hasErrors())
                return Response.invalidParameter(form.errorsAsJson());

            Farm farm = Json.fromJson(request, Farm.class);
            farm.setId(id);
            farm.update();

            return Response.updatedEntity(Json.toJson(farm));
        } catch (Exception e) {
            return NsExceptionsUtils.update(e);
        }
    }

//@CoffeAppsecurity
    public Result delete(Long id) {
        try {
            Ebean.delete(Farm.findById(id));
            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

//@CoffeAppsecurity
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

//@CoffeAppsecurity
    public Result findById(Long id) {
        try {
            Farm farm = Farm.findById(id);
            return Response.foundEntity(Json.toJson(farm));
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }


//@CoffeAppsecurity
    public Result findAll( Integer index, Integer size, String collection,
                           String name, String sort, Long status, boolean deleted){
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = Farm.findAll(index, size, pathProperties, name,  sort, status, deleted);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

}
