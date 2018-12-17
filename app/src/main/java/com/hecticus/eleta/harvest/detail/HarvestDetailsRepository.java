package com.hecticus.eleta.harvest.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hecticus.eleta.R;
import com.hecticus.eleta.internet.InternetManager;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model_new.Invoice;
import com.hecticus.eleta.model_new.InvoiceDetail;
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
import com.hecticus.eleta.util.ErrorHandling;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import hugo.weaving.DebugLog;
import io.realm.Realm;
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
    public void saveHarvestRequest(final InvoicePost invoicePost, boolean isAdd, final Boolean addAnother) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Lot lot = realm.where(Lot.class).equalTo("id", invoicePost.getLot()).findFirst();
                try{
                    lot.setLastUse(Calendar.getInstance().getTime());
                }catch (Exception e){
                    e.printStackTrace();
                }
                realm.insertOrUpdate(lot);
            }
        });
        if (!InternetManager.isConnected(mPresenter.context) || ManagerDB.invoiceHasOfflineOperation(invoicePost,isAdd)) {
            if (isAdd) {
                if(ManagerDB.saveNewInvoice1(Constants.TYPE_HARVESTER, invoicePost)){
                    onHarvestUpdated(addAnother);
                } else {
                    onError(ErrorHandling.errorCodeBDLocal + mPresenter.context.getString(R.string.error_saving_changes));
                }
            }
        } else {
            Call<CreateInvoiceResponse> call;
            if (isAdd) {
                Invoice invoice = new Invoice(invoicePost, invoicePost.getProviderId()); //ManagerDB.getProviderById(invoicePost.getProviderId()));
                Log.d("DEBUG invoice", new Gson().toJson(invoice));
                call = invoiceApi.newInvoiceDetail(invoice);
                call.enqueue(new Callback<CreateInvoiceResponse>() {
                    @DebugLog
                    @Override
                    public void onResponse(@NonNull Call<CreateInvoiceResponse> call, @NonNull Response<CreateInvoiceResponse> response) {
                        try {
                            if (response.isSuccessful()) {
                                getDetails(response.body().getResult().getInvoiceId(), addAnother);
                            } else {
                                Log.d("DEBUG", response.errorBody().string());
                                onError(ErrorHandling.errorCodeWebServiceNotSuccess + mPresenter.context.getString(R.string.error_saving_changes));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ErrorHandling.errorCodeInServerResponseProcessing(e);
                            onError(ErrorHandling.errorCodeInServerResponseProcessing + mPresenter.context.getString(R.string.error_saving_changes));
                        }

                    }

                    @DebugLog
                    @Override
                    public void onFailure(Call<CreateInvoiceResponse> call, Throwable t) {
                        ErrorHandling.syncErrorCodeWebServiceFailed(t);
                        onError(ErrorHandling.errorCodeWebServiceFailed + mPresenter.context.getString(R.string.error_saving_changes));
                    }
                });
            }
        }
    }

    @Override
    public void editHarvestRequest(InvoiceDetails invoiceDetails) {
        if (!InternetManager.isConnected(mPresenter.context) /*|| ManagerDB.invoiceHasOfflineOperation(invoicePost,false)*/) {
                Log.d("OFFLINE", "--->saveHarvestRequest Offline Edit");
                if (ManagerDB.updateInvoiceDetails1(invoiceDetails, 2))
                    onHarvestUpdated(false);
                else
                    onError(ErrorHandling.errorCodeBDLocal + mPresenter.context.getString(R.string.error_saving_harvests));
        } else {
                endPoint(invoiceDetails);
        }
    }

    private void endPoint(InvoiceDetails invoiceDetail){

        Call<CreateInvoiceResponse> call;

        /*InvoiceDetails post = new InvoiceDetails();
        post.setInvoice(new com.hecticus.eleta.model.response.invoice.Invoice(invoiceDetail.getInvoice().getInvoiceId()));
        post.setItemType(new ItemType(invoiceDetail.getItemType().getId()));
        post.setLot(new Lot(invoiceDetail.getLot().getId()));
        post.setAmount(invoiceDetail.getAmount());
        post.setReceiverName(invoiceDetail.getReceiverName());
        post.setDispatcherName(invoiceDetail.getDispatcherName());*/



        Gson g = new Gson();
        Log.d("DEBUG con nueva fecha", g.toJson(new InvoiceDetail(invoiceDetail)));
        call = invoiceApi.updateInvoiceDetailNewEndpoint(invoiceDetail.getId(), new InvoiceDetail(invoiceDetail));
        call.enqueue(new Callback<CreateInvoiceResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<CreateInvoiceResponse> call, @NonNull Response<CreateInvoiceResponse> response) {
                /*try {
                    LogDataBase.e("BUG", "--->onResponse saveHarvestRequest1" + response.body());
                    LogDataBase.e("BUG", "--->onResponse saveHarvestRequest2" + response.message());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }*/

                try {
                    if (response.isSuccessful()) {
                        //onHarvestUpdated();
                        getDetails(response.body().getResult().getInvoiceId(), false);
                    } else {
                        Log.e("RETRO", "--->ERROR" + new JSONObject(response.errorBody().string()));
                        onError(ErrorHandling.errorCodeWebServiceNotSuccess + mPresenter.context.getString(R.string.error_saving_harvests));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorHandling.errorCodeInServerResponseProcessing(e);
                    onError(ErrorHandling.errorCodeInServerResponseProcessing + mPresenter.context.getString(R.string.error_saving_harvests));
                }

            }

            @DebugLog
            @Override
            public void onFailure(Call<CreateInvoiceResponse> call, Throwable t) {
                ErrorHandling.syncErrorCodeWebServiceFailed(t);
                onError(ErrorHandling.errorCodeWebServiceFailed + mPresenter.context.getString(R.string.error_saving_harvests));
            }
        });
    }

    private void getDetails(final int invoiceId, final Boolean addAnother){
        Call<InvoiceDetailsResponse> call = invoiceApi.getInvoiceDetails(invoiceId);

        call.enqueue(new Callback<InvoiceDetailsResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<InvoiceDetailsResponse> call,
                                   @NonNull Response<InvoiceDetailsResponse> response) {

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        ManagerDB.saveDetailsOfInvoice(response.body().getListInvoiceDetails());
                        onHarvestUpdated(addAnother);
                    } else { //todo brayan de aqui para abajo puede que toque comentar los msj y descomentar los onHarvestUpdated();
                        onError(ErrorHandling.errorCodeWebServiceNotSuccess + mPresenter.context.getString(R.string.error_getting_harvests));
                        //onHarvestUpdated();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorHandling.errorCodeInServerResponseProcessing(e);
                    onError(ErrorHandling.errorCodeInServerResponseProcessing + mPresenter.context.getString(R.string.error_getting_harvests));
                    //onHarvestUpdated();

                }
            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<InvoiceDetailsResponse> call, @NonNull Throwable t) {
                ErrorHandling.syncErrorCodeWebServiceFailed(t);
                onError(ErrorHandling.errorCodeWebServiceFailed + mPresenter.context.getString(R.string.error_getting_harvests));
                //onHarvestUpdated();
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
    public void onHarvestUpdated(Boolean addAnother) {
        mPresenter.onHarvestUpdated(addAnother);
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
        //todo delete all
        ManagerDB.saveNewFarms(response.getResult());
        mPresenter.loadFarms(response.getResult());
    }

    @DebugLog
    @Override
    public void getLotsByFarmRequest(final int idFarm) {
        if (!InternetManager.isConnected(mPresenter.context)) {
            List<Lot> lotList = ManagerDB.getAllLotsByFarm(idFarm);
            if (lotList != null) {
                mPresenter.loadLots(lotList);
            } else {
                onError(mPresenter.context.getString(R.string.error_getting_lots));
            }
        } else {
            Call<LotsListResponse> call = harvestApi.getLotsByFarm(idFarm,"nameLot");

            call.enqueue(new Callback<LotsListResponse>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<LotsListResponse> call,
                                       @NonNull Response<LotsListResponse> response) {

                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            //todo delete all
                            ManagerDB.saveNewLots(response.body().getResult());
                            onLotsSuccess(ManagerDB.getAllLotsByFarm(idFarm));
                            //onLotsSuccess(response.body());
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
    public void onLotsSuccess(List<Lot> listLot) {
        mPresenter.loadLots(listLot);
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
