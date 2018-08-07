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

    static final String HARVEST_SEARCH_URL = "provider/search?sort=fullName_Provider&collection=m";
    static final String HARVEST_DELETE_URL = "provider/{idProvider}";
    static final String FARMS_URL = "farm?sort=name_farm&collection=s";
    static final String LOTS_BY_FARM_URL = "lot/getByIdFarm/{idFarm}?sort=nameLot";
    static final String HARVEST_ITEMS_URL = "itemType/getByProviderTypeId/{providerTypeId}/0"; //Constants.TYPE_HARVESTER

    /*@GET("provider/search?sort=fullName_Provider&collection=m")
    Call<HarvestsListResponse> searchHarvest();

    @DELETE("provider/{idProvider}")
    Call<Message> deleteHarvest(@Path("idHarvest") int idHarvest);

    @GET("farm?sort=name_farm&collection=s")
    Call<FarmsListResponse> getFarms();

    @GET("lot/getByIdFarm/{idFarm}?sort=nameLot")
    Call<LotsListResponse> getLotsByFarm(@Path("idFarm") int idFarm);

    @GET("itemType/getByProviderTypeId/{providerTypeId}/0")
    Call<ItemTypesListResponse> getItemsType(@Path("providerTypeId") int providerTypeId);*/



    @GET("provider/search?sort=fullName_Provider&collection=m")
    Call<HarvestsListResponse> searchHarvest();

    @DELETE("provider/{id}")
    Call<Message> deleteHarvest(@Path("id") int idHarvest);

    //listo
    @GET("farm?sort=nameFarm")//&collection=s")
    Call<FarmsListResponse> getFarms();

    //listo
    @GET("lot")
    Call<LotsListResponse> getLotsByFarm(@Query("farm") int idFarm, @Query("sort") String name);

    //listo
    @GET("itemType")//todo falta el status
    Call<ItemTypesListResponse> getItemsType(@Query("providerType") int providerTypeId);

}
