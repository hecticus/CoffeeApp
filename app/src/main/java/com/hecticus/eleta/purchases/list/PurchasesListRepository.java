package com.hecticus.eleta.purchases.list;

import android.support.annotation.NonNull;

import com.hecticus.eleta.R;
import com.hecticus.eleta.model.Session;
import com.hecticus.eleta.model.response.Message;
import com.hecticus.eleta.model.response.invoice.InvoiceListResponse;
import com.hecticus.eleta.model.retrofit_interface.InvoiceRetrofitInterface;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.Util;

import java.io.IOException;

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
                                        .addHeader("Authorization", Session.getAccessToken(mPresenter.context))
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
    @Override
    public void onError(String error) {
        mPresenter.onError(error);
    }

    @DebugLog
    @Override
    public void purchasesRequest(final int index) {
        Call<InvoiceListResponse> call = invoiceApi.getInvoicesByDateByTypeProvider(Util.getCurrentDate(), Constants.TYPE_SELLER, index);//Util.getCurrentDate()//"2017-09-28"

        call.enqueue(new Callback<InvoiceListResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<InvoiceListResponse> call,
                                   @NonNull Response<InvoiceListResponse> response) {

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        onGetPurchasesSuccess(response.body());
                    } else
                        onError(mPresenter.context.getString(R.string.error_getting_purchases));
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_getting_purchases));
                }
            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<InvoiceListResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                onError(mPresenter.context.getString(R.string.error_getting_purchases));
            }
        });
    }

    @DebugLog
    @Override
    public void deletePurchase(int harvestId) {
        Call<Message> call = invoiceApi.deleteInvoice(harvestId);
        call.enqueue(new Callback<Message>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                try {
                    if (response.isSuccessful()) {// && response.body().getMessage().equals("Successful deleted")
                        mPresenter.onPurchaseDeleted();
                    } else
                        onError(mPresenter.context.getString(R.string.error_deleting_purchase));
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_deleting_purchase));
                }
            }

            @DebugLog
            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                t.printStackTrace();
                onError(mPresenter.context.getString(R.string.error_deleting_purchase));
            }
        });
    }

    @DebugLog
    @Override
    public void onGetPurchasesSuccess(InvoiceListResponse purchasesList) {
        mPresenter.updatePager(purchasesList.getPager());
        mPresenter.handleSuccessfulPurchasesRequest(purchasesList.getResult());
    }
}