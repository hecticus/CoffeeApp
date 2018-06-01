package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.utils.ListPagerCollection;
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
    
    private static Lot lotDao = new Lot();
    private static Farm farmDao = new Farm();
    private static InvoiceDetail invoiceDetailDao = new InvoiceDetail();
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

            if (json.get("idLot") == null )
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
            lot.update();
            return  Response.updatedEntity(Json.toJson(lot));
        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

//    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Lot lot = Lot.findById(id);

            List<InvoiceDetail> invoiceDetails = InvoiceDetail.getOpenByLotId(id);
            if(lot != null  && invoiceDetails.size()==0) {

                lot.setStatusDelete(1);
                lot.update();// = lotDao.update(lot);

                return Response.deletedEntity();
            } else {

                if(lot == null)  return  Response.messageNotDeleted("no existe el registro a eliminar");
                else  return  Response.messageNotDeleted("el registro tiene facturas aun no cerradas");
            }
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }

//    @CoffeAppsecurity
    public Result deletes() {
        boolean aux_delete = true;
        try {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            List<Long> aux = JsonUtils.toArrayLong(json, "ids");
            for (Long id : aux) {
                Lot lot = Lot.findById(id);
                List<InvoiceDetail> invoiceDetails = InvoiceDetail.getOpenByLotId(id);
                if(lot != null  && invoiceDetails.size()==0) {
                    lot.setStatusDelete(1);
                    lot.update();

                    return Response.deletedEntity();
                } else {
                    aux_delete = false;

                }

            }

            if(aux_delete)  return  Response.messageNotDeleted("La eliminacion fue correcta");
            else  return  Response.messageNotDeleted(" algunos registros tiene facturas aun no cerradas");
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
    public Result findAll(String name, Integer pageindex, Integer pagesize, String sort, String collection, Integer all, Long idFarm, Integer statusLot) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = lotDao.findAll(name, pageindex, pagesize, sort, pathProperties, all, idFarm.intValue());//, statusLot);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

    public Result getByNameLot(String NameLot, String order)
    {
        String strOrder = "ASC";
        try {

            if (NameLot.equals("-1")) NameLot = "";

            if(!order.equals("-1")) strOrder = order;

            if(!strOrder.equals("ASC") && !strOrder.equals("DESC"))
                return Response.requiredParameter("order (ASC o DESC)");

            if(NameLot.equals(""))
                return Response.message("Falta el atributo [name]");

            List<Lot> lots = Lot.getByNameLot(NameLot,strOrder);
            return Response.foundEntity(Json.toJson(lots));

        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    public Result getByStatusLot(String StatusLot, String order)
    {
        String strOrder = "ASC";
        try {

            if(!order.equals("-1")) strOrder = order;

            if(!strOrder.equals("ASC") && !strOrder.equals("DESC"))
                return Response.requiredParameter("order (ASC o DESC)");


            List<Lot> lots = Lot.getByStatusLot(StatusLot,strOrder);
            return Response.foundEntity(Json.toJson(lots));

        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }


//    @CoffeAppsecurity
    public  Result getByIdFarm(Long idFarm, Integer index, Integer size, String sort, String collection)
    {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = Lot.getByIdFarm(idFarm, index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

}
