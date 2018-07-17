package com.hecticus.eleta.model_new.retrofit_interface;

import com.hecticus.eleta.model.request.invoice.CloseInvoicePost;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.response.Message;
import com.hecticus.eleta.model.response.invoice.CreateInvoiceResponse;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailsResponse;
import com.hecticus.eleta.model.response.invoice.InvoiceListResponse;
import com.hecticus.eleta.model.response.invoice.ReceiptResponse;
import com.hecticus.eleta.model_new.Invoice;
import com.hecticus.eleta.model_new.InvoiceDetail;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by roselyn545 on 18/9/17.
 */

public interface InvoiceRetrofitInterface {

    static final String INVOICES_LIST_URL = "/invoice/getByDateByTypeProvider/{date}/{typeProvider}/{index}/10";
    static final String INVOICE_DETAILS_URL = "/invoiceDetail/findAllByIdInvoice/{invoiceId}";
    static final String INVOICE_DELETE_URL = "invoice/{idInvoice}";
    static final String INVOICE_DETAIL_DELETE_URL = "invoiceDetail/deleteAllByIdInvoiceAndDate/{idInvoice}/{date}";
    static final String INVOICE_DETAIL_NEW_URL = "invoice/buyHarvestsAndCoffe";
    static final String CREATE_RECEIPT_URL = "invoice/createReceipt/{idInvoice}";
    static final String INVOICE_CLOSE_URL = "invoice";

     /*@GET("/invoice/getByDateByTypeProvider/{date}/{typeProvider}/{index}/10")
    Call<InvoiceListResponse> getInvoicesByDateByTypeProvider(@Path("date") String date,@Path("typeProvider") int typeProvider,@Path("index") int index);

    @GET("/invoiceDetail/findAllByIdInvoice/{invoiceId}")
    Call<InvoiceDetailsResponse> getInvoiceDetails(@Path("invoiceId") int invoiceId);

    @DELETE("invoice/{idInvoice}")
    Call<Message> deleteInvoice(@Path("idInvoice") int idInvoice);

    @DELETE("invoiceDetail/deleteAllByIdInvoiceAndDate/{idInvoice}/{date}")
    Call<InvoiceDetailsResponse> deleteInvoiceDetail(@Path("idInvoice") int idInvoice,@Path("date") String date);

    @POST("invoice/buyHarvestsAndCoffe")
    Call<CreateInvoiceResponse> newInvoiceDetail(@Body InvoicePost post);

    @PUT("invoice/buyHarvestsAndCoffe")
    Call<CreateInvoiceResponse> updateInvoiceDetail(@Body InvoicePost post);

    @GET("invoice/createReceipt/{idInvoice}")
    Call<ReceiptResponse> getReceipt(@Path("idInvoice") int invoiceId);

    @PUT("invoice")
    Call<Message> closeInvoice(@Body CloseInvoicePost post);*/

     /*
GET     /invoice                                                                controllers.Invoices.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null,sort: String ?= null, providerId: Long ?= 0L, typeProvider: Long ?= 0L, startDate: String ?= null, endDate: String ?= null, statusInvoice: Long ?= 0L, deleted: Boolean ?= false)
GET     /invoice/:id                                                            controllers.Invoices.findById(id : Long)
#GET     /invoice/createReceipt/:id                                              controllers.Invoices.createReceipt(id : Long)
#POST    /invoice/buyHarvestsAndCoffe                                            controllers.Invoices.buyHarvestsAndCoffe()
#PUT     /invoice/buyHarvestsAndCoffe                                            controllers.Invoices.updateBuyHarvestsAndCoffe()
POST    /invoice/delete                                                         controllers.Invoices.deletes()
POST    /invoice                                                                controllers.Invoices.create()
PUT     /invoice/:id                                                            controllers.Invoices.update(id : Long)
DELETE  /invoice/:id                                                            controllers.Invoices.delete(id : Long)

     */

     /*
GET     /invoiceDetail                                                          controllers.InvoiceDetails.findAll(pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null,  sort: String ?= null, invoice: Long ?= 0L, itemType: Long ?= 0L, lot: Long ?= 0L, store: Long ?= 0L,  nameDelivered: String ?= null, nameReceived: String ?= null, startDate: String ?= null,  statusInvoiceDetail: Long ?= 0L, deleted: Boolean ?= false)
GET     /invoiceDetail/:id                                                      controllers.InvoiceDetails.findById(id : Long)
POST    /invoiceDetail                                                          controllers.InvoiceDetails.create()
POST    /invoiceDetail/delete                                                   controllers.InvoiceDetails.deletes()
PUT     /invoiceDetail/:id                                                      controllers.InvoiceDetails.update(id : Long)
DELETE  /invoiceDetail/:id                                                      controllers.InvoiceDetails.delete(id : Long)
      */


    //todo cambio
    @GET("invoice")//getByDateByTypeProvider/{date}/{typeProvider}/{index}/10")
    Call<InvoiceListResponse> getInvoicesByDateByTypeProvider(@Query("startDate") String date, @Query("typeProvider") int typeProvider/*, @Query("pageindex") int index, @Query("pagesize") int size*/);

    //todo cambio
    @GET("invoiceDetail")///findAllByIdInvoice/{invoiceId}")
    Call<InvoiceDetailsResponse> getInvoiceDetails(@Query("invoice") int invoiceId);

    @DELETE("invoice/{id}")
    Call<Message> deleteInvoice(@Path("id") int idInvoice);

    //@DELETE("invoiceDetail/deleteAllByIdInvoiceAndDate/{idInvoice}/{date}") todo cambio
    @DELETE("invoiceDetail/{id}")//pasarle el array de ids estoy pasando los un array vacio
    Call<InvoiceDetailsResponse> deleteInvoiceDetail(@Path("id") int idInvoice/*,@Path("date") String date, @Body ArrayList<Long> ids*/);

    @POST("invoice2")//{idProvider}/{date}")
    Call<CreateInvoiceResponse> newInvoiceDetail(@Body Invoice post/*, @Path("idProvider") int idProvider, @Path("date") String date*/); //supongo q lleva invoice y no invoice post

    /*@PUT("invoice")
    Call<CreateInvoiceResponse> updateInvoiceDetail(@Body InvoicePost post);*/

    /*@PUT("invoiceDetail/{id}")
    Call<CreateInvoiceResponse> updateInvoiceDetail(@Body InvoicePost post);*/

    @PUT("invoiceDetail/{id}")
    Call<CreateInvoiceResponse> updateInvoiceDetailNewEndpoint(@Path("id") int invoiceId, @Body InvoiceDetail post);

    @GET("invoice/createReceipt/{id}")
    Call<ReceiptResponse> getReceipt(@Path("id") int invoiceId);

    //todo cambio
    @PUT("invoice/{id}")
    Call<Message> closeInvoice(@Path("id") int invoiceId, @Body Invoice post);

}