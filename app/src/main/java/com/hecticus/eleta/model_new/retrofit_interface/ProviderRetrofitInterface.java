package com.hecticus.eleta.model_new.retrofit_interface;

import com.hecticus.eleta.model.response.Message;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.providers.ProviderCreationResponse;
import com.hecticus.eleta.model.response.providers.ProviderImageUpdateResponse;
import com.hecticus.eleta.model.response.providers.ProvidersListResponse;
import com.hecticus.eleta.model_new.MultimediaProfile;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Edwin on 2017-09-16.
 */

public interface ProviderRetrofitInterface {

    //listo sincroniza
    @POST("provider")
    Call<ProviderCreationResponse> createProvider(@Body Provider providerToCreate);

    //listo sincroniza
    @PUT("provider/{id}")
    Call<ProviderCreationResponse> updateProviderData(@Path("id") int id, @Body Provider providerToUpdate);

    //nada no esta offline
    @POST("provider/{id}/mediaProfile")
    Call<ResponseBody> updateProviderImage(@Path("id") int id, @Body MultimediaProfile media);

    //nada no estaoffline
    @PUT("multimedias/{id}")
    Call<ResponseBody> putProviderImage(@Path("id") int id, @Body MultimediaProfile media);

    //listo
    @GET("provider")
    Call<ProvidersListResponse> searchProviders(@Query("providerType") int providerType, @Query("") String name);

    //listo
    @GET("provider")
    Call<ProvidersListResponse> providersByType(@Query("providerType") int providerTypeId);

    //listo
    @DELETE("provider/{idProvider}")
    Call<ResponseBody> deleteProvider(@Path("idProvider") int idProvider);

}
