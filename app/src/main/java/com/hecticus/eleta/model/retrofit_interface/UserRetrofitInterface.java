package com.hecticus.eleta.model.retrofit_interface;

import com.hecticus.eleta.model.request.LoginPost;
import com.hecticus.eleta.model.response.LoginResponse;
import com.hecticus.eleta.model.response.Message;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by roselyn545 on 15/9/17.
 */

public interface UserRetrofitInterface {

    String loginUrl = "user/login";
    String logoutUrl = "user/logout";
    String recoverPasswordUrl = "user/reset/{email}";

    @POST(loginUrl)
    Call<LoginResponse> loginRequest(@Body LoginPost post);

    @GET(logoutUrl)
    Call<Message> logOutRequest();

    @GET(recoverPasswordUrl)
    Call<Message> recoverPasswordRequest(@Path("email") String email);
}
