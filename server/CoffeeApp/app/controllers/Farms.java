package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.*;
import io.ebean.Ebean;
import io.ebean.PagedList;
import models.Farm;
import controllers.responseUtils.PropertiesCollection;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.HSecurity;

import javax.inject.Inject;

/**
 * Created by darwin on 30/08/17.
 * modify sm21 2018
 */
public class Farms extends Controller {

    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    @HSecurity
    public Result create() {
        try {
            JsonNode request = request().body().asJson();
            if (request == null)
                return ResponseN.requiredJson();

            Form<Farm> form = formFactory.form(Farm.class).bind(request);
            if (form.hasErrors())
                return ResponseN.invalidParameter(form.errorsAsJson());

            Farm farm = form.get();
            farm.insert();

            return ResponseN.createdEntity(Json.toJson(farm));
        } catch (Exception e) {
            return NsExceptionsUtils.create(e);
        }
    }

    @HSecurity
    public Result update(Long id) {
        try {
            JsonNode request = request().body().asJson();
            if (request == null)
                return ResponseN.requiredJson();

            Form<Farm> form = formFactory.form(Farm.class).bind(request);
            if (form.hasErrors())
                return ResponseN.invalidParameter(form.errorsAsJson());

            Farm farm = Json.fromJson(request, Farm.class);
            farm.setId(id);
            farm.update();

            return ResponseN.updatedEntity(Json.toJson(farm));
        } catch (Exception e) {
            return NsExceptionsUtils.update(e);
        }
    }

    @HSecurity
    public Result delete(Long id) {
        try {
            Ebean.delete(Farm.findById(id));
            return ResponseN.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

    @HSecurity
    public Result deletes() {
        try {
            JsonNode json = request().body().asJson();
            if (json == null)
                return ResponseN.requiredJson();

            Ebean.deleteAll(Farm.class, JsonUtils.toArrayLong(json, "ids"));

            return ResponseN.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

    @HSecurity
    public Result findById(Long id) {
        try {
            Farm farm = Farm.findById(id);
            return ResponseN.foundEntity(Json.toJson(farm));
        } catch (Exception e) {
            return NsExceptionsUtils.find(e);
        }
    }


    @HSecurity
    public Result findAll( Integer index, Integer size, String collection,
                           String name, String sort, Long status, boolean deleted){
        try {
//            ListPagerCollection listPager = Farm.findAll(index, size, propertiesCollection.getPathProperties(collection),
            PagedList pagedList = Farm.findAll(index, size, propertiesCollection.getPathProperties(collection),
                                                            name,  sort, status, deleted);

            return ResponseN.foundEntity(pagedList, propertiesCollection.getPathProperties(collection));
        }catch(Exception e){
            return NsExceptionsUtilsN.find(e);
        }
    }
}


//        url
//        https:localhost:9000
//        https:localhost:9000
//        urll
//        http://localhost:9000
//        http://localhost:9000
//        user
//        shamuel.manrrique@hecticus.com
//        shamuel.manrrique@hecticus.com
//        password
//        root
//        root
//        web
//        web_site
//        web_site
//        secret
//        hola