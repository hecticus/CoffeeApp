package com.hecticus.eleta.model_new.retrofit_interface;

import com.hecticus.eleta.model.response.Message;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.providers.ProviderCreationResponse;
import com.hecticus.eleta.model.response.providers.ProviderImageUpdateResponse;
import com.hecticus.eleta.model.response.providers.ProvidersListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Edwin on 2017-09-16.
 */

public interface ProviderRetrofitInterface {

    String CREATE_OR_UPDATE_PROVIDER_DATA_URL = "provider";
    String UPDATE_PROVIDER_IMAGE_URL = "provider/uploadPhotoProvider";
    String PROVIDER_SEARCH_URL = "provider/getByNameDocByTypeProvider/{name}/{providerType}/ASC";
    String PROVIDER_DELETE_URL = "provider/{idProvider}";
    String PROVIDERS_BY_TYPE_URL = "provider/getByTypeProvider/{providerTypeId}/ASC";

    @POST(CREATE_OR_UPDATE_PROVIDER_DATA_URL)
    Call<ProviderCreationResponse> createProvider(@Body Provider providerToCreate);

    @PUT(CREATE_OR_UPDATE_PROVIDER_DATA_URL)
    Call<ProviderCreationResponse> updateProviderData(@Body Provider providerToUpdate);

    @POST(UPDATE_PROVIDER_IMAGE_URL)
    Call<ProviderImageUpdateResponse> updateProviderImage(@Body Provider providerWithBase64Image);

    @GET(PROVIDER_SEARCH_URL)
    Call<ProvidersListResponse> searchProviders(@Path("providerType") int providerType, @Path("name") String name);

    @GET(PROVIDERS_BY_TYPE_URL)
    Call<ProvidersListResponse> providersByType(@Path("providerTypeId") int providerTypeId);

    @DELETE(PROVIDER_DELETE_URL)
    Call<Message> deleteProvider(@Path("idProvider") int idProvider);

}
