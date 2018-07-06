package com.hecticus.eleta.model_new;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hecticus.eleta.model_new.persistence.ManagerDB;
import com.hecticus.eleta.model_new.persistence.ManagerServices;
import com.hecticus.eleta.model.response.item.ItemTypesListResponse;
import com.hecticus.eleta.model.response.providers.ProvidersListResponse;
import com.hecticus.eleta.model.response.purity.PurityListResponse;
import com.hecticus.eleta.model_new.retrofit_interface.ProviderRetrofitInterface;
import com.hecticus.eleta.model_new.retrofit_interface.PurchaseRetrofitInterface;
import com.hecticus.eleta.util.Constants;

import java.io.IOException;

import hugo.weaving.DebugLog;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by roselyn545 on 21/10/17.
 */

public class GlobalRequests {

    private final PurchaseRetrofitInterface purchasesApi;
    private final ProviderRetrofitInterface providersApi;

    @DebugLog
    public GlobalRequests(final Context context) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
                                        .addHeader("Authorization", SessionManager.getAccessToken(context))
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

        purchasesApi = retrofit.create(PurchaseRetrofitInterface.class);
        providersApi = retrofit.create(ProviderRetrofitInterface.class);

        getHarvesterItems();
        getSellerItems();
        getPurities();
        getSellers(); //Harvesters already cached when opening app (Providers tab)
    }

    @DebugLog
    private void getHarvesterItems() {
        Call<ItemTypesListResponse> harvesterItemsCall = purchasesApi.getItemsType(Constants.TYPE_HARVESTER);
        new ManagerServices<>(harvesterItemsCall, new ManagerServices.ServiceListener<ItemTypesListResponse>() {
            @DebugLog
            @Override
            public void onSuccess(Response<ItemTypesListResponse> response) {
                try {
                    ManagerDB.saveNewItemsType(Constants.TYPE_HARVESTER, response.body().getResult());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @DebugLog
            @Override
            public void onError(boolean fail, int code, Response<ItemTypesListResponse> response, String errorMessage) {
            }

            @DebugLog
            @Override
            public void onInvalidToken() {
            }
        });
    }

    @DebugLog
    private void getSellerItems() {
        Call<ItemTypesListResponse> sellerItemsCall = purchasesApi.getItemsType(Constants.TYPE_SELLER);
        new ManagerServices<>(sellerItemsCall, new ManagerServices.ServiceListener<ItemTypesListResponse>() {
            @DebugLog
            @Override
            public void onSuccess(Response<ItemTypesListResponse> response) {
                try {
                    ManagerDB.saveNewItemsType(Constants.TYPE_SELLER, response.body().getResult());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @DebugLog
            @Override
            public void onError(boolean fail, int code, Response<ItemTypesListResponse> response, String errorMessage) {
            }

            @DebugLog
            @Override
            public void onInvalidToken() {
            }
        });
    }

    @DebugLog
    private void getPurities() {
        Call<PurityListResponse> puritiesCall = purchasesApi.getPurities();
        new ManagerServices<>(puritiesCall, new ManagerServices.ServiceListener<PurityListResponse>() {
            @DebugLog
            @Override
            public void onSuccess(Response<PurityListResponse> response) {
                try {
                    ManagerDB.saveNewPurities(response.body().getResult());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @DebugLog
            @Override
            public void onError(boolean fail, int code, Response<PurityListResponse> response, String errorMessage) {
            }

            @DebugLog
            @Override
            public void onInvalidToken() {
            }
        });
    }

    @DebugLog
    private void getSellers() {
        final int sellerProviderType = Constants.TYPE_SELLER;

        Call<ProvidersListResponse> sellersCall = providersApi.providersByType(sellerProviderType);

        new ManagerServices<>(sellersCall, new ManagerServices.ServiceListener<ProvidersListResponse>() {
            @DebugLog
            @Override
            public void onSuccess(Response<ProvidersListResponse> response) {
                try {
                    ManagerDB.updateProviders(response.body().getResult());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @DebugLog
            @Override
            public void onError(boolean fail, int code, Response<ProvidersListResponse> response, String errorMessage) {
            }

            @DebugLog
            @Override
            public void onInvalidToken() {
            }
        });
    }
}