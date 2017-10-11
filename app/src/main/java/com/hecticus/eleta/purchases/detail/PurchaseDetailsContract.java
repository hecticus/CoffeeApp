package com.hecticus.eleta.purchases.detail;

import com.hecticus.eleta.model.PurchaseModel;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
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

        void onClickSaveChangesButton();

        void onClickAddProviderButton();

        void updateStores(List<Store> stores, int selectedId);

        void updateItems(List<ItemType> itemTypeList, int selectedId);

        void updatePurities(List<Purity> puritiesList);

        void updateProvider(String provider);

        void loadHeader(String providerName, String imageUrl);

        void loadFields(boolean freight, String amount, String price, String dispatcher, String observation);
    }

    public interface Actions {

        void initFields();

        boolean isCanEdit();

        boolean isAdd();

        void onSaveChanges(int storeId, boolean freight, int itemId, String amount, String price, List<Purity> purities, String dispatcher, String observations);

        InvoicePost getChanges(int storeId, boolean freight, int itemId, String amount, String price, List<Purity> purities, String dispatcher, String observations);

        void onUpdateError();

        void onError(String error);

        void onUpdatePurchase();

        void loadStores(List<Store> storesList);

        void loadItems(List<ItemType> itemTypeList);

        void loadPurities(List<Purity> purityList);

        void onProviderSelected(Provider provider);

    }

    public interface Repository {

        void savePurchaseResquest(InvoicePost invoicePost, boolean isAdd);

        void onError();

        void onError(String error);

        void onSuccessUpdatePurchase();

        void getItemTypesRequest();

        void onItemTypesSuccess(ItemTypesListResponse response);

        void getStoresRequest();

        void onStoresSuccess(StoresListResponse response);

        void getPuritiesRequest();

        void onPuritiesSuccess(PurityListResponse response);

    }
}
