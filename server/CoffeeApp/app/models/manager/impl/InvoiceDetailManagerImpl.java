package models.manager.impl;

import com.avaje.ebean.text.PathProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.dao.*;
import models.dao.impl.*;
import models.dao.utils.ListPagerCollection;
import models.domain.Invoice;
import models.domain.InvoiceDetail;
import models.domain.ItemType;
import models.domain.Lot;
import models.domain.Store;
import models.manager.InvoiceDetailManager;
import models.manager.requestUtils.Request;
import models.manager.responseUtils.ExceptionsUtils;
import models.manager.responseUtils.PropertiesCollection;
import models.manager.responseUtils.Response;
import models.manager.responseUtils.ResponseCollection;
import org.joda.time.DateTime;
import play.libs.Json;
import play.mvc.Result;

import java.sql.Timestamp;
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
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();

    public InvoiceDetailManagerImpl(){
        propertiesCollection.putPropertiesCollection("s", "(*)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

    @Override
    public Result create() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json== null)
                return Response.requiredJson();


            JsonNode id_invoice = json.get("id_invoice");
            if (id_invoice==  null)
                return Response.requiredParameter("id_invoice");


            JsonNode id_itemType = json.get("id_itemType");
            if (id_itemType== null)
                return Response.requiredParameter("id_itemType");


            JsonNode id_lot = json.get("id_lot");
            if (id_lot== null)
                return Response.requiredParameter("id_lot");

            JsonNode priceItemTypeByLot = json.get("priceItemTypeByLot");
            if (priceItemTypeByLot==  null)
                return Response.requiredParameter("priceItemTypeByLot");

            JsonNode amount = json.get("amountInvoiceDetail");
            if (amount== null)
                return Response.requiredParameter("amountInvoiceDetail");

            JsonNode freight = json.get("freightInvoiceDetail");
            if (freight==  null)
                return Response.requiredParameter("freightInvoiceDetail");

            JsonNode nameReceived = json.get("nameReceivedInvoiceDetail");
            if (nameReceived==  null)
                return Response.requiredParameter("nameReceivedInvoiceDetail");

            JsonNode nameDelivered = json.get("nameDeliveredInvoiceDetail");
            if (nameDelivered==  null)
                return Response.requiredParameter("nameDeliveredInvoiceDetail");

            JsonNode startDate =  Request.removeParameter(json, "startDateInvoiceDetail");;
            if (startDate==  null)
                return Response.requiredParameter("startDateInvoiceDetail");


            // mapping object-json
            InvoiceDetail invoiceDetail = Json.fromJson(json, InvoiceDetail.class);


            JsonNode id_store = json.get("id_store");
            if (id_store != null)
                invoiceDetail.setStore(storeDao.findById(id_store.asLong()));

            invoiceDetail.setInvoice(invoiceDao.findById(id_invoice.asLong()));
            invoiceDetail.setItemType(itemTypeDao.findById(id_itemType.asLong()));
            invoiceDetail.setLot(lotDao.findById(id_lot.asLong()));

            DateTime startDatetime =  Request.dateTimeFormatter.parseDateTime(startDate.asText());


            invoiceDetail.setStartDateInvoiceDetail(startDatetime);

            invoiceDetail = invoiceDetailDao.create(invoiceDetail);
          //  return Response.createdEntity(Response.toJson(invoiceDetail, InvoiceDetail.class));
            return  Response.createdEntity(Json.toJson(invoiceDetail));

        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

    @Override
    public Result update() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json== null)
                return Response.requiredJson();

            JsonNode id = json.get("idInvoiceDetail");
            if (id == null)
                return Response.requiredParameter("idInvoiceDetail");

            InvoiceDetail invoiceDetail = invoiceDetailDao.findById(id.asLong());

            JsonNode priceItemTypeByLot = json.get("priceItemTypeByLot");
            if (priceItemTypeByLot!= null)
                invoiceDetail.setPriceItemTypeByLot(Float.parseFloat(priceItemTypeByLot.asText()));

            JsonNode amount = json.get("amountInvoiceDetail");
            if (amount!= null)
                invoiceDetail.setAmountInvoiceDetail(amount.asInt());

            JsonNode freight = json.get("freightInvoiceDetail");
            if (freight!= null)
                invoiceDetail.setFreightInvoiceDetail(freight.asBoolean());

            JsonNode nameReceived = json.get("nameReceivedInvoiceDetail");
            if (nameReceived!= null)
                invoiceDetail.setNameReceivedInvoiceDetail(nameReceived.asText());

            JsonNode nameDelivered = json.get("nameDeliveredInvoiceDetail");
            if (nameDelivered!= null)
                invoiceDetail.setNameDeliveredInvoiceDetail(nameDelivered.asText());

            JsonNode startDate =  Request.removeParameter(json, "startDateInvoiceDetail");
            if (startDate != null)
            {
                DateTime startDatetime =  Request.dateTimeFormatter.parseDateTime(startDate.asText());
                invoiceDetail.setStartDateInvoiceDetail(startDatetime);
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
           // return Response.updatedEntity(Response.toJson(invoiceDetail, InvoiceDetail.class));
            return  Response.updatedEntity(Json.toJson(invoiceDetail));

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
            return Response.foundEntity(Response.toJson(invoiceDetail, InvoiceDetail.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

 /*   @Override
    public Result findAll(Integer index, Integer size) {
        try {
            List<InvoiceDetail> invoiceDetails = invoiceDetailDao.findAll(index, size);
            return Response.foundEntity(Response.toJson(invoiceDetails, InvoiceDetail.class));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

*/
    @Override
    public Result findAllByIdInvoice(Long IdInvoice) {
        try {
            ObjectNode result;
            JsonNode aux1;
            List<InvoiceDetail> aux2;
            result = Json.newObject();

            List<InvoiceDetail> invoiceDetails = invoiceDetailDao.findAllByIdInvoice(IdInvoice);
            result.set("summary",invoiceDetailDao.findAllByIdInvoiceSummary(IdInvoice));
            result.set("deatils",Json.toJson(invoiceDetails));

            return Response.foundEntity(result);

        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @Override
    public Result deleteAllByIdInvoiceAndDate( Long IdInvoice, String  date)
    {
        try {
             List<InvoiceDetail> invoiceDetails;

             int result = invoiceDetailDao.deleteAllByIdInvoiceAndDate(IdInvoice,date);

             return this.findAllByIdInvoice(IdInvoice);

        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @Override
    public Result findAll(Integer index, Integer size, String sort, String collection) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = invoiceDetailDao.findAll(index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

    @Override
    public Result findAllSearch(String name, Integer index, Integer size, String sort, String collection) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = invoiceDetailDao.findAllSearch(index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


    @Override
    public Result preCreate() {


        try {

            InvoiceDetail invoiceDetail = new InvoiceDetail();
            Invoice invoice = new Invoice();
            ItemType itemType = new ItemType ();
            Store store = new Store();
            Lot lot = new Lot();

            invoiceDetail.setInvoice(invoice);
            invoiceDetail.setItemType(itemType);
            invoiceDetail.setStore(store);
            invoiceDetail.setLot(lot);

            return Response.foundEntity(
                    Json.toJson(invoiceDetail));
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

}

