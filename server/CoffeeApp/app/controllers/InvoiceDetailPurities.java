package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.responseUtils.ExceptionsUtils;
import controllers.responseUtils.PropertiesCollection;
import controllers.responseUtils.ResponseCollection;
import controllers.utils.JsonUtils;
import controllers.utils.ListPagerCollection;
import controllers.utils.NsExceptionsUtils;
import io.ebean.Ebean;
import io.ebean.text.PathProperties;
import models.InvoiceDetail;
import models.InvoiceDetailPurity;
import controllers.responseUtils.Response;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import javax.inject.Inject;


/**
 * Created by sm21 on 10/05/18.
 */
public class InvoiceDetailPurities  extends Controller {

    @Inject
    private FormFactory formFactory;
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

//@CoffeAppsecurity
    public  Result create() {
        try {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            Form<InvoiceDetailPurity> form = formFactory.form(InvoiceDetailPurity.class).bind(json);
            if (form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            InvoiceDetailPurity invoiceDetailPurity = Json.fromJson(json, InvoiceDetailPurity.class);
            invoiceDetailPurity.save();
            return Response.createdEntity(Json.toJson(invoiceDetailPurity));

        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

//@CoffeAppsecurity
    public Result update(Long id) {
        try {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            Form<InvoiceDetailPurity> form = formFactory.form(InvoiceDetailPurity.class).bind(json);
            if (form.hasErrors())
                return controllers.utils.Response.invalidParameter(form.errorsAsJson());

            InvoiceDetailPurity invoiceDetailPurity = Json.fromJson(json, InvoiceDetailPurity.class);
            invoiceDetailPurity.setId(id);

            invoiceDetailPurity.update();
            return Response.updatedEntity(Json.toJson(invoiceDetailPurity));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

//@CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Ebean.delete(InvoiceDetail.findById(id));
            return Response.deletedEntity();
        } catch (Exception e) {
            return Response.responseExceptionUpdated(e);
        }
    }

    //@CoffeAppsecurity
    public Result deletes() {
        try {
            JsonNode json = request().body().asJson();
            if (json == null)
                return controllers.utils.Response.requiredJson();

            Ebean.deleteAll(InvoiceDetailPurity.class, JsonUtils.toArrayLong(json, "ids"));

            return controllers.utils.Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

//@CoffeAppsecurity
    public Result findById(Long id) {
        try {
            InvoiceDetailPurity invoiceDetailPurity = InvoiceDetailPurity.findById(id);
            return Response.foundEntity(Response.toJson(invoiceDetailPurity, InvoiceDetailPurity.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    ////@CoffeAppsecurity
    public Result findAll(Integer pageIndex, Integer pageSize, String collection, String sort,
                          Long purity, Long invoiceDetail, boolean deleted){
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = InvoiceDetailPurity.findAll(pageIndex, pageSize, pathProperties, sort,
                                                purity, invoiceDetail, deleted);
            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

}
