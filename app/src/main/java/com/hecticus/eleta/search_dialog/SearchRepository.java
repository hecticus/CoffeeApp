package com.hecticus.eleta.search_dialog;

import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.hecticus.eleta.R;
import com.hecticus.eleta.internet.InternetManager;
import com.hecticus.eleta.model.response.providers.ProviderType;
import com.hecticus.eleta.model_new.SessionManager;
import com.hecticus.eleta.model_new.persistence.ManagerDB;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.providers.ProvidersListResponse;
import com.hecticus.eleta.model_new.retrofit_interface.ProviderRetrofitInterface;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.ErrorHandling;

import java.io.IOException;
import java.util.ArrayList;
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

public class SearchRepository implements SearchContract.Repository {

    private final ProviderRetrofitInterface providersApi;
    private final SearchPresenter mPresenter;
    private List<Provider> currentProviders = new ArrayList<Provider>();

    @DebugLog
    public SearchRepository(SearchPresenter presenterParam) {

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
    @Override
    public void getAllProvidersByType(final int type) {
        if (!InternetManager.isConnected(mPresenter.context)) {
            currentProviders = ManagerDB.getAllProvidersByType(type);
            mPresenter.handleSuccessfulSortedProvidersRequest(currentProviders);
        } else {
            Call<ProvidersListResponse> call = providersApi.providersByType(type);

            call.enqueue(new Callback<ProvidersListResponse>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<ProvidersListResponse> call,
                                       @NonNull Response<ProvidersListResponse> response) {

                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            ManagerDB.updateProviders(response.body().getResult(), type);
                            List<Provider> finalList = ManagerDB.mixAndGetValids(type, response.body().getResult());
                            currentProviders = finalList;
                            onGetProvidersSuccess(finalList);
                        } else {
                            onError(ErrorHandling.errorCodeWebServiceNotSuccess + mPresenter.context.getString(R.string.error_getting_providers));
                        }
                    } catch (Exception e) {
                        ErrorHandling.errorCodeInServerResponseProcessing(e);
                        e.printStackTrace();
                        onError(ErrorHandling.errorCodeInServerResponseProcessing + mPresenter.context.getString(R.string.error_getting_providers));
                    }
                }

                @DebugLog
                @Override
                public void onFailure(@NonNull Call<ProvidersListResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    ErrorHandling.syncErrorCodeWebServiceFailed(t);
                    onError(ErrorHandling.errorCodeWebServiceFailed + mPresenter.context.getString(R.string.error_getting_providers));
                }
            });
        }
    }

    @Override
    public List<Provider> getCurrentProviders() {
        return currentProviders;
    }

    @Override
    public void onGetProvidersSuccess(List<Provider> providersList) {
        mPresenter.handleSuccessfulProvidersRequest(providersList);
    }

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
                            ManagerDB.updateProviders(response.body().getResult(), type);
                            List<Provider> finalList = ManagerDB.mixAndGetValids(type, response.body().getResult(), name);
                            onGetProvidersSuccess(finalList);
                        } else
                            onError(ErrorHandling.errorCodeWebServiceNotSuccess + mPresenter.context.getString(R.string.error_getting_providers));
                    } catch (Exception e) {
                        ErrorHandling.errorCodeInServerResponseProcessing(e);
                        e.printStackTrace();
                        onError(ErrorHandling.errorCodeInServerResponseProcessing + mPresenter.context.getString(R.string.error_getting_providers));
                    }
                }

                @DebugLog
                @Override
                public void onFailure(@NonNull Call<ProvidersListResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    ErrorHandling.syncErrorCodeWebServiceFailed(t);
                    onError(ErrorHandling.errorCodeWebServiceFailed + mPresenter.context.getString(R.string.error_getting_providers));
                }
            });
        }
    }

    @Override
    public void onError(String error) {
        mPresenter.onError(error);
    }
}
