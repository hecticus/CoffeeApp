package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.utils.PropertiesCollection;

import controllers.utils.JsonUtils;

import controllers.utils.NsExceptionsUtils;
import controllers.utils.Response;
import io.ebean.Ebean;
import io.ebean.PagedList;
import models.InvoiceDetail;
import models.InvoiceDetailPurity;
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

    @CoffeAppsecurity
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
            return NsExceptionsUtils.create(e);
        }
    }

    @CoffeAppsecurity
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
            return NsExceptionsUtils.update(e);
        }
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            Ebean.delete(InvoiceDetail.findById(id));
            return Response.deletedEntity();
        } catch (Exception e) {
            return NsExceptionsUtils.delete(e);
        }
    }

    @CoffeAppsecurity
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

    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            InvoiceDetailPurity invoiceDetailPurity = InvoiceDetailPurity.findById(id);
            return Response.foundEntity(Json.toJson(invoiceDetailPurity));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @CoffeAppsecurity
    public Result findAll(Integer pageIndex, Integer pageSize, String collection, String sort,
                          Long purity, Long invoiceDetail, boolean deleted){
        try {
            PagedList pagedList = InvoiceDetailPurity.findAll(pageIndex, pageSize,
                                                                propertiesCollection.getPathProperties(collection), sort,
                                                                purity, invoiceDetail, deleted);

            return Response.foundEntity( pagedList, propertiesCollection.getPathProperties(collection));
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }
}
