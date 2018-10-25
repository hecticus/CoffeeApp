package com.hecticus.eleta.login;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.hecticus.eleta.R;
import com.hecticus.eleta.internet.InternetManager;
import com.hecticus.eleta.model_new.SessionManager;
import com.hecticus.eleta.model.request.AuthorizationRequest;
import com.hecticus.eleta.model.response.AccessTokenResponse;
import com.hecticus.eleta.model_new.retrofit_interface.UserRetrofitInterface;
import com.hecticus.eleta.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
        if(!InternetManager.isConnected(mPresenter.context)){
            onLoginError(mPresenter.context.getString(R.string.error_login_is_not_internet));
        }else {
                /*Gson gson = new GsonBuilder().create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();*/

                //UserRetrofitInterface api = retrofit.create(UserRetrofitInterface.class);
                AuthorizationRequest authorizationRequest = new AuthorizationRequest(email, password);


                Call<ResponseBody> call = userApi.loginRequest(authorizationRequest.grant_type, authorizationRequest.username
                        , authorizationRequest.password,
                        authorizationRequest.client_id);
                //Utils.log(new Gson().toJson(authorizationRequest));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        Log.d("DEBUG", "response: " + response.code());
                        // onLoginSuccess(/*response.body()*/);
                        if (response.code() == 200) {
                            try {
                                JSONObject json = new JSONObject(response.body().string());
                                Log.d("DEBUG sign in", "json " + json);
                                AccessTokenResponse accessToken = new ObjectMapper().readValue(String.valueOf(json), AccessTokenResponse.class);
                                onLoginSuccess(accessToken);
                                //Utils.setAccessToken(accessToken.getAccess_token());
                                //id = json.getLong("user_id");
                                //getUserEmployee(id);


                            } catch (IOException | JSONException e) {
                                onLoginError(mPresenter.context.getString(R.string.error_something_went_wrong));
                                e.printStackTrace();
                            }//*/
                        } else if (response.code() == 400) {
                            onLoginError(mPresenter.context.getString(R.string.error_credentials));
                        } else if (response.code() == 403) {
                            onLoginError(mPresenter.context.getString(R.string.error_insufficient_permits));
                            //Utils.snackbarLong(SigninActivity.this, "Permiso insuficientes", contButtonSignIn);
                        } else {
                            onLoginError(mPresenter.context.getString(R.string.error_connection));
                            //Utils.snackbarLong(SigninActivity.this, R.string.error_connection, contButtonSignIn);
                            //LogDataBase.d("DEBUG", "Error en la conexion con el user " + user.getEmail());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        onLoginError(null);
                        //Utils.snackbarLong(SigninActivity.this, R.string.error_connection, contButtonSignIn);
                        Log.d("ERROR", t.toString());
                        t.printStackTrace();
                    }
                });

            /*AuthorizationRequest authorizationRequest = new AuthorizationRequest(email, password);


            Call<LoginResponse> call = userApi.loginRequest(authorizationRequest.grant_type,authorizationRequest.username
                                                            ,authorizationRequest.password,
                                                            authorizationRequest.client_id);
            call.enqueue(new Callback<LoginResponse>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        try {
                            onLoginSuccess(response.body());
                        } catch (Exception e) {
                            e.printStackTrace();
                            onLoginError(null);
                        }
                    } else {
                        //try {
                            //JSONObject errorJsonObject = new JSONObject(response.errorBody().string());
                            LogDataBase.d("LOGIN", "--->loginErrorResponse: " + response.errorBody().toString());//errorJsonObject);
                            //onLoginError(errorJsonObject.optString("message", null));
                        /*} catch (JSONException | IOException e) {
                            e.printStackTrace();
                            onLoginError(null);
                        }*/
                    /*}
                }

                @DebugLog
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    t.printStackTrace();
                    LogDataBase.e("RETRO", "--->Login Repository onFailure");
                    onLoginError(null);
                }
            });*/
        }
    }

    @Override
    public void recoveryPasswordRequest(String email) {

    }


    public void getUser(final AccessTokenResponse accessTokenResponse) {

            Call<ResponseBody> call = userApi.getUser(accessTokenResponse.getUser_id());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    Log.d("DEBUG", "response: " + response.code());
                    if (response.code() == 200) {
                        try {
                            JSONObject json = new JSONObject(response.body().string());
                            Log.d("DEBUG user", "json " + json);
                            JSONObject result = json.getJSONObject("result");
                            String email = result.getJSONObject("authUser").getString("email");
                            String name = result.getString("firstName");
                            Long id = result.getLong("id");
                            saveTokens(accessTokenResponse, email, name, id);
                            mPresenter.onLoginSuccess();
                        } catch (IOException | JSONException e) {
                            //onLoginError(mPresenter.context.getString(R.string.error_something_went_wrong));
                            e.printStackTrace();
                        }
                    } else  {
                        onLoginError(mPresenter.context.getString(R.string.error_connection));
                        //Utils.snackbarLong(SigninActivity.this, R.string.error_connection, contButtonSignIn);
                        //LogDataBase.d("DEBUG", "Error en la conexion con el user " + user.getEmail());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    onLoginError(null);
                    //Utils.snackbarLong(SigninActivity.this, R.string.error_connection, contButtonSignIn);
                    Log.d("ERROR", t.toString());
                    t.printStackTrace();
                }
            });
    }

    @DebugLog
    @Override
    public void onLoginSuccess(AccessTokenResponse accessTokenResponse) {
        getUser(accessTokenResponse);
        //saveTokens(accessTokenResponse);
        //mPresenter.onLoginSuccess();
    }

    @DebugLog
    @Override
    public void onLoginError(String errorMessageFromServer) {
        mPresenter.onLoginError(errorMessageFromServer);
    }

    @DebugLog
    @Override
    public void saveTokens(AccessTokenResponse accessTokenResponse, String email, String name, Long id) {
        Context context = mPresenter.context;
        SessionManager.updateSession(context, accessTokenResponse, email, name, id);
    }

}
