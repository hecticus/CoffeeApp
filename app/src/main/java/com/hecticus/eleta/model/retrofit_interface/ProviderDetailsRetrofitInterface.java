package com.hecticus.eleta.model.retrofit_interface;

import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.providers.ProviderCreationResponse;
import com.hecticus.eleta.model.response.providers.ProviderImageUpdateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Edwin on 2017-09-17.
 */

public interface ProviderDetailsRetrofitInterface {

    String CREATE_OR_UPDATE_PROVIDER_DATA_URL = "provider";
    String UPDATE_PROVIDER_IMAGE_URL = "provider/uploadPhotoProvider";

    @POST(CREATE_OR_UPDATE_PROVIDER_DATA_URL)
    Call<ProviderCreationResponse> createProvider(@Body Provider providerToCreate);

    @PUT(CREATE_OR_UPDATE_PROVIDER_DATA_URL)
    Call<ProviderCreationResponse> updateProviderData(@Body Provider providerToUpdate);

    @POST(UPDATE_PROVIDER_IMAGE_URL)
    Call<ProviderImageUpdateResponse> updateProviderImage(@Body Provider providerWithBase64Image);

}
