package com.hecticus.eleta.provider.list;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hecticus.eleta.R;
import com.hecticus.eleta.model.Session;
import com.hecticus.eleta.model.response.Message;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.providers.ProvidersListResponse;
import com.hecticus.eleta.model.retrofit_interface.ProviderRetrofitInterface;
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
    public void getProvidersOfType(int providerType) {
        Call<ProvidersListResponse> call = providersApi.providersByType(providerType);

        call.enqueue(new Callback<ProvidersListResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<ProvidersListResponse> call,
                                   @NonNull Response<ProvidersListResponse> response) {

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        currentProviders = response.body().getResult();
                        onGetProvidersSuccess(response.body());
                        return;
                    } else
                        onError(mPresenter.context.getString(R.string.error_getting_providers));
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_getting_providers));
                }
                try {
                    Log.d("RETRO", "--->getProvidersOfType unsuccess (" + response.code() + ") body: " + response.body());
                    Log.d("RETRO", "--->getProvidersOfType unsuccess (" + response.code() + ") error body: " + new JSONObject(response.errorBody().string()));
                } catch (JSONException | IOException e) {
                    Log.d("RETRO", "--->getProvidersOfType error error (" + e.getMessage());
                    e.printStackTrace();
                }
            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<ProvidersListResponse> call, @NonNull Throwable t) {
                Log.d("RETRO", "--->getProvidersOfType onFailure: " + t.getMessage());
                t.printStackTrace();
                onError(mPresenter.context.getString(R.string.error_getting_providers));
            }
        });
    }

    @DebugLog
    @Override
    public void onGetProvidersSuccess(ProvidersListResponse providersListResponse) {
        //mPresenter.updatePager(providersListResponse.getPager());
        mPresenter.handleSuccessfulProvidersRequest(providersListResponse.getResult());
    }

    @Override
    public void searchProvidersByTypeByName(int type, String name) {
        Call<ProvidersListResponse> call = providersApi.searchProviders(type, name);

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

    @DebugLog
    @Override
    public void deleteProvider(final int providerId) {
        Call<Message> call = providersApi.deleteProvider(providerId);
        call.enqueue(new Callback<Message>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                try {
                    if (response.isSuccessful() && response.body().getMessage().equals("Successful deleted")) {
                        mPresenter.onProviderDeleted();
                    } else {
                        JSONObject object = new JSONObject(response.errorBody().string());
                        if (object.optInt("error") == 409) {
                            onError(mPresenter.context.getString(R.string.provider_with_invoices));
                        } else {
                            onError(mPresenter.context.getString(R.string.error_deleting_provider));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(mPresenter.context.getString(R.string.error_deleting_provider));
                }
            }

            @DebugLog
            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                t.printStackTrace();
                onError(mPresenter.context.getString(R.string.error_deleting_provider));
            }
        });
    }
}
