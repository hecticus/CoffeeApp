package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.utils.ListPagerCollection;
import controllers.parsers.queryStringBindable.Pager;
import io.ebean.text.PathProperties;
import models.*;
import models.requestUtils.Request;
import models.responseUtils.ExceptionsUtils;
import models.responseUtils.PropertiesCollection;
import models.responseUtils.Response;
import models.responseUtils.ResponseCollection;
import org.joda.time.DateTime;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.authorization.CoffeAppsecurity;

import java.util.List;

/**
 * Created by sm21 on 10/05/18.
 */
public class Invoices extends Controller {

    private static Invoice invoiceDao = new Invoice();
    private static InvoiceDetail invoiceDetailDao = new InvoiceDetail();
    private static InvoiceDetailPurity invoiceDetailPurityDao = new InvoiceDetailPurity();
    private static Purity purityDao = new Purity();
    private static Provider providerDao = new Provider();
    private static Lot lotDao = new Lot();
    private static PropertiesCollection propertiesCollection = new PropertiesCollection();
    private static ItemType itemTypeDao = new ItemType();
    private static Store storeDao = new Store();


    public Invoices(){
        propertiesCollection.putPropertiesCollection("s", "(id, name)");
        propertiesCollection.putPropertiesCollection("m", "(*" +
                ", provider(id_Provider, identificationDoc_Provider, fullName_Provider, address_Provider" +
                " phoneNumber_Provider,email_Provider, providerType(id_ProviderType, name_ProviderType))");
    }

//    @CoffeAppsecurity
    public  Result preCreate() {
        try {
            Provider provider = new Provider();
            Invoice invoice = new Invoice();
            invoice.setProvider(provider);

            return Response.foundEntity(Json.toJson(invoice));
        } catch (Exception e) {
            return ExceptionsUtils.find(e);
        }
    }

//    @CoffeAppsecurity
    public    Result create() {
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

            invoice.setProvider(Provider.findById(id_provider.asLong()));
            invoice.setStartDateInvoice(startDatetime);
            invoice.setClosedDateInvoice(closedDatetime);

            invoice.save();// = invoiceDao.create(invoice);
            //  return Response.createdEntity(Response.toJson(invoice, InvoiceResponse.class));
            return  Response.createdEntity(Json.toJson(invoice));

        }catch(Exception e){
            return Response.responseExceptionCreated(e);
        }
    }

    //@CoffeAppsecurity
    public  Result update() {
        try
        {
            JsonNode json = request().body().asJson();
            if(json == null)
                return Response.requiredJson();

            JsonNode id = json.get("idInvoice");
            if (id == null)
                return Response.requiredParameter("idInvoice");


            //   Invoice invoice =  Json.fromJson(json, Invoice.class);

            Invoice invoice =   Invoice.findById(id.asLong());

            JsonNode id_provider = json.get("id_provider");
            if (id_provider != null)
                invoice.setProvider(Provider.findById(id_provider.asLong()));



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

            invoice.update();// = invoiceDao.update(invoice);
            //  return Response.updatedEntity(Response.toJson(invoice, InvoiceResponse.class));
            return  Response.updatedEntity(Json.toJson(invoice));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    @CoffeAppsecurity
    public  Result delete(Long id) {

        try{
            Invoice invoice = Invoice.findById(id);
            if(invoice != null) {

                invoice.setStatusDelete(1);
                invoice.setStatusInvoice(3);
                invoice.update();// = invoiceDao.update(invoice);

                if(Invoice.deletedInvoice(id))
                    return  Response.message("Fallo ejecucion del store procedure");

                return Response.message("Entity Deleted");
            } else {
                return  Response.message("Successful no existe el registro a eliminar");
            }
        } catch (Exception e) {
            return Response.responseExceptionDeleted(e);
        }
    }

//    @CoffeAppsecurity
    public  Result findById(Long id) {
        try {
            Invoice invoice = Invoice.findById(id);
            return Response.foundEntity(Json.toJson((invoice)));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

//    @CoffeAppsecurity
    public   Result findAll(String name, Integer pageIndex, Integer pageSize, String sort, String collection, Long
                                            id_provider, Long id_providertype, String startDate, String endDate, Integer status, Integer all){
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = Invoice.findAll(name, pageIndex, pageSize, sort, pathProperties, id_provider, id_providertype, startDate, endDate, status, all);

            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }

    //    @CoffeAppsecurity
    public  Result findAllSearch(String name, Integer pageIndex, Integer pageSize, String sort, String collection, Long id_provider, Long id_providertype, String startDate, String endDate) {
        try {
            PathProperties pathProperties = propertiesCollection.getPathProperties(collection);
            ListPagerCollection listPager = Invoice.getByDateByTypeProvider(startDate, id_providertype,  pageIndex, pageSize);//, sort, pathProperties, id_provider, startDate, endDate);
            return ResponseCollection.foundEntity(listPager, pathProperties);
        }catch(Exception e){
            return ExceptionsUtils.find(e);
        }
    }


    @CoffeAppsecurity
    public  Result buyHarvestsAndCoffe(){
        float monto;
        InvoiceDetailPurity invoiceDetailPurity;
        JsonNode json = request().body().asJson();
        if(json == null)
            return Response.requiredJson();

        JsonNode idprovider = json.get("idProvider");
        Long idProvider;
        if (idprovider == null) {
            JsonNode identificationDocProvider = json.get("identificationDocProvider");
            if(identificationDocProvider == null){
                return Response.requiredParameter("identificationDocProvider or idProvider");
            }else{
                idProvider = Provider.getByIdentificationDoc(identificationDocProvider.asText()).getIdProvider();
            }
        }else{
            idProvider = idprovider.asLong();
        }
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


            JsonNode Amount = itemtypeAux.get("amount");
            if (Amount == null)
                return Response.requiredParameter("amount");

            JsonNode idItemtype = itemtypeAux.get("idItemType");
            if (idItemtype == null)
                return Response.requiredParameter("idItemType");

            ItemType itemType = ItemType.findById(idItemtype.asLong());

            InvoiceDetail invoiceDetail = new InvoiceDetail();
            invoiceDetail.setItemType(itemType);

            if (buyOption.asInt() == 1)
            {

                JsonNode idLot = json.get("idLot");
                if (idLot == null)
                    return Response.requiredParameter("idLot");

                Lot lot = Lot.findById(idLot.asLong());

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

                invoiceDetail.setStore(Store.findById(id_store.asLong()));

                monto = Amount.asInt() * (float) price.asDouble();

                invoiceDetail.setPriceItemTypeByLot((float) 0.00);

                /*
                comentado por el nuevo caculo que viene en caos de compra
                monto = Amount.asInt() * itemType.getCostItemType();

                int Discount = (-1)*Math.round((monto*puritys.getDiscountRatePurity())/100);
                invoiceDetailPurity.setDiscountRatePurity(Discount);

                monto = monto+Discount;*/
            }

            invoiceDetail.setAmountInvoiceDetail(Amount.floatValue());
            invoiceDetail.setFreightInvoiceDetail(freigh.asBoolean());
            invoiceDetail.setNameDeliveredInvoiceDetail(nameDelivered.asText());
            invoiceDetail.setNameReceivedInvoiceDetail(nameReceived.asText());
            invoiceDetail.setNoteInvoiceDetail(note.asText());

            DateTime startDatetime;
            startDatetime = Request.dateTimeFormatter.parseDateTime(startDate.asText());


            invoiceDetail.setStartDateInvoiceDetail(startDatetime);

            List<Invoice> invoices = invoiceDao.getOpenByProviderId(idProvider);

            if (!invoices.isEmpty() && invoices.get(0).getStartDateInvoice().toString("yyyy-MM-dd").equals(startDatetime.toString("yyyy-MM-dd"))) {
                openInvoice = invoices.get(0);
            } else {
                openInvoice = new Invoice();
                openInvoice.setStartDateInvoice(startDatetime);
                openInvoice.setClosedDateInvoice(startDatetime);
                openInvoice.setProvider(Provider.findById(idProvider));
            }


            openInvoice.setTotalInvoice(monto + openInvoice.getTotalInvoice());

            List<InvoiceDetail> invoiceDetails = openInvoice.getInvoiceDetails();

            invoiceDetails.add(invoiceDetail);
            openInvoice.setInvoiceDetails(invoiceDetails);


            if (!invoices.isEmpty() && invoices.get(0).getStartDateInvoice().toString("yyyy-MM-dd").equals(startDatetime.toString("yyyy-MM-dd")))
            {
                openInvoice.update();// = invoiceDao.update(openInvoice);
            }
            else
            {
                openInvoice.save();// = invoiceDao.create(openInvoice);
            }

            invoiceDetail.setInvoice(openInvoice);
            invoiceDetail.save();// = invoiceDetailDao.create(invoiceDetail);

            if(buyOption.asInt() == 2)
            {

                JsonNode purities = itemtypeAux.get("purities");
                if (purities == null)
                    return Response.requiredParameter("purities");
                for(JsonNode purity : purities)
                {
                    invoiceDetailPurity = new InvoiceDetailPurity();
                    JsonNode idPurity = purity.get("idPurity");
                    if (idPurity == null)
                        return Response.requiredParameter("idPurity");

                    JsonNode valueRateInvoiceDetailPurity = purity.get("valueRateInvoiceDetailPurity");
                    if (valueRateInvoiceDetailPurity == null)
                        return Response.requiredParameter("valueRateInvoiceDetailPurity");

                    Purity puritys = Purity.findById(idPurity.asLong());

                    invoiceDetailPurity.setPurity(puritys);
                    invoiceDetailPurity.setValueRateInvoiceDetailPurity(valueRateInvoiceDetailPurity.asInt());
                    invoiceDetailPurity.setInvoiceDetail(invoiceDetail);
                    invoiceDetailPurityDao.save();//.create(invoiceDetailPurity);
                }
            }

        }

        openInvoice.setTotalInvoice(invoiceDao.calcularTotalInvoice(openInvoice.getIdInvoice()));
        openInvoice.update();
//        invoiceDao.update(openInvoice);
        return Response.createdEntity(Json.toJson(openInvoice));
    }

    @CoffeAppsecurity
    public  Result updateBuyHarvestsAndCoffe() {
        float monto=0;
        boolean auxCreate = false;
        InvoiceDetailPurity invoiceDetailPurity;
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

            invoiceDetail.setAmountInvoiceDetail(Amount.floatValue());
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

            openInvoice.update();// = invoiceDao.update(openInvoice);

            invoiceDetail.setInvoice(openInvoice);
            invoiceDetail.update();// = invoiceDetailDao.update(invoiceDetail);

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

                    invoiceDetailPurity = invoiceDetailPurityDao.getByIdInvopiceDetailsByIdPurity(invoiceDetail.getIdInvoiceDetail(), idPurity.asLong());

                    if(invoiceDetailPurity==null)
                    {
                        invoiceDetailPurity = new InvoiceDetailPurity();
                        auxCreate=true;
                    }

                    invoiceDetailPurity.setPurity(puritys);
                    invoiceDetailPurity.setValueRateInvoiceDetailPurity(valueRateInvoiceDetailPurity.asInt());
                    invoiceDetailPurity.setInvoiceDetail(invoiceDetail);
                    if(auxCreate) invoiceDetailPurity.save();//invoiceDetailPurityDao.create(invoiceDetailPurity);
                    else invoiceDetailPurity.update();//invoiceDetailPurityDao.update(invoiceDetailPurity);
                }

            }

        }

        openInvoice.setTotalInvoice(invoiceDao.calcularTotalInvoice(openInvoice.getIdInvoice()));
        openInvoice.update();
//        invoiceDao.update(openInvoice);
        return Response.updatedEntity(Json.toJson(openInvoice));
    }

    @CoffeAppsecurity
    public  Result createReceipt(Long idInvoice)  {
        Invoice invoice = invoiceDao.findById(idInvoice);
        ObjectNode response = Json.newObject();
        ((ObjectNode) response).put("nameCompany", Config.getString("nameCompany"));
        ((ObjectNode) response).put("invoiceDescription", Config.getString("invoiceDescription"));
        ((ObjectNode) response).put("invoiceType", Config.getString("invoiceType"));
        ((ObjectNode) response).put("RUC", Config.getString("RUC"));
        ((ObjectNode) response).put("telephonoCompany", Config.getString("telephonoCompany"));
        ((ObjectNode) response).put("invoice", Json.toJson(invoice));

        return Response.createdEntity(response);    }



}
