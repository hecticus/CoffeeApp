package com.hecticus.eleta.purchases.detail;

import android.content.Context;

import com.hecticus.eleta.R;
import com.hecticus.eleta.model.PurchaseModel;
import com.hecticus.eleta.model.request.invoice.ItemPost;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.item.ItemType;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.purity.Purity;
import com.hecticus.eleta.model.response.store.Store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 22/9/17.
 */

public class PurchaseDetailsPresenter implements PurchaseDetailsContract.Actions{

    Context context;
    private PurchaseDetailsContract.View mView;
    private PurchaseDetailsContract.Repository mRepository;
    //private PurchaseModel currentPurchase;
    private boolean isAdd = false;
    private boolean canEdit = false;
    private Provider currentProvider = null;
    private List<InvoiceDetails> currentDetailsList = null;

    private boolean initializedStore = false;
    private boolean initializedItems = false;

    @DebugLog
    public PurchaseDetailsPresenter(Context context, PurchaseDetailsContract.View mView, boolean isAddParam, boolean canEditParam, Provider provider, List<InvoiceDetails> details) {
        this.context = context;
        this.mView = mView;
        mRepository = new PurchaseDetailsRepository(this);
        //currentPurchase = currentPurchaseParam;
        isAdd = isAddParam;
        canEdit = canEditParam;
        currentProvider = provider;
        currentDetailsList = details;
    }

    @Override
    public void initFields() {
        mView.showWorkingIndicator();
        mView.enableEdition(canEdit);
        mRepository.getStoresRequest();
        mRepository.getItemTypesRequest();
        mRepository.getPuritiesRequest();

        if (!isAdd) {
            if (currentProvider!=null) {
                mView.loadHeader(currentProvider.getFullNameProvider(),currentProvider.getPhotoProvider());
                if (currentDetailsList != null && currentDetailsList.size()>0) {
                    mView.loadObservation(currentDetailsList.get(0).getObservation());
                }
            }
        } else {

        }
    }

    @Override
    public boolean isCanEdit() {
        return canEdit;
    }

    @Override
    public boolean isAdd() {
        return isAdd;
    }

    @Override
    public void onSaveChanges(PurchaseModel purchaseModel) {

        if (purchaseModel != null) {
            if (currentProvider == null) {
                mView.showUpdateMessage(context.getString(R.string.you_must_select_a_harvester));
                return;
            }
            if (purchaseModel.getStoreId() == -1) {
                mView.showUpdateMessage(context.getString(R.string.you_must_select_a_valid_lot));
                return;
            }

            if (purchaseModel.getItems() == null || purchaseModel.getItems().size() == 0) {
                mView.showUpdateMessage(context.getString(R.string.you_must_enter_some_weight));
                return;
            }

            if (isAdd()) {
                if (getItems(purchaseModel.getItems()).size() == 0) {
                    mView.showUpdateMessage(context.getString(R.string.you_must_enter_some_weight));
                    return;
                }

                //mRepository.saveHarvestResquest(harvest);
            } else {
                /*HashMap<String, Object> changes = getChanges(harvest);
                mView.showWorkingIndicator();
                mRepository.updatedHarvestResquest(changes);*/
            }
        }
    }

    private List<ItemPost> getItems(List<ItemType> items) {
        List<ItemPost> post = new ArrayList<ItemPost>();
        for (ItemType item: items) {
            if (item.getWeightString().trim().isEmpty()){
                post.add(new ItemPost(item.getId(), Integer.parseInt(item.getWeightString().trim())));
            }
        }
        return post;
    }

    @Override
    public HashMap<String, Object> getChanges(PurchaseModel purchaseModel) {
        //TODO
        return null;
    }

    @Override
    public void onCancelChangesButtonClicked() {
        undoChanges();
    }

    @Override
    public void onUpdateError() {
        mView.hideWorkingIndicator();
        mView.showUpdateMessage(context.getString(R.string.error_saving_changes));
    }

    @Override
    public void onError(String error) {
        mView.showUpdateMessage(error);
    }


    @Override
    public void onUpdatePurchase(PurchaseModel purchaseModel) {
        //this.currentPurchase = purchaseModel;
        mView.updateFields(purchaseModel);
        mView.hideWorkingIndicator();
        mView.updateMenuOptions();
        mView.showUpdateMessage(context.getString(R.string.data_updated_correctly));
    }

    @DebugLog
    @Override
    public void loadStores(List<Store> storesList) {
        if (!isAdd && !initializedStore) {
            initializedStore = true;
            if (currentDetailsList.size()<=0){
                mView.updateStores(storesList, -1);
                return;
            }
            Store store = currentDetailsList.get(0).getStore();
            if (store!=null){
                mView.updateStores(storesList, store.getId());
                return;
            }
        }

        mView.updateStores(storesList, -1);
    }

    @Override
    public void loadItems(List<ItemType> itemTypeList) {
        mView.hideWorkingIndicator();

        if (!isAdd && !initializedItems) {
            initializedItems = true;

            /*for (ItemType item: itemTypeList) {
                InvoiceDetails invoice = InvoiceDetails.getItem(currentDetailsList,item.getId());
                if (invoice != null){
                    item.setWeightString(invoice.getAmount()+"");
                }
            }*/
        }
        mView.updateItems(itemTypeList,-1);
    }

    @Override
    public void loadPurities(List<Purity> purityList) {
        mView.hideWorkingIndicator();

        if (!isAdd && !initializedItems) {
            initializedItems = true;

            /*for (Purity item: purityList) {
                InvoiceDetails invoice = InvoiceDetails.getItem(currentDetailsList,item.getId());
                if (invoice != null){
                    item.setWeightString(invoice.getAmount()+"");
                }
            }*/
        }
        mView.updatePurities(purityList);
    }

    @Override
    public void undoChanges() {
        //mView.updateFields(currentPurchase);
    }

    @Override
    public void onProviderSelected(Provider provider) {
        currentProvider = provider;
        mView.updateProvider(provider.getFullNameProvider());
    }
}
