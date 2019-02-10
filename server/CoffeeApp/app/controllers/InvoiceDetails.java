package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import controllers.utils.*;
import io.ebean.Ebean;
import io.ebean.PagedList;
import models.*;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import javax.inject.Inject;

/**
 * Created by sm21 on 10/05/18.
 */
public class InvoiceDetails extends Controller {

    @Inject
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    @CoffeAppsecurity
    public Result create() {
        try {
            JsonNode json = request().body().asJson();
            if(json== null)
                return Response.requiredJson();

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
            return NsExceptionsUtils.create(e);
        }
    }

    @CoffeAppsecurity
    public Result update(Long id) {
        try {
            JsonNode json = request().body().asJson();
            if(json== null)
                return Response.requiredJson();

            InvoiceDetail invoiceDetail = Json.fromJson(json, InvoiceDetail.class);
            if (invoiceDetail.getLot() != null ){
                Lot lot = Lot.findById(invoiceDetail.getLot().getId());
                invoiceDetail.setPriceItemTypeByLot(lot.getPriceLot());
            }

            invoiceDetail.setId(id);
            invoiceDetail.update();

            return  Response.createdEntity(Json.toJson(invoiceDetail));
        }catch(Exception e) {
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
            return NsExceptionsUtils.find(e);
        }
    }



    @CoffeAppsecurity
    public Result findAll(Integer pageIndex, Integer pageSize, String collection, String sort,
                          Long invoice, Long itemType, Long lot, Long store, String nameReceived,
                          String nameDelivered, String startDate, Long status, boolean deleted){
        try {

            PagedList pagedList = InvoiceDetail.findAll(pageIndex, pageSize, propertiesCollection.getPathProperties(collection), sort,
                    invoice, itemType, lot,store, nameReceived, nameDelivered,
                    startDate, status, deleted);

            return Response.foundEntity(pagedList, propertiesCollection.getPathProperties(collection));
        }catch(Exception e){
            return NsExceptionsUtils.find(e);
        }
    }


}
