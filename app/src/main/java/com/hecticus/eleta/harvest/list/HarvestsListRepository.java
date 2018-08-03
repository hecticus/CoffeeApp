package com.hecticus.eleta.harvest.list;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hecticus.eleta.R;
import com.hecticus.eleta.internet.InternetManager;
import com.hecticus.eleta.model_new.SessionManager;
import com.hecticus.eleta.model_new.persistence.ManagerDB;
import com.hecticus.eleta.model.response.Message;
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
 * Created by roselyn545 on 15/9/17.
 */

public class HarvestsListRepository implements HarvestsListContract.Repository {

    private final InvoiceRetrofitInterface harvestApi;
    private final HarvestsListPresenter mPresenter;

    public HarvestsListRepository(HarvestsListPresenter presenterParam) {

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

        harvestApi = retrofit.create(InvoiceRetrofitInterface.class);
    }

    @DebugLog
    @Override
    public void onError(String error) {
        mPresenter.onError(error);
    }

    @DebugLog
    private void manageError(String error, Response response) {
        try {
            if (response != null && response.code() == 400) {
                SessionManager.clearPreferences(mPresenter.context);
                mPresenter.invalidToken();
                return;
            }
            onError(error);
        } catch (Exception e) {
            e.printStackTrace();
            onError(error);
        }
    }

    @DebugLog
    @Override
    public void getHarvestsRequest(final int index) {
        if (!InternetManager.isConnected(mPresenter.context)) {
            List<Invoice> invoiceList = ManagerDB.getAllInvoicesByType(Constants.TYPE_HARVESTER, Util.getCurrentDate());
            if (invoiceList != null) {
                mPresenter.handleSuccessfulMixedHarvestsRequest(invoiceList);
            } else {
                onError(mPresenter.context.getString(R.string.error_getting_harvests));
            }
        } else {
            Call<InvoiceListResponse> call = harvestApi.getInvoicesByDateByTypeProvider(Util.getCurrentDate(), Constants.TYPE_HARVESTER/*, index, 10*/);//Util.getCurrentDate()//"2017-09-25"

            call.enqueue(new Callback<InvoiceListResponse>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<InvoiceListResponse> call,
                                       @NonNull Response<InvoiceListResponse> response) {

                    try {
                        if (response.isSuccessful() && response.body() != null) {

                            Log.d("OFFLINE", "--->getHarvestsRequest (" + response.body().getResult().size() + ") Response: " + response.body());

                            ManagerDB.saveNewInvoicesByType(Constants.TYPE_HARVESTER, response.body().getResult());

                            List<Invoice> invoiceList = ManagerDB.getAllInvoicesByType(Constants.TYPE_HARVESTER, Util.getCurrentDate());

                            if (invoiceList != null) {
                                Log.d("OFFLINE", "--->getHarvestsRequest local after request: " + invoiceList.size());
                                mPresenter.handleSuccessfulMixedHarvestsRequest(invoiceList);
                            } else {
                                Log.e("OFFLINE", "--->getHarvestsRequest local after request null list");
                                onError(mPresenter.context.getString(R.string.error_getting_harvests));

                            }
                            //onGetHarvestsSuccess(response.body());//todo eliminar
                        } else {
                            manageError(mPresenter.context.getString(R.string.error_getting_harvests), response);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        onError(mPresenter.context.getString(R.string.error_getting_harvests));
                    }
                }

                @DebugLog
                @Override
                public void onFailure(@NonNull Call<InvoiceListResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_getting_harvests));
                }
            });
        }
    }

    @DebugLog
    @Override
    public void deleteHarvest(final Invoice harvestInvoice) {

        final int remoteInvoiceId = harvestInvoice.getInvoiceId();
        final int localInvoiceId = harvestInvoice.getLocalId();

        if (!InternetManager.isConnected(mPresenter.context) || ManagerDB.invoiceHasOfflineOperation(harvestInvoice)) {
            if (ManagerDB.deleteHarvestOrPurchaseInvoice(remoteInvoiceId, localInvoiceId))
                mPresenter.onHarvestDeleted();
            else
                onError(mPresenter.context.getString(R.string.error_deleting_harvest));
        } else {
            Call<Message> call = harvestApi.deleteInvoice(remoteInvoiceId);
            call.enqueue(new Callback<Message>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                    try {
                        if (response.isSuccessful()) {
                            // In case the harvest was created locally and it's not synced
                            ManagerDB.deleteHarvestOrPurchaseInvoice(remoteInvoiceId, localInvoiceId);
                            mPresenter.onHarvestDeleted();
                        } else
                            manageError(mPresenter.context.getString(R.string.error_deleting_harvest), response);

                    } catch (Exception e) {
                        e.printStackTrace();
                        onError(mPresenter.context.getString(R.string.error_deleting_harvest));
                    }
                }

                @DebugLog
                @Override
                public void onFailure(Call<Message> call, Throwable t) {
                    t.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_deleting_harvest));
                }
            });
        }
    }

    @DebugLog
    @Override
    public void onGetHarvestsSuccess(InvoiceListResponse harvestsList) {
        mPresenter.updatePager(harvestsList.getPager());
        mPresenter.handleSuccessfulMixedHarvestsRequest(harvestsList.getResult());
    }

    @DebugLog
    @Override
    public void getReceiptDetails(Invoice invoiceParam) {

        if (!InternetManager.isConnected(mPresenter.context) || ManagerDB.invoiceHasOfflineOperation(invoiceParam)) {

            Log.d("PRINT", "--->getReceiptDetails FROM OFFLINE");

            //List<HarvestOfDay> invoiceList = ManagerDB.getAllHarvestsOrPurchasesOfDayByInvoice(invoiceParam.getInvoiceId(), invoiceParam.getLocalId());
            List<InvoiceDetails> detailsList =
                    ManagerDB.getAllDetailsOfInvoiceByIdUnsorted(
                            invoiceParam.getInvoiceId(),
                            invoiceParam.getLocalId(),
                            true);

            InvoiceDetailsResponse localResponse = new InvoiceDetailsResponse();

            /*if (invoiceList != null) {
                localResponse.setHarvests(invoiceList);
            } else {
                onError(mPresenter.context.getString(R.string.error_getting_information_to_print));
            }*/
            if (detailsList != null) {
                localResponse.setListInvoiceDetails(detailsList);
            } else {
                onError(mPresenter.context.getString(R.string.error_getting_information_to_print));
            }

            if (detailsList != null) {
                localResponse.setListInvoiceDetails(detailsList);
            }

            //mPresenter.handleSuccessfulHarvestsOrPurchasesOfInvoiceRequest(localResponse);
            onGetReceiptDetailsSuccess(localResponse);

        } else {

            Log.d("PRINT", "--->getReceiptDetails FROM ONLINE");

            Call<InvoiceDetailsResponse> call = harvestApi.getInvoiceDetails(invoiceParam.getInvoiceId());

            call.enqueue(new Callback<InvoiceDetailsResponse>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<InvoiceDetailsResponse> call,
                                       @NonNull Response<InvoiceDetailsResponse> response) {

                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            onGetReceiptDetailsSuccess(response.body());
                        } else
                            manageError(mPresenter.context.getString(R.string.error_getting_information_to_print), response);

                    } catch (Exception e) {
                        e.printStackTrace();
                        onError(mPresenter.context.getString(R.string.error_getting_information_to_print));
                    }
                }

                @DebugLog
                @Override
                public void onFailure(@NonNull Call<InvoiceDetailsResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_getting_information_to_print));
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
            Call<ReceiptResponse> call = harvestApi.getReceipt(invoiceParam.getInvoiceId());

            call.enqueue(new Callback<ReceiptResponse>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<ReceiptResponse> call,
                                       @NonNull Response<ReceiptResponse> response) {

                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            onGetReceiptSuccess(response.body());
                        } else
                            manageError(mPresenter.context.getString(R.string.error_getting_information_to_print), response);

                    } catch (Exception e) {
                        e.printStackTrace();
                        onError(mPresenter.context.getString(R.string.error_getting_information_to_print));
                    }
                }

                @DebugLog
                @Override
                public void onFailure(@NonNull Call<ReceiptResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_getting_information_to_print));
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
