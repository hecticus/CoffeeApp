package com.hecticus.eleta.of_day;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hecticus.eleta.R;
import com.hecticus.eleta.internet.InternetManager;
import com.hecticus.eleta.model.response.StatusInvoice;
import com.hecticus.eleta.model_new.SessionManager;
import com.hecticus.eleta.model_new.persistence.ManagerDB;
import com.hecticus.eleta.model.response.Message;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailsResponse;
import com.hecticus.eleta.model.response.invoice.ReceiptResponse;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model_new.retrofit_interface.InvoiceRetrofitInterface;
import com.hecticus.eleta.util.Constants;

import java.io.IOException;
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

            //List<HarvestOfDay> invoiceList = ManagerDB.getAllHarvestsOrPurchasesOfDayByInvoice(invoice.getInvoiceId(), invoice.getLocalId());
            //Log.d("DEBUG harvestofday", String.valueOf(invoiceList.size()));
            List<InvoiceDetails> detailsList = ManagerDB.getAllDetailsOfInvoiceByIdUnsorted(
                    invoice.getInvoiceId(),
                    invoice.getLocalId(),
                    isForHarvest);
            Log.d("DEBUG 555555555555", "..." +detailsList.size());

            Log.d("DEBUG json invoiceDe", "id" + String.valueOf(invoice.getInvoiceId()));
            Log.d("DEBUG json invoiceDet", "idLocal" + String.valueOf(invoice.getLocalId()));

            InvoiceDetailsResponse localResponse = new InvoiceDetailsResponse();

            /*if (invoiceList != null) {
                localResponse.setHarvests(invoiceList);
            } else {
                onError(mPresenter.context.getString(R.string.error_getting_harvests));
            }*/

            if (detailsList != null) {
                localResponse.setListInvoiceDetails(detailsList);
            } else {
                onError(mPresenter.context.getString(R.string.error_getting_harvests));
            }

            if (detailsList != null) {
                localResponse.setListInvoiceDetails(detailsList);
            }

            mPresenter.handleSuccessfulHarvestsOrPurchasesOfInvoiceRequest(localResponse, false);

        } else {

            Log.d("HOD", "--->getHarvestsOrPurchasesOfInvoiceRequest FROM ONLINE");

            Call<InvoiceDetailsResponse> call = invoiceApi.getInvoiceDetails(invoice.getInvoiceId());
            Log.d("DEBUG", String.valueOf(invoice.getInvoiceId()));
            call.enqueue(new Callback<InvoiceDetailsResponse>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<InvoiceDetailsResponse> call,
                                       @NonNull Response<InvoiceDetailsResponse> response) {

                    try {
                        Log.d("DEBUG", "paso2");
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d("DEBUG", "paso3");
                            //ManagerDB.saveNewHarvestsOrPurchasesOfDayById(invoice.getInvoiceId(), response.body().getHarvests(true));
                            Log.d("DEBUG", "paso4");
                            ManagerDB.saveDetailsOfInvoice(response.body().getListInvoiceDetails());
                            Log.d("DEBUG", "paso5");
                            InvoiceDetailsResponse invoiceDetailsResponse = new InvoiceDetailsResponse();
                            invoiceDetailsResponse.setListInvoiceDetails(ManagerDB.mixAndGetValidsInvoiceDetails(response.body().getListInvoiceDetails(), invoice.getInvoiceId()));
                            onGetHarvestsSuccess(/*response.body()*/ invoiceDetailsResponse, true);
                            Log.d("DEBUG", "paso6");
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
                    Log.d("DEBUG", "paso5");
                    t.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_getting_harvests));
                }
            });
        }
    }

    @DebugLog
    @Override
    public void deleteHarvestOrPurchase(Invoice invoice, String date, final InvoiceDetails harvestOrPurchase) {
        if (!InternetManager.isConnected(mPresenter.context) /*|| ManagerDB.invoiceHasOfflineOperation(invoice)*/) {
            Log.d("DEBUG", "policia 2");
            Log.d("DEBUG", "policia id"+ harvestOrPurchase.getId());
            Log.d("DEBUG", "policia id" + harvestOrPurchase.getLocalId());

            if (ManagerDB.deleteInvoiceDetails(harvestOrPurchase.getId(), harvestOrPurchase.getLocalId())) {
                Log.d("DEBUG", "policia 3");
                List<InvoiceDetails> detailsList = ManagerDB.getAllDetailsOfInvoiceByIdUnsorted(
                        invoice.getInvoiceId(),
                        invoice.getLocalId(),
                        isForHarvest);
                mPresenter.onHarvestDeleted(detailsList, true);
            }
            /*if (ManagerDB.delete(invoice.getId2(), date, harvestOrPurchase.getId())) {
                List<HarvestOfDay> harvestsOrPurchasesOfDayList = ManagerDB.getAllHarvestsOrPurchasesOfDayByInvoice(invoice.getInvoiceId(), invoice.getLocalId());
                List<InvoiceDetails> detailsList = ManagerDB.getAllDetailsOfInvoiceByIdUnsorted(
                        invoice.getInvoiceId(),
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
                    ManagerDB.delete(invoice.getInvoiceId(), invoice.getLocalId());
                } else {
                    Log.d("HOD", "--->Invoice has no items of day to also delete");
                }

                mPresenter.onHarvestDeleted(response, false);
            } else {
                onError(mPresenter.context.getString(R.string.error_deleting_harvest));
            }*/
        } else {
            Call<InvoiceDetailsResponse> call = invoiceApi.deleteInvoiceDetail(harvestOrPurchase.getId()/*, date, new ArrayList<Long>()*/);
            call.enqueue(new Callback<InvoiceDetailsResponse>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<InvoiceDetailsResponse> call, @NonNull Response<InvoiceDetailsResponse> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            //ManagerDB.deleteInvoiceDetails(harvestOrPurchase.getId(), harvestOrPurchase.getLocalId()); todo no se
                            ManagerDB.deleteInvoiceDetails(harvestOrPurchase.getId(), harvestOrPurchase.getLocalId());
                            //Log.d("DEBUG delete online", String.valueOf(variable));

                            /*List<InvoiceDetails> detailsList = ManagerDB.getAllDetailsOfInvoiceByIdUnsorted(
                                    harvestOrPurchase.getId(),
                                    harvestOrPurchase.getLocalId(),
                                    isForHarvest);*/
                            mPresenter.onHarvestDeleted(response.body().getListInvoiceDetails(), true);
                        } else
                            Log.d("DEBUG", "ERROR BORRANDO1");
                            manageError(mPresenter.context.getString(R.string.error_deleting_harvest), response);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("DEBUG", "ERROR BORRANDO2");
                        onError(mPresenter.context.getString(R.string.error_deleting_harvest));
                    }
                }

                @DebugLog
                @Override
                public void onFailure(Call<InvoiceDetailsResponse> call, Throwable t) {
                    t.printStackTrace();
                    Log.d("DEBUG", "ERROR BORRANDO3");
                    onError(mPresenter.context.getString(R.string.error_deleting_harvest));
                }
            });
        }
    }

    @DebugLog
    @Override

    public void closeInvoiceRequest(Invoice post) {
        if (!InternetManager.isConnected(mPresenter.context)) {
            ManagerDB.updateStatusInvoice(post);
            mPresenter.onCloseInvoiceSuccessful();

        } else {
            com.hecticus.eleta.model_new.Invoice invoice1
                    = new com.hecticus.eleta.model_new.Invoice(post,
                    post.getProvider(),
                    new StatusInvoice(12, false, "Cerrada", null));
            //Log.d("DEBUG id close", post.getId() + "");
            Call<Message> call = invoiceApi.closeInvoice(invoice1.getId().intValue(), invoice1);

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
    }

    @DebugLog
    @Override
    public void onGetHarvestsSuccess(InvoiceDetailsResponse invoiceDetailsResponse, Boolean control) {
        mPresenter.handleSuccessfulHarvestsOrPurchasesOfInvoiceRequest(invoiceDetailsResponse, control);
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

            Call<ReceiptResponse> call = invoiceApi.getReceipt(invoiceParam.getInvoiceId());

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
