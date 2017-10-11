package com.hecticus.eleta.purchases.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hecticus.eleta.R;
import com.hecticus.eleta.harvest.detail.HarvestDetailsPresenter;
import com.hecticus.eleta.model.PurchaseModel;
import com.hecticus.eleta.model.Session;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.response.Message;
import com.hecticus.eleta.model.response.item.ItemTypesListResponse;
import com.hecticus.eleta.model.response.purity.PurityListResponse;
import com.hecticus.eleta.model.response.store.StoresListResponse;
import com.hecticus.eleta.model.retrofit_interface.InvoiceRetrofitInterface;
import com.hecticus.eleta.model.retrofit_interface.PurchaseRetrofitInterface;
import com.hecticus.eleta.util.Constants;

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
 * Created by roselyn545 on 22/9/17.
 */

public class PurchaseDetailsRepository implements PurchaseDetailsContract.Repository{

    private final PurchaseRetrofitInterface purchaseApi;
    private final InvoiceRetrofitInterface invoiceApi;
    private final PurchaseDetailsPresenter mPresenter;

    public PurchaseDetailsRepository(PurchaseDetailsPresenter presenterParam) {

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

        purchaseApi = retrofit.create(PurchaseRetrofitInterface.class);
        invoiceApi = retrofit.create(InvoiceRetrofitInterface.class);
        mPresenter = presenterParam;
    }

    @Override
    public void savePurchaseResquest(InvoicePost invoicePost, boolean isAdd) {
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
                        onSuccessUpdatePurchase();
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
    public void onSuccessUpdatePurchase() {
        mPresenter.onUpdatePurchase();
    }

    @Override
    public void getItemTypesRequest() {
        Call<ItemTypesListResponse> call = purchaseApi.getItemsType(Constants.TYPE_SELLER);

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
    public void getStoresRequest() {
        Call<StoresListResponse> call = purchaseApi.getStores();

        call.enqueue(new Callback<StoresListResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<StoresListResponse> call,
                                   @NonNull Response<StoresListResponse> response) {

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        onStoresSuccess(response.body());
                    } else
                        onError(mPresenter.context.getString(R.string.error_getting_farms));
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_getting_farms));
                }
            }
            @DebugLog
            @Override
            public void onFailure(@NonNull Call<StoresListResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                onError(mPresenter.context.getString(R.string.error_getting_farms));
            }
        });
    }

    @DebugLog
    @Override
    public void onStoresSuccess(StoresListResponse response) {
        mPresenter.loadStores(response.getResult());
    }

    @Override
    public void getPuritiesRequest() {
        Call<PurityListResponse> call = purchaseApi.getPurities();

        call.enqueue(new Callback<PurityListResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<PurityListResponse> call,
                                   @NonNull Response<PurityListResponse> response) {

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        onPuritiesSuccess(response.body());
                    } else
                        onError(mPresenter.context.getString(R.string.error_getting_purities));
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_getting_purities));
                }
            }
            @DebugLog
            @Override
            public void onFailure(@NonNull Call<PurityListResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                onError(mPresenter.context.getString(R.string.error_getting_purities));
            }
        });
    }

    @Override
    public void onPuritiesSuccess(PurityListResponse response) {
        mPresenter.loadPurities(response.getResult());
    }
}
