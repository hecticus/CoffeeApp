package com.hecticus.eleta.provider.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hecticus.eleta.R;
import com.hecticus.eleta.internet.InternetManager;
import com.hecticus.eleta.model.SessionManager;
import com.hecticus.eleta.model.persistence.ManagerDB;
import com.hecticus.eleta.model.persistence.ManagerServices;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.providers.ProviderCreationResponse;
import com.hecticus.eleta.model.response.providers.ProviderImageUpdateResponse;
import com.hecticus.eleta.model.retrofit_interface.ProviderRetrofitInterface;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

public class ProviderDetailsRepository implements ProviderDetailsContract.Repository {

    private final ProviderRetrofitInterface providerDetailsDataApi;
    private final ProviderRetrofitInterface providerImageApi;
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
                                        .addHeader("Authorization", SessionManager.getAccessToken(mPresenter.context))
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
                                        .addHeader("Authorization", SessionManager.getAccessToken(mPresenter.context))
                                        .addHeader("Content-Type", "application/json").build();
                                return chain.proceed(request);
                            }
                        })
                //.readTimeout(30, TimeUnit.SECONDS)
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

        providerDetailsDataApi = retrofitWithNormalTimeout.create(ProviderRetrofitInterface.class);
        providerImageApi = retrofitWithLongTimeout.create(ProviderRetrofitInterface.class);

        mPresenter = presenterParam;
        mContext = context;
    }

    public ProviderDetailsRepository(ProviderDetailsPresenter presenterParam) {
        this(presenterParam, presenterParam.context);
    }

    @DebugLog
    @Override
    public void createProviderRequest(Provider providerParam, final String imagePath) {

        if (providerParam.getIdProviderType() == -1) {
            Log.d("DETAILS", "--->Populating provider type: " + providerParam.getProviderType().getIdProviderType());

        } else
            Log.d("DETAILS", "--->Using provider type: " + providerParam.getIdProviderType());

        if (!InternetManager.isConnected(mContext)) {
            if (providerIdDocExistsInLocalStorageX(providerParam)) {
                Log.w("OFFLINE", "--->Can't create provider ("
                        + providerParam.getIdentificationDocProvider()
                        + "). Id doc already exists in local storage // Full provider: " + providerParam);
                if (providerParam.isHarvester())
                    onCreateError(mPresenter.context.getString(R.string.dni_already_exists));
                else
                    onCreateError(mPresenter.context.getString(R.string.ruc_already_exists));
            } else if (isSellerAndNameExistsInLocalStorage(providerParam)) {
                Log.w("OFFLINE", "--->Can't create seller ("
                        + providerParam.getFullNameProvider()
                        + "). Name already exists in local storage // Full seller: " + providerParam);
                onCreateError(mPresenter.context.getString(R.string.name_already_exists));
            } else {
                providerParam.setAddOffline(true);
                providerParam.setUnixtime(System.currentTimeMillis() / 1000L);

                if (imagePath != null)
                    providerParam.setPhotoProvider(imagePath);

                Log.d("OFFLINE", "--->Saved offline provider: " + providerParam);

                if (ManagerDB.saveNewProvider(providerParam)) {
                    Log.d("DETAILS", "--->Success saved local");
                    onProviderSaved(providerParam);
                } else {
                    onCreateError(mPresenter.context.getString(R.string.error_during_operation));
                }
            }

        } else {
            Log.d("DETAILS", "--->Sent provider: " + providerParam);

            Call<ProviderCreationResponse> call = providerDetailsDataApi.createProvider(providerParam);

            new ManagerServices<>(call, new ManagerServices.ServiceListener<ProviderCreationResponse>() {
                @DebugLog
                @Override
                public void onSuccess(Response<ProviderCreationResponse> response) {
                    try {
                        mPresenter.uploadImage(response.body().getProvider(), imagePath);
                        onProviderSaved(response.body().getProvider());
                        Log.d("DETAILS", "--->Success createProviderRequest:" + response.body());
                    } catch (Exception e) {
                        onCreateError(mPresenter.context.getString(R.string.error_during_operation));
                    }
                }

                @DebugLog
                @Override
                public void onError(boolean fail, int code, Response<ProviderCreationResponse> response, String errorMessage) {
                    if (fail || code != 409) {
                        Log.d("DETAILS", "--->createProviderRequest Error (" + code + "):" + (response != null ? response.body() : ""));
                        onCreateError(mPresenter.context.getString(R.string.error_during_operation));
                    } else {
                        Log.d("DETAILS", "--->createProviderRequest Error 1a Existe (" + code + "):" + response.body());
                        onCreateError(mPresenter.context.getString(R.string.already_exists));
                    }
                }

                @DebugLog
                @Override
                public void onInvalidToken() {
                    /*Session.clearPreferences(mPresenter.context);
                    mPresenter.invalidToken();*/
                }
            });
        }
    }

    @DebugLog
    @Override
    public void updateProviderRequest(final Provider providerParam) {
        if (providerParam.getIdProviderType() == -1) {
            Log.d("DETAILS", "--->Populating provider type: " + providerParam.getProviderType().getIdProviderType());
            providerParam.setIdProviderType(providerParam.getProviderType().getIdProviderType());
        } else
            Log.d("DETAILS", "--->Using provider type: " + providerParam.getIdProviderType());


        if (!InternetManager.isConnected(mContext) || providerParam.getIdProvider() < 0 || ManagerDB.providerHasOfflineOperation(providerParam)) {
            if (providerHasChangedIdDoc(providerParam) &&
                    providerIdDocExistsInLocalStorageX(providerParam)) {
                Log.w("OFFLINE", "--->Can't update provider ("
                        + providerParam.getIdentificationDocProvider()
                        + "). Id doc already exists in local storage. Full provider: " + providerParam);
                if (providerParam.isHarvester())
                    onDataUpdateError(Constants.ErrorType.DNI_EXISTING);
                else
                    onDataUpdateError(Constants.ErrorType.RUC_EXISTING);
            } else if (isSellerAndNameExistsInLocalStorage(providerParam)) {
                Log.w("OFFLINE", "--->Can't update seller ("
                        + providerParam.getFullNameProvider()
                        + "). Name already exists in local storage. Full seller: " + providerParam);
                onDataUpdateError(Constants.ErrorType.NAME_EXISTING);
            } else {
                Log.d("DETAILS", "--->Provider a actualizar: " + providerParam);

                providerParam.setAddOffline(providerParam.getIdProvider() < 0);
                providerParam.setEditOffline(true);
                if (providerParam.getUnixtime() == -1)
                    providerParam.setUnixtime(System.currentTimeMillis() / 1000L);

                if (ManagerDB.updateExistingProvider(providerParam)) {
                    Log.d("OFFLINE", "--->Success saved offline: " + providerParam);
                    onProviderSaved(providerParam);
                } else {
                    onDataUpdateError(Constants.ErrorType.GENERIC_ERROR_DURING_OPERATION);
                }
            }
        } else {
            Log.d("DETAILS", "--->Sent provider: " + providerParam);
            Call<ProviderCreationResponse> call = providerDetailsDataApi.updateProviderData(providerParam);
            ManagerServices service = new ManagerServices<ProviderCreationResponse>(call, new ManagerServices.ServiceListener<ProviderCreationResponse>() {
                @DebugLog
                @Override
                public void onSuccess(Response<ProviderCreationResponse> response) {
                    try {
                        onProviderSaved(response.body().getProvider());
                        Log.d("DETAILS", "--->Success updateProviderRequest:" + response.body());
                    } catch (Exception e) {
                        onDataUpdateError(Constants.ErrorType.GENERIC_ERROR_DURING_OPERATION);
                    }
                }

                @DebugLog
                @Override
                public void onError(boolean fail, int code, Response<ProviderCreationResponse> response, String errorMessage) {
                    if (fail || code != 409) {
                        Log.d("DETAILS", "--->updateProviderRequest Error 2 (" + code + "):" + (response != null ? response.body() : ""));
                        onDataUpdateError(Constants.ErrorType.GENERIC_ERROR_DURING_OPERATION);
                    } else {
                        if (errorMessage != null && errorMessage.equals("registered [fullNameProvider]")) {
                            Log.d("DETAILS", "--->updateProviderRequest Error 2 fullNameProvider (" + code + "):" + (response != null ? response.body() : ""));
                            onDataUpdateError(Constants.ErrorType.NAME_EXISTING);
                        } else {
                            Log.d("DETAILS", "--->updateProviderRequest Error DNI/RUC (" + response.code() + "):" + response.body());
                            if (providerParam.isHarvester())
                                onDataUpdateError(Constants.ErrorType.DNI_EXISTING);
                            else
                                onDataUpdateError(Constants.ErrorType.RUC_EXISTING);
                        }
                    }
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
    private boolean providerIdDocExistsInLocalStorageX(Provider providerParam) {
        Provider savedWithSameIdDoc = Realm.getDefaultInstance()
                .where(Provider.class)
                .equalTo("identificationDocProvider", providerParam.getIdentificationDocProvider())
                .findFirst();
        return (savedWithSameIdDoc != null);
    }

    @DebugLog
    private boolean isSellerAndNameExistsInLocalStorage(Provider providerParam) {

        if (providerParam.isHarvester()) // This rule is only for sellers
            return false;

        Provider sellerWithSameName = Realm.getDefaultInstance()
                .where(Provider.class)
                .equalTo("idProviderType", Constants.TYPE_SELLER)
                .equalTo("fullNameProvider", providerParam.getFullNameProvider())
                .findFirst();
        return (sellerWithSameName != null);
    }

    @DebugLog
    private boolean providerHasChangedIdDoc(Provider providerParam) {
        return !providerParam.getIdentificationDocProvider()
                .equals(providerParam.getIdentificationDocProviderChange());
    }

    @DebugLog
    @Override
    public void onCreateError(String message) {
        mPresenter.onCreateError(message);
    }

    @DebugLog
    @Override
    public void onDataUpdateError(Constants.ErrorType errorType) {
        mPresenter.onUpdateError(errorType, null);
    }

    @DebugLog
    @Override
    public void onImageUpdateError(Provider provider, String previousImageString, String errorMessage) {
        provider.setPhotoProvider(previousImageString);
        mPresenter.onUpdateError(Constants.ErrorType.ERROR_UPDATING_IMAGE, errorMessage);
    }

    @DebugLog
    @Override
    public void onImageUpdateSuccess(String newImageString) {
        mPresenter.onImageUpdateSuccess(newImageString);
    }

    @DebugLog
    @Override
    public void onProviderSaved(Provider provider) {
        mPresenter.onProviderSaved(provider);
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
                        onImageUpdateError(provider, previousProviderImageString, null);
                    }
                } else {

                    String errorMessage = null;

                    try {
                        errorMessage = new JSONObject(response.errorBody().string()) + "";
                        Log.e("DETAILS", "--->uploadImageRequest Error " + errorMessage);
                    } catch (JSONException | IOException e) {
                        Log.e("DETAILS", "--->uploadImageRequest Error with error");
                        e.printStackTrace();
                    }
                    onImageUpdateError(provider, previousProviderImageString, errorMessage);
                }
            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<ProviderImageUpdateResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.d("RETRO", "--->ERROR: " + t.getMessage());
                onImageUpdateError(provider, previousProviderImageString, null);
            }
        });
    }
}