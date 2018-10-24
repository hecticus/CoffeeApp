package com.hecticus.eleta.model_new.retrofit_interface;

import com.hecticus.eleta.model.response.Message;
import com.hecticus.eleta.model.response.farm.FarmsListResponse;
import com.hecticus.eleta.model.response.harvest.HarvestsListResponse;
import com.hecticus.eleta.model.response.item.ItemTypesListResponse;
import com.hecticus.eleta.model.response.lot.LotsListResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by roselyn545 on 17/9/17.
 */

public interface HarvestRetrofitInterface {

    //listo
    @GET("farm?sort=nameFarm")
    Call<FarmsListResponse> getFarms();

    //listo
    @GET("lot")
    Call<LotsListResponse> getLot();

    //listo
    @GET("lot")
    Call<LotsListResponse> getLotsByFarm(@Query("farm") int idFarm, @Query("sort") String name);

    //listo
    @GET("itemType")//todo falta el status
    Call<ItemTypesListResponse> getItemsType(@Query("providerType") int providerTypeId);

}
