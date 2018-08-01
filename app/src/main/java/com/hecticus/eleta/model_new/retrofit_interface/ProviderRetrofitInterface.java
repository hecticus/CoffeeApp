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

    /*String CREATE_OR_UPDATE_PROVIDER_DATA_URL = "provider";
    String UPDATE_PROVIDER_IMAGE_URL = "provider/uploadPhotoProvider";
    String PROVIDER_SEARCH_URL = "provider/getByNameDocByTypeProvider/{name}/{providerType}/ASC";
    String PROVIDER_DELETE_URL = "provider/{idProvider}";
    String PROVIDERS_BY_TYPE_URL = "provider/getByTypeProvider/{providerTypeId}/ASC";

    @POST("provider")
    Call<ProviderCreationResponse> createProvider(@Body Provider providerToCreate);

    @PUT("provider")
    Call<ProviderCreationResponse> updateProviderData(@Body Provider providerToUpdate);

    @POST("provider/uploadPhotoProvider")
    Call<ProviderImageUpdateResponse> updateProviderImage(@Body Provider providerWithBase64Image);

    @GET("provider/getByNameDocByTypeProvider/{name}/{providerType}/ASC")
    Call<ProvidersListResponse> searchProviders(@Path("providerType") int providerType, @Path("name") String name);

    @GET("provider/getByTypeProvider/{providerTypeId}/ASC")
    Call<ProvidersListResponse> providersByType(@Path("providerTypeId") int providerTypeId);

    @DELETE("provider/{idProvider}")
    Call<Message> deleteProvider(@Path("idProvider") int idProvider);*/

    /*
    #NEW
GET     /provider                                                               controllers.Providers.findAll( pageindex: Integer ?= null, pagesize: Integer ?= null, collection: String ?= null, sort: String ?= null, nameProvider: String ?= null, providerType: Long ?= 0L, nitProvider: String ?= null, addressProvider: String ?= null, numberProvider: String ?= null, emailProvider: String ?= null, contactNameProvider: String ?= null, statusProvider: Long ?= 0L, deleted: Boolean ?= false)
GET     /provider/:id                                                           controllers.Providers.findById(id : Long)
POST    /provider                                                               controllers.Providers.create()
POST    /provider/delete                                                        controllers.Providers.deletes()
#POST    /provider/uploadPhotoProvider                                           controllers.Providers.uploadPhotoProvider()
PUT     /provider/:id                                                                controllers.Providers.update(id : Long)
DELETE  /provider/:id                                                           controllers.Providers.delete(id : Long)
     */
    @POST("provider")
    Call<ProviderCreationResponse> createProvider( @Body Provider providerToCreate);

    @PUT("provider/{id}")
    Call<ProviderCreationResponse> updateProviderData(@Path("id") int id, @Body Provider providerToUpdate);

    @POST("provider/{id}/mediaProfile")//("provider/uploadPhotoProvider")
    Call<ResponseBody> updateProviderImage(@Path("id") int id, @Body MultimediaProfile media);

    @PUT("multimedias/{id}")
    Call<ResponseBody> putProviderImage(@Path("id") int id, @Body MultimediaProfile media);

    @GET("provider/{id}")
    Call<ResponseBody> getProviderById(@Path("id") int id);

    @GET("provider")
    Call<ProvidersListResponse> searchProviders(@Query("providerType") int providerType, @Query("nameProvider") String name);

    @GET("provider")
    Call<ProvidersListResponse> providersByType(@Query("providerType") int providerTypeId);

    @DELETE("provider/{idProvider}")
    Call<Message> deleteProvider(@Path("idProvider") int idProvider);

}
