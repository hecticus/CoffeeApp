package com.hecticus.eleta.purchases.detail;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hecticus.eleta.R;
import com.hecticus.eleta.internet.InternetManager;
import com.hecticus.eleta.model_new.Invoice;
import com.hecticus.eleta.model_new.SessionManager;
import com.hecticus.eleta.model_new.persistence.ManagerDB;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.response.invoice.CreateInvoiceResponse;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailPurity;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailsResponse;
import com.hecticus.eleta.model.response.item.ItemType;
import com.hecticus.eleta.model.response.item.ItemTypesListResponse;
import com.hecticus.eleta.model.response.purity.Purity;
import com.hecticus.eleta.model.response.purity.PurityListResponse;
import com.hecticus.eleta.model.response.store.Store;
import com.hecticus.eleta.model.response.store.StoresListResponse;
import com.hecticus.eleta.model_new.retrofit_interface.InvoiceRetrofitInterface;
import com.hecticus.eleta.model_new.retrofit_interface.PurchaseRetrofitInterface;
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
 * Created by roselyn545 on 22/9/17.
 */

public class PurchaseDetailsRepository implements PurchaseDetailsContract.Repository {

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

        purchaseApi = retrofit.create(PurchaseRetrofitInterface.class);
        invoiceApi = retrofit.create(InvoiceRetrofitInterface.class);
        mPresenter = presenterParam;
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
    public void savePurchaseRequest(InvoicePost invoicePost, boolean isAdd) {
        if (!InternetManager.isConnected(mPresenter.context) || ManagerDB.invoiceHasOfflineOperation(invoicePost, isAdd)) {
            if (isAdd) {
                if (ManagerDB.saveNewInvoice(Constants.TYPE_SELLER, invoicePost)) {
                    onPurchaseUpdated();
                } else {
                    onError();
                }
            } else {
                Log.d("OFFLINE", "--->saveHarvestRequest Offline Edit");
                if (ManagerDB.updateInvoiceDetails(invoicePost, mPresenter.getOriginalDetailsPuritiesList())) {
                    onPurchaseUpdated();
                } else
                    onError();
            }
        } else {
            Call<CreateInvoiceResponse> call;
            //Gson g = new Gson();
            if (isAdd) {
                Log.d("DEBUG1", "PASO");
                call = invoiceApi.newInvoiceDetail(invoicePost/*, invoicePost.getProviderId(), invoicePost.getStartDate()*/);
                //Log.d("DEBUG1", g.toJson(invoicePost));
            } else {
                //Log.d("DEBUG2", g.toJson(invoicePost));
                call = invoiceApi.updateInvoiceDetail(invoicePost);
            }
            call.enqueue(new Callback<CreateInvoiceResponse>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<CreateInvoiceResponse> call, @NonNull Response<CreateInvoiceResponse> response) {
                    try {
                        Log.d("DEBUG2", "PASO");
                        if (response.isSuccessful()) {
                            Log.d("DEBUG3", "PASO");
                            getAndSaveInvoiceDetails(response.body().getResult().getId(), true);
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

    @DebugLog
    private void getAndSaveInvoiceDetails(final int invoiceId, boolean isAppOnline) {
        Call<InvoiceDetailsResponse> call = invoiceApi.getInvoiceDetails(invoiceId);

        call.enqueue(new Callback<InvoiceDetailsResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<InvoiceDetailsResponse> call,
                                   @NonNull Response<InvoiceDetailsResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        ManagerDB.saveNewHarvestsOrPurchasesOfDayById(invoiceId, response.body().getHarvests());
                        ManagerDB.saveDetailsOfInvoice(response.body().getListInvoiceDetails());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                onPurchaseUpdated();
            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<InvoiceDetailsResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.d("DEBUG5", "errorrrrrrrrrrrrrrrrrrrrrrrrr");
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
    public void onPurchaseUpdated() {
        mPresenter.onPurchaseUpdated();
    }

    @Override
    public void getItemTypesRequest() {
        if (!InternetManager.isConnected(mPresenter.context)) {
            List<ItemType> itemTypeList = ManagerDB.getAllItemsType(Constants.TYPE_SELLER);
            if (itemTypeList != null) {
                mPresenter.loadItems(itemTypeList);
            } else {
                onError(mPresenter.context.getString(R.string.error_getting_items));
            }
        } else {
            Call<ItemTypesListResponse> call = purchaseApi.getItemsType("nameItemType", Constants.TYPE_SELLER);

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

    @Override
    public void onItemTypesSuccess(ItemTypesListResponse response) {
        ManagerDB.saveNewItemsType(Constants.TYPE_SELLER, response.getResult());
        mPresenter.loadItems(response.getResult());
    }

    @DebugLog
    @Override
    public void getStoresRequest() {
        if (!InternetManager.isConnected(mPresenter.context)) {
            List<Store> storeList = ManagerDB.getAllStores();
            if (storeList != null) {
                mPresenter.loadSortedStores(storeList);
            } else {
                onError(mPresenter.context.getString(R.string.error_getting_stores));
            }
        } else {
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
                            manageError(mPresenter.context.getString(R.string.error_getting_stores), response);

                    } catch (Exception e) {
                        e.printStackTrace();
                        onError(mPresenter.context.getString(R.string.error_getting_stores));
                    }
                }

                @DebugLog
                @Override
                public void onFailure(@NonNull Call<StoresListResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_getting_stores));
                }
            });
        }
    }

    @DebugLog
    @Override
    public void onStoresSuccess(StoresListResponse response) {
        ManagerDB.saveNewStores(response.getResult());
        mPresenter.loadStores(response.getResult());
    }

    @DebugLog
    @Override
    public void getPuritiesRequest(boolean purchaseHasOfflineOperation) {
        if (purchaseHasOfflineOperation || !InternetManager.isConnected(mPresenter.context)) {
            List<Purity> allPuritiesList = ManagerDB.getAllPurities();
            if (allPuritiesList != null) {
                mPresenter.loadSortedPurities(allPuritiesList);
            } else {
                onError(mPresenter.context.getString(R.string.error_getting_purities));
            }
        } else {
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
                            manageError(mPresenter.context.getString(R.string.error_getting_purities), response);

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
    }

    @DebugLog
    @Override
    public void onPuritiesSuccess(PurityListResponse response) {
        ManagerDB.saveNewPurities(response.getResult());
        mPresenter.loadPurities(response.getResult());
    }

    @Override
    public ItemType getItemTypeById(int id) {
        return ManagerDB.getItemTypeById(id);
    }

    @Override
    public Store getStoreById(int id) {
        return ManagerDB.getStoreById(id);
    }

    @DebugLog
    @Override
    public List<InvoiceDetailPurity> getPuritiesByLocalDetailId(String idDetail) {
        return ManagerDB.getPuritiesByLocalDetailId(idDetail);
    }
}
