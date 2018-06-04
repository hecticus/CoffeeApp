package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.utils.ListPagerCollection;
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

//    @CoffeAppsecurity
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

//    @CoffeAppsecurity
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


//            JsonNode id_invoice = json.get("id_invoice");
//            if (id_invoice==  null)
//                return Response.requiredParameter("id_invoice");
//
//
//            JsonNode id_itemType = json.get("id_itemType");
//            if (id_itemType== null)
//                return Response.requiredParameter("id_itemType");
//
//
//            JsonNode id_lot = json.get("id_lot");
//            if (id_lot== null)
//                return Response.requiredParameter("id_lot");
//
//            JsonNode priceItemTypeByLot = json.get("priceItemTypeByLot");
//            if (priceItemTypeByLot==  null)
//                return Response.requiredParameter("priceItemTypeByLot");
//
//            JsonNode amount = json.get("amountInvoiceDetail");
//            if (amount== null)
//                return Response.requiredParameter("amountInvoiceDetail");
//
//            JsonNode freight = json.get("freightInvoiceDetail");
//            if (freight==  null)
//                return Response.requiredParameter("freightInvoiceDetail");
//
//            JsonNode nameReceived = json.get("nameReceivedInvoiceDetail");
//            if (nameReceived==  null)
//                return Response.requiredParameter("nameReceivedInvoiceDetail");
//
//            JsonNode nameDelivered = json.get("nameDeliveredInvoiceDetail");
//            if (nameDelivered==  null)
//                return Response.requiredParameter("nameDeliveredInvoiceDetail");
//
//            JsonNode startDate =  Request.removeParameter(json, "startDateInvoiceDetail");;
//            if (startDate==  null)
//                return Response.requiredParameter("startDateInvoiceDetail");
//
//            System.out.println("hola/////////");
//            // mapping object-json
//            InvoiceDetail invoiceDetail = Json.fromJson(json, InvoiceDetail.class);
//            System.out.println(("chao---------------------"));
//
//            JsonNode id_store = json.get("id_store");
//            if (id_store != null)
//                invoiceDetail.setStore(Store.findById(id_store.asLong()));
//
//            System.out.println("/////////////////////////////");
//            invoiceDetail.setInvoice(Invoice.findById(id_invoice.asLong()));
//            System.out.println("/////////////////////////////"+invoiceDetail.getInvoice().getIdInvoice());
//            invoiceDetail.setItemType(ItemType.findById(id_itemType.asLong()));
//            System.out.println("/////////////////////////////"+invoiceDetail.getItemType().getNameItemType());
//            invoiceDetail.setLot(Lot.findById(id_lot.asLong()));
//            System.out.println("/////////////////////////////"+invoiceDetail.getLot().getNameLot());
//
//            System.out.println();
//            DateTime startDatetime = Request.dateTimeFormatter.parseDateTime(startDate.asText());
////            DateTime startDatetime =  Request.dateTimeFormatter.parseDateTime(startDate.asText());
//            System.out.println(startDate.toString());
//
//            invoiceDetail.setStartDateInvoiceDetail(startDatetime);
//
//            invoiceDetail.save();// = invoiceDetailDao.create(invoiceDetail);
//            //  return Response.createdEntity(Response.toJson(invoiceDetail, InvoiceDetail.class));
//            System.out.println(invoiceDetail.getIdInvoiceDetail());
//            return  Response.createdEntity(Json.toJson(invoiceDetail));
            return  Response.createdEntity(Json.toJson(invoiceDetail));
        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

//    @CoffeAppsecurity
    public Result update() {
        try {
            JsonNode json = request().body().asJson();
            if(json== null)
                return Response.requiredJson();

            JsonNode id = json.get("idInvoiceDetail");
            if (id == null )
                return Response.invalidParameter("Missing parameter idInvoice");

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

    //        JsonNode id = json.get("idInvoiceDetail");
    //        if (id == null )
    //            return badRequest("Missing parameter idInvoiceDetail");
    //
    //        if(!InvoiceDetail.existId(id.asLong()))
    //            return badRequest("There is no InvoiceDetail with id");
    //
    //        try{
    //            InvoiceDetail invoiceDetail = InvoiceDetail.findById(id.asLong());
    //
    //            System.out.println(invoiceDetail.getNameReceivedInvoiceDetail());
    //
    //            JsonNode priceItemTypeByLot = json.findValue("priceItemTypeByLot");
    //            if (priceItemTypeByLot!= null)
    //                invoiceDetail.setPriceItemTypeByLot(priceItemTypeByLot.decimalValue());
    //
    //            JsonNode amount = json.findValue("amountInvoiceDetail");
    //            if (amount!= null)
    //                invoiceDetail.setAmountInvoiceDetail(amount.decimalValue());
    //
    //            JsonNode freight = json.findValue("freightInvoiceDetail");
    //            if (freight!= null)
    //                invoiceDetail.setFreightInvoiceDetail(freight.asBoolean());
    //
    //            JsonNode nameReceived = json.findValue("nameReceivedInvoiceDetail");
    //            if (nameReceived!= null)
    //                invoiceDetail.setNameReceivedInvoiceDetail(nameReceived.asText());
    //
    //            JsonNode nameDelivered = json.findValue("nameDeliveredInvoiceDetail");
    //            if (nameDelivered!= null)
    //                invoiceDetail.setNameDeliveredInvoiceDetail(nameDelivered.asText());
    //
    //            JsonNode startDate =  Request.removeParameter(json, "startDateInvoiceDetail");
    //            if (startDate != null){
    //                DateTime startDatetime =  Request.dateTimeFormatter.parseDateTime(startDate.asText());
    //                invoiceDetail.setStartDateInvoiceDetail(startDatetime);
    //            }
    //
    //            JsonNode id_invoice = json.findValue("id_invoice");
    //            if (id_invoice != null)
    //                invoiceDetail.setInvoice(Invoice.findById(id_invoice.asLong()));
    //
    //            JsonNode id_itemType = json.findValue("id_itemType");
    //            if (id_itemType != null)
    //                invoiceDetail.setItemType(ItemType.findById(id_itemType.asLong()));
    //
    //            JsonNode id_lot = json.findValue("id_lot");
    //            if (id_lot != null)
    //                invoiceDetail.setLot(Lot.findById(id_lot.asLong()));
    //
    //            JsonNode id_store = json.findValue("id_store");
    //            if (id_store != null)
    //                invoiceDetail.setStore(Store.findById(id_store.asLong()));
    //
    //            invoiceDetail.update();
    //            return  ok(Json.toJson(invoiceDetail));

        }catch(Exception e) {
            return Response.responseExceptionUpdated(e);
        }

    }

//    @CoffeAppsecurity
    public Result delete(Long id) {
        Invoice invoice;
        try{
            InvoiceDetail invoiceDetail = InvoiceDetail.findById(id);
            invoiceDetail.getInvoice().setStatusDelete(1);
            invoiceDetail.getInvoice().update();
            invoiceDetail.setAmountInvoiceDetail(BigDecimal.ZERO);
            invoiceDetail.update();

            return Response.deletedEntity();
            //            if(invoiceDetail != null) {
//
//                invoiceDetail.setStatusDelete(1);
//                invoiceDetail.update();
//
//                List<InvoiceDetail> invoicesDetailsOpen= InvoiceDetail.findAllByIdInvoice(invoiceDetail.getInvoice().getIdInvoice());
//                invoice = invoiceDetail.getInvoice();
//                if ( invoicesDetailsOpen.size()==0) {
//
//                    invoice.setStatusDelete(1);
//
//                }
//                BigDecimal newTotal = Invoice.calcularTotalInvoice(invoice.getIdInvoice());
//                invoice.setTotalInvoice(newTotal);
//                invoice.update();// = invoiceDao.update(invoice);
//
//                return Response.deletedEntity();
//            } else {
//                return  Response.message("Successful no existe el registro a eliminar");
//            }
        } catch (Exception e) {
            return Response.responseExceptionUpdated(e);
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


    @CoffeAppsecurity
    public Result findAllByIdInvoice(Long IdInvoice) {
        try {
            List<InvoiceDetail> invoiceDetails = InvoiceDetail.findAllByIdInvoice(IdInvoice);
            return Response.foundEntity(Response.toJson(invoiceDetails, InvoiceDetailShortResponse.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @CoffeAppsecurity
    public Result deleteAllByIdInvoiceAndDate( Long IdInvoice, String  date)
    {
        BigDecimal newTotal;
        try {
            List<InvoiceDetail> invoiceDetails;

            int result = InvoiceDetail.deleteAllByIdInvoiceAndDate(IdInvoice,date);

            List<InvoiceDetail> invoicesDetailsOpen= InvoiceDetail.findAllByIdInvoice(IdInvoice);
            Invoice  invoice = Invoice.findById(IdInvoice);
            if ( invoicesDetailsOpen.size()==0){

                invoice.setStatusDelete(1);

            }
            newTotal = Invoice.calcularTotalInvoice(IdInvoice);
            invoice.setTotalInvoice(newTotal);
            invoice.update();// = invoiceDao.update(invoice);
            return this.findAllByIdInvoice(IdInvoice);

        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

//    @CoffeAppsecurity
    public Result findAll(Integer index, Integer size, String sort, String collection, Long invoiceId) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = InvoiceDetail.findAll(index, size, sort, pathProperties, invoiceId);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


}
