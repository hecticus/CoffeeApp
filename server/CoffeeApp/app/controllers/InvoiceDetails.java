package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.utils.ListPagerCollection;
import io.ebean.Ebean;
import io.ebean.text.PathProperties;
import models.*;
import controllers.responseUtils.ExceptionsUtils;
import controllers.responseUtils.PropertiesCollection;
import controllers.responseUtils.Response;
import controllers.responseUtils.ResponseCollection;
import controllers.responseUtils.responseObject.InvoiceDetailShortResponse;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;


/**
 * Created by sm21 on 10/05/18.
 */
public class InvoiceDetails extends Controller {

    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public InvoiceDetails(){
        propertiesCollection.putPropertiesCollection("s", "(*)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

////@CoffeAppsecurity
    public Result preCreate() {
        try {
            InvoiceDetail invoiceDetail = new InvoiceDetail();
            Invoice invoice = new Invoice();
            ItemType itemType = new ItemType ();
            Store store = new Store();
            Lot lot = new Lot();

            invoiceDetail.setInvoice(invoice);
            invoiceDetail.setItemType(itemType);
            invoiceDetail.setStore(store);
            invoiceDetail.setLot(lot);

            return Response.foundEntity(
                    Json.toJson(invoiceDetail));
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

////@CoffeAppsecurity
    public Result create() {
        try {
            JsonNode json = request().body().asJson();
            if(json== null)
                return Response.requiredJson();

            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(json.toString());
            //invoice
            ObjectNode invoiceNode = Json.newObject();
            invoiceNode.set("idInvoice", json.findValue("id_invoice"));
            node.set("invoice", invoiceNode);

            //itemType
            ObjectNode itemTypeNode = Json.newObject();
            itemTypeNode.set("idItemType", json.findValue("id_itemType"));
            node.set("itemType", itemTypeNode);

            //lot
            ObjectNode lotNode = Json.newObject();
            lotNode.set("idLot", json.findValue("id_lot"));
            node.set("lot", lotNode);

            //store
            ObjectNode storeNode = Json.newObject();
            storeNode.set("idStore", json.findValue("id_store"));
            node.set("store", storeNode);

            Form<InvoiceDetail> form = formFactory.form(InvoiceDetail.class).bind(node);
            if (form.hasErrors()){
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());
            }

            InvoiceDetail invoiceDetail = Json.fromJson(node, InvoiceDetail.class);
            invoiceDetail.save();
            return  Response.createdEntity(Json.toJson(invoiceDetail));
        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

////@CoffeAppsecurity
    public Result update() {
        try {
            JsonNode json = request().body().asJson();
            if(json== null)
                return Response.requiredJson();

            JsonNode id = json.get("idInvoiceDetail");
            if (id == null )
                return Response.invalidParameter("Missing parameter idInvoiceDetail");

            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(json.toString());
            //invoice
            ObjectNode invoiceNode = Json.newObject();
            invoiceNode.set("idInvoice", json.findValue("id_invoice"));
            node.set("invoice", invoiceNode);

            //itemType
            ObjectNode itemTypeNode = Json.newObject();
            itemTypeNode.set("idItemType", json.findValue("id_itemType"));
            node.set("itemType", itemTypeNode);

            //lot
            ObjectNode lotNode = Json.newObject();
            lotNode.set("idLot", json.findValue("id_lot"));
            node.set("lot", lotNode);

            //store
            ObjectNode storeNode = Json.newObject();
            storeNode.set("idStore", json.findValue("id_store"));
            node.set("store", storeNode);

            Form<InvoiceDetail> form = formFactory.form(InvoiceDetail.class).bind(node);
            if (form.hasErrors()){
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());
            }

            InvoiceDetail invoiceDetail = Json.fromJson(node, InvoiceDetail.class);
            invoiceDetail.update();

            return  Response.createdEntity(Json.toJson(invoiceDetail));
        }catch(Exception e) {
            return Response.responseExceptionUpdated(e);
        }

    }

////@CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Ebean.delete(InvoiceDetail.findById(id));
            return Response.deletedEntity();
        } catch (Exception e) {
            return Response.responseExceptionUpdated(e);
        }
    }

//@CoffeAppsecurity
    public Result deleteAllByIdInvoiceAndDate( Long IdInvoice, String  date) {
        try {
            List invoiceDetails = InvoiceDetail.findAll(null, null, null, null,
                    IdInvoice, null, null,null, null,
                    date, null, false).entities;
            Ebean.deleteAll((List<InvoiceDetail>) invoiceDetails);
            return Response.deletedEntity();
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    //@CoffeAppsecurity
    public Result findById(Long id) {
        try {
            InvoiceDetail invoiceDetail = InvoiceDetail.findById(id);
            return Response.foundEntity(Response.toJson(invoiceDetail, InvoiceDetail.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }



////@CoffeAppsecurity
    public Result findAll(Integer pageIndex, Integer pageSize, String collection, String sort,
                          Long invoice, Long itemType, Long lot, Long store, String nameReceivedInvoiceDetail,
                          String startDateInvoiceDetail, Long status, boolean deleted){
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = InvoiceDetail.findAll(pageIndex, pageSize, pathProperties, sort,
                    invoice, itemType, lot,store, nameReceivedInvoiceDetail,
                     startDateInvoiceDetail, status, deleted);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


}
