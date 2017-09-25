package models.manager.impl;

import com.avaje.ebean.text.PathProperties;
import models.dao.*;
import models.dao.impl.*;
import models.dao.utils.ListPagerCollection;
import models.domain.*;
import models.manager.responseUtils.*;
import org.joda.time.DateTime;
import com.fasterxml.jackson.databind.JsonNode;
import models.manager.InvoiceManager;
import models.manager.responseUtils.responseObject.InvoiceResponse;
import play.libs.Json;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

import static play.mvc.Controller.request;
import models.manager.requestUtils.Request;
/**
 * Created by drocha on 25/04/17.
 */
public class InvoiceManagerImpl  implements InvoiceManager
{


    private static InvoiceDao invoiceDao = new InvoiceDaoImpl();
    private static InvoiceDetailDao invoiceDetailDao = new InvoiceDetailDaoImpl();
    private static ProviderDao providerDao = new ProviderDaoImpl();
    private static LotDao lotDao = new LotDaoImpl();
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();
    private static ItemTypeDao itemTypeDao = new ItemTypeDaoImpl();
    private static StoreDao storeDao = new StoreDaoImpl();

    public InvoiceManagerImpl(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*)");
    }

    @Override
    public Result create() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();


            JsonNode id_provider = json.get("id_provider");
            if (id_provider == null)
                return Response.requiredParameter("id_provider");

            JsonNode startDate =  Request.removeParameter(json, "startDateInvoice");;
            if (startDate == null)
                return Response.requiredParameter("startDateInvoice");

            JsonNode closedDate =  Request.removeParameter(json, "closedDateInvoice");
            if (closedDate == null)
                return Response.requiredParameter("closedDateInvoice");

            JsonNode status = json.get("statusInvoice");
            if (status == null)
                return Response.requiredParameter("statusInvoice");

            JsonNode total = json.get("totalInvoice");
            if (total == null)
                return Response.requiredParameter("totalInvoice");

            DateTime startDatetime =  Request.dateFormatter.parseDateTime(startDate.asText());
            DateTime closedDatetime =  Request.dateFormatter.parseDateTime(closedDate.asText());



            // mapping object-json
            Invoice invoice = Json.fromJson(json, Invoice.class);

            invoice.setProvider(providerDao.findById(id_provider.asLong()));
            invoice.setStartDateInvoice(startDatetime);
            invoice.setClosedDateInvoice(closedDatetime);

            invoice = invoiceDao.create(invoice);
          //  return Response.createdEntity(Response.toJson(invoice, InvoiceResponse.class));
            return  Response.createdEntity(Json.toJson(invoice));

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

            JsonNode id = json.get("idInvoice");
            if (id == null)
                return Response.requiredParameter("idInvoice");


         //   Invoice invoice =  Json.fromJson(json, Invoice.class);

            Invoice invoice =   invoiceDao.findById(id.asLong());

            JsonNode id_provider = json.get("id_provider");
            if (id_provider != null)
                invoice.setProvider(providerDao.findById(id_provider.asLong()));

            JsonNode status = json.get("status");
            if (status != null)
            {
                invoice.setStatusInvoice(status.asInt());
                JsonNode closedDate =  Request.removeParameter(json, "closedDate");
                if (closedDate == null)
                {
                    return Response.requiredParameter("closedDate");
                }
                invoice.setClosedDateInvoice(Request.dateFormatter.parseDateTime(closedDate.asText()));
            }

            invoice = invoiceDao.update(invoice);
          //  return Response.updatedEntity(Response.toJson(invoice, InvoiceResponse.class));
            return  Response.updatedEntity(Json.toJson(invoice));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    @Override
    public Result delete(Long id) {
        try{
            Invoice invoice = invoiceDao.findById(id);
            if(invoice != null) {

                invoice.setStatusDelete(1);
                invoice.setStatusInvoice(3);
                invoice = invoiceDao.update(invoice);

                if(invoiceDao.deletedInvoice(id))
                    return  Response.message("Fallo ejecucion del store procedure");

                return Response.message("Entity Deleted");
            } else {
                return  Response.message("Successful no existe el registro a eliminar");
            }
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }
    /*public Result delete(Long id) {
        try {

            Invoice invoice = findById(id);
            //    invoiceDao.delete(id);
            return Response.deletedEntity();

        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }*/

    @Override
    public Result findById(Long id) {
        try {
            Invoice invoice = invoiceDao.findById(id);
            return Response.foundEntity(Json.toJson((invoice)));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

  /*  @Override
    public Result findAll(Integer index, Integer size) {
        try {
            List<Invoice> invoices = invoiceDao.findAll(index, size);
            return Response.foundEntity(Json.toJson((invoices)));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }*/

    @Override
    public Result getByDateByTypeProvider(String date, Integer typeProvider, Integer pageIndex, Integer pagesize)
    {
        try {
        //    List<Invoice> invoices = invoiceDao.getByDateByTypeProvider(date,typeProvider,pageIndex,pagesize);
        //    return Response.foundEntity(Json.toJson((invoices)));

            ListPagerCollection invoices = invoiceDao.getByDateByTypeProvider(date,typeProvider,pageIndex,pagesize);
            return ResponseCollection.foundEntity(invoices, null);
        }catch(Exception e) {
            e.printStackTrace();
            return Response.internalServerErrorLF();
        }

    }

    @Override
    public Result getByDateByProviderId(String date, Long providerId)
    {
        try {
            List<Invoice> invoices = invoiceDao.getByDateByProviderId(date,providerId);
            return Response.foundEntity(Json.toJson((invoices)));
        }catch(Exception e) {
            e.printStackTrace();
            return Response.internalServerErrorLF();
        }

    }

    public Result getOpenByProviderId(Long providerId)
    {
        try {
            List<Invoice> invoices = invoiceDao.getOpenByProviderId(providerId);
            return Response.foundEntity(Json.toJson(invoices));
        }catch(Exception e) {
            e.printStackTrace();
            return Response.internalServerErrorLF();
        }
    }

    @Override
    public Result findAll(Integer index, Integer size, String sort, String collection) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = invoiceDao.findAll(index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

    @Override
    public Result findAllSearch(String name, Integer index, Integer size, String sort, String collection) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = invoiceDao.findAllSearch( index, size, sort, pathProperties);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


    @Override
    public Result preCreate() {


        try {
            Provider provider = new Provider();
            Invoice invoice = new Invoice();
            invoice.setProvider(provider);

            return Response.foundEntity(
                    Json.toJson(invoice));
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

    @Override
    public Result buyHarvestsAndCoffe()
    {
        float monto;
        JsonNode json = request().body().asJson();
        if(json == null)
            return Response.requiredJson();

        JsonNode idProvider = json.get("idProvider");
        if (idProvider == null)
            return Response.requiredParameter("idProvider");

        JsonNode idLot = json.get("idLot");
        if (idLot == null)
            return Response.requiredParameter("idLot");

        JsonNode Amount = json.get("Amount");
        if (Amount == null)
            return Response.requiredParameter("Amount");

        JsonNode idItemtypes = json.get("idItemtypes");
        if (idItemtypes == null)
            return Response.requiredParameter("idItemtypes");

        JsonNode nameReceived = json.get("nameReceivedInvoiceDetail");
        if (nameReceived== null)
            return Response.requiredParameter("nameReceivedInvoiceDetail");

        JsonNode nameDelivered = json.get("nameDeliveredInvoiceDetail");
        if (nameDelivered==  null)
            return Response.requiredParameter("nameDeliveredInvoiceDetail");

        JsonNode startDate =  Request.removeParameter(json, "startDateInvoiceDetail");;
        if (startDate==  null)
            return Response.requiredParameter("startDateInvoiceDetail");

        JsonNode buyOption = json.get("buyOption");
        if (buyOption==  null)
            return Response.requiredParameter("buyOption");

        if(buyOption.asInt() !=1 && buyOption.asInt() != 2)
            return Response.message("buyOption: 1 for buy Harvests And 2 for buy Coffe");

        Lot lot = lotDao.findById(idLot.asLong());


        List<Long> aux_idItemtypes = new ArrayList<Long>();
        aux_idItemtypes = JsonUtils.toArrayLong(idItemtypes, "ids");


        Invoice openInvoice = null;
        for (Long idItemtype : aux_idItemtypes) {

            ItemType itemType = itemTypeDao.findById(idItemtype);

            InvoiceDetail invoiceDetail = new InvoiceDetail();
            invoiceDetail.setItemType(itemType);
            invoiceDetail.setLot(lot);
            invoiceDetail.setPriceItemTypeByLot(lot.getPrice_lot());

            if (buyOption.asInt() == 1) {
                monto = Amount.asInt() * lot.getPrice_lot();
            } else {
                monto = Amount.asInt() * itemType.getCostItemType();
            }

            invoiceDetail.setAmountInvoiceDetail(Amount.asInt());
            invoiceDetail.setFreightInvoiceDetail(false);
            invoiceDetail.setNameDeliveredInvoiceDetail(nameReceived.asText());
            invoiceDetail.setNameReceivedInvoiceDetail(nameDelivered.asText());

            JsonNode id_store = json.get("id_store");
            if (id_store != null)
                invoiceDetail.setStore(storeDao.findById(id_store.asLong()));

            DateTime startDatetime = Request.dateTimeFormatter.parseDateTime(startDate.asText());

            invoiceDetail.setStartDateInvoiceDetail(startDatetime);


            List<Invoice> invoices = invoiceDao.getOpenByProviderId(idProvider.asLong());


            if (!invoices.isEmpty()) {
                openInvoice = invoices.get(0);
            } else {
                openInvoice = new Invoice();
                openInvoice.setStartDateInvoice(startDatetime);
                openInvoice.setClosedDateInvoice(startDatetime);
                openInvoice.setProvider(providerDao.findById(idProvider.asLong()));
            }


            openInvoice.setTotalInvoice(monto + openInvoice.getTotalInvoice());

            List<InvoiceDetail> invoiceDetails = openInvoice.getInvoiceDetails();

            invoiceDetails.add(invoiceDetail);
            openInvoice.setInvoiceDetails(invoiceDetails);


            if (!invoices.isEmpty()) {
                openInvoice = invoiceDao.update(openInvoice);
                invoiceDetail.setInvoice(openInvoice);
                invoiceDetail = invoiceDetailDao.create(invoiceDetail);
       //         return Response.updatedEntity(Json.toJson(openInvoice));
            } else {
                openInvoice = invoiceDao.create(openInvoice);
                invoiceDetail.setInvoice(openInvoice);
                invoiceDetail = invoiceDetailDao.create(invoiceDetail);
         //       return Response.createdEntity(Json.toJson(openInvoice));

            }

        }

        return Response.createdEntity(Json.toJson(openInvoice));
    }


}

