package com.hecticus.eleta.purchases.detail;

import android.content.Context;
import android.util.Log;

import com.hecticus.eleta.R;
import com.hecticus.eleta.model_new.SessionManager;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.request.invoice.ItemPost;
import com.hecticus.eleta.model.request.invoice.PurityPost;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailPurity;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.item.ItemType;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.purity.Purity;
import com.hecticus.eleta.model.response.store.Store;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hugo.weaving.DebugLog;
import io.realm.Realm;

/**
 * Created by roselyn545 on 22/9/17.
 */

public class PurchaseDetailsPresenter implements PurchaseDetailsContract.Actions {

    Context context;
    private PurchaseDetailsContract.View mView;
    private PurchaseDetailsContract.Repository mRepository;
    private boolean isAdd = false;
    private boolean canEdit = false;
    private boolean invoiceHasOfflineOperation = false;
    private Provider currentProvider = null;
    private List<InvoiceDetails> currentDetailsList = null;

    private boolean initializedStore = false;
    private boolean initializedItems = false;
    private boolean initializedPurities = false;

    private InvoicePost invoicePost = null;

    private List<InvoiceDetailPurity> originalDetailsPuritiesList;
    private List<Purity> changedPuritiesList;

    @DebugLog
    public List<InvoiceDetailPurity> getOriginalDetailsPuritiesList() {
        return originalDetailsPuritiesList;
    }

    @DebugLog
    public PurchaseDetailsPresenter(Context context, PurchaseDetailsContract.View mView,
                                    boolean isAddParam, boolean canEditParam, boolean invoiceHasOfflineOperationParam,
                                    Provider provider, List<InvoiceDetails> details) {
        this.context = context;
        this.mView = mView;
        mRepository = new PurchaseDetailsRepository(this);
        isAdd = isAddParam;
        canEdit = canEditParam;
        invoiceHasOfflineOperation = invoiceHasOfflineOperationParam;
        currentProvider = provider;
        currentDetailsList = details;
    }

    @DebugLog
    @Override
    public void initFields() {
        mView.showWorkingIndicator();
        mView.enableEdition(canEdit);
        mRepository.getStoresRequest();
        mRepository.getItemTypesRequest();
        mRepository.getPuritiesRequest(invoiceHasOfflineOperation);

        if (!isAdd) {
            if (currentProvider != null) {
                mView.loadHeader(currentProvider.getFullNameProvider(), currentProvider.getPhotoProvider());
                if (currentDetailsList != null && currentDetailsList.size() > 0) {
                    InvoiceDetails details = currentDetailsList.get(0);
                    mView.loadFields(details.isFreight(), details.getAmount() + "", details.getPriceItem() + "", details.getDispatcherName(), details.getObservation());
                }
            }
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

    @DebugLog
    @Override
    public void onSaveChanges(Store selectedStore, boolean freight, int itemId, String amount, String price, List<Purity> purities, String dispatcher, String observations) {
        Log.d("DEBUG itemId", "desde presenter"+String.valueOf(itemId));
        mView.showWorkingIndicator();

        if (currentProvider == null) {
            mView.hideWorkingIndicator();
            mView.showMessage(context.getString(R.string.you_must_select_a_provider));
            return;
        }

        if (selectedStore == null) {
            mView.hideWorkingIndicator();
            mView.showMessage(context.getString(R.string.you_must_select_a_valid_store));
            return;
        }

        if (itemId == -1) {
            mView.hideWorkingIndicator();
            mView.showMessage(context.getString(R.string.you_must_select_a_valid_item));
            return;
        }

        if (amount.trim().isEmpty()) {
            mView.hideWorkingIndicator();
            mView.showMessage(context.getString(R.string.you_must_enter_some_weight));
            return;
        }

        if (price.trim().isEmpty()) {
            mView.hideWorkingIndicator();
            mView.showMessage(context.getString(R.string.you_must_enter_some_price));
            return;
        }

        if (purities == null || purities.size() == 0) {
            mView.hideWorkingIndicator();
            mView.showMessage(context.getString(R.string.you_must_enter_some_purity));
            return;
        }

        if (dispatcher.trim().isEmpty()) {
            mView.hideWorkingIndicator();
            mView.showMessage(context.getString(R.string.you_must_enter_dispatcher_name));
            return;
        }

        invoicePost = new InvoicePost();

        if (isAdd()) {

            List<PurityPost> puritiesPost = getPuritiesPostsList(purities);

            ItemPost item = new ItemPost(itemId, Float.parseFloat(amount.trim()));
            item.setPrice(Float.parseFloat(price.trim()));
            item.setPurities(puritiesPost);
            item.setStoreId(selectedStore.getId());

            List<ItemPost> itemList = new ArrayList<>();
            itemList.add(item);
            invoicePost.setItems(itemList);

            invoicePost.setFreigh(freight);
            invoicePost.setDispatcherName(dispatcher);
            invoicePost.setObservations(observations);

            mView.hideWorkingIndicator();
            mView.showDialogConfirmation();

        } else {
            changedPuritiesList = purities;

            invoicePost = getChanges(selectedStore.getId(), freight, itemId, amount, price, purities, dispatcher, observations);
            if (invoicePost == null) {
                mView.hideWorkingIndicator();
                return;
            }

            mView.hideWorkingIndicator();
            mView.showDialogConfirmation();
        }
    }

    @DebugLog
    private void updateOriginalDetailsPuritiesListWithChanges() {
        if (originalDetailsPuritiesList == null) {
            Log.w("PURITIES", "--->updateOriginalDetailsPuritiesListWithChanges ignored " +
                    "because of nullity");
            return;
        }

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        for (int i = 0; i < originalDetailsPuritiesList.size(); i++) {
            try {
                Log.d("PURITIES", "--->Detail change: "
                        + originalDetailsPuritiesList.get(i).getPurity().getRateValue()
                        + "->"
                        + changedPuritiesList.get(i).getWeightString());

                originalDetailsPuritiesList.get(i).setRateValue(changedPuritiesList.get(i).getWeightStringAsFloat());
                originalDetailsPuritiesList.get(i).getPurity().setRateValueAndWeightString(changedPuritiesList.get(i).getWeightString());

                Log.d("PURITIES", "--->updateOriginalDetailsPuritiesListWithChanges insertOrUpdate: "
                        + originalDetailsPuritiesList.get(i));
                realm.insertOrUpdate(originalDetailsPuritiesList.get(i));

            } catch (Exception e) {
                Log.e("PURITIES", "--->updateOriginalDetailsPuritiesListWithChanges Exception: " + e);
                e.printStackTrace();
            }
        }
        realm.commitTransaction();
    }

    @DebugLog
    private List<PurityPost> getPuritiesPostsList(List<Purity> purities) {
        List<PurityPost> post = new ArrayList<>();
        for (Purity purity : purities) {
            if (!purity.getWeightString().trim().isEmpty()) {
                post.add(new PurityPost(purity.getId(), Float.parseFloat(purity.getWeightString().trim())));
            }
        }
        return post;
    }

    @DebugLog
    @Override
    public InvoicePost getChanges(int storeId, boolean freight, int itemId, String amount, String price, List<Purity> purities, String dispatcher, String observations) {
        InvoicePost invoice = null;

        if (currentDetailsList == null || currentDetailsList.size() <= 0)
            return invoice;

        InvoiceDetails details = currentDetailsList.get(0);

        if (details.isFreight() != freight) {
            invoice = new InvoicePost();
            invoice.setFreigh(freight);
        }

        if ((details.getItemType() != null && details.getItemType().getId() != itemId) ||
                details.getPriceItem() != Float.parseFloat(price.trim()) ||
                details.getAmount() != Float.parseFloat(amount.trim()) ||
                details.getStore() != null && details.getStore().getId() != storeId ||
                details.getStore() == null) {

            if (invoice == null) {
                invoice = new InvoicePost();
            }

            ItemPost item = new ItemPost(itemId, Float.parseFloat(amount.trim()), details.getId());
            item.setPrice(Float.parseFloat(price.trim()));
            item.setPurities(new ArrayList<PurityPost>());
            item.setStoreId(storeId);

            List<ItemPost> itemList = new ArrayList<>();
            itemList.add(item);
            invoice.setItems(itemList);
        }

        if (details.getDispatcherName() != null && !details.getDispatcherName().equals(dispatcher)) {
            if (invoice == null) {
                invoice = new InvoicePost();
            }
            invoice.setDispatcherName(dispatcher);
        }

        if (details.getObservation() != null && !details.getObservation().equals(observations)) {
            if (invoice == null) {
                invoice = new InvoicePost();
            }
            invoice.setObservations(observations);
        }

        List<PurityPost> postPurities = new ArrayList<>();
        List<InvoiceDetailPurity> detailPurities = details.getDetailPurities();

        for (Purity purity : purities) {
            InvoiceDetailPurity purityDetail =
                    InvoiceDetailPurity.findInvoiceDetailPurityInListGivenPurityId(detailPurities, purity.getId());

            if (purityDetail != null) {
                if (!purity.getWeightString().trim().equals(purityDetail.getRateValue() + "")) {
                    postPurities.add(new PurityPost(purity.getId(), Float.parseFloat(purity.getWeightString().trim())));
                }
            } else {
                if (!purity.getWeightString().trim().isEmpty()) {
                    postPurities.add(new PurityPost(purity.getId(), Float.parseFloat(purity.getWeightString().trim())));
                }
            }
        }

        if (postPurities.size() > 0) {
            if (invoice == null) {
                invoice = new InvoicePost();
            }

            if (invoice.getItems() != null) {
                if (invoice.getItems().size() > 0) {
                    invoice.getItems().get(0).setPurities(postPurities);
                }
            } else {
                ItemPost item = new ItemPost(itemId, details.getAmount(), details.getId());
                item.setPrice(details.getPriceItem());
                item.setPurities(postPurities);
                List<ItemPost> itemList = new ArrayList<>();
                itemList.add(item);
                invoice.setItems(itemList);
            }
        }

        return invoice;
    }

    @DebugLog
    @Override
    public void onUpdateError() {
        mView.hideWorkingIndicator();
        mView.showMessage(context.getString(R.string.error_saving_changes));
    }

    @DebugLog
    @Override
    public void onError(String error) {
        mView.hideWorkingIndicator();
        mView.showMessage(error);
    }

    @DebugLog
    @Override
    public void onPurchaseUpdated() {
        mView.hideWorkingIndicator();
        mView.showMessage(context.getString(R.string.data_updated_correctly));
        mView.handleSuccessfulUpdate();
    }

    @DebugLog
    @Override
    public void loadStores(List<Store> storesList) {

        Collections.sort(storesList, new Comparator<Store>() {
            @Override
            public int compare(Store o1, Store o2) {
                String string1 = o1.getName() != null ? o1.getName().toLowerCase() : "";
                String string2 = o2.getName() != null ? o2.getName().toLowerCase() : "";
                return string1.compareTo(string2);
            }
        });


        if (!isAdd && !initializedStore) {
            initializedStore = true;
            if (currentDetailsList.size() <= 0) {
                mView.updateStores(storesList, -1);
                return;
            }
            Store store = currentDetailsList.get(0).getStore();
            if (store != null) {
                mView.updateStores(storesList, store.getId());
                return;
            }
        }

        mView.updateStores(storesList, -1);
    }

    private void initStore() {
        int itemStoreId = currentDetailsList.get(0).getStoreId();
        if (itemStoreId != -1) {
            Store store = mRepository.getStoreById(itemStoreId);
            if (store != null) {
                currentDetailsList.get(0).setStore(store);
            }
        }
    }

    @Override
    public void loadSortedStores(List<Store> storesList) {
        if (!isAdd && !initializedStore) {
            initializedStore = true;
            if (currentDetailsList.size() <= 0) {
                mView.updateStores(storesList, -1);
                return;
            }

            if (currentDetailsList.get(0).getStore() == null) {
                initStore();
            }

            if (currentDetailsList.get(0).getStore() != null) {
                mView.updateStores(storesList, currentDetailsList.get(0).getStore().getId());
                return;
            }
        }

        mView.updateStores(storesList, -1);
    }

    private void initItem() {
        int itemTypeId = currentDetailsList.get(0).getItemTypeId();
        if (itemTypeId != -1) {
            ItemType item = mRepository.getItemTypeById(itemTypeId);
            if (item != null) {
                currentDetailsList.get(0).setItemType(item);
            }
        }
    }

    @Override
    public void loadItems(List<ItemType> itemTypeList) {

        if (!isAdd && !initializedItems) {
            initializedItems = true;

            if (currentDetailsList.size() <= 0) {
                mView.updateItems(itemTypeList, -1);
                return;
            }

            if (currentDetailsList.get(0).getItemType() == null) {
                initItem();
            }

            if (currentDetailsList.get(0).getItemType() != null) {
                mView.updateItems(itemTypeList, currentDetailsList.get(0).getItemType().getId());
                return;
            }
        }
        mView.updateItems(itemTypeList, -1);
        mView.hideWorkingIndicator();

    }

    @DebugLog
    private void initPuritiesInCurrentDetailsList() {
        if (!currentDetailsList.get(0).getWholeId().isEmpty()) {
            List<InvoiceDetailPurity> detailPuritiesList = mRepository.getPuritiesByLocalDetailId(currentDetailsList.get(0).getWholeId());
            currentDetailsList.get(0).setDetailPurities(detailPuritiesList);
        }
    }

    @DebugLog
    @Override
    public void loadPurities(List<Purity> purityList) {

        Collections.sort(purityList, new Comparator<Purity>() {
            @Override
            public int compare(Purity o1, Purity o2) {
                String string1 = o1.getName() != null ? o1.getName().toLowerCase() : "";
                String string2 = o2.getName() != null ? o2.getName().toLowerCase() : "";
                return string1.compareTo(string2);
            }
        });

        if (!isAdd && !initializedPurities) {
            initializedPurities = true;

            if (currentDetailsList.size() <= 0) {
                Log.d("PURITIES", "--->mView.updatePurities NO DETAILS");
                mView.updatePurities(purityList);
                return;
            }

            if (currentDetailsList.get(0).getDetailPurities() == null) {
                initPuritiesInCurrentDetailsList();
            }

            List<InvoiceDetailPurity> detailPurities = currentDetailsList.get(0).getDetailPurities();

            Log.d("PURITIES", "--->loadPurities. Handling currentDetailsList->" + currentDetailsList);

            for (Purity currentPurity : purityList) {
                InvoiceDetailPurity purityInInvoiceDetail = InvoiceDetailPurity.findInvoiceDetailPurityInListGivenPurityId(detailPurities, currentPurity.getId());
                if (purityInInvoiceDetail != null) {
                    Log.d("PURITIES", "--->setWeightString ("
                            + purityInInvoiceDetail.getPurity().getName() +
                            "): " + purityInInvoiceDetail.getRateValue());

                    currentPurity.setRateValueAndWeightString(purityInInvoiceDetail.getRateValue());

                } else {
                    Log.d("PURITIES", "--->setWeightString CANT for " + currentPurity.getName());
                }
            }
        } else
            Log.d("PURITIES", "--->" + getClass().getSimpleName() + " No isAdd");

        mView.updatePurities(purityList);
        mView.hideWorkingIndicator();

    }

    @DebugLog
    @Override
    public void loadSortedPurities(List<Purity> purityList) {
        if (!isAdd && !initializedPurities) {
            initializedPurities = true;

            if (currentDetailsList.size() <= 0) {
                mView.updatePurities(purityList);
                return;
            }

            if (currentDetailsList.get(0).getDetailPurities() == null) {
                initPuritiesInCurrentDetailsList();
            }

            originalDetailsPuritiesList = currentDetailsList.get(0).getDetailPurities();
            if (originalDetailsPuritiesList == null) {
                Log.w("PURITIES", "--->originalDetailsPuritiesList could not be initialized");
            } else {
                Log.d("PURITIES", "--->originalDetailsPuritiesList initialized: " + originalDetailsPuritiesList);
            }


            for (Purity item : purityList) {
                InvoiceDetailPurity purity = InvoiceDetailPurity.findInvoiceDetailPurityInListGivenPurityId(originalDetailsPuritiesList, item.getId());
                if (purity != null) {
                    item.setRateValueAndWeightString(purity.getRateValue());
                }
            }
        }
        mView.updatePurities(purityList);
        mView.hideWorkingIndicator();
    }

    @DebugLog
    @Override
    public void onProviderSelected(Provider provider) {
        currentProvider = provider;
        mView.updateProvider(provider.getFullNameProvider());
    }

    @Override
    public void invalidToken() {
        mView.invalidToken();
    }

    @DebugLog
    @Override
    public void onSaveConfirmedInDialog() {
        mView.showWorkingIndicator();

        invoicePost.setIdentificationDocProvider(currentProvider.getIdentificationDocProvider());
        invoicePost.setProviderName(currentProvider.getFullNameProvider());
        invoicePost.setType(Constants.TYPE_SELLER);

        if (isAdd()) {
            invoicePost.setProviderId(currentProvider.getIdProvider());
            invoicePost.setReceiverName(SessionManager.getUserName(context));
            invoicePost.setStartDate(Util.getCurrentDateForInvoice());
            invoicePost.setDate(invoicePost.getStartDate().split(" ")[0]);
            invoicePost.setBuyOption(Constants.BUY_OPTION_PURCHASE);

            Log.d("TEST", "--->getCurrentDateForInvoice " + invoicePost.getStartDate());

            mRepository.savePurchaseRequest(invoicePost, true);
        } else {
            if (currentDetailsList.get(0).getInvoice() == null) {
                invoicePost.setInvoiceId(currentDetailsList.get(0).getInvoiceId());
            } else {
                invoicePost.setInvoiceId(currentDetailsList.get(0).getInvoice().getId());
            }
            //invoice.setId2(currentDetailsList.get(0).getInvoice().getId());
            invoicePost.setProviderId(currentProvider.getIdProvider());
            invoicePost.setReceiverName(currentDetailsList.get(0).getReceiverName());
            invoicePost.setStartDate(currentDetailsList.get(0).getStartDate());
            invoicePost.setBuyOption(Constants.BUY_OPTION_PURCHASE);

            if (invoicePost.getObservations() == null) {
                invoicePost.setObservations(currentDetailsList.get(0).getObservation());
            }
            if (invoicePost.getDispatcherName() == null) {
                invoicePost.setDispatcherName(currentDetailsList.get(0).getDispatcherName());
            }
            if (invoicePost.getItems() == null) {
                invoicePost.setItems(new ArrayList<ItemPost>());
            }
            invoicePost.setDate(invoicePost.getStartDate().split(" ")[0]);

            updateOriginalDetailsPuritiesListWithChanges();
            mRepository.savePurchaseRequest(invoicePost, false);
        }
    }
}
