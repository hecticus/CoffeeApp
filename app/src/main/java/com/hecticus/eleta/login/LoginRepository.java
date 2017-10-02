package com.hecticus.eleta.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hecticus.eleta.model.Session;
import com.hecticus.eleta.model.request.LoginPost;
import com.hecticus.eleta.model.response.LoginResponse;
import com.hecticus.eleta.model.retrofit_interface.UserRetrofitInterface;
import com.hecticus.eleta.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by roselyn545 on 15/9/17.
 */

public class LoginRepository implements LoginContract.Repository {

    private UserRetrofitInterface userApi = null;

    private final LoginPresenter mPresenter;

    public LoginRepository(LoginPresenter presenterParam) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
                                        .addHeader("Content-Type", "application/json").build();
                                return chain.proceed(request);
                            }
                        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        userApi = retrofit.create(UserRetrofitInterface.class);
        mPresenter = presenterParam;
    }

    @DebugLog
    @Override
    public void loginRequest(String email, String password) {

        LoginPost post = new LoginPost();
        post.setEmail(email);
        post.setPassword(password);
        Log.d("TEST", post.toString());

        Call<LoginResponse> call = userApi.loginRequest(post);
        call.enqueue(new Callback<LoginResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        onLoginSuccess(response.body());
                    } catch (Exception e) {
                        e.printStackTrace();
                        onLoginError();
                    }
                } else {
                    try {
                        Log.d("LOGIN", "--->Error " + new JSONObject(response.errorBody().string()));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    onLoginError();
                }
            }

            @DebugLog
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e("RETRO", "--->ERROR");
                onLoginError();
            }
        });
    }

    @Override
    public void recoveryPasswordRequest(String email) {

    }

    @DebugLog
    @Override
    public void onLoginSuccess(LoginResponse response) {
        saveTokens(response);
        mPresenter.onLoginSuccess();
    }

    @DebugLog
    @Override
    public void onLoginError() {
        mPresenter.onLoginError();
    }

    @DebugLog
    @Override
    public void saveTokens(LoginResponse response) {
        Context context = mPresenter.context;
        Session.updateSession(context, response);
    }

}
