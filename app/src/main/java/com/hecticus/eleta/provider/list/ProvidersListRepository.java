package com.hecticus.eleta.provider.list;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.hecticus.eleta.R;
import com.hecticus.eleta.internet.InternetManager;
import com.hecticus.eleta.model_new.SessionManager;
import com.hecticus.eleta.model_new.persistence.ManagerDB;
import com.hecticus.eleta.model_new.persistence.ManagerServices;
import com.hecticus.eleta.model.response.Message;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.providers.ProvidersListResponse;
import com.hecticus.eleta.model_new.retrofit_interface.ProviderRetrofitInterface;
import com.hecticus.eleta.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by roselyn545 on 16/9/17.
 */

public class ProvidersListRepository implements ProvidersListContract.Repository {

    private final ProviderRetrofitInterface providersApi;
    private final ProvidersListPresenter mPresenter;

    private List<Provider> currentProviders = new ArrayList<Provider>();

    @DebugLog
    public ProvidersListRepository(ProvidersListPresenter presenterParam) {

        mPresenter = presenterParam;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build();

        providersApi = retrofit.create(ProviderRetrofitInterface.class);
    }

    @DebugLog
    private void manageError(String error, Response response) {
        try {
            if (response!=null && response.code() == 400) {
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
    public void onError(String error) {
        mPresenter.onError(error);
    }


    @Override
    public List<Provider> getCurrentProviders() {
        return currentProviders;
    }


    @DebugLog
    @Override
    public void getProvidersOfType(final int providerType) {
        if (!InternetManager.isConnected(mPresenter.context)) {
            currentProviders = ManagerDB.getAllProvidersByType(providerType);
            Log.d("TEST", "--->current providers " + currentProviders);
            mPresenter.handleSuccessfulMixedProvidersRequest(currentProviders);
        } else {
            Call<ProvidersListResponse> call = providersApi.providersByType(providerType);
            new ManagerServices<>(call, new ManagerServices.ServiceListener<ProvidersListResponse>() {
                @DebugLog
                @Override
                public void onSuccess(Response<ProvidersListResponse> response) {
                    try {
                        ManagerDB.updateProviders(response.body().getResult(), providerType);
                        List<Provider> finalList = ManagerDB.mixAndGetValids(providerType, response.body().getResult());
                        currentProviders = finalList;
                        onGetProvidersSuccess(finalList);
                    } catch (Exception e) {
                        e.printStackTrace();
                        manageError(mPresenter.context.getString(R.string.error_getting_providers), response);
                    }
                }

                @DebugLog
                @Override
                public void onError(boolean fail, int code, Response<ProvidersListResponse> response, String errorMessage) {
                    manageError(mPresenter.context.getString(R.string.error_getting_providers), response);
                }

                @DebugLog
                @Override
                public void onInvalidToken() {
                    SessionManager.clearPreferences(mPresenter.context);
                    mPresenter.invalidToken();
                }
            });
        }
    }

    @DebugLog
    @Override
    public void onGetProvidersSuccess(List<Provider> providersList) {
        //mPresenter.updatePager(providersListResponse.getPager());
        mPresenter.handleSuccessfulMixedProvidersRequest(providersList);
    }

    @DebugLog
    @Override
    public void searchProvidersByTypeByName(final int type, final String name) {
        if (!InternetManager.isConnected(mPresenter.context)) {
            mPresenter.handleSuccessfulSortedProvidersRequest(ManagerDB.searchProvidersByTypeAndText(type, name));
        } else {
            Call<ProvidersListResponse> call = providersApi.searchProviders(type, name);

            call.enqueue(new Callback<ProvidersListResponse>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<ProvidersListResponse> call,
                                       @NonNull Response<ProvidersListResponse> response) {

                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Provider> finalList = ManagerDB.mixAndGetValids(type, response.body().getResult(), name);
                            onGetProvidersSuccess(finalList);
                        } else
                            manageError(mPresenter.context.getString(R.string.error_getting_providers), response);
                    } catch (Exception e) {
                        e.printStackTrace();
                        onError(mPresenter.context.getString(R.string.error_getting_providers));
                    }
                }

                @DebugLog
                @Override
                public void onFailure(@NonNull Call<ProvidersListResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_getting_providers));
                }
            });
        }
    }

    @DebugLog
    @Override
    public void deleteProvider(Provider provider) {
        if (!InternetManager.isConnected(mPresenter.context) || provider.getIdProvider() <0 || ManagerDB.providerHasOfflineOperation(provider)) {
            //TODO check if has invoices

            if (ManagerDB.deleteProvider(provider)) {
                mPresenter.onProviderDeleted();
            } else {
                onError(mPresenter.context.getString(R.string.error_deleting_provider));
            }
        } else {
            Call<ResponseBody> call = providersApi.deleteProvider(provider.getIdProvider());
            //Utils.log(new Gson().toJson(authorizationRequest));
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    Log.d("DEBUG", "response: " + response.code());
                    // onLoginSuccess(/*response.body()*/);
                    if (response.code() == 200) {
                        try {
                            mPresenter.onProviderDeleted();
                        } catch (Exception e) {
                            manageError(mPresenter.context.getString(R.string.error_deleting_provider), response);
                        }
                    } else {
                        if (response.code() == 409){ //.equals("Constrain violation: Invoices Open")) {
                            manageError(mPresenter.context.getString(R.string.cant_delete_provider_has_open_invoices), response);
                        } else {
                            manageError(mPresenter.context.getString(R.string.error_deleting_provider), response);
                        }

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    onError(mPresenter.context.getString(R.string.error_deleting_provider));
                    //(mPresenter.context.getString(R.string.error_deleting_provider), );
                    Log.d("ERROR", t.toString());
                    t.printStackTrace();
                }
            });
            /*Call<Message> call = providersApi.deleteProvider(provider.getIdProvider());
            new ManagerServices<>(call, new ManagerServices.ServiceListener<Message>() {
                @DebugLog
                @Override
                public void onSuccess(Response<Message> response) {
                    try {
                        mPresenter.onProviderDeleted();
                    } catch (Exception e) {
                        manageError(mPresenter.context.getString(R.string.error_deleting_provider), response);
                    }
                }

                @DebugLog
                @Override
                public void onError(boolean fail, int code, Response<Message> response, String errorMessage) {
                    try {
                        //response.errorBody().string();
                        Log.e("DEBUGERROR", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (response.errorBody().s.equals("Constrain violation: Invoices Open")) {
                        manageError(mPresenter.context.getString(R.string.cant_delete_provider_has_open_invoices), response);
                    } else {
                        manageError(mPresenter.context.getString(R.string.error_deleting_provider), response);
                    }
                }

                @DebugLog
                @Override
                public void onInvalidToken() {
                    SessionManager.clearPreferences(mPresenter.context);
                    mPresenter.invalidToken();
                }
            });*/
        }

    }
}
