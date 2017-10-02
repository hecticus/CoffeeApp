package com.hecticus.eleta.model.retrofit_interface;

import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.response.Message;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailsResponse;
import com.hecticus.eleta.model.response.invoice.InvoiceListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by roselyn545 on 18/9/17.
 */

public interface InvoiceRetrofitInterface {

    static final String INVOICES_LIST_URL = "/invoice/getByDateByTypeProvider/{date}/{typeProvider}/{index}/10";
    static final String INVOICE_DETAILS_URL = "/invoiceDetail/findAllByIdInvoice/{invoiceId}";
    static final String INVOICE_DELETE_URL = "invoice/{idInvoice}";
    static final String INVOICE_DETAIL_DELETE_URL = "invoiceDetail/deleteAllByIdInvoiceAndDate/{idInvoice}/{date}";
    static final String INVOICE_DETAIL_NEW_URL = "invoice/buyHarvestsAndCoffe";


    @GET(INVOICES_LIST_URL)
    Call<InvoiceListResponse> getInvoicesByDateByTypeProvider(@Path("date") String date,@Path("typeProvider") int typeProvider,@Path("index") int index);

    @GET(INVOICE_DETAILS_URL)
    Call<InvoiceDetailsResponse> getInvoiceDetails(@Path("invoiceId") int invoiceId);

    @DELETE(INVOICE_DELETE_URL)
    Call<Message> deleteInvoice(@Path("idInvoice") int idInvoice);

    @DELETE(INVOICE_DETAIL_DELETE_URL)
    Call<InvoiceDetailsResponse> deleteInvoiceDetail(@Path("idInvoice") int idInvoice,@Path("date") String date);

    @POST(INVOICE_DETAIL_NEW_URL)
    Call<Message> newInvoiceDetail(@Body InvoicePost post);

    @PUT(INVOICE_DETAIL_NEW_URL)
    Call<Message> updateInvoiceDetail(@Body InvoicePost post);

}