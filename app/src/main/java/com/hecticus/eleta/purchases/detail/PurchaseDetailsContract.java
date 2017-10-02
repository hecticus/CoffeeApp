package com.hecticus.eleta.purchases.detail;

import com.hecticus.eleta.model.PurchaseModel;
import com.hecticus.eleta.model.response.purity.Purity;
import com.hecticus.eleta.model.response.item.ItemType;
import com.hecticus.eleta.model.response.item.ItemTypesListResponse;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.purity.PurityListResponse;
import com.hecticus.eleta.model.response.store.Store;
import com.hecticus.eleta.model.response.store.StoresListResponse;

import java.util.HashMap;
import java.util.List;

/**
 * Created by roselyn545 on 22/9/17.
 */

public class PurchaseDetailsContract {

    public interface View {

        void showWorkingIndicator();

        void hideWorkingIndicator();

        void handleSuccessfulUpdate();

        void showUpdateMessage(String message);

        void enableEdition(boolean enabled);

        PurchaseModel getValues();

        void updateFields(PurchaseModel purchase);

        void updateMenuOptions();

        void onClickSaveChangesButton();

        void onClickAddProviderButton();

        void updateStores(List<Store> stores, int selectedId);

        void updateItems(List<ItemType> itemTypeList, int selectedId);

        void updatePurities(List<Purity> puritiesList);

        void updateProvider(String provider);

        void loadHeader(String providerName, String imageUrl);

        void loadObservation(String observation);
    }

    public interface Actions {

        void initFields();

        boolean isCanEdit();

        boolean isAdd();

        void onSaveChanges(PurchaseModel purchaseModel);

        HashMap<String, Object> getChanges(PurchaseModel purchaseModel);

        void onCancelChangesButtonClicked();

        void onUpdateError();

        void onError(String error);

        void onUpdatePurchase(PurchaseModel purchaseModel);

        void loadStores(List<Store> storesList);

        void loadItems(List<ItemType> itemTypeList);

        void loadPurities(List<Purity> purityList);

        void undoChanges();

        void onProviderSelected(Provider provider);

    }

    public interface Repository {

        void updatedPurchaseResquest(HashMap<String, Object> purchase);

        void savePurchaseResquest(PurchaseModel purchaseModel);

        void onError();

        void onError(String error);

        void onSuccessUpdatePurchase(PurchaseModel purchaseModel);

        void getItemTypesRequest();

        void onItemTypesSuccess(ItemTypesListResponse response);

        void getStoresRequest();

        void onStoresSuccess(StoresListResponse response);

        void getPuritiesRequest();

        void onPuritiesSuccess(PurityListResponse response);

    }
}
