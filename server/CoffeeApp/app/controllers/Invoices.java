package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.parsers.queryStringBindable.DateTimeRange;
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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by sm21 on 10/05/18.
 */
public class Invoices extends Controller {

    @Inject
    private FormFactory formFactory;

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

            String tim = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX").format(ZonedDateTime.now());
            System.out.println(tim );

            Form<Invoice> form = formFactory.form(Invoice.class).bind(json);
            if (form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            Invoice invoice = Json.fromJson(json, Invoice.class);

            JsonNode date = json.findValue("start");
            if(date == null)
                return Response.requiredParameter("Requiere fecha de inicio de factura");
            invoice.setStartDate(ZonedDateTime.parse (date.asText(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")));
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
                                    String sort, Long id_provider, Long id_providertype,  String startDate,
                                    String endDate, Long status ,boolean deleted,  String nitName){
        try {

            ZonedDateTime startTime_to = null;
            ZonedDateTime startTime_from = null;

            if (startDate != null){
                startTime_to =  ZonedDateTime.parse (startDate,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX"));
            }

            if (endDate != null){
                 startTime_from =  ZonedDateTime.parse (endDate,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX"));
            }

            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = Invoice.findAll( pageIndex, pageSize, pathProperties, sort, id_provider,
                                                                        id_providertype, startTime_to, startTime_from,
                                                                        status, deleted, nitName);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


    @CoffeAppsecurity
    public  Result buyHarvestsAndCoffe( ){

        boolean auxCreate = false;
        JsonNode json = request().body().asJson();
        if(json == null)
            return Response.requiredJson();

        // 1 true  harvest    //////// 0 false buyCoffe
        JsonNode buyOption = json.get("buyOption");
        if (buyOption ==  null)
            return Response.requiredParameter("buyOption");
        Boolean option = buyOption.asBoolean();

        JsonNode itemtypes = json.get("itemtypes");
        if (itemtypes == null)
            return Response.requiredParameter("itemtypes");

        JsonNode date = json.findValue("start");
        if(date == null)
            return Response.requiredParameter("Requiere fecha de inicio de factura");

        ZonedDateTime fecha =  ZonedDateTime.parse (date.asText(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX"));

        Form<Invoice> form = formFactory.form(Invoice.class).bind(json);
        if (form.hasErrors())
            return controllers.utils.Response.invalidParameter(form.errorsAsJson());

        // Invoice invoice2 = Json.fromJson(json, Invoice.class);
        // Tengo el invoice recibido
        Invoice invoice = form.get();

        Provider provStatus = Provider.findById(invoice.getProvider().getId());
        if (provStatus.getStatusProvider().getId().intValue() == 42 ){
            return Response.requiredParameter("Proveedor Inactivo");
        }

        List<Invoice> invoiceList = Invoice.invoicesListByProvider(invoice.getProvider().getId(), fecha);

        // Invoice invoices = invoiceList.get(0);
        // Invoice invoices = Invoice.invoicesByProvider(invoice.getProvider(), fecha);

        Invoice newInvoice = null;

        if( invoiceList.isEmpty()){
            newInvoice = new Invoice();
            newInvoice.setProvider(invoice.getProvider());
            newInvoice.setStatusInvoice(StatusInvoice.findById(new Long(11)));
        }else{
            newInvoice = invoiceList.get(0);
        }

        for (JsonNode item : itemtypes) {

            Form<InvoiceDetail> formDetail = formFactory.form(InvoiceDetail.class).bind(item);
            if (formDetail.hasErrors())
                return controllers.utils.Response.invalidParameter(formDetail.errorsAsJson());

            JsonNode dateStart = item.findValue("start");
            if(dateStart == null)
                return Response.requiredParameter("Requiere fecha de inicio de Detalle");

            ZonedDateTime startTime =  ZonedDateTime.parse (dateStart.asText(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX"));

            InvoiceDetail invoiceDetail = Json.fromJson(item, InvoiceDetail.class);

            if (option) {
                // Harvest -- true
                JsonNode idLot = item.get("lot");
                if (idLot == null)
                    return Response.requiredParameter("lot");

                Lot lot = Lot.findById(invoiceDetail.getLot().getId());
                System.out.println(lot.getNameLot());

                System.out.println(invoiceDetail.getLot().getId());
                invoiceDetail.setLot(lot);
                invoiceDetail.setPriceItemTypeByLot(lot.getPriceLot());
                invoiceDetail.setCostItemType(new BigDecimal(0));
            } else {
                // Coffee -- false
                JsonNode price = item.get("price");
                if (price == null)
                    return Response.requiredParameter("price");

                JsonNode store = item.get("store");
                if (store == null)
                    return Response.requiredParameter("store");

                invoiceDetail.setPriceItemTypeByLot(new BigDecimal(0));
                invoiceDetail.setCostItemType(price.decimalValue());

            }

            // Busco la lista de invoicesDetail asociado a ese Invoice
            List<InvoiceDetail> invoiceDetails = newInvoice.getInvoiceDetails();

            invoiceDetails.add(invoiceDetail);

            newInvoice.setInvoiceDetails(invoiceDetails);
            newInvoice.setStartDate(fecha);
            newInvoice.save();

            invoiceDetail.setInvoice(newInvoice);
            invoiceDetail.setStartDate(startTime);
            invoiceDetail.save();

            if(!option)  {

                JsonNode purities = item.get("purities");
                if (purities == null)
                    return Response.requiredParameter("purities");
                for(JsonNode purity : purities) {
//                    InvoiceDetailPurity invoiceDetailPurity = new InvoiceDetailPurity();
                    JsonNode idPurity = purity.get("idPurity");
                    if (idPurity == null)
                        return Response.requiredParameter("idPurity");

                    JsonNode valueRateInvoiceDetailPurity = purity.get("valueRateInvoiceDetailPurity");
                    if (valueRateInvoiceDetailPurity == null)
                        return Response.requiredParameter("valueRateInvoiceDetailPurity");

                    Purity puritys = Purity.findById(idPurity.asLong());

                    InvoiceDetailPurity invoiceDetailPurity = InvoiceDetailPurity.getByIdInvopiceDetailsByIdPurity(
                            invoiceDetail.getId(), idPurity.asLong());

                    if(invoiceDetailPurity==null) {
                        invoiceDetailPurity = new InvoiceDetailPurity();
                        auxCreate = true;
                    }
                    invoiceDetailPurity.setPurity(puritys);
                    invoiceDetailPurity.setValueRateInvoiceDetailPurity(valueRateInvoiceDetailPurity.decimalValue());
                    invoiceDetailPurity.setDiscountRatePurity(puritys.getDiscountRatePurity());
                    invoiceDetailPurity.setInvoiceDetail(invoiceDetail);
                    invoiceDetailPurity.setTotalDiscountPurity(puritys.getDiscountRatePurity().multiply( valueRateInvoiceDetailPurity.decimalValue()));
                    if(auxCreate) invoiceDetailPurity.save();
                    else invoiceDetailPurity.update();


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
        response.put("nameCompany",  Config.getString("nameCompany"));
        response.put("invoiceDescription", Config.getString("invoiceDescription"));
        response.put("invoiceType", Config.getString("invoiceType"));
        response.put("RUC", Config.getString("RUC"));
        response.put("telephonoCompany", Config.getString("telephonoCompany"));
        response.set("invoice", Json.toJson(invoice));
        return Response.createdEntity(response);
    }

}
