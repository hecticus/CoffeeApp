package com.hecticus.eleta.purchases.detail;

import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailPurity;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.item.ItemType;
import com.hecticus.eleta.model.response.item.ItemTypesListResponse;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.purity.Purity;
import com.hecticus.eleta.model.response.purity.PurityListResponse;
import com.hecticus.eleta.model.response.store.Store;
import com.hecticus.eleta.model.response.store.StoresListResponse;

import java.util.List;

/**
 * Created by roselyn545 on 22/9/17.
 */

public class PurchaseDetailsContract {

    public interface View {

        void showWorkingIndicator();

        void hideWorkingIndicator();

        void handleSuccessfulUpdate();

        void showMessage(String message);

        void enableEdition(boolean enabled);

        void onClickSaveChangesButton();

        void onClickAddProviderButton();

        void updateStores(List<Store> stores, int selectedId);

        void updateItems(List<ItemType> itemTypeList, int selectedId);

        void updatePurities(List<Purity> puritiesList);

        void updateProvider(String provider);

        void loadHeader(String providerName, String imageUrl);

        void loadFields(boolean freight, String amount, String price, String dispatcher, String observation);

        void invalidToken();

        void showDialogConfirmation();

    }

    public interface Actions {

        void initFields();

        boolean isCanEdit();

        boolean isAdd();

        void onSaveChanges(Store selectedStore, boolean freight, int itemId, String amount, String price, List<Purity> purities, String dispatcher, String observations);

        InvoicePost getChanges(int storeId, boolean freight, int itemId, String amount, String price, List<Purity> purities, String dispatcher, String observations);

        void onUpdateError();

        void onError(String error);

        void onPurchaseUpdated();

        void loadStores(List<Store> storesList);

        void loadSortedStores(List<Store> storesList);

        void loadItems(List<ItemType> itemTypeList);

        void loadPurities(List<Purity> purityList);

        void loadSortedPurities(List<Purity> purityList);

        void onProviderSelected(Provider provider);

        void invalidToken();

        void onSaveConfirmedInDialog();
    }

    public interface Repository {

        void savePurchaseRequest(InvoicePost invoicePost, boolean isAdd);

        void editPurchaseRequest(InvoicePost invoicePost, InvoiceDetails invoiceDetail);

        void onError();

        void onError(String error);

        void onPurchaseUpdated();

        void getItemTypesRequest();

        void onItemTypesSuccess(ItemTypesListResponse response);

        void getStoresRequest();

        void onStoresSuccess(StoresListResponse response);

        void getPuritiesRequest(boolean purchaseHasOfflineOperation);

        void onPuritiesSuccess(PurityListResponse response);

        ItemType getItemTypeById(int id);

        Store getStoreById(int id);

        List<InvoiceDetailPurity> getPuritiesByLocalDetailId(String idDetail);

        /*Store getStoreById(int id);

        ItemType getItemTypeById(int id);

        List<InvoiceDetailPurity> getDetailPuritiesByIdDetail(int id);*/
    }
}
