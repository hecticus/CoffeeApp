package com.hecticus.eleta.of_day;

import android.support.annotation.NonNull;

import com.hecticus.eleta.R;
import com.hecticus.eleta.model.Session;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailsResponse;
import com.hecticus.eleta.model.retrofit_interface.InvoiceRetrofitInterface;
import com.hecticus.eleta.util.Constants;

import java.io.IOException;
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

public class InvoicesOfDayListRepository implements InvoicesOfDayListContract.Repository{

    private final InvoiceRetrofitInterface invoiceApi;
    private final InvoicesOfDayListPresenter mPresenter;

    public InvoicesOfDayListRepository(InvoicesOfDayListPresenter presenterParam) {

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

    @Override
    public void onError(String error) {
        mPresenter.onError(error);
    }

    @Override
    public void harvestsRequest(int invoiceId) {
        Call<InvoiceDetailsResponse> call = invoiceApi.getInvoiceDetails(invoiceId);

        call.enqueue(new Callback<InvoiceDetailsResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<InvoiceDetailsResponse> call,
                                   @NonNull Response<InvoiceDetailsResponse> response) {

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        onGetHarvestsSuccess(response.body());
                    } else
                        onError(mPresenter.context.getString(R.string.error_getting_harvests));
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

    @Override
    public void deleteHarvest(int invoiceId, String date) {
        Call<InvoiceDetailsResponse> call = invoiceApi.deleteInvoiceDetail(invoiceId,date);
        call.enqueue(new Callback<InvoiceDetailsResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<InvoiceDetailsResponse> call, @NonNull Response<InvoiceDetailsResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        mPresenter.onHarvestDeleted(response.body());
                    } else
                        onError(mPresenter.context.getString(R.string.error_deleting_harvest));
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

    @Override
    public void onGetHarvestsSuccess(InvoiceDetailsResponse invoiceDetailsResponse) {
        //mPresenter.updatePager(harvestsList.getPager());
        /*List<HarvestOfDay> lista = new ArrayList<HarvestOfDay>();
        lista.add(new HarvestOfDay());
        lista.add(new HarvestOfDay());*/
        mPresenter.handleSuccessfulHarvestsRequest(invoiceDetailsResponse);
    }
}
