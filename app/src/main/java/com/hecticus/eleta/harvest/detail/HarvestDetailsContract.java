package com.hecticus.eleta.harvest.detail;

import com.hecticus.eleta.model.HarvestModel;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.response.farm.Farm;
import com.hecticus.eleta.model.response.farm.FarmsListResponse;
import com.hecticus.eleta.model.response.item.ItemType;
import com.hecticus.eleta.model.response.item.ItemTypesListResponse;
import com.hecticus.eleta.model.response.lot.Lot;
import com.hecticus.eleta.model.response.lot.LotsListResponse;
import com.hecticus.eleta.model.response.providers.Provider;

import java.util.HashMap;
import java.util.List;

/**
 * Created by roselyn545 on 16/9/17.
 */

public class HarvestDetailsContract {

    public interface View {

        void showWorkingIndicator();

        void hideWorkingIndicator();

        void handleSuccessfulUpdate();

        void showUpdateMessage(String message);

        void enableEdition(boolean enabled);

        void onClickSaveChangesButton();

        void onClickAddHarvesterButton();

        void updateFarms(List<Farm> farmsList, int selectedId);

        void updateLots(List<Lot> lotsList, int selectedId);

        void updateItems(List<ItemType> itemTypeList);

        void updateProvider(String provider);

        void loadHeader(String providerName, String imageUrl);

        void loadObservation(String observation);

    }

    public interface Actions {

        void initFields();

        boolean isCanEdit();

        boolean isAdd();

        void onSaveChanges(int lotId, List<ItemType> items, String observations);

        InvoicePost getChanges(int lotId, List<ItemType> items, String observations);

        void onUpdateError();

        void onError(String error);

        void onUpdateHarvest();

        void loadFarms(List<Farm> farmsList);

        void loadLots(List<Lot> lotsList);

        void loadItems(List<ItemType> itemTypeList);

        void getLotsByFarm(int idFarm);

        void onProviderSelected(Provider provider);

    }

    public interface Repository {

        void saveHarvestResquest(InvoicePost invoicePost, boolean isAdd);

        void onError();

        void onError(String error);

        void onSuccessUpdateHarvest();

        void getItemTypesRequest();

        void onItemTypesSuccess(ItemTypesListResponse response);

        void getFarmsRequest();

        void onFarmsSuccess(FarmsListResponse response);

        void getLotsByFarmRequest(int idFarm);

        void onLotsSuccess(LotsListResponse response);
    }
}
