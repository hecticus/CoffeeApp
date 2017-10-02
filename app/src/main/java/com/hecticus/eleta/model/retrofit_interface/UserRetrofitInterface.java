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

    static final String loginURL = "user/login";
    static final String recoverPasswordURL = "user/reset/{email}";

    @POST(loginURL)
    Call<LoginResponse> loginRequest(@Body LoginPost post);

    @GET(recoverPasswordURL)
    Call<Message> recoverPassword(@Path("email") String email);

}
