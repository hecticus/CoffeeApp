package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.utils.ListPagerCollection;
import controllers.utils.NsExceptionsUtils;
import io.ebean.Ebean;
import io.ebean.text.PathProperties;
import models.InvoiceDetail;
import models.Purity;
import controllers.responseUtils.ExceptionsUtils;
import controllers.responseUtils.PropertiesCollection;
import controllers.responseUtils.Response;
import controllers.responseUtils.ResponseCollection;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import javax.inject.Inject;
import java.util.List;


/**
 * Created by sm21 on 10/05/18.
 */
public class Purities extends Controller{

    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public Purities(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

    @CoffeAppsecurity
    public Result preCreate() {
        try {
            return Response.foundEntity(Json.toJson(new Purity()));
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

    @CoffeAppsecurity
    public Result create() {
        try {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            Form<Purity> form = formFactory.form(Purity.class).bind(json);
            if (form.hasErrors()){
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());
            }

            // mapping object-json
            Purity purity = Json.fromJson(json, Purity.class);
            purity.save();
            return Response.createdEntity(Json.toJson(purity));
        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

    @CoffeAppsecurity
    public Result update(Long id) {
        try {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            Form<Purity> form = formFactory.form(Purity.class).bind(json);
            if (form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            Purity purity = Json.fromJson(json, Purity.class);
            purity.setId(id);
            purity.update();
            return Response.updatedEntity(Json.toJson(purity));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Ebean.delete(Purity.findById(id));
            return Response.deletedEntity();
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }

    @CoffeAppsecurity
    public Result deletes() {
        try {
            JsonNode json = request().body().asJson();
            if (json == null)
                return controllers.utils.Response.requiredJson();

            Ebean.deleteAll(Purity.class, controllers.utils.JsonUtils.toArrayLong(json, "ids"));

            return controllers.utils.Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            return Response.foundEntity(Json.toJson(Purity.findById(id)));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @CoffeAppsecurity
    public Result findAll(Integer index, Integer size, String collection, String sort, String name, boolean deleted){
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = Purity.findAll(index, size, pathProperties, sort, name, deleted);
            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


}