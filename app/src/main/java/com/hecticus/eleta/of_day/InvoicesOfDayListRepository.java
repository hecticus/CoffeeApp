package com.hecticus.eleta.of_day;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hecticus.eleta.R;
import com.hecticus.eleta.internet.InternetManager;
import com.hecticus.eleta.model_new.SessionManager;
import com.hecticus.eleta.model_new.persistence.ManagerDB;
import com.hecticus.eleta.model.request.invoice.CloseInvoicePost;
import com.hecticus.eleta.model.response.Message;
import com.hecticus.eleta.model.response.harvest.HarvestOfDay;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailsResponse;
import com.hecticus.eleta.model.response.invoice.ReceiptResponse;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model_new.retrofit_interface.InvoiceRetrofitInterface;
import com.hecticus.eleta.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

public class InvoicesOfDayListRepository implements InvoicesOfDayListContract.Repository {

    private final InvoiceRetrofitInterface invoiceApi;
    private final InvoicesOfDayListPresenter mPresenter;

    private final boolean isForHarvest;

    public InvoicesOfDayListRepository(InvoicesOfDayListPresenter presenterParam, boolean isForHarvestParam) {

        mPresenter = presenterParam;

        isForHarvest = isForHarvestParam;

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
                        })
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).
                        build();

        invoiceApi = retrofit.create(InvoiceRetrofitInterface.class);
    }

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
    public Provider getProviderById(int id) {
        return ManagerDB.getProviderById(id);
    }

    @DebugLog
    @Override
    public Provider getProviderByIdentificationDoc(String identificationDoc) {
        return ManagerDB.getProviderByIdentificationDoc(identificationDoc);
    }

    @DebugLog
    @Override
    public void onError(String error) {
        mPresenter.onError(error);
    }

    @DebugLog
    @Override
    public void getHarvestsOrPurchasesOfInvoiceRequest(final Invoice invoice) {
        if (!InternetManager.isConnected(mPresenter.context) || ManagerDB.invoiceHasOfflineOperation(invoice)) {

            Log.d("HOD", "--->getHarvestsOrPurchasesOfInvoiceRequest FROM OFFLINE");

            List<HarvestOfDay> invoiceList = ManagerDB.getAllHarvestsOrPurchasesOfDayByInvoice(invoice.getId(), invoice.getLocalId());
            List<InvoiceDetails> detailsList = ManagerDB.getAllDetailsOfInvoiceByIdUnsorted(
                    invoice.getId(),
                    invoice.getLocalId(),
                    isForHarvest);

            InvoiceDetailsResponse localResponse = new InvoiceDetailsResponse();

            if (invoiceList != null) {
                localResponse.setHarvests(invoiceList);
            } else {
                onError(mPresenter.context.getString(R.string.error_getting_harvests));
            }

            if (detailsList != null) {
                localResponse.setListInvoiceDetails(detailsList);
            }

            mPresenter.handleSuccessfulHarvestsOrPurchasesOfInvoiceRequest(localResponse);

        } else {

            Log.d("HOD", "--->getHarvestsOrPurchasesOfInvoiceRequest FROM ONLINE");

            Call<InvoiceDetailsResponse> call = invoiceApi.getInvoiceDetails(invoice.getId());

            call.enqueue(new Callback<InvoiceDetailsResponse>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<InvoiceDetailsResponse> call,
                                       @NonNull Response<InvoiceDetailsResponse> response) {

                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            ManagerDB.saveNewHarvestsOrPurchasesOfDayById(invoice.getId(), response.body().getHarvests());
                            ManagerDB.saveDetailsOfInvoice(response.body().getListInvoiceDetails());
                            onGetHarvestsSuccess(response.body());
                        } else
                            manageError(mPresenter.context.getString(R.string.error_getting_harvests), response);

                    } catch (Exception e) {
                        e.printStackTrace();
                        onError(mPresenter.context.getString(R.string.error_getting_harvests));
                    }
                }

                @DebugLog
                @Override
                public void onFailure(@NonNull Call<InvoiceDetailsResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_getting_harvests));
                }
            });
        }
    }

    @DebugLog
    @Override
    public void deleteHarvestOrPurchase(Invoice invoice, String date, String harvestOrPurchaseId) {
        if (!InternetManager.isConnected(mPresenter.context) || ManagerDB.invoiceHasOfflineOperation(invoice)) {
            if (ManagerDB.delete(invoice.getId2(), date, harvestOrPurchaseId)) {
                List<HarvestOfDay> harvestsOrPurchasesOfDayList = ManagerDB.getAllHarvestsOrPurchasesOfDayByInvoice(invoice.getId(), invoice.getLocalId());
                List<InvoiceDetails> detailsList = ManagerDB.getAllDetailsOfInvoiceByIdUnsorted(
                        invoice.getId(),
                        invoice.getLocalId(),
                        isForHarvest);

                InvoiceDetailsResponse response = new InvoiceDetailsResponse();
                if (harvestsOrPurchasesOfDayList != null) {
                    response.setHarvests(harvestsOrPurchasesOfDayList);
                } else {
                    onError(mPresenter.context.getString(R.string.error_getting_harvests));
                    return;
                }

                if (detailsList != null) {
                    response.setListInvoiceDetails(detailsList);
                }

                if (harvestsOrPurchasesOfDayList.size() == 0) {
                    Log.d("HOD", "--->Invoice has " + harvestsOrPurchasesOfDayList.size() + " items of day to also delete");
                    ManagerDB.delete(invoice.getId(), invoice.getLocalId());
                } else {
                    Log.d("HOD", "--->Invoice has no items of day to also delete");
                }

                mPresenter.onHarvestDeleted(response);
            } else {
                onError(mPresenter.context.getString(R.string.error_deleting_harvest));
            }
        } else {
            Call<InvoiceDetailsResponse> call = invoiceApi.deleteInvoiceDetail(invoice.getId(), date, new ArrayList<Long>());
            call.enqueue(new Callback<InvoiceDetailsResponse>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<InvoiceDetailsResponse> call, @NonNull Response<InvoiceDetailsResponse> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            mPresenter.onHarvestDeleted(response.body());
                        } else
                            manageError(mPresenter.context.getString(R.string.error_deleting_harvest), response);

                    } catch (Exception e) {
                        e.printStackTrace();
                        onError(mPresenter.context.getString(R.string.error_deleting_harvest));
                    }
                }

                @DebugLog
                @Override
                public void onFailure(Call<InvoiceDetailsResponse> call, Throwable t) {
                    t.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_deleting_harvest));
                }
            });
        }
    }

    @DebugLog
    @Override
    public void closeInvoiceRequest(CloseInvoicePost post) {
        Call<Message> call = invoiceApi.closeInvoice(post.getId(), post);

        call.enqueue(new Callback<Message>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<Message> call,
                                   @NonNull Response<Message> response) {

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        mPresenter.onCloseInvoiceSuccessful();
                    } else {
                        manageError(mPresenter.context.getString(R.string.error_closing_purchase), response);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_closing_purchase));
                }
            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                t.printStackTrace();
                onError(mPresenter.context.getString(R.string.error_closing_purchase));
            }
        });
    }

    @DebugLog
    @Override
    public void onGetHarvestsSuccess(InvoiceDetailsResponse invoiceDetailsResponse) {
        mPresenter.handleSuccessfulHarvestsOrPurchasesOfInvoiceRequest(invoiceDetailsResponse);
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
