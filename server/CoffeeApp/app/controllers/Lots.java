package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.utils.ListPagerCollection;
import io.ebean.Ebean;
import io.ebean.text.PathProperties;
import models.Farm;
import models.InvoiceDetail;
import models.Lot;
import controllers.requestUtils.Request;
import controllers.responseUtils.*;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sm21 on 10/05/18.
 */
public class Lots extends Controller {

    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public Lots(){
        propertiesCollection.putPropertiesCollection("s", "(idLot, nameLot)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

    //@CoffeAppsecurity
    public Result preCreate() {
        try {
            Farm farm = new Farm();
            Lot lot = new Lot();
            lot.setFarm(farm);

            return Response.foundEntity(
                    Json.toJson(lot));
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

    //@CoffeAppsecurity
    public Result create() {
        try {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(json.toString());
            node.set("nameLot", json.findValue("name"));
            node.set("priceLot", json.findValue("price_lot"));

            //farm
            ObjectNode farmNode = Json.newObject();
            farmNode.set("idFarm", json.findValue("farm"));
            node.set("farm", farmNode);

            Form<Lot> form = formFactory.form(Lot.class).bind(node);
            if(form.hasErrors())
                return badRequest(form.errorsAsJson());

            Lot lot = Json.fromJson(node, Lot.class);
            lot.save();
            return  Response.updatedEntity(Json.toJson(lot));

        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

//    @CoffeAppsecurity
    public Result update() {
        try{
            JsonNode json = request().body().asJson();
            if(json == null)
                return badRequest("Expecting Json data");

            Long id = json.get("idLot").asLong();
            if (id == null )
                return badRequest("Missing parameter idLot");

            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(json.toString());
            node.set("nameLot", json.findValue("name"));
            node.set("priceLot", json.findValue("price_lot"));
            ObjectNode farmNode = Json.newObject();
            farmNode.set("idFarm", json.findValue("farm"));
            node.set("farm", farmNode);

            Form<Lot> form = formFactory.form(Lot.class).bind(node);
            if(form.hasErrors())
                return badRequest(form.errorsAsJson());

            Lot lot = Json.fromJson(node, Lot.class);
            lot.setIdLot(id);
            lot.update();
            return  Response.updatedEntity(Json.toJson(lot));
        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

//    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Ebean.delete(Lot.findById(id));
            return Response.deletedEntity();
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }

//    @CoffeAppsecurity
    public Result deletes() {
        try {
           Ebean.deleteAll(Lot.finder.query().findList());
            return Response.deletedEntity();
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

//    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            Lot lot = Lot.findById(id);
            return Response.foundEntity(Response.toJson(lot, Lot.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    //    @CoffeAppsecurity
    public Result findAll( Integer pageIndex, Integer pageSize, String collection, String sort,
                           String name, Long idFarm, Integer status, boolean deleted){
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = Lot.findAll( pageIndex, pageSize, pathProperties, sort, name, idFarm,
                                                        status, deleted);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }



}
