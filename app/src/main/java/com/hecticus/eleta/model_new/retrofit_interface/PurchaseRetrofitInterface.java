package com.hecticus.eleta.model_new.retrofit_interface;

import com.hecticus.eleta.model.response.item.ItemTypesListResponse;
import com.hecticus.eleta.model.response.purity.PurityListResponse;
import com.hecticus.eleta.model.response.store.StoresListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by roselyn545 on 26/9/17.
 */

public interface PurchaseRetrofitInterface {

    //listo
    @GET("store")
    Call<StoresListResponse> getStores();

    //listo
    @GET("purity")
    Call<PurityListResponse> getPurities();

    //listo
    @GET("itemType")//getByProviderTypeId/{providerTypeId}/0?sort=nameItemType")
    Call<ItemTypesListResponse> getItemsType(@Query("sort") String sort, @Query("providerType") int providerType);


}
