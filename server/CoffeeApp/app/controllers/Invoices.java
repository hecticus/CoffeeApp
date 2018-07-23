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
import java.math.BigDecimal;
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
            List<InvoiceDetail> ids = InvoiceDetail.findByProviderId(id);
            Ebean.delete(Invoice.findById(id));
            Ebean.deleteAll(InvoiceDetail.class, ids);
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
        if (startDate ==  null)
            return Response.requiredParameter("startDateInvoiceDetail");
        Invoice invoice = Json.fromJson(json, Invoice.class);
        String fecha = startDate.asText().split(" ")[0];


        List<Invoice> invoices = Invoice.getOpenByProviderId(invoice.getProvider().getId(), fecha);

        Invoice newInvoice = null;

        if(invoices.isEmpty()){
            newInvoice = new Invoice();
            newInvoice.setProvider(invoice.getProvider());
            newInvoice.setStatusInvoice(StatusInvoice.findById(new Long(11)));
        }else{
            for (Invoice i : invoices ) {
                StatusInvoice status = i.getStatusInvoice();
                if( status != null & status.getId().intValue() == 11) {
                        System.out.println(status.getId());
                        System.out.println(i.getId() + "Id");
                        newInvoice = i;
                        break;
                }
//                else{
//                    newInvoice = new Invoice();
//                    newInvoice.setProvider(invoice.getProvider());
//                    newInvoice.setStatusInvoice(StatusInvoice.findById(new Long(11)));
//                }
            }
        }


        for (JsonNode item : itemtypes) {
            Form<InvoiceDetail> formDetail = formFactory.form(InvoiceDetail.class).bind(item);
            if (formDetail.hasErrors())
                return controllers.utils.Response.invalidParameter(formDetail.errorsAsJson());

            InvoiceDetail invoiceDetail = Json.fromJson(item, InvoiceDetail.class);

            invoiceDetail.setCostItemType(ItemType.findById(
                    item.get("itemType").findValue("id").asLong()).getCostItemType());
            BigDecimal ii = ItemType.findById(
                    item.get("itemType").findValue("id").asLong()).getCostItemType();

            if (option) {
                JsonNode idLot = json.get("lot");
                if (idLot == null)
                    return Response.requiredParameter("lot");

                invoiceDetail.setPriceItemTypeByLot(Lot.findById(
                        item.get("lot").findValue("id").asLong()).getPriceLot());
            } else {
                invoiceDetail.setPriceItemTypeByLot(item.get("priceItemTypeByLot").decimalValue());
//                invoiceDetail.setPriceItemTypeByLot(new BigDecimal(0));
            }

            invoiceDetail.save();

            // Buscos la lista de invoicesDetail asociado a esa Invoice
            List<InvoiceDetail> invoiceDetails = newInvoice.getInvoiceDetails();
//            if(newInvoice.getInvoiceDetails().isEmpty())
            invoiceDetails.add(invoiceDetail);
            newInvoice.setInvoiceDetails(invoiceDetails);

            newInvoice.save();
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
