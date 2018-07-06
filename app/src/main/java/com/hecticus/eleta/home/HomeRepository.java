package com.hecticus.eleta.home;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hecticus.eleta.model_new.SessionManager;
import com.hecticus.eleta.model.response.Message;
import com.hecticus.eleta.model_new.retrofit_interface.UserRetrofitInterface;
import com.hecticus.eleta.recovery_password.RecoveryPasswordActivity;
import com.hecticus.eleta.util.Constants;

import java.io.IOException;

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
 * Created by Edwin on 2017-12-30.
 */

public class HomeRepository implements HomeContract.Repository {

    private final UserRetrofitInterface userApi;

    private final HomePresenter mPresenter;

    public HomeRepository(HomePresenter presenterParam) {

        mPresenter = presenterParam;

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
                                        .addHeader("Authorization", SessionManager.getAccessToken(mPresenter.context))
                                        .addHeader("Content-Type", "application/json").build();
                                return chain.proceed(request);
                            }
                        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).
                        build();

        userApi = retrofit.create(UserRetrofitInterface.class);
    }

    @DebugLog
    @Override
    public void logOutRequest() {
        Call<Message> call = userApi.logOutRequest("hola");

        call.enqueue(new Callback<Message>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.isSuccessful()) {
                    try {
                        if ("OK".equalsIgnoreCase(response.body().getMessage())) {
                            onLogoutSuccess();
                        } else {
                            onLogoutError();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        onLogoutError();
                    }
                } else
                    onLogoutError();
            }

            @DebugLog
            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                t.printStackTrace();
                Log.e("RETRO", "--->" + RecoveryPasswordActivity.class.getSimpleName() + " onFailure +" + call + " + " + t);
                onLogoutError();
            }
        });
    }

    @DebugLog
    @Override
    public void onLogoutSuccess() {
        mPresenter.onLogoutRequestSuccess();
    }

    @DebugLog
    @Override
    public void onLogoutError() {
        mPresenter.onLogoutRequestError();
    }
}
