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

    //static final String HARVEST_SEARCH_URL = "provider/search?sort=fullName_Provider&collection=m";
    //static final String HARVEST_DELETE_URL = "provider/{idProvider}";
    static final String STORES_URL = "store/getByStatusStore/1/ASC";
    static final String PURITIES_URL = "purity/getByStatusPurity/1/ASC";
    static final String PURCHASE_ITEMS_URL = "itemType/getByProviderTypeId/{providerTypeId}/0?sort=nameItemType"; //Constants.TYPE_HARVESTER

    /*@GET(HARVEST_SEARCH_URL)
    Call<HarvestsListResponse> searchHarvest();

    @DELETE(HARVEST_DELETE_URL)
    Call<Message> deleteHarvestInvoice(@Path("idHarvest") int idHarvest);*/

    /*
    @GET("store/getByStatusStore/1/ASC")
    Call<StoresListResponse> getStores();

    @GET("purity/getByStatusPurity/1/ASC")
    Call<PurityListResponse> getPurities();

    @GET("itemType/getByProviderTypeId/{providerTypeId}/0?sort=nameItemType")
    Call<ItemTypesListResponse> getItemsType(@Path("providerTypeId") int providerTypeId);
     */

    /*

     */

    @GET("store")
    Call<StoresListResponse> getStores(/*@Query("statusStore") Long statusStore*/);

    @GET("purity")
    Call<PurityListResponse> getPurities(/*@Query("statusStore") Long statusStore*/);

    @GET("itemType")//getByProviderTypeId/{providerTypeId}/0?sort=nameItemType")
    Call<ItemTypesListResponse> getItemsType(@Query("sort") String sort, @Query("providerType") int providerType);


}
