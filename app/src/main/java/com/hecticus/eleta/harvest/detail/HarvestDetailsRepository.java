package com.hecticus.eleta.harvest.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hecticus.eleta.R;
import com.hecticus.eleta.model.HarvestModel;
import com.hecticus.eleta.model.Session;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.response.Message;
import com.hecticus.eleta.model.response.farm.FarmsListResponse;
import com.hecticus.eleta.model.response.item.ItemTypesListResponse;
import com.hecticus.eleta.model.response.lot.LotsListResponse;
import com.hecticus.eleta.model.retrofit_interface.HarvestRetrofitInterface;
import com.hecticus.eleta.model.retrofit_interface.InvoiceRetrofitInterface;
import com.hecticus.eleta.provider.detail.ProviderDetailsPresenter;
import com.hecticus.eleta.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

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
 * Created by roselyn545 on 16/9/17.
 */

public class HarvestDetailsRepository implements HarvestDetailsContract.Repository{

    private final HarvestDetailsPresenter mPresenter;
    private final InvoiceRetrofitInterface invoiceApi;
    private final HarvestRetrofitInterface harvestApi;


    public HarvestDetailsRepository(HarvestDetailsPresenter presenterParam, Context context) {

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

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();

        harvestApi = retrofit.create(HarvestRetrofitInterface.class);
        invoiceApi = retrofit.create(InvoiceRetrofitInterface.class);

        mPresenter = presenterParam;
    }

    public HarvestDetailsRepository(HarvestDetailsPresenter presenterParam) {
        this(presenterParam, presenterParam.context);
    }

    @DebugLog
    @Override
    public void saveHarvestResquest(InvoicePost invoicePost, boolean isAdd) {
        Call<Message> call;
        if (isAdd){
            call = invoiceApi.newInvoiceDetail(invoicePost);
        }else{
            call = invoiceApi.updateInvoiceDetail(invoicePost);
        }
        call.enqueue(new Callback<Message>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                try {
                    if (response.isSuccessful()) {
                        onSuccessUpdateHarvest();
                    } else {
                        Log.e("RETRO", "--->ERROR" + new JSONObject(response.errorBody().string()));
                        onError();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    onError();
                }

            }
            @DebugLog
            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                t.printStackTrace();
                Log.e("RETRO", "--->ERROR");
                onError();
            }
        });
    }

    @Override
    public void onError() {
        mPresenter.onUpdateError();
    }

    @Override
    public void onError(String error) {
        mPresenter.onError(error);
    }

    @Override
    public void onSuccessUpdateHarvest() {
        mPresenter.onUpdateHarvest();
    }

    @DebugLog
    @Override
    public void getItemTypesRequest() {
        Call<ItemTypesListResponse> call = harvestApi.getItemsType(Constants.TYPE_HARVESTER);

        call.enqueue(new Callback<ItemTypesListResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<ItemTypesListResponse> call,
                                   @NonNull Response<ItemTypesListResponse> response) {

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        onItemTypesSuccess(response.body());
                    } else
                        onError(mPresenter.context.getString(R.string.error_getting_items));
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_getting_items));
                }
            }
            @DebugLog
            @Override
            public void onFailure(@NonNull Call<ItemTypesListResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                onError(mPresenter.context.getString(R.string.error_getting_items));
            }
        });
    }

    @Override
    public void onItemTypesSuccess(ItemTypesListResponse response) {
        mPresenter.loadItems(response.getResult());
    }

    @DebugLog
    @Override
    public void getFarmsRequest() {
        Call<FarmsListResponse> call = harvestApi.getFarms();

        call.enqueue(new Callback<FarmsListResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<FarmsListResponse> call,
                                   @NonNull Response<FarmsListResponse> response) {

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        onFarmsSuccess(response.body());
                    } else
                        onError(mPresenter.context.getString(R.string.error_getting_farms));
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_getting_farms));
                }
            }
            @DebugLog
            @Override
            public void onFailure(@NonNull Call<FarmsListResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                onError(mPresenter.context.getString(R.string.error_getting_farms));
            }
        });
    }

    @Override
    public void onFarmsSuccess(FarmsListResponse response) {
        mPresenter.loadFarms(response.getResult());
    }

    @DebugLog
    @Override
    public void getLotsByFarmRequest(int idFarm) {
        Call<LotsListResponse> call = harvestApi.getLotsByFarm(idFarm);

        call.enqueue(new Callback<LotsListResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<LotsListResponse> call,
                                   @NonNull Response<LotsListResponse> response) {

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        onLotsSuccess(response.body());
                    } else
                        onError(mPresenter.context.getString(R.string.error_getting_lots));
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_getting_lots));
                }
            }
            @DebugLog
            @Override
            public void onFailure(@NonNull Call<LotsListResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                onError(mPresenter.context.getString(R.string.error_getting_lots));
            }
        });
    }

    @Override
    public void onLotsSuccess(LotsListResponse response) {
        mPresenter.loadLots(response.getResult());
    }
}
