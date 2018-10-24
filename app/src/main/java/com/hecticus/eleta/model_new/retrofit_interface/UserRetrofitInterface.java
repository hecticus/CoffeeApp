package com.hecticus.eleta.model_new.retrofit_interface;

import com.hecticus.eleta.model.response.Message;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by roselyn545 on 15/9/17.
 */

public interface UserRetrofitInterface {

    @FormUrlEncoded
    @POST("oauth/token")
    Call<ResponseBody> loginRequest(@Field("grant_type") String grant_type, @Field("username") String username
            , @Field("password") String password, @Field("client_id") String client_id);

    @GET("user/{id}")
    Call<ResponseBody> getUser(@Path("id") long id);

    @GET("oauth/revokeToken")
    Call<ResponseBody> logOutRequest();

    @GET("oauth/auth")
    Call<ResponseBody> recoverPasswordRequest(@Path("email") String email);
}
