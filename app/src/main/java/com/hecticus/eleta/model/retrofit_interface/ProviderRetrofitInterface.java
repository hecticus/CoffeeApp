package com.hecticus.eleta.model.retrofit_interface;

import com.hecticus.eleta.model.response.Message;
import com.hecticus.eleta.model.response.providers.ProvidersListResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Edwin on 2017-09-16.
 */

public interface ProviderRetrofitInterface {

    static final String PROVIDER_SEARCH_URL = "provider/getByNameDocByTypeProvider/{name}/{providerType}/ASC";
    static final String PROVIDER_DELETE_URL = "provider/{idProvider}";
    static final String PROVIDERS_BY_TYPE_URL = "provider/getByTypeProvider/{providerTypeId}/ASC";

    @GET(PROVIDER_SEARCH_URL)
    Call<ProvidersListResponse> searchProviders(@Path("providerType") int providerType, @Path("name") String name);

    @GET(PROVIDERS_BY_TYPE_URL)
    Call<ProvidersListResponse> providersByType(@Path("providerTypeId") int providerTypeId);


    @DELETE(PROVIDER_DELETE_URL)
    Call<Message> deleteProvider(@Path("idProvider") int idProvider);

}
