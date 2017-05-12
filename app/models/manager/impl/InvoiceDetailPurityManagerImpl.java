package models.manager.impl;

import com.fasterxml.jackson.databind.JsonNode;
import models.dao.InvoiceDetailPurityDao;
import models.dao.impl.InvoiceDetailPurityDaoImpl;
import models.dao.InvoiceDetailDao;
import models.dao.impl.InvoiceDetailDaoImpl;
import models.dao.PurityDao;
import models.dao.impl.PurityDaoImpl;
import models.domain.InvoiceDetailPurity;
import models.manager.InvoiceDetailPurityManager;
import models.manager.responseUtils.Response;
import models.manager.responseUtils.responseObject.InvoiceDetailPurityResponse;
import play.libs.Json;
import play.mvc.Result;

import java.util.List;

import static play.mvc.Controller.request;

/**
 * Created by drocha on 26/04/17.
 */
public class InvoiceDetailPurityManagerImpl  implements InvoiceDetailPurityManager {




    private static InvoiceDetailPurityDao invoiceDetailPurityDao = new InvoiceDetailPurityDaoImpl();
    private static PurityDao purityDao = new PurityDaoImpl();
    private static InvoiceDetailDao invoiceDetailDao = new InvoiceDetailDaoImpl();

    @Override
    public Result create() {
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
            invoiceDetailPurity.setInvoiceDetails(invoiceDetailDao.findById(id_invoiceDetail.asLong()));

            invoiceDetailPurity = invoiceDetailPurityDao.create(invoiceDetailPurity);
            return Response.createdEntity(Json.toJson(invoiceDetailPurity));

        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

    @Override
    public Result update() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            JsonNode id = json.get("id");
            if (id == null)
                return Response.requiredParameter("id");

            InvoiceDetailPurity invoiceDetailPurity =  Json.fromJson(json, InvoiceDetailPurity.class);

            JsonNode id_purity = json.get("id_purity");
            if (id_purity != null)
                invoiceDetailPurity.setPurity(purityDao.findById(id_purity.asLong()));


            JsonNode id_invoiceDetail = json.get("id_invoiceDetail");
            if (id_invoiceDetail != null)
                invoiceDetailPurity.setInvoiceDetails(invoiceDetailDao.findById(id_invoiceDetail.asLong()));

            invoiceDetailPurity = invoiceDetailPurityDao.update(invoiceDetailPurity);
            return Response.updatedEntity(Json.toJson(invoiceDetailPurity));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    @Override
    public Result delete(Long id) {
        try{
            InvoiceDetailPurity invoiceDetailPurity = invoiceDetailPurityDao.findById(id);
            if(invoiceDetailPurity != null) {

                invoiceDetailPurity.setStatusDelete(1);
                invoiceDetailPurity = invoiceDetailPurityDao.update(invoiceDetailPurity);

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

    @Override
    public Result findById(Long id) {
        try {
            InvoiceDetailPurity invoiceDetailPurity = invoiceDetailPurityDao.findById(id);
            return Response.foundEntity(Response.toJson(invoiceDetailPurity, InvoiceDetailPurityResponse.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @Override
    public Result findAll(Integer index, Integer size) {
        try {
            List<InvoiceDetailPurity> invoiceDetailPuritys = invoiceDetailPurityDao.findAll(index, size);
            return Response.foundEntity(Json.toJson(invoiceDetailPuritys));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

}
