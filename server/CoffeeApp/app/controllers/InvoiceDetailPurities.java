package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.InvoiceDetail;
import models.InvoiceDetailPurity;
import models.Purity;
import controllers.responseUtils.Response;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import java.util.List;


/**
 * Created by sm21 on 10/05/18.
 */
public class InvoiceDetailPurities  extends Controller {

    
    private static InvoiceDetailPurity invoiceDetailPurityDao = new InvoiceDetailPurity();
    private static Purity purityDao = new Purity();
    private static InvoiceDetail invoiceDetailDao = new InvoiceDetail();

    @CoffeAppsecurity
    public  Result create() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();


            JsonNode id_purity = json.get("id_purity");
            if (id_purity == null)
                return Response.requiredParameter("id_purity");

            JsonNode id_invoiceDetail = json.get("id_invoiceDetail");
            if (id_invoiceDetail == null)
                return Response.requiredParameter("id_invoiceDetail");


            JsonNode valueRateInvoiceDetailPurity = json.get("valueRateInvoiceDetailPurity");
            if (valueRateInvoiceDetailPurity == null)
                return Response.requiredParameter("valueRateInvoiceDetailPurity");


            JsonNode totalDiscountPurity = json.get("totalDiscountPurity");
            if (totalDiscountPurity == null)
                return Response.requiredParameter("totalDiscountPurity");


            // mapping object-json
            InvoiceDetailPurity invoiceDetailPurity = Json.fromJson(json, InvoiceDetailPurity.class);

            invoiceDetailPurity.setPurity(purityDao.findById(id_purity.asLong()));
            invoiceDetailPurity.setInvoiceDetail(invoiceDetailDao.findById(id_invoiceDetail.asLong()));

            //invoiceDetailPurity = invoiceDetailPurityDao.save();//.create(invoiceDetailPurity);
            invoiceDetailPurity.save();
            return Response.createdEntity(Json.toJson(invoiceDetailPurity));

        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

    @CoffeAppsecurity
    public Result update() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            JsonNode id = json.get("idInvoiceDetailPurity");
            if (id == null)
                return Response.requiredParameter("idInvoiceDetailPurity");

            InvoiceDetailPurity invoiceDetailPurity =  Json.fromJson(json, InvoiceDetailPurity.class);

            JsonNode id_purity = json.get("id_purity");
            if (id_purity != null)
                invoiceDetailPurity.setPurity(purityDao.findById(id_purity.asLong()));


            JsonNode id_invoiceDetail = json.get("id_invoiceDetail");
            if (id_invoiceDetail != null)
                invoiceDetailPurity.setInvoiceDetail(invoiceDetailDao.findById(id_invoiceDetail.asLong()));

            invoiceDetailPurity.update();// = invoiceDetailPurityDao.update(invoiceDetailPurity);
            return Response.updatedEntity(Json.toJson(invoiceDetailPurity));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    @CoffeAppsecurity
    public Result delete(Long id) {
        try{
            InvoiceDetailPurity invoiceDetailPurity = invoiceDetailPurityDao.findById(id);
            if(invoiceDetailPurity != null) {

                invoiceDetailPurity.setStatusDelete(1);
//                invoiceDetailPurity = invoiceDetailPurityDao.update(invoiceDetailPurity);
                invoiceDetailPurity.update();
                return Response.deletedEntity();
            } else {
                return  Response.message("Successful no existe el registro a eliminar");
            }
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }
    /*public Result delete(Long id) {
        try {

            InvoiceDetailPurity invoiceDetailPurity = findById(id);
            //    invoiceDetailPurityDao.delete(id);
            return Response.deletedEntity();

        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }*/

    @CoffeAppsecurity
    public Result findById(Long id) {
        try {
            InvoiceDetailPurity invoiceDetailPurity = invoiceDetailPurityDao.findById(id);
            return Response.foundEntity(Response.toJson(invoiceDetailPurity, InvoiceDetailPurity.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @CoffeAppsecurity
    public Result findAll(Integer index, Integer size) {
        try {
            List<InvoiceDetailPurity> invoiceDetailPuritys = invoiceDetailPurityDao.findAll(index, size);
            return Response.foundEntity(Json.toJson(invoiceDetailPuritys));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

}
