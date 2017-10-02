package com.hecticus.eleta.model.retrofit_interface;

import com.hecticus.eleta.model.response.Message;
import com.hecticus.eleta.model.response.farm.FarmsListResponse;
import com.hecticus.eleta.model.response.harvest.HarvestsListResponse;
import com.hecticus.eleta.model.response.item.ItemTypesListResponse;
import com.hecticus.eleta.model.response.lot.LotsListResponse;
import com.hecticus.eleta.model.response.providers.ProvidersListResponse;
import com.hecticus.eleta.util.Constants;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by roselyn545 on 17/9/17.
 */

public interface HarvestRetrofitInterface {

    static final String HARVEST_SEARCH_URL = "provider/search?sort=fullName_Provider&collection=m";
    static final String HARVEST_DELETE_URL = "provider/{idProvider}";
    static final String FARMS_URL = "farm?sort=name_farm&collection=s";
    static final String LOTS_BY_FARM_URL = "lot/getByIdFarm/{idFarm}";
    static final String HARVEST_ITEMS_URL = "itemType/getByProviderTypeId/{providerTypeId}/0"; //Constants.TYPE_HARVESTER

    @GET(HARVEST_SEARCH_URL)
    Call<HarvestsListResponse> searchHarvest();

    @DELETE(HARVEST_DELETE_URL)
    Call<Message> deleteHarvest(@Path("idHarvest") int idHarvest);

    @GET(FARMS_URL)
    Call<FarmsListResponse> getFarms();

    @GET(LOTS_BY_FARM_URL)
    Call<LotsListResponse> getLotsByFarm(@Path("idFarm") int idFarm);

    @GET(HARVEST_ITEMS_URL)
    Call<ItemTypesListResponse> getItemsType(@Path("providerTypeId") int providerTypeId);
}
