package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.ListPagerCollection;
import controllers.utils.NsExceptionsUtils;
import io.ebean.Ebean;
import io.ebean.text.PathProperties;
import models.Lot;
import controllers.responseUtils.*;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.HSecurity;

import javax.inject.Inject;

/**
 * Created by sm21 on 10/05/18.
 */
public class Lots extends Controller {

    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    @HSecurity
    public Result create() {
        try {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            Form<Lot> form = formFactory.form(Lot.class).bind(json);
            if(form.hasErrors())
                return badRequest(form.errorsAsJson());

            Lot lot = Json.fromJson(json, Lot.class);
            lot.save();
            return  Response.createdEntity(Json.toJson(lot));

        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

    @HSecurity
    public Result update(Long id) {
        try{
            JsonNode json = request().body().asJson();
            if(json == null)
                return badRequest("Expecting Json data");

            Form<Lot> form = formFactory.form(Lot.class).bind(json);
            if(form.hasErrors())
                return badRequest(form.errorsAsJson());

            Lot lot = Json.fromJson(json, Lot.class);
            lot.setId(id);
            lot.update();
            return  Response.updatedEntity(Json.toJson(lot));
        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }


    @HSecurity
    public Result delete(Long id) {
        try{
            Ebean.delete(Lot.findById(id));
            return Response.deletedEntity();
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }

    @HSecurity
    public Result deletes() {
        try {
            JsonNode json = request().body().asJson();
            if (json == null)
                return controllers.utils.Response.requiredJson();

            Ebean.deleteAll(Lot.class, controllers.utils.JsonUtils.toArrayLong(json, "ids"));

            return controllers.utils.Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

    @HSecurity
    public Result findById(Long id) {
        try {
            return Response.foundEntity(Json.toJson(Lot.findById(id)));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @HSecurity
    public Result findAll( Integer pageIndex, Integer pageSize, String collection, String sort,
                           String name, Long idFarm, Long status, boolean deleted){
        try {
            ListPagerCollection listPager = Lot.findAll( pageIndex, pageSize,  propertiesCollection.getPathProperties(collection), sort, name, idFarm,
                                                        status, deleted);

            return ResponseCollection.foundEntity(listPager,  propertiesCollection.getPathProperties(collection));
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }



}
