package models.manager.impl;

import com.fasterxml.jackson.databind.JsonNode;
import models.dao.*;
import models.dao.impl.*;
import models.domain.InvoiceDetail;
import models.manager.InvoiceDetailManager;
import models.manager.requestUtils.Request;
import models.manager.responseUtils.Response;
import models.manager.responseUtils.responseObject.InvoiceDetailResponse;
import models.manager.responseUtils.responseObject.InvoiceDetailShortResponse;
import org.joda.time.DateTime;
import play.libs.Json;
import play.mvc.Result;

import java.util.List;

import static play.mvc.Controller.request;

/**
 * Created by drocha on 26/04/17.
 */
public class InvoiceDetailManagerImpl  implements InvoiceDetailManager {




    private static InvoiceDetailDao invoiceDetailDao = new InvoiceDetailDaoImpl();
    private static InvoiceDao invoiceDao = new InvoiceDaoImpl();
    private static ItemTypeDao itemTypeDao = new ItemTypeDaoImpl();
    private static LotDao lotDao = new LotDaoImpl();
    private static StoreDao storeDao = new StoreDaoImpl();

    @Override
    public Result create() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();


            JsonNode id_invoice = json.get("id_invoice");
            if (id_invoice == null)
                return Response.requiredParameter("id_invoice");


            JsonNode id_itemType = json.get("id_itemType");
            if (id_itemType == null)
                return Response.requiredParameter("id_itemType");


            JsonNode id_lot = json.get("id_lot");
            if (id_lot == null)
                return Response.requiredParameter("id_lot");

            JsonNode cost = json.get("cost");
            if (cost == null)
                return Response.requiredParameter("cost");

            JsonNode amount = json.get("amount");
            if (amount == null)
                return Response.requiredParameter("amount");

            JsonNode freight = json.get("freight");
            if (freight == null)
                return Response.requiredParameter("freight");

            JsonNode nameReceived = json.get("nameReceived");
            if (nameReceived == null)
                return Response.requiredParameter("nameReceived");

            JsonNode nameDelivered = json.get("nameDelivered");
            if (nameDelivered == null)
                return Response.requiredParameter("nameDelivered");

            JsonNode startDate =  Request.removeParameter(json, "startDate");;
            if (startDate == null)
                return Response.requiredParameter("startDate");


            // mapping object-json
            InvoiceDetail invoiceDetail = Json.fromJson(json, InvoiceDetail.class);


            JsonNode id_store = json.get("id_store");
            if (id_store != null)
                invoiceDetail.setStore(storeDao.findById(id_store.asLong()));

            invoiceDetail.setInvoice(invoiceDao.findById(id_invoice.asLong()));
            invoiceDetail.setItemType(itemTypeDao.findById(id_itemType.asLong()));
            invoiceDetail.setLot(lotDao.findById(id_lot.asLong()));

            DateTime startDatetime =  Request.dateTimeFormatter.parseDateTime(startDate.asText());


            invoiceDetail.setStartDate(startDatetime);

            invoiceDetail = invoiceDetailDao.create(invoiceDetail);
            return Response.createdEntity(Response.toJson(invoiceDetail, InvoiceDetailResponse.class));

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

          //  InvoiceDetail invoiceDetail =  Json.fromJson(json, InvoiceDetail.class);

            InvoiceDetail invoiceDetail = invoiceDetailDao.findById(id.asLong());

            JsonNode startDate =  Request.removeParameter(json, "startDate");
            if (startDate != null)
            {
                DateTime startDatetime =  Request.dateTimeFormatter.parseDateTime(startDate.asText());
                invoiceDetail.setStartDate(startDatetime);
            }
            JsonNode id_invoice = json.get("id_invoice");
            if (id_invoice != null)
                invoiceDetail.setInvoice(invoiceDao.findById(id_invoice.asLong()));

            JsonNode id_itemType = json.get("id_itemType");
            if (id_itemType != null)
                invoiceDetail.setItemType(itemTypeDao.findById(id_itemType.asLong()));

            JsonNode id_lot = json.get("id_lot");
            if (id_lot != null)
                invoiceDetail.setLot(lotDao.findById(id_lot.asLong()));

            JsonNode id_store = json.get("id_store");
            if (id_store != null)
                invoiceDetail.setStore(storeDao.findById(id_store.asLong()));

            invoiceDetail = invoiceDetailDao.update(invoiceDetail);
            return Response.updatedEntity(Response.toJson(invoiceDetail, InvoiceDetailShortResponse.class));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    @Override
    public Result delete(Long id) {
        try{
            InvoiceDetail invoiceDetail = invoiceDetailDao.findById(id);
            if(invoiceDetail != null) {

                invoiceDetail.setStatusDelete(1);
                invoiceDetail = invoiceDetailDao.update(invoiceDetail);

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

            InvoiceDetail invoiceDetail = findById(id);
            //    invoiceDetailDao.delete(id);
            return Response.deletedEntity();

        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }*/

    @Override
    public Result findById(Long id) {
        try {
            InvoiceDetail invoiceDetail = invoiceDetailDao.findById(id);
            return Response.foundEntity(Response.toJson(invoiceDetail, InvoiceDetailShortResponse.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @Override
    public Result findAll(Integer index, Integer size) {
        try {
            List<InvoiceDetail> invoiceDetails = invoiceDetailDao.findAll(index, size);
            return Response.foundEntity(Response.toJson(invoiceDetails, InvoiceDetailShortResponse.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }


    @Override
    public Result findAllByIdInvoice(Long IdInvoice) {
        try {
            List<InvoiceDetail> invoiceDetails = invoiceDetailDao.findAllByIdInvoice(IdInvoice);
            return Response.foundEntity(Response.toJson(invoiceDetails, InvoiceDetailShortResponse.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

}

