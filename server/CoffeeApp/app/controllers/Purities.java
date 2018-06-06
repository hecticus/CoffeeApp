package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.utils.ListPagerCollection;
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

    //    @CoffeAppsecurity
    public Result preCreate() {
        try {
            return Response.foundEntity(Json.toJson(new Purity()));
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

            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(json.toString());
            node.set("NamePurity", json.findValue("namePurity"));
            Form<Purity> form = formFactory.form(Purity.class).bind(node);
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

//    @CoffeAppsecurity
    public Result update() {
        try {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            JsonNode id = json.get("idPurity");
            if (id == null)
                return Response.requiredParameter("idPurity");

            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(json.toString());
            node.set("NamePurity", json.findValue("namePurity"));
            Form<Purity> form = formFactory.form(Purity.class).bind(node);
            if (form.hasErrors()){
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());
            }

            // mapping object-json
            Purity purity = Json.fromJson(json, Purity.class);
            purity.setIdPurity(id.asLong());
            purity.update();
            return Response.updatedEntity(Json.toJson(purity));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

//    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Purity purity = Purity.findById(id);
//            purity.setStatusDelete(1);
            purity.update();
            return Response.deletedEntity();
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }

//    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            return Response.foundEntity(Json.toJson( Purity.findById(id)));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    public Result getByNamePurity(String NamePurity, String order)
    {
        String strOrder = "ASC";
        try {

            if (NamePurity.equals("-1")) NamePurity = "";

            if(!order.equals("-1")) strOrder = order;

            if(!strOrder.equals("ASC") && !strOrder.equals("DESC"))
                return Response.requiredParameter("order (ASC o DESC)");

            if(NamePurity.equals(""))
                return Response.message("Falta el atributo [name]");

            List<Purity> purities = Purity.getByNamePurity(NamePurity,strOrder);
            return Response.foundEntity(Json.toJson(purities));

        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    public Result getByStatusPurity(String StatusPurity, String order)
    {
        String strOrder = "ASC";
        try {

            if(!order.equals("-1")) strOrder = order;

            if(!strOrder.equals("ASC") && !strOrder.equals("DESC"))
                return Response.requiredParameter("order (ASC o DESC)");


            List<Purity> purities = Purity.getByStatusPurity(StatusPurity,strOrder);
            return Response.foundEntity(Json.toJson(purities));

        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    //@CoffeAppsecurity
    public Result findAll(String name, Integer index, Integer size, String sort, String collection,  Integer status){
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = Purity.findAll(name, index, size, sort, pathProperties, status);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

    @CoffeAppsecurity
    public Result findAllSearch(String name, Integer index, Integer size, String sort, String collection) {
        try {

            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = Purity.findAllSearch(name, index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

}