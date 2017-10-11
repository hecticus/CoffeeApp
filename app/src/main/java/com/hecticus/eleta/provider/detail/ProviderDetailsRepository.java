package com.hecticus.eleta.provider.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hecticus.eleta.R;
import com.hecticus.eleta.model.Session;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.providers.ProviderCreationResponse;
import com.hecticus.eleta.model.response.providers.ProviderImageUpdateResponse;
import com.hecticus.eleta.model.retrofit_interface.ProviderDetailsRetrofitInterface;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

public class ProviderDetailsRepository implements ProviderDetailsContract.Repository {

    private final ProviderDetailsRetrofitInterface providerDetailsDataApi;
    private final ProviderDetailsRetrofitInterface providerImageApi;
    private final ProviderDetailsPresenter mPresenter;
    private final Context mContext;

    public ProviderDetailsRepository(ProviderDetailsPresenter presenterParam, Context context) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient httpClientWithNormalTimeout = new OkHttpClient.Builder()
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

        OkHttpClient httpClientWithLongTimeout = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
                                        .addHeader("Authorization", Session.getAccessToken(mPresenter.context))
                                        .addHeader("Content-Type", "application/json").build();
                                return chain.proceed(request);
                            }
                        })
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofitWithNormalTimeout = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClientWithNormalTimeout)
                .build();

        Retrofit retrofitWithLongTimeout = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClientWithLongTimeout)
                .build();

        providerDetailsDataApi = retrofitWithNormalTimeout.create(ProviderDetailsRetrofitInterface.class);
        providerImageApi = retrofitWithLongTimeout.create(ProviderDetailsRetrofitInterface.class);

        mPresenter = presenterParam;
        mContext = context;
    }

    public ProviderDetailsRepository(ProviderDetailsPresenter presenterParam) {
        this(presenterParam, presenterParam.context);
    }

    @DebugLog
    @Override
    public void createProviderRequest(Provider providerParam, final String imagePath) {

        if (providerParam.getIdProviderType() == null) {
            Log.d("DETAILS", "--->Populating provider type: " + providerParam.getProviderType().getIdProviderType());
            providerParam.setIdProviderType(providerParam.getProviderType().getIdProviderType());
        } else
            Log.d("DETAILS", "--->Using provider type: " + providerParam.getIdProviderType());

        Log.d("DETAILS", "--->Sent provider: " + providerParam);

        Call<ProviderCreationResponse> call = providerDetailsDataApi.createProvider(providerParam);
        call.enqueue(new Callback<ProviderCreationResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<ProviderCreationResponse> call, @NonNull Response<ProviderCreationResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        mPresenter.uploadImage(response.body().getProvider(), imagePath);
                        onSuccessSaveProvider(response.body().getProvider());
                        Log.d("DETAILS", "--->Success " + response.body());
                    } catch (Exception e) {
                        Log.d("DETAILS", "--->createProviderRequest Error (" + response.code() + "):" + response.body());
                        e.printStackTrace();
                        onCreateError(mPresenter.context.getString(R.string.error_during_operation));
                    }
                } else {

                    try {
                        JSONObject object = new JSONObject(response.errorBody().string());

                        if (object.optInt("error") == 409) {
                            Log.d("DETAILS", "--->createProviderRequest Error 1a Existe (" + response.code() + "):" + response.body());

                            onCreateError(mPresenter.context.getString(R.string.already_exists));
                        } else {
                            Log.d("DETAILS", "--->createProviderRequest Error 1b No Existia (" + response.code() + "):" + response.body());

                            onCreateError(mPresenter.context.getString(R.string.error_during_operation));
                        }
                    } catch (JSONException | IOException e) {
                        Log.d("DETAILS", "--->createProviderRequest Error 2 (" + response.code() + "):" + response.body());
                        e.printStackTrace();
                        onCreateError(mPresenter.context.getString(R.string.error_during_operation));
                    }
                }
            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<ProviderCreationResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.d("RETRO", "--->ERROR: " + t.getMessage());
                onCreateError(mPresenter.context.getString(R.string.error_during_operation));
            }
        });
    }

    @DebugLog
    @Override
    public void updateProviderRequest(Provider providerParam) {
        if (providerParam.getIdProviderType() == null) {
            Log.d("DETAILS", "--->Populating provider type: " + providerParam.getProviderType().getIdProviderType());
            providerParam.setIdProviderType(providerParam.getProviderType().getIdProviderType());
        } else
            Log.d("DETAILS", "--->Using provider type: " + providerParam.getIdProviderType());

        Log.d("DETAILS", "--->Sent provider: " + providerParam);

        Call<ProviderCreationResponse> call = providerDetailsDataApi.updateProviderData(providerParam);
        call.enqueue(new Callback<ProviderCreationResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<ProviderCreationResponse> call, @NonNull Response<ProviderCreationResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.d("DETAILS", "--->Success " + response.body());
                        onSuccessSaveProvider(response.body().getProvider());
                    } catch (Exception e) {
                        e.printStackTrace();
                        onDataUpdateError(false);
                    }
                } else {
                    try {
                        JSONObject object = new JSONObject(response.errorBody().string());

                        if (object.optInt("error") == 409) {
                            Log.d("DETAILS", "--->updateProviderRequest Error 1a Existe (" + response.code() + "):" + response.body());

                            onDataUpdateError(true);
                        } else {
                            Log.d("DETAILS", "--->updateProviderRequest Error 1b No Existia (" + response.code() + "):" + response.body());

                            onDataUpdateError(false);
                        }
                    } catch (JSONException | IOException e) {
                        Log.d("DETAILS", "--->updateProviderRequest Error 2 (" + response.code() + "):" + response.body());
                        e.printStackTrace();
                        onDataUpdateError(false);
                    }
                }
            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<ProviderCreationResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.d("RETRO", "--->ERROR: " + t.getMessage());
                onDataUpdateError(false);
            }
        });
    }

    @DebugLog
    @Override
    public void onCreateError(String message) {
        mPresenter.onCreateError(message);
    }

    @Override
    public void onDataUpdateError(boolean isAlreadyExists) {
        if (isAlreadyExists)
            mPresenter.onUpdateError(2);
        else
            mPresenter.onUpdateError(0);
    }

    @Override
    public void onImageUpdateError(Provider provider, String previousImageString) {
        provider.setPhotoProvider(previousImageString);
        mPresenter.onUpdateError(1);
    }

    @DebugLog
    @Override
    public void onImageUpdateSuccess(String newImageString) {
        mPresenter.onImageUpdateSuccess(newImageString);
    }

    @DebugLog
    @Override
    public void onSuccessSaveProvider(Provider provider) {
        mPresenter.onSavedProvider(provider);
    }

    @DebugLog
    @Override
    public void uploadImageRequest(final Provider provider, String newImagePath) {

        String base64Image = FileUtils.getOptimizedBase64FromImagePath(newImagePath);

        final String previousProviderImageString = provider.getPhotoProvider();

        provider.setPhotoProvider(base64Image);

        Call<ProviderImageUpdateResponse> call = providerImageApi.updateProviderImage(provider);
        call.enqueue(new Callback<ProviderImageUpdateResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<ProviderImageUpdateResponse> call,
                                   @NonNull Response<ProviderImageUpdateResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.d("DETAILS", "--->uploadImageRequest Success " + response.body());

                        onImageUpdateSuccess(response.body().getUploadedImageUrl());

                    } catch (Exception e) {
                        e.printStackTrace();
                        onImageUpdateError(provider, previousProviderImageString);
                    }
                } else {
                    try {
                        Log.e("DETAILS", "--->uploadImageRequest Error " + new JSONObject(response.errorBody().string()));
                    } catch (JSONException | IOException e) {
                        Log.e("DETAILS", "--->uploadImageRequest Error with error");
                        e.printStackTrace();
                    }
                    onImageUpdateError(provider, previousProviderImageString);
                }
            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<ProviderImageUpdateResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.d("RETRO", "--->ERROR: " + t.getMessage());
                onImageUpdateError(provider, previousProviderImageString);
            }
        });
    }
}
