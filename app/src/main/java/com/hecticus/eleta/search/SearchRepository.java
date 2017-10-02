package com.hecticus.eleta.search;

import android.support.annotation.NonNull;

import com.hecticus.eleta.R;
import com.hecticus.eleta.model.Session;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.providers.ProvidersListResponse;
import com.hecticus.eleta.model.retrofit_interface.ProviderRetrofitInterface;
import com.hecticus.eleta.util.Constants;

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

public class SearchRepository implements SearchContract.Repository{

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
                                        .addHeader("Authorization", Session.getAccessToken(mPresenter.context))
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
    @Override
    public void getProviders(int type) {
        Call<ProvidersListResponse> call = providersApi.providersByType(type);

        call.enqueue(new Callback<ProvidersListResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<ProvidersListResponse> call,
                                   @NonNull Response<ProvidersListResponse> response) {

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        currentProviders = response.body().getResult();
                        onGetProvidersSuccess(response.body());
                    } else
                        onError(mPresenter.context.getString(R.string.error_getting_providers));
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

    @Override
    public List<Provider> getCurrentProviders() {
        return currentProviders;
    }

    @Override
    public void onGetProvidersSuccess(ProvidersListResponse providersListResponse) {
        mPresenter.handleSuccessfulProvidersRequest(providersListResponse.getResult());
    }

    @Override
    public void searchProvidersByTypeByName(int type, String name) {
        Call<ProvidersListResponse> call = providersApi.searchProviders(type,name);

        call.enqueue(new Callback<ProvidersListResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<ProvidersListResponse> call,
                                   @NonNull Response<ProvidersListResponse> response) {

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        onGetProvidersSuccess(response.body());
                    } else
                        onError(mPresenter.context.getString(R.string.error_getting_providers));
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

    @Override
    public void onError(String error) {
        mPresenter.onError(error);
    }
}
