package com.hecticus.eleta.harvest.list;

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

        harvestApi = retrofit.create(InvoiceRetrofitInterface.class);
    }

    @DebugLog
    @Override
    public void onError(String error) {
        mPresenter.onError(error);
    }

    @DebugLog
    @Override
    public void harvestsRequest(final int index) {
        Call<InvoiceListResponse> call = harvestApi.getInvoicesByDateByTypeProvider("2017-09-28",Constants.TYPE_HARVESTER,index);//Util.getCurrentDate()//"2017-09-25"

        call.enqueue(new Callback<InvoiceListResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<InvoiceListResponse> call,
                                   @NonNull Response<InvoiceListResponse> response) {

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
            public void onFailure(@NonNull Call<InvoiceListResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                onError(mPresenter.context.getString(R.string.error_getting_harvests));
            }
        });
    }

    @DebugLog
    @Override
    public void deleteHarvest(int harvestId) {
        Call<Message> call = harvestApi.deleteInvoice(harvestId);
        call.enqueue(new Callback<Message>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                try {
                    if (response.isSuccessful() && response.body().getMessage().equals("Successful deleted")) {
                        mPresenter.onHarvestDeleted();
                    } else
                        onError(mPresenter.context.getString(R.string.error_deleting_harvest));
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

    @DebugLog
    @Override
    public void onGetHarvestsSuccess(InvoiceListResponse harvestsList) {
        mPresenter.updatePager(harvestsList.getPager());
        mPresenter.handleSuccessfulHarvestsRequest(harvestsList.getResult());
    }
}
