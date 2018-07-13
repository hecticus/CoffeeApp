package controllers;

import com.typesafe.config.Config;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.utils.JsonUtils;
import controllers.utils.ListPagerCollection;
import controllers.utils.NsExceptionsUtils;
import io.ebean.Ebean;
import io.ebean.text.PathProperties;
import models.*;
import controllers.responseUtils.ExceptionsUtils;
import controllers.responseUtils.PropertiesCollection;
import controllers.responseUtils.Response;
import controllers.responseUtils.ResponseCollection;
import models.status.StatusInvoice;
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
public class Invoices extends Controller {

    @Inject
    private FormFactory formFactory;

    @Inject
    Config config;

    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public Invoices(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*" +
                ", provider(id_Provider, identificationDoc_Provider, fullName_Provider, address_Provider" +
                " phoneNumber_Provider,email_Provider, providerType(id_ProviderType, name_ProviderType))");
    }

    @CoffeAppsecurity
    public    Result create() {
        try{
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            Form<Invoice> form = formFactory.form(Invoice.class).bind(json);
            if (form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            Invoice invoice = Json.fromJson(json, Invoice.class);
            invoice.save();
            return  Response.createdEntity(Json.toJson(invoice));

        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

    @CoffeAppsecurity
    public  Result update(Long id) {
        try {
            JsonNode json = request().body().asJson();
            if(json== null)
                return badRequest("Expecting Json data");

            Form<Invoice> form = formFactory.form(Invoice.class).bind(json);
            if (form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            Invoice invoice = Json.fromJson(json, Invoice.class);
            invoice.setId(id);
            invoice.update();
            return Response.updatedEntity(Json.toJson(invoice));
        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    @CoffeAppsecurity
    public  Result delete(Long id) {
        try{
            Ebean.delete(Invoice.findById(id));
            return controllers.utils.Response.deletedEntity();
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

            Ebean.deleteAll(Invoice.class, JsonUtils.toArrayLong(json, "ids"));

            return controllers.utils.Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

    @CoffeAppsecurity
    public  Result findById(Long id) {
        try {
            return Response.foundEntity(Json.toJson(Invoice.findById(id)));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @CoffeAppsecurity
    public   Result findAll( Integer pageIndex, Integer pageSize,  String collection,
                                    String sort, Long id_provider, Long id_providertype, String startDate,
                                    String endDate, Long status ,boolean deleted){
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = Invoice.findAll( pageIndex, pageSize, pathProperties, sort, id_provider,
                                                                        id_providertype, startDate, endDate, status, deleted);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


    @CoffeAppsecurity
    public  Result buyHarvestsAndCoffe( ){

        JsonNode json = request().body().asJson();
        if(json == null)
            return Response.requiredJson();

        // 1 true  harvest    //////// 2 false buy coffe
        JsonNode buyOption = json.get("buyOption");
        if (buyOption ==  null)
            return Response.requiredParameter("buyOption");
        Boolean option = buyOption.asBoolean();

        JsonNode itemtypes = json.get("itemtypes");
        if (itemtypes == null)
            return Response.requiredParameter("itemtypes");

        Form<Invoice> form = formFactory.form(Invoice.class).bind(json);
        if (form.hasErrors())
            return controllers.utils.Response.invalidParameter(form.errorsAsJson());

        JsonNode startDate =  json.get("startDate");;
        if (startDate==  null)
            return Response.requiredParameter("startDateInvoiceDetail");
        Invoice invoice = Json.fromJson(json, Invoice.class);
        String fecha =startDate.asText().split(" ")[0];
//        String fecha = invoice.getStartDateInvoice().toString().split(" ")[0];
        System.out.println(fecha +"-+");


        List<Invoice> invoices = Invoice.getOpenByProviderId(invoice.getProvider().getId(), fecha);

        Invoice newInvoice = null;

        if(invoices.isEmpty()){
            newInvoice = new Invoice();
            newInvoice.setProvider(invoice.getProvider());
            newInvoice.setStatusInvoice(StatusInvoice.findById(new Long(11)));
        }else{
            for (Invoice i : invoices ) {
                StatusInvoice status = i.getStatusInvoice();
                if( status != null) {
                    if (status.getId().intValue() == 11) System.out.println(status);
                        newInvoice = i;
                }else{
                    newInvoice = new Invoice();
                    newInvoice.setProvider(invoice.getProvider());
                    newInvoice.setStatusInvoice(StatusInvoice.findById(new Long(11)));
                }
            }
        }


        for (JsonNode item : itemtypes) {

//            JsonNode amount = itemtypeAux.get("amountInvoiceDetail");
//            if (amount == null)
//                return Response.requiredParameter("amountInvoiceDetail");
            Form<InvoiceDetail> formDetail = formFactory.form(InvoiceDetail.class).bind(item);
            if (formDetail.hasErrors())
                return controllers.utils.Response.invalidParameter(formDetail.errorsAsJson());

            InvoiceDetail invoiceDetail = Json.fromJson(item, InvoiceDetail.class);
            invoiceDetail.save();

            // Buscos la lista de invoicesDetail asociado a esa Invoice
            List<InvoiceDetail> invoiceDetails = newInvoice.getInvoiceDetails();
            invoiceDetails.add(invoiceDetail);
            newInvoice.setInvoiceDetails(invoiceDetails);

            if (!invoices.isEmpty()){
                newInvoice.update();
            } else {
                newInvoice.save();
            }

            invoiceDetail.setInvoice(newInvoice);
            invoiceDetail.save();

            if(!option)  {

                JsonNode purities = item.get("purities");
                if (purities == null)
                    return Response.requiredParameter("purities");
                for(JsonNode purity : purities) {
                    InvoiceDetailPurity invoiceDetailPurity = new InvoiceDetailPurity();
                    JsonNode idPurity = purity.get("idPurity");
                    if (idPurity == null)
                        return Response.requiredParameter("idPurity");

                    JsonNode valueRateInvoiceDetailPurity = purity.get("valueRateInvoiceDetailPurity");
                    if (valueRateInvoiceDetailPurity == null)
                        return Response.requiredParameter("valueRateInvoiceDetailPurity");

                    Purity puritys = Purity.findById(idPurity.asLong());

                    invoiceDetailPurity.setPurity(puritys);
                    invoiceDetailPurity.setValueRateInvoiceDetailPurity(valueRateInvoiceDetailPurity.asInt());
                    invoiceDetailPurity.setInvoiceDetail(invoiceDetail);
                    invoiceDetailPurity.save();
                }
            }
        }
        newInvoice.update();
        return Response.createdEntity(Json.toJson(newInvoice));
    }


//    @CoffeAppsecurity
//    public  Result updateBuyHarvestsAndCoffe() {
//        BigDecimal monto;
//        boolean auxCreate = false;
//        InvoiceDetailPurity invoiceDetailPurity;
//
//        JsonNode json = request().body().asJson();
//        if(json == null)
//            return Response.requiredJson();
//
//        JsonNode idInvoice = json.get("idInvoice");
//        if (idInvoice == null)
//            return Response.requiredParameter("idInvoice");
//
//        JsonNode idProvider = json.get("idProvider");
//        if (idProvider == null)
//            return Response.requiredParameter("idProvider");
//
//        JsonNode itemtypes = json.get("itemtypes");
//        if (itemtypes == null)
//            return Response.requiredParameter("itemtypes");
//
//        JsonNode nameReceived = json.get("nameReceivedInvoiceDetail");
//        if (nameReceived== null)
//            return Response.requiredParameter("nameReceivedInvoiceDetail");
//
//        JsonNode nameDelivered = json.get("nameDeliveredInvoiceDetail");
//        if (nameDelivered==  null)
//            return Response.requiredParameter("nameDeliveredInvoiceDetail");
//
//        JsonNode note = json.get("note");
//
//        JsonNode freigh = json.get("freigh");
//        if (freigh==  null)
//            return Response.requiredParameter("freigh");
//
//        JsonNode buyOption = json.get("buyOption");
//        if (buyOption==  null)
//            return Response.requiredParameter("buyOption");
//
//        if(buyOption.asInt() !=1 && buyOption.asInt() != 2)
//            return Response.message("buyOption: 1 for buy Harvests And 2 for buy Coffe");
//
//        Invoice openInvoice = Invoice.findById(idInvoice.asLong());
//
//        for (JsonNode itemtypeAux : itemtypes) {
//
//            JsonNode Amount = itemtypeAux.get("amount");
//            if (Amount == null)
//                return Response.requiredParameter("amount");
//
//            JsonNode idItemtype = itemtypeAux.get("idItemType");
//            if (idItemtype == null)
//                return Response.requiredParameter("idItemType");
//
//            JsonNode idInvoiceDetail = itemtypeAux.get("idInvoiceDetail");
//            if (idInvoiceDetail == null)
//                return Response.requiredParameter("idInvoiceDetail");
//
//            ItemType itemType = ItemType.findById(idItemtype.asLong());
//
//            InvoiceDetail invoiceDetail = InvoiceDetail.findById(idInvoiceDetail.asLong());  //new InvoiceDetail();
//            invoiceDetail.setItemType(itemType);
//
//
//            if (buyOption.asInt() == 1) {
//                JsonNode idLot = json.get("idLot");
//                if (idLot == null)
//                    return Response.requiredParameter("idLot");
//
//                Lot lot = Lot.findById(idLot.asLong());
//
//                invoiceDetail.setLot(lot);
//                invoiceDetail.setPriceItemTypeByLot(lot.getPrice_lot());
//
//                monto = Amount.decimalValue().multiply( lot.getPrice_lot());
//            } else {
//                JsonNode price = itemtypeAux.get("price");
//                if (price == null)
//                    return Response.requiredParameter("price");
//
//                invoiceDetail.setCostItemType(price.decimalValue());
//
//                JsonNode id_store = itemtypeAux.get("id_store");
//                if (id_store == null)
//                    return Response.requiredParameter("id_store");
//
//                invoiceDetail.setStore(Store.findById(id_store.asLong()));
//
//                monto = Amount.decimalValue().multiply(price.decimalValue());
//                monto = Amount.asInt() * itemType.getCostItemType();
//
//                int Discount = (-1)*Math.round((monto*puritys.getDiscountRatePurity())/100);
//                invoiceDetailPurity.setDiscountRatePurity(Discount);
//
//                monto = monto+Discount;
//
//            }
//
//            invoiceDetail.setAmountInvoiceDetail(Amount.decimalValue());
//            invoiceDetail.setFreightInvoiceDetail(freigh.asBoolean());
//            invoiceDetail.setNameDeliveredInvoiceDetail(nameDelivered.asText());
//            invoiceDetail.setNameReceivedInvoiceDetail(nameReceived.asText());
//            invoiceDetail.setNoteInvoiceDetail(note.asText());
//
////            DateTime startDatetime;
////            startDatetime = Request.dateTimeFormatter.parseDateTime(startDate.asText());
////            invoiceDetail.setStartDateInvoiceDetail(startDatetime);
////
////            openInvoice.setStartDateInvoice(startDatetime);
////            openInvoice.setClosedDateInvoice(startDatetime);
//            openInvoice.setProvider(Provider.findById(idProvider.asLong()));
//            openInvoice.setTotalInvoice(monto);
//
//            List<InvoiceDetail> invoiceDetails = openInvoice.getInvoiceDetails();
//
//            invoiceDetails.add(invoiceDetail);
//            openInvoice.setInvoiceDetails(invoiceDetails);
//
//            openInvoice.update();// = invoiceDao.update(openInvoice);
//
//            invoiceDetail.setInvoice(openInvoice);
//            invoiceDetail.update();// = invoiceDetailDao.update(invoiceDetail);
//
//            if(buyOption.asInt() == 2){
//                JsonNode purities = itemtypeAux.get("purities");
//                if (purities == null)
//                    return Response.requiredParameter("purities");
//                for(JsonNode purity : purities){
//                    JsonNode idPurity = purity.get("idPurity");
//                    if (idPurity == null)
//                        return Response.requiredParameter("idPurity");
//
//                    JsonNode valueRateInvoiceDetailPurity = purity.get("valueRateInvoiceDetailPurity");
//                    if (valueRateInvoiceDetailPurity == null)
//                        return Response.requiredParameter("valueRateInvoiceDetailPurity");
//
//                    Purity puritys = Purity.findById(idPurity.asLong());
//
//                    invoiceDetailPurity = InvoiceDetailPurity.getByIdInvoiceDetailsByIdPurity(invoiceDetail.getIdInvoiceDetail(), idPurity.asLong());
//
//                    if(invoiceDetailPurity==null){
//                        invoiceDetailPurity = new InvoiceDetailPurity();
//                        auxCreate=true;
//                    }
//
//                    invoiceDetailPurity.setPurity(puritys);
//                    invoiceDetailPurity.setValueRateInvoiceDetailPurity(valueRateInvoiceDetailPurity.asInt());
//                    invoiceDetailPurity.setInvoiceDetail(invoiceDetail);
//                    if(auxCreate) invoiceDetailPurity.save();//invoiceDetailPurityDao.create(invoiceDetailPurity);
//                    else invoiceDetailPurity.update();//invoiceDetailPurityDao.update(invoiceDetailPurity);
//                }
//
//            }
//
//        }
//
//        openInvoice.setTotalInvoice(Invoice.calcularTotalInvoice(openInvoice.getIdInvoice()));
//        openInvoice.update();// invoiceDao.update(openInvoice);
//        return Response.updatedEntity(Json.toJson(openInvoice));
//}


    @CoffeAppsecurity
    public  Result createReceipt(Long idInvoice)  {
        Invoice invoice = Invoice.findById(idInvoice);
        ObjectNode response = Json.newObject();
        response.put("nameCompany", config.getString("play.company.name"));
        response.put("invoiceDescription", config.getString("play.harvest.invoice.description"));
        response.put("invoiceType", config.getString("play.harvest.invoice.type"));
        response.put("RUC", config.getString("play.purchase.ruc"));
        response.put("telephonoCompany", config.getString("play.company.telephone"));
        response.set("invoice", Json.toJson(invoice));
        return Response.createdEntity(response);
    }


}
