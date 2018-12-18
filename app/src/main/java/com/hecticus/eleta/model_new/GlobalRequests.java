package com.hecticus.eleta.model_new;

import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hecticus.eleta.home.HomeActivity;
import com.hecticus.eleta.internet.InternetManager;
import com.hecticus.eleta.model.response.farm.FarmsListResponse;
import com.hecticus.eleta.model.response.lot.LotsListResponse;
import com.hecticus.eleta.model.response.store.StoresListResponse;
import com.hecticus.eleta.model_new.persistence.ManagerDB;
import com.hecticus.eleta.model_new.persistence.ManagerServices;
import com.hecticus.eleta.model.response.item.ItemTypesListResponse;
import com.hecticus.eleta.model.response.providers.ProvidersListResponse;
import com.hecticus.eleta.model.response.purity.PurityListResponse;
import com.hecticus.eleta.model_new.retrofit_interface.HarvestRetrofitInterface;
import com.hecticus.eleta.model_new.retrofit_interface.ProviderRetrofitInterface;
import com.hecticus.eleta.model_new.retrofit_interface.PurchaseRetrofitInterface;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.ErrorHandling;

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
    private final HarvestRetrofitInterface harvestsApi;
    private Context context;

    @DebugLog
    public GlobalRequests(final Context context) {
        this.context = context;
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
        harvestsApi = retrofit.create(HarvestRetrofitInterface.class);

        getLot();
        getHarvesterItems();
        getFarms();
        getSellerItems();
        getPurities();
        getStore();
        getSellers(); //Harvesters already cached when opening app (Providers tab)
    }

    @DebugLog
    private void getHarvesterItems() {
        Call<ItemTypesListResponse> harvesterItemsCall = purchasesApi.getItemsType("nameItemType",Constants.TYPE_HARVESTER);
        new ManagerServices<>(harvesterItemsCall, new ManagerServices.ServiceListener<ItemTypesListResponse>() {
            @DebugLog
            @Override
            public void onSuccess(Response<ItemTypesListResponse> response) {
                try {
                    ManagerDB.saveNewItemsType(Constants.TYPE_HARVESTER, response.body().getResult());
                } catch (Exception e) {
                    ErrorHandling.errorCodeInServerResponseProcessing(e);
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
    private void getFarms() {
        Call<FarmsListResponse> harvesterItemsCall = harvestsApi.getFarms();
        new ManagerServices<>(harvesterItemsCall, new ManagerServices.ServiceListener<FarmsListResponse>() {
            @DebugLog
            @Override
            public void onSuccess(Response<FarmsListResponse> response) {
                try {
                    ManagerDB.saveNewFarms( response.body().getResult());
                } catch (Exception e) {
                    ErrorHandling.errorCodeInServerResponseProcessing(e);
                    e.printStackTrace();
                }
            }

            @DebugLog
            @Override
            public void onError(boolean fail, int code, Response<FarmsListResponse> response, String errorMessage) {
            }

            @DebugLog
            @Override
            public void onInvalidToken() {
            }
        });
    }

    @DebugLog
    private void getLot() {
        if(!InternetManager.isConnected(context)){
            ManagerDB.updateLotsNotOrderLastUse(context);
        }else {
            Call<LotsListResponse> harvesterItemsCall = harvestsApi.getLot();
            new ManagerServices<>(harvesterItemsCall, new ManagerServices.ServiceListener<LotsListResponse>() {
                @DebugLog
                @Override
                public void onSuccess(Response<LotsListResponse> response) {
                    try {
                        ManagerDB.saveNewLots1(response.body().getResult());
                        ManagerDB.updateLotsNotOrderLastUse(context);

                    } catch (Exception e) {
                        ErrorHandling.errorCodeInServerResponseProcessing(e);
                        e.printStackTrace();
                    }
                }

                @DebugLog
                @Override
                public void onError(boolean fail, int code, Response<LotsListResponse> response, String errorMessage) {
                }

                @DebugLog
                @Override
                public void onInvalidToken() {
                }
            });
        }
    }

    @DebugLog
    private void getStore() {
        Call<StoresListResponse> harvesterItemsCall = purchasesApi.getStores();
        new ManagerServices<>(harvesterItemsCall, new ManagerServices.ServiceListener<StoresListResponse>() {
            @DebugLog
            @Override
            public void onSuccess(Response<StoresListResponse> response) {
                try {
                    ManagerDB.saveNewStores(response.body().getResult());
                } catch (Exception e) {
                    ErrorHandling.errorCodeInServerResponseProcessing(e);
                    e.printStackTrace();
                }
            }

            @DebugLog
            @Override
            public void onError(boolean fail, int code, Response<StoresListResponse> response, String errorMessage) {
            }

            @DebugLog
            @Override
            public void onInvalidToken() {
            }
        });
    }

    @DebugLog
    private void getSellerItems() {
        Call<ItemTypesListResponse> sellerItemsCall = purchasesApi.getItemsType("nameItemType", Constants.TYPE_SELLER);
        new ManagerServices<>(sellerItemsCall, new ManagerServices.ServiceListener<ItemTypesListResponse>() {
            @DebugLog
            @Override
            public void onSuccess(Response<ItemTypesListResponse> response) {
                try {
                    ManagerDB.saveNewItemsType(Constants.TYPE_SELLER, response.body().getResult());
                } catch (Exception e) {
                    ErrorHandling.errorCodeInServerResponseProcessing(e);
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
                    ErrorHandling.errorCodeInServerResponseProcessing(e);
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
                    ManagerDB.updateProviders(response.body().getResult(), sellerProviderType);
                } catch (Exception e) {
                    ErrorHandling.errorCodeInServerResponseProcessing(e);
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