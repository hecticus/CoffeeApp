package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.utils.JsonUtils;
import controllers.utils.ListPagerCollection;
import controllers.utils.NsExceptionsUtils;
import daemonTask.TimeClosed;
import io.ebean.Ebean;
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
import java.util.List;

/**
 * Created by sm21 on 10/05/18.
 *
 * "EEE, d MMM yyyy HH:mm:ss Z" OJOJOJOJOJOJOJOJOJOJ aNGULAR
 */
public class Invoices extends Controller {

    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    @CoffeAppsecurity
    public    Result create() {
        try{
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            Invoice invoice = Json.fromJson(json, Invoice.class);

            invoice.insert();
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

            Invoice invoice = Json.fromJson(json, Invoice.class);

            if (Provider.findById(invoice.getProvider().getId()) == null ||
                    Provider.findById(invoice.getProvider().getId()).getStatusProvider().getId().intValue() == 42  ){
                return Response.requiredParameter("Proveedor Inactivo");
            }

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
    public  Result closeInvoice() {
        try {
            TimeClosed.closeInvoice();
            return Response.foundEntity(Json.toJson("Facturas cerradas"));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @CoffeAppsecurity
    public   Result findAll( Integer pageIndex, Integer pageSize,  String collection,
                             String sort, Long id_provider, Long providerType,  String startDate,
                             String endDate, Long status ,boolean deleted,  String nitName){
        try {

            ListPagerCollection listPager = Invoice.findAll( pageIndex, pageSize,
                    propertiesCollection.getPathProperties(collection),
                    sort, id_provider,
                    providerType, startDate, endDate,
                    status, deleted, nitName);

            return ResponseCollection.foundEntity(listPager,  propertiesCollection.getPathProperties(collection));
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

    @CoffeAppsecurity
    public  Result createTotalReport() {
        try {
            ListPagerCollection listPager = Invoice.createTotalReport();

            return ResponseCollection.foundEntity(listPager,  propertiesCollection.getPathProperties(null));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @CoffeAppsecurity
    public  Result createDetailReport(Integer pageIndex, Integer pageSize,  String collection,
                                      String sort, Long id_provider, Long id_providertype,  String startDate,
                                      String endDate, Long status ,boolean deleted,  String nitName) {
        try {
            ListPagerCollection listPager = Invoice.createDetailReport(pageIndex, pageSize,
                    propertiesCollection.getPathProperties(collection),
                    sort, id_provider, id_providertype, startDate,
                    endDate, status, deleted, nitName);

            return ResponseCollection.foundEntity(listPager,  propertiesCollection.getPathProperties(null));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @CoffeAppsecurity
    public  Result createReport(  Integer pageIndex, Integer pageSize,  String collection,
                                  String sort, Long id_provider, Long id_providertype,  String startDate,
                                  String endDate, Long status ,boolean deleted,  String nitName) {
        try {
            ListPagerCollection listPager = Invoice.createReport(pageIndex, pageSize,
                    propertiesCollection.getPathProperties(collection),
                    sort, id_provider, id_providertype, startDate,
                    endDate, status, deleted, nitName);

            return ResponseCollection.foundEntity(listPager,  propertiesCollection.getPathProperties(collection));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @CoffeAppsecurity
    public  Result buyHarvestsAndCoffe( ){
        try{

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

            Invoice invoice = Json.fromJson(json, Invoice.class);
            ZonedDateTime auxDate = invoice.getStartDate();

            if (Provider.findById(invoice.getProvider().getId()) == null ||
                    Provider.findById(invoice.getProvider().getId()).getStatusProvider().getId().intValue() == 42  ){
                return Response.requiredParameter("Proveedor Inactivo");
            }

            // Get all invoice by provider and date
            List<Invoice> invoiceList = Invoice.invoicesListByProvider(invoice.getProvider().getId(), auxDate);

            Invoice newInvoice = null;

            if( invoiceList.isEmpty()){
                newInvoice = new Invoice();
                newInvoice.setProvider(invoice.getProvider());
                newInvoice.setStatusInvoice(StatusInvoice.findById(new Long(11)));
                newInvoice.setStartDate(auxDate);
            }else{
                newInvoice = invoiceList.get(0);
            }

            for (JsonNode item : itemtypes) {

                InvoiceDetail invoiceDetail = Json.fromJson(item, InvoiceDetail.class);

                if (option) {
                    // Harvest -- true
                    JsonNode idLot = item.get("lot");
                    if (idLot == null)
                        return Response.requiredParameter("lot");

                    Lot lot = Lot.findById(invoiceDetail.getLot().getId());
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
                newInvoice.save();

                invoiceDetail.setInvoice(newInvoice);
//            invoiceDetail.setStartDate(startTime);
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

        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
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
