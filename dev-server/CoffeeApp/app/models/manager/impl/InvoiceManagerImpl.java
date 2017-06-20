package models.manager.impl;

import models.domain.Invoice;
import org.joda.time.DateTime;
import com.fasterxml.jackson.databind.JsonNode;
import models.dao.InvoiceDao;
import models.dao.impl.InvoiceDaoImpl;
import models.dao.ProviderDao;
import models.dao.impl.ProviderDaoImpl;
import models.manager.InvoiceManager;
import models.manager.responseUtils.Response;
import models.manager.responseUtils.responseObject.InvoiceResponse;
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
    private static ProviderDao providerDao = new ProviderDaoImpl();

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

            JsonNode startDate =  Request.removeParameter(json, "startDate");;
            if (startDate == null)
                return Response.requiredParameter("startDate");

            JsonNode closedDate =  Request.removeParameter(json, "closedDate");
            if (closedDate == null)
                return Response.requiredParameter("closedDate");

            JsonNode status = json.get("status");
            if (status == null)
                return Response.requiredParameter("status");

            JsonNode total = json.get("total");
            if (total == null)
                return Response.requiredParameter("total");

            DateTime startDatetime =  Request.dateFormatter.parseDateTime(startDate.asText());
            DateTime closedDatetime =  Request.dateFormatter.parseDateTime(closedDate.asText());



            // mapping object-json
            Invoice invoice = Json.fromJson(json, Invoice.class);

            invoice.setProvider(providerDao.findById(id_provider.asLong()));
            invoice.setStartDateInvoice(startDatetime);
            invoice.setClosedDateInvoice(closedDatetime);

            invoice = invoiceDao.create(invoice);
            return Response.createdEntity(Response.toJson(invoice, InvoiceResponse.class));

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
            return Response.updatedEntity(Response.toJson(invoice, InvoiceResponse.class));

        }catch(Exception e){
            return Response.responseExceptionUpdated(e);
        }
    }

    @Override
    public Result delete(Long id) {
        try{
            Invoice invoice = invoiceDao.findById(id);
            if(invoice != null) {

   //             invoice.setStatusDelete(1);
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

    @Override
    public Result findAll(Integer index, Integer size) {
        try {
            List<Invoice> invoices = invoiceDao.findAll(index, size);
            return Response.foundEntity(Json.toJson((invoices)));
        }catch(Exception e){
            return Response.internalServerErrorLF();
        }
    }

    @Override
    public Result getByDateByTypeProvider(String date, Integer typeProvider)
    {
        try {
            List<Invoice> invoices = invoiceDao.getByDateByTypeProvider(date,typeProvider);
            return Response.foundEntity(Json.toJson((invoices)));
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

}

