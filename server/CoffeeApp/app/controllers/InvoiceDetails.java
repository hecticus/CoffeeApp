package controllers;

import com.fasterxml.jackson.databind.JsonNode;
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
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.reflect.internal.Trees;
import security.authorization.CoffeAppsecurity;

import javax.inject.Inject;
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

    @CoffeAppsecurity
    public Result create() {
        try {
            JsonNode json = request().body().asJson();
            if(json== null)
                return Response.requiredJson();

            Form<InvoiceDetail> form = formFactory.form(InvoiceDetail.class).bind(json);
            if (form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            InvoiceDetail invoiceDetail = Json.fromJson(json, InvoiceDetail.class);


            if (invoiceDetail.getLot() != null ){
                Lot lot = Lot.findById(invoiceDetail.getLot().getId());
                invoiceDetail.setPriceItemTypeByLot(lot.getPriceLot());
            }

            if (invoiceDetail.getStore() != null ){
                JsonNode price = json.get("costItemType");
                if (price == null)
                    return Response.requiredParameter("costItemType");
            }


            invoiceDetail.save();
            return  Response.createdEntity(Json.toJson(invoiceDetail));
        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

    @CoffeAppsecurity
    public Result update(Long id) {
        try {
            JsonNode json = request().body().asJson();
            if(json== null)
                return Response.requiredJson();

            Form<InvoiceDetail> form = formFactory.form(InvoiceDetail.class).bind(json);
            if (form.hasErrors()){
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());
            }

            InvoiceDetail invoiceDetail = Json.fromJson(json, InvoiceDetail.class);
            if (invoiceDetail.getLot() != null ){
                Lot lot = Lot.findById(invoiceDetail.getLot().getId());
                invoiceDetail.setPriceItemTypeByLot(lot.getPriceLot());
            }

            JsonNode purities = json.get("purities");
            if (purities == null)
                return Response.requiredParameter("purities");

            List<Integer> detailPurities = InvoiceDetailPurity.getByIdInvopiceDetails(id);
            if(!detailPurities.isEmpty() ){
                Ebean.deleteAllPermanent(InvoiceDetailPurity.class, detailPurities );
            }

            for(JsonNode purity : purities) {

                JsonNode idPurity = purity.get("idPurity");
                if (idPurity == null)
                    return Response.requiredParameter("idPurity");

                JsonNode valueRateInvoiceDetailPurity = purity.get("valueRateInvoiceDetailPurity");
                if (valueRateInvoiceDetailPurity == null)
                    return Response.requiredParameter("valueRateInvoiceDetailPurity");

                Purity puritys = Purity.findById(idPurity.asLong());

                InvoiceDetailPurity invoiceDetailPurity = new InvoiceDetailPurity();

                invoiceDetailPurity.setPurity(puritys);
                invoiceDetailPurity.setValueRateInvoiceDetailPurity(valueRateInvoiceDetailPurity.decimalValue());
                invoiceDetailPurity.setDiscountRatePurity(puritys.getDiscountRatePurity());
                invoiceDetailPurity.setInvoiceDetail(InvoiceDetail.findById(id));
                invoiceDetailPurity.setTotalDiscountPurity(puritys.getDiscountRatePurity().multiply(valueRateInvoiceDetailPurity.decimalValue()));

                invoiceDetailPurity.save();

            }

            invoiceDetail.setId(id);
            invoiceDetail.update();

            return  Response.createdEntity(Json.toJson(invoiceDetail));
        }catch(Exception e) {
            return Response.responseExceptionUpdated(e);
        }

    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Ebean.delete(InvoiceDetail.findById(id));
            return Response.deletedEntity();
        } catch (Exception e) {
            return Response.responseExceptionUpdated(e);
        }
    }

    @CoffeAppsecurity
    public Result deletes( ) {
        try {
            JsonNode json = request().body().asJson();
            if (json == null)
                return Response.requiredJson();

            Ebean.deleteAll(InvoiceDetail.class, JsonUtils.toArrayLong(json, "ids"));

            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

        @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            InvoiceDetail invoiceDetail = InvoiceDetail.findById(id);
            return Response.foundEntity(Json.toJson(invoiceDetail));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }



    @CoffeAppsecurity
    public Result findAll(Integer pageIndex, Integer pageSize, String collection, String sort,
                          Long invoice, Long itemType, Long lot, Long store, String nameReceived,
                          String nameDelivered, DateTimeRange startDate, Long status, boolean deleted){
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = InvoiceDetail.findAll(pageIndex, pageSize, pathProperties, sort,
                                                                invoice, itemType, lot,store, nameReceived, nameDelivered,
                                                                startDate., status, deleted);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


}
