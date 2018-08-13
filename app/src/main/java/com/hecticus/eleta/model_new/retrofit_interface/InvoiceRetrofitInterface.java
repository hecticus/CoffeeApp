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

import okhttp3.ResponseBody;
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

    //listo
    @GET("invoice")//getByDateByTypeProvider/{date}/{typeProvider}/{index}/10")
    Call<InvoiceListResponse> getInvoicesByDateByTypeProvider(@Query("startDate") String date, @Query("typeProvider") int typeProvider/*, @Query("pageindex") int index, @Query("pagesize") int size*/);

    //listo con sincronizacion
    @DELETE("invoice/{id}")
    Call<Message> deleteInvoice(@Path("id") int idInvoice);

    //listo sincroniza, pero si edite no sincroniza
    @POST("invoice2")//{idProvider}/{date}")
    Call<CreateInvoiceResponse> newInvoiceDetail(@Body Invoice post/*, @Path("idProvider") int idProvider, @Path("date") String date*/); //supongo q lleva invoice y no invoice post

    @POST("invoiceDetail")//{idProvider}/{date}")
    Call<ResponseBody> newInvoiceDetailAdd(@Body InvoiceDetail post/*, @Path("idProvider") int idProvider, @Path("date") String date*/); //supongo q lleva invoice y no invoice post


    //listo sin sincronizacion
    @PUT("invoice/{id}")
    Call<Message> closeInvoice(@Path("id") int invoiceId, @Body Invoice post);

    //listo todo no se si cuando se llama dentro del save invoice se guardan los invoice details
    @GET("invoiceDetail")//findAllByIdInvoice/{invoiceId}")
    Call<InvoiceDetailsResponse> getInvoiceDetails(@Query("invoice") int invoiceId);

    //listo sin sincronizacion
    @DELETE("invoiceDetail/{id}")
    Call<InvoiceDetailsResponse> deleteInvoiceDetail(@Path("id") int idInvoice);

    //listo sin sincronizacion
    @PUT("invoiceDetail/{id}")
    Call<CreateInvoiceResponse> updateInvoiceDetailNewEndpoint(@Path("id") int invoiceId, @Body InvoiceDetail post);

    @PUT("invoiceDetail/{id}")
    Call<ResponseBody> updateInvoiceDetail(@Path("id") int invoiceId, @Body InvoiceDetail post);

    //listo creo no necesita sincronizacion
    @GET("invoice/createReceipt/{id}")
    Call<ReceiptResponse> getReceipt(@Path("id") int invoiceId);



}