package com.hecticus.eleta.purchases.list;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hecticus.eleta.R;
import com.hecticus.eleta.internet.InternetManager;
import com.hecticus.eleta.model_new.SessionManager;
import com.hecticus.eleta.model_new.persistence.ManagerDB;
import com.hecticus.eleta.model.response.Message;
import com.hecticus.eleta.model.response.harvest.HarvestOfDay;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailsResponse;
import com.hecticus.eleta.model.response.invoice.InvoiceListResponse;
import com.hecticus.eleta.model.response.invoice.ReceiptResponse;
import com.hecticus.eleta.model_new.retrofit_interface.InvoiceRetrofitInterface;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.Util;

import java.io.IOException;
import java.util.List;

import hugo.weaving.DebugLog;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by roselyn545 on 18/9/17.
 */

public class PurchasesListRepository implements PurchasesListContract.Repository {

    private final InvoiceRetrofitInterface invoiceApi;
    private final PurchasesListPresenter mPresenter;

    public PurchasesListRepository(PurchasesListPresenter presenterParam) {

        mPresenter = presenterParam;

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
                                        .addHeader("Authorization", SessionManager.getAccessToken(mPresenter.context))
                                        .addHeader("Content-Type", "application/json").build();
                                return chain.proceed(request);
                            }
                        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).
                        build();

        invoiceApi = retrofit.create(InvoiceRetrofitInterface.class);
    }

    @DebugLog
    private void manageError(String error, Response response, String cause) {
        try {

            if (response != null && response.code() == 400) {
                SessionManager.clearPreferences(mPresenter.context);
                mPresenter.invalidToken();
                return;
            }
            onError(error, cause);
        } catch (Exception e) {
            e.printStackTrace();
            onError(error, "New exception: " + e + " // Original cause: " + cause);
        }
    }

    @DebugLog
    @Override
    public void onError(String error, String cause) {
        mPresenter.onError(error, cause);
    }

    @DebugLog
    @Override
    public void getPurchasesRequest(final int index) {
        if (!InternetManager.isConnected(mPresenter.context)) {
            List<Invoice> invoiceList = ManagerDB.getAllInvoicesByType(Constants.TYPE_SELLER, Util.getCurrentDate());
            if (invoiceList != null) {
                mPresenter.handleSuccessfulPurchasesRequest(invoiceList);
            } else {
                onError(mPresenter.context.getString(R.string.error_getting_purchases), "getPurchasesRequest getAllInvoicesByType null list");
            }
        } else {
            Call<InvoiceListResponse> call = invoiceApi.getInvoicesByDateByTypeProvider(Util.getCurrentDate(), Constants.TYPE_SELLER, index, 10);//Util.getCurrentDate()//"2017-09-28"

            call.enqueue(new Callback<InvoiceListResponse>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<InvoiceListResponse> call,
                                       @NonNull Response<InvoiceListResponse> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null) {

                            Log.d("OFFLINE", "--->getPurchasesRequest (" + response.body().getResult().size() + ") Response: " + response.body());

                            ManagerDB.saveNewInvoicesByType(Constants.TYPE_SELLER, response.body().getResult());

                            List<Invoice> invoiceList = ManagerDB.getAllInvoicesByType(Constants.TYPE_SELLER, Util.getCurrentDate());
                            if (invoiceList != null) {
                                Log.d("OFFLINE", "--->getPurchasesRequest local after request: " + invoiceList.size());
                                mPresenter.handleSuccessfulPurchasesRequest(invoiceList);
                            } else {
                                Log.e("OFFLINE", "--->getPurchasesRequest local after request null list");
                                onError(mPresenter.context.getString(R.string.error_getting_purchases), "Got purchases from response but null in db");
                            }
                        } else
                            manageError(mPresenter.context.getString(R.string.error_getting_purchases), response, "Got bad purchases response: " + response);
                    } catch (Exception e) {
                        e.printStackTrace();
                        onError(mPresenter.context.getString(R.string.error_getting_purchases),
                                "Exception when handling purchases response: " + e + " // "
                                        + (response == null ? "Null response" : "Response body: " + response.body()));
                    }
                }

                @DebugLog
                @Override
                public void onFailure(@NonNull Call<InvoiceListResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_getting_purchases), "onFailure getting purchases: " + t);
                }
            });
        }
    }

    @DebugLog
    @Override
/*
    public void deletePurchase(int remoteInvoiceId, int localInvoiceId) {
        if (!InternetManager.isConnected(mPresenter.context)  || ManagerDB.invoiceHasOfflineOperation(remoteInvoiceId, localInvoiceId)) {
            if (ManagerDB.deleteHarvestInvoice(remoteInvoiceId, localInvoiceId))
*/
    public void deletePurchase(final int remoteInvoiceId, final int localInvoiceId) {
        if (!InternetManager.isConnected(mPresenter.context)) {
            if (ManagerDB.deleteHarvestOrPurchaseInvoice(remoteInvoiceId, localInvoiceId))
                mPresenter.onPurchaseDeleted();
            else
                onError(mPresenter.context.getString(R.string.error_deleting_purchase), "Cannot delete purchase from db");
        } else {

            Call<Message> call = invoiceApi.deleteInvoice(remoteInvoiceId);
            call.enqueue(new Callback<Message>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                    try {
                        if (response.isSuccessful()) {
                            // In case the purchase was created locally and it's not synced
                            ManagerDB.deleteHarvestOrPurchaseInvoice(remoteInvoiceId, localInvoiceId);

                            mPresenter.onPurchaseDeleted();
                        } else
                            manageError(mPresenter.context.getString(R.string.error_deleting_purchase), response, "deletePurchase bad response: " + response);
                    } catch (Exception e) {
                        e.printStackTrace();
                        onError(mPresenter.context.getString(R.string.error_deleting_purchase),
                                "Exception when handling deletePurchase response: " + e + " // "
                                        + (response == null ? "Null response" : "Response body: " + response.body()));
                    }
                }

                @DebugLog
                @Override
                public void onFailure(Call<Message> call, Throwable t) {
                    t.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_deleting_purchase), "deletePurchase failure: " + t);
                }
            });
        }
    }

    @DebugLog
    @Override
    public void onGetPurchasesSuccess(InvoiceListResponse purchasesList) {
        mPresenter.updatePager(purchasesList.getPager());
        mPresenter.handleSuccessfulPurchasesRequest(purchasesList.getResult());
    }

    @DebugLog
    @Override
    public void getReceiptDetails(Invoice invoiceParam) {

        if (!InternetManager.isConnected(mPresenter.context) || ManagerDB.invoiceHasOfflineOperation(invoiceParam)) {

            Log.d("PRINT", "--->getReceiptDetails FROM OFFLINE");

            List<HarvestOfDay> invoiceList = ManagerDB.getAllHarvestsOrPurchasesOfDayByInvoice(invoiceParam.getId(), invoiceParam.getLocalId());
            List<InvoiceDetails> detailsList = ManagerDB.getAllDetailsOfInvoiceByIdUnsorted(
                    invoiceParam.getId(),
                    invoiceParam.getLocalId(),
                    false);

            InvoiceDetailsResponse localResponse = new InvoiceDetailsResponse();

            if (invoiceList != null) {
                localResponse.setHarvestsList(invoiceList);
            } else {
                onError(mPresenter.context.getString(R.string.error_getting_information_to_print), "No offline invoice found");
            }

            if (detailsList != null) {
                localResponse.setDetailsList(detailsList);
            }

            //mPresenter.handleSuccessfulHarvestsOrPurchasesOfInvoiceRequest(localResponse);
            onGetReceiptDetailsSuccess(localResponse);

        } else {
            Log.d("PRINT", "--->getReceiptDetails FROM ONLINE");


            Call<InvoiceDetailsResponse> call = invoiceApi.getInvoiceDetails(invoiceParam.getId());

            call.enqueue(new Callback<InvoiceDetailsResponse>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<InvoiceDetailsResponse> call,
                                       @NonNull Response<InvoiceDetailsResponse> response) {

                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            onGetReceiptDetailsSuccess(response.body());
                        } else
                            manageError(mPresenter.context.getString(R.string.error_getting_information_to_print), response, "getReceiptDetails bad response: " + response);

                    } catch (Exception e) {
                        e.printStackTrace();
                        onError(mPresenter.context.getString(R.string.error_getting_information_to_print), "getReceiptDetails exception" + e);
                    }
                }

                @DebugLog
                @Override
                public void onFailure(@NonNull Call<InvoiceDetailsResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_getting_information_to_print), t + "");
                }
            });
        }
    }

    @DebugLog
    @Override
    public void onGetReceiptDetailsSuccess(InvoiceDetailsResponse detailsResponse) {
        mPresenter.onGetReceiptDetailsSuccess(detailsResponse);
    }

    @DebugLog
    @Override
    public void getReceiptOfInvoiceForPrinting(Invoice invoiceParam) {
        if (!InternetManager.isConnected(mPresenter.context) || ManagerDB.invoiceHasOfflineOperation(invoiceParam)) {

            ReceiptResponse fakeReceiptResponse = new ReceiptResponse();

            fakeReceiptResponse.setInvoice(invoiceParam);

            fakeReceiptResponse.setCompanyName(Constants.PrintingHeaderFallback.COMPANY_NAME);
            fakeReceiptResponse.setCompanyTelephone(Constants.PrintingHeaderFallback.COMPANY_TELEPHONE);
            fakeReceiptResponse.setInvoiceDescription(Constants.PrintingHeaderFallback.HARVEST_INVOICE_DESCRIPTION);
            fakeReceiptResponse.setInvoiceType(Constants.PrintingHeaderFallback.HARVEST_INVOICE_TYPE);
            fakeReceiptResponse.setRuc(Constants.PrintingHeaderFallback.PURCHASE_RUC);

            onGetReceiptSuccess(fakeReceiptResponse);

        } else {
            Call<ReceiptResponse> call = invoiceApi.getReceipt(invoiceParam.getId());

            call.enqueue(new Callback<ReceiptResponse>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<ReceiptResponse> call,
                                       @NonNull Response<ReceiptResponse> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            onGetReceiptSuccess(response.body());
                        } else
                            manageError(mPresenter.context.getString(R.string.error_getting_information_to_print), response, "getReceiptOfInvoiceForPrinting bad response: " + response);

                    } catch (Exception e) {
                        e.printStackTrace();
                        onError(mPresenter.context.getString(R.string.error_getting_information_to_print), "getReceiptOfInvoiceForPrinting exception: " + e);
                    }
                }

                @DebugLog
                @Override
                public void onFailure(@NonNull Call<ReceiptResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_getting_information_to_print), t + "");
                }
            });
        }

    }

    @DebugLog
    @Override
    public void onGetReceiptSuccess(ReceiptResponse receiptResponse) {
        mPresenter.onGetReceiptSuccess(receiptResponse);
    }
}