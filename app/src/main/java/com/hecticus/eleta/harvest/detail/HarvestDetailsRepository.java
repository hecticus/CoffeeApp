package com.hecticus.eleta.harvest.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hecticus.eleta.R;
import com.hecticus.eleta.internet.InternetManager;
import com.hecticus.eleta.model_new.SessionManager;
import com.hecticus.eleta.model_new.persistence.ManagerDB;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.response.farm.Farm;
import com.hecticus.eleta.model.response.farm.FarmsListResponse;
import com.hecticus.eleta.model.response.invoice.CreateInvoiceResponse;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailsResponse;
import com.hecticus.eleta.model.response.item.ItemType;
import com.hecticus.eleta.model.response.item.ItemTypesListResponse;
import com.hecticus.eleta.model.response.lot.Lot;
import com.hecticus.eleta.model.response.lot.LotsListResponse;
import com.hecticus.eleta.model_new.retrofit_interface.HarvestRetrofitInterface;
import com.hecticus.eleta.model_new.retrofit_interface.InvoiceRetrofitInterface;
import com.hecticus.eleta.util.Constants;

import org.json.JSONObject;

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
 * Created by roselyn545 on 16/9/17.
 */

public class HarvestDetailsRepository implements HarvestDetailsContract.Repository {

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
                                        .addHeader("Authorization", SessionManager.getAccessToken(mPresenter.context))
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
    private void manageError(Response response) {
        try {
            if (response != null && response.code() == 400) {
                SessionManager.clearPreferences(mPresenter.context);
                mPresenter.invalidToken();
                return;
            }
            onError();
        } catch (Exception e) {
            e.printStackTrace();
            onError();
        }
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
    public void saveHarvestRequest(InvoicePost invoicePost, boolean isAdd) {
        if (!InternetManager.isConnected(mPresenter.context) || ManagerDB.invoiceHasOfflineOperation(invoicePost,isAdd)) {
            if (isAdd) {
                if (ManagerDB.saveNewInvoice(Constants.TYPE_HARVESTER, invoicePost)) {
                    onHarvestUpdated();
                } else {
                    onError();
                }
            } else {
                Log.d("OFFLINE", "--->saveHarvestRequest Offline Edit");
                if (ManagerDB.updateInvoiceDetails(invoicePost, null))
                    onHarvestUpdated();
                else
                    onError();
            }
        } else {
            Call<CreateInvoiceResponse> call;
            if (isAdd) {
                call = invoiceApi.newInvoiceDetail(invoicePost);
            } else {
                call = invoiceApi.updateInvoiceDetail(invoicePost);
            }
            call.enqueue(new Callback<CreateInvoiceResponse>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<CreateInvoiceResponse> call, @NonNull Response<CreateInvoiceResponse> response) {
                    try {
                            Log.e("BUG", "--->onResponse saveHarvestRequest1" + response.body());
                        Log.e("BUG", "--->onResponse saveHarvestRequest2" + response.message());
                    }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    try {
                        if (response.isSuccessful()) {
                            //onHarvestUpdated();
                            getDetails(response.body().getResult().getInvoiceId());
                        } else {
                            Log.e("RETRO", "--->ERROR" + new JSONObject(response.errorBody().string()));
                            manageError(response);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        onError();
                    }

                }

                @DebugLog
                @Override
                public void onFailure(Call<CreateInvoiceResponse> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("RETRO", "--->ERROR");
                    onError();
                }
            });
        }
    }

    private void getDetails(final int invoiceId){
        Call<InvoiceDetailsResponse> call = invoiceApi.getInvoiceDetails(invoiceId);

        call.enqueue(new Callback<InvoiceDetailsResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<InvoiceDetailsResponse> call,
                                   @NonNull Response<InvoiceDetailsResponse> response) {

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        ManagerDB.saveNewHarvestsOrPurchasesOfDayById(invoiceId, response.body().getHarvests());
                        ManagerDB.saveDetailsOfInvoice(response.body().getDetails());
                        onHarvestUpdated();
                    } else
                        //manageError(mPresenter.context.getString(R.string.error_getting_harvests), response);
                        onHarvestUpdated();


                } catch (Exception e) {
                    e.printStackTrace();
                    //onError(mPresenter.context.getString(R.string.error_getting_harvests));
                    onHarvestUpdated();

                }
            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<InvoiceDetailsResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                //onError(mPresenter.context.getString(R.string.error_getting_harvests));
                onHarvestUpdated();
            }
        });
    }

    @DebugLog
    @Override
    public void onError() {
        mPresenter.onUpdateError();
    }

    @DebugLog
    @Override
    public void onError(String error) {
        mPresenter.onError(error);
    }

    @DebugLog
    @Override
    public void onHarvestUpdated() {
        mPresenter.onHarvestUpdated();
    }

    @DebugLog
    @Override
    public void getItemTypesRequest() {
        if (!InternetManager.isConnected(mPresenter.context)) {
            List<ItemType> itemTypeList = ManagerDB.getAllItemsType(Constants.TYPE_HARVESTER);
            if (itemTypeList != null) {
                mPresenter.loadSortedItems(itemTypeList);
            } else {
                onError(mPresenter.context.getString(R.string.error_getting_items));
            }
        } else {
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
                            manageError(mPresenter.context.getString(R.string.error_getting_items), response);
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
    }

    @DebugLog
    @Override
    public void onItemTypesSuccess(ItemTypesListResponse response) {
        ManagerDB.saveNewItemsType(Constants.TYPE_HARVESTER, response.getResult());
        mPresenter.loadItems(response.getResult());
    }

    @DebugLog
    @Override
    public void getFarmsRequest() {
        if (!InternetManager.isConnected(mPresenter.context)) {
            List<Farm> farmList = ManagerDB.getAllFarms();
            if (farmList != null) {
                mPresenter.loadFarms(farmList);
            } else {
                onError(mPresenter.context.getString(R.string.error_getting_farms));
            }
        } else {
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
                            manageError(mPresenter.context.getString(R.string.error_getting_farms), response);

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
    }

    @DebugLog
    @Override
    public void onFarmsSuccess(FarmsListResponse response) {
        ManagerDB.saveNewFarms(response.getResult());
        mPresenter.loadFarms(response.getResult());
    }

    @DebugLog
    @Override
    public void getLotsByFarmRequest(int idFarm) {
        if (!InternetManager.isConnected(mPresenter.context)) {
            List<Lot> lotList = ManagerDB.getAllLotsByFarm(idFarm);
            if (lotList != null) {
                mPresenter.loadLots(lotList);
            } else {
                onError(mPresenter.context.getString(R.string.error_getting_lots));
            }
        } else {
            Call<LotsListResponse> call = harvestApi.getLotsByFarm(idFarm);

            call.enqueue(new Callback<LotsListResponse>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<LotsListResponse> call,
                                       @NonNull Response<LotsListResponse> response) {

                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            ManagerDB.saveNewLots(response.body().getResult());
                            onLotsSuccess(response.body());
                        } else
                            manageError(mPresenter.context.getString(R.string.error_getting_lots), response);
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
    }

    @DebugLog
    @Override
    public void onLotsSuccess(LotsListResponse response) {
        mPresenter.loadLots(response.getResult());
    }

    @DebugLog
    @Override
    public Lot getLotById(int id) {
        return ManagerDB.getLotById(id);
    }

    @DebugLog
    @Override
    public ItemType getItemTypeById(int id) {
        return ManagerDB.getItemTypeById(id);
    }

}
