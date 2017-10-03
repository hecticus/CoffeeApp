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
import play.libs.Json;
import play.mvc.Result;
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
    private static InvoiceDetailPurityDao invoiceDetailPurityDao = new InvoiceDetailPurityDaoImpl();
    private static PurityDao purityDao = new PurityDaoImpl();
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

            DateTime startDatetime =  Request.dateTimeFormatter.parseDateTime((startDate.asText()));
            DateTime closedDatetime =  Request.dateTimeFormatter.parseDateTime((closedDate.asText()));



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
                invoice.setClosedDateInvoice(Request.dateTimeFormatter.parseDateTime((closedDate.asText())));
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

        JsonNode itemtypes = json.get("itemtypes");
        if (itemtypes == null)
            return Response.requiredParameter("itemtypes");

        JsonNode nameReceived = json.get("nameReceivedInvoiceDetail");
        if (nameReceived== null)
            return Response.requiredParameter("nameReceivedInvoiceDetail");

        JsonNode nameDelivered = json.get("nameDeliveredInvoiceDetail");
        if (nameDelivered==  null)
            return Response.requiredParameter("nameDeliveredInvoiceDetail");

        JsonNode startDate =  Request.removeParameter(json, "startDateInvoiceDetail");;
        if (startDate==  null)
            return Response.requiredParameter("startDateInvoiceDetail");

        JsonNode note = json.get("note");

        JsonNode freigh = json.get("freigh");
        if (freigh==  null)
            return Response.requiredParameter("freigh");

        JsonNode buyOption = json.get("buyOption");
        if (buyOption==  null)
            return Response.requiredParameter("buyOption");

        if(buyOption.asInt() !=1 && buyOption.asInt() != 2)
            return Response.message("buyOption: 1 for buy Harvests And 2 for buy Coffe");



       Invoice openInvoice = null;

        for (JsonNode itemtypeAux : itemtypes) {

            InvoiceDetailPurity invoiceDetailPurity = new InvoiceDetailPurity();
            JsonNode Amount = itemtypeAux.get("amount");
            if (Amount == null)
                return Response.requiredParameter("amount");

            JsonNode idItemtype = itemtypeAux.get("idItemType");
            if (idItemtype == null)
                return Response.requiredParameter("idItemType");

            ItemType itemType = itemTypeDao.findById(idItemtype.asLong());

            InvoiceDetail invoiceDetail = new InvoiceDetail();
            invoiceDetail.setItemType(itemType);

            if (buyOption.asInt() == 1)
            {

                JsonNode idLot = json.get("idLot");
                if (idLot == null)
                    return Response.requiredParameter("idLot");

                Lot lot = lotDao.findById(idLot.asLong());

                invoiceDetail.setLot(lot);
                invoiceDetail.setPriceItemTypeByLot(lot.getPrice_lot());
                monto = Amount.asInt() * lot.getPrice_lot();
            } else
            {
                JsonNode price = itemtypeAux.get("price");
                if (price == null)
                    return Response.requiredParameter("price");

                invoiceDetail.setCostItemType(price.asDouble());

                JsonNode id_store = itemtypeAux.get("id_store");
                if (id_store == null)
                    return Response.requiredParameter("id_store");

                invoiceDetail.setStore(storeDao.findById(id_store.asLong()));

                monto = Amount.asInt() * (float) price.asDouble();

                invoiceDetail.setPriceItemTypeByLot((float) 0.00);

                /*
                comentado por el nuevo caculo que viene en caos de compra
                monto = Amount.asInt() * itemType.getCostItemType();

                int Discount = (-1)*Math.round((monto*puritys.getDiscountRatePurity())/100);
                invoiceDetailPurity.setDiscountRatePurity(Discount);

                monto = monto+Discount;*/
            }

            invoiceDetail.setAmountInvoiceDetail(Amount.asInt());
            invoiceDetail.setFreightInvoiceDetail(freigh.asBoolean());
            invoiceDetail.setNameDeliveredInvoiceDetail(nameDelivered.asText());
            invoiceDetail.setNameReceivedInvoiceDetail(nameReceived.asText());
            invoiceDetail.setNoteInvoiceDetail(note.asText());

            DateTime startDatetime;
            startDatetime = Request.dateTimeFormatter.parseDateTime(startDate.asText());


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


            if (!invoices.isEmpty())
            {
                openInvoice = invoiceDao.update(openInvoice);
            }
            else
            {
                openInvoice = invoiceDao.create(openInvoice);
            }

            invoiceDetail.setInvoice(openInvoice);
            invoiceDetail = invoiceDetailDao.create(invoiceDetail);

            if(buyOption.asInt() == 2)
            {

                JsonNode purities = itemtypeAux.get("purities");
                if (purities == null)
                    return Response.requiredParameter("purities");
                for(JsonNode purity : purities)
                {
                    JsonNode idPurity = purity.get("idPurity");
                    if (idPurity == null)
                        return Response.requiredParameter("idPurity");

                    JsonNode valueRateInvoiceDetailPurity = purity.get("valueRateInvoiceDetailPurity");
                    if (valueRateInvoiceDetailPurity == null)
                        return Response.requiredParameter("valueRateInvoiceDetailPurity");

                    Purity puritys = purityDao.findById(idPurity.asLong());

                    invoiceDetailPurity.setPurity(puritys);
                    invoiceDetailPurity.setValueRateInvoiceDetailPurity(valueRateInvoiceDetailPurity.asInt());
                    invoiceDetailPurity.setInvoiceDetail(invoiceDetail);
                    invoiceDetailPurityDao.create(invoiceDetailPurity);
                }
            }

        }

        return Response.createdEntity(Json.toJson(openInvoice));
    }

    @Override
    public Result updateBuyHarvestsAndCoffe()
    {
        float monto=0;
        JsonNode json = request().body().asJson();
        if(json == null)
            return Response.requiredJson();

        JsonNode idInvoice = json.get("idInvoice");
        if (idInvoice == null)
            return Response.requiredParameter("idInvoice");

        JsonNode idProvider = json.get("idProvider");
        if (idProvider == null)
            return Response.requiredParameter("idProvider");

        JsonNode itemtypes = json.get("itemtypes");
        if (itemtypes == null)
            return Response.requiredParameter("itemtypes");

        JsonNode nameReceived = json.get("nameReceivedInvoiceDetail");
        if (nameReceived== null)
            return Response.requiredParameter("nameReceivedInvoiceDetail");

        JsonNode nameDelivered = json.get("nameDeliveredInvoiceDetail");
        if (nameDelivered==  null)
            return Response.requiredParameter("nameDeliveredInvoiceDetail");

        JsonNode startDate =  Request.removeParameter(json, "startDateInvoiceDetail");;
        if (startDate==  null)
            return Response.requiredParameter("startDateInvoiceDetail");

        JsonNode note = json.get("note");


        JsonNode freigh = json.get("freigh");
        if (freigh==  null)
            return Response.requiredParameter("freigh");

        JsonNode buyOption = json.get("buyOption");
        if (buyOption==  null)
            return Response.requiredParameter("buyOption");

        if(buyOption.asInt() !=1 && buyOption.asInt() != 2)
            return Response.message("buyOption: 1 for buy Harvests And 2 for buy Coffe");

        Invoice openInvoice = invoiceDao.findById(idInvoice.asLong());

        if(openInvoice.getStatusDelete()==1)
            return Response.message("no es posible modificar");

        for (JsonNode itemtypeAux : itemtypes) {


            JsonNode Amount = itemtypeAux.get("amount");
            if (Amount == null)
                return Response.requiredParameter("amount");

            JsonNode idItemtype = itemtypeAux.get("idItemType");
            if (idItemtype == null)
                return Response.requiredParameter("idItemType");

            JsonNode idInvoiceDetail = itemtypeAux.get("idInvoiceDetail");
            if (idInvoiceDetail == null)
                return Response.requiredParameter("idInvoiceDetail");

            ItemType itemType = itemTypeDao.findById(idItemtype.asLong());

            InvoiceDetail invoiceDetail = invoiceDetailDao.findById(idInvoiceDetail.asLong());  //new InvoiceDetail();
            invoiceDetail.setItemType(itemType);


            if (buyOption.asInt() == 1)
            {
                JsonNode idLot = json.get("idLot");
                if (idLot == null)
                    return Response.requiredParameter("idLot");

                Lot lot = lotDao.findById(idLot.asLong());

                invoiceDetail.setLot(lot);
                invoiceDetail.setPriceItemTypeByLot(lot.getPrice_lot());

                monto = Amount.asInt() * lot.getPrice_lot();
            } else
            {
                JsonNode price = itemtypeAux.get("price");
                if (price == null)
                    return Response.requiredParameter("price");

                invoiceDetail.setCostItemType(price.asDouble());

                JsonNode id_store = itemtypeAux.get("id_store");
                if (id_store == null)
                    return Response.requiredParameter("id_store");

                invoiceDetail.setStore(storeDao.findById(id_store.asLong()));

                monto = Amount.asInt() * (float) price.asDouble();
                /*
                monto = Amount.asInt() * itemType.getCostItemType();

                int Discount = (-1)*Math.round((monto*puritys.getDiscountRatePurity())/100);
                invoiceDetailPurity.setDiscountRatePurity(Discount);

                monto = monto+Discount;*/
            }

            invoiceDetail.setAmountInvoiceDetail(Amount.asInt());
            invoiceDetail.setFreightInvoiceDetail(freigh.asBoolean());
            invoiceDetail.setNameDeliveredInvoiceDetail(nameDelivered.asText());
            invoiceDetail.setNameReceivedInvoiceDetail(nameReceived.asText());
            invoiceDetail.setNoteInvoiceDetail(note.asText());

            DateTime startDatetime;
            startDatetime = Request.dateTimeFormatter.parseDateTime(startDate.asText());
            invoiceDetail.setStartDateInvoiceDetail(startDatetime);

            openInvoice.setStartDateInvoice(startDatetime);
            openInvoice.setClosedDateInvoice(startDatetime);
            openInvoice.setProvider(providerDao.findById(idProvider.asLong()));
            openInvoice.setTotalInvoice(monto+0.00);

            List<InvoiceDetail> invoiceDetails = openInvoice.getInvoiceDetails();

            invoiceDetails.add(invoiceDetail);
            openInvoice.setInvoiceDetails(invoiceDetails);

            openInvoice = invoiceDao.update(openInvoice);

            invoiceDetail.setInvoice(openInvoice);
            invoiceDetail = invoiceDetailDao.update(invoiceDetail);

            if(buyOption.asInt() == 2)
            {
               JsonNode purities = itemtypeAux.get("purities");
                if (purities == null)
                    return Response.requiredParameter("purities");
                for(JsonNode purity : purities)
                {
                    JsonNode idPurity = purity.get("idPurity");
                    if (idPurity == null)
                        return Response.requiredParameter("idPurity");

                    JsonNode valueRateInvoiceDetailPurity = purity.get("valueRateInvoiceDetailPurity");
                    if (valueRateInvoiceDetailPurity == null)
                        return Response.requiredParameter("valueRateInvoiceDetailPurity");

                    Purity puritys = purityDao.findById(idPurity.asLong());

                    InvoiceDetailPurity invoiceDetailPurity = invoiceDetailPurityDao.getByIdInvopiceDetailsByIdPurity(invoiceDetail.getIdInvoiceDetail(), idPurity.asLong());

                    invoiceDetailPurity.setPurity(puritys);
                    invoiceDetailPurity.setValueRateInvoiceDetailPurity(valueRateInvoiceDetailPurity.asInt());
                    invoiceDetailPurity.setInvoiceDetail(invoiceDetail);
                    invoiceDetailPurityDao.update(invoiceDetailPurity);
                }

            }

        }

        return Response.updatedEntity(Json.toJson(openInvoice));
    }



}

