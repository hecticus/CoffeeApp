package controllers;

import com.fasterxml.jackson.databind.JsonNode;
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
    public Result create() {
        try {
            JsonNode json = request().body().asJson();
            if(json== null)
                return Response.requiredJson();

            Form<InvoiceDetail> form = formFactory.form(InvoiceDetail.class).bind(json);
            if (form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            InvoiceDetail invoiceDetail = Json.fromJson(json, InvoiceDetail.class);
            invoiceDetail.save();
            return  Response.createdEntity(Json.toJson(invoiceDetail));
        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

////@CoffeAppsecurity
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
            invoiceDetail.setId(id);
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
                          Long invoice, Long itemType, Long lot, Long store, String nameReceived,
                          String nameDelivered, String startDateInvoiceDetail, Long status, boolean deleted){
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = InvoiceDetail.findAll(pageIndex, pageSize, pathProperties, sort,
                                                                invoice, itemType, lot,store, nameReceived, nameDelivered,
                                                                startDateInvoiceDetail, status, deleted);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


}
