package com.hecticus.eleta.purchases.detail;

import android.content.Context;
import android.util.Log;

import com.hecticus.eleta.R;
import com.hecticus.eleta.model.Session;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.request.invoice.ItemPost;
import com.hecticus.eleta.model.request.invoice.PurityPost;
import com.hecticus.eleta.model.response.invoice.Invoice;
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

/**
 * Created by roselyn545 on 22/9/17.
 */

public class PurchaseDetailsPresenter implements PurchaseDetailsContract.Actions{

    Context context;
    private PurchaseDetailsContract.View mView;
    private PurchaseDetailsContract.Repository mRepository;
    private boolean isAdd = false;
    private boolean canEdit = false;
    private Provider currentProvider = null;
    private List<InvoiceDetails> currentDetailsList = null;

    private boolean initializedStore = false;
    private boolean initializedItems = false;
    private boolean initializedPurities = false;

    @DebugLog
    public PurchaseDetailsPresenter(Context context, PurchaseDetailsContract.View mView, boolean isAddParam, boolean canEditParam, Provider provider, List<InvoiceDetails> details) {
        this.context = context;
        this.mView = mView;
        mRepository = new PurchaseDetailsRepository(this);
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
                    InvoiceDetails details = currentDetailsList.get(0);
                    mView.loadFields(details.isFreight(), details.getAmount()+"", details.getPriceItem()+"", details.getDispatcherName(), details.getObservation());
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

    @Override
    public void onSaveChanges(int storeId, boolean freight, int itemId, String amount, String price, List<Purity> purities, String dispatcher, String observations) {
        mView.showWorkingIndicator();

        if (currentProvider == null) {
            mView.hideWorkingIndicator();
            mView.showUpdateMessage(context.getString(R.string.you_must_select_a_provider));
            return;
        }

        if (storeId == -1) {
            mView.hideWorkingIndicator();
            mView.showUpdateMessage(context.getString(R.string.you_must_select_a_valid_store));
            return;
        }

        if (itemId == -1) {
            mView.hideWorkingIndicator();
            mView.showUpdateMessage(context.getString(R.string.you_must_select_a_valid_item));
            return;
        }

        if (amount.trim().isEmpty()) {
            mView.hideWorkingIndicator();
            mView.showUpdateMessage(context.getString(R.string.you_must_enter_some_weight));
            return;
        }

        if (price.trim().isEmpty()) {
            mView.hideWorkingIndicator();
            mView.showUpdateMessage(context.getString(R.string.you_must_enter_some_price));
            return;
        }

        if (purities == null || purities.size() == 0) {
            mView.hideWorkingIndicator();
            mView.showUpdateMessage(context.getString(R.string.you_must_enter_some_purity));
            return;
        }

        if (dispatcher.trim().isEmpty()) {
            mView.hideWorkingIndicator();
            mView.showUpdateMessage(context.getString(R.string.you_must_enter_dispatcher_name));
            return;
        }

        InvoicePost invoice = new InvoicePost();

        if (isAdd()) {

            List<PurityPost> puritiesPost = getPurities(purities);

            /*if (puritiesPost.size() == 0) {
                mView.hideWorkingIndicator();
                mView.showUpdateMessage(context.getString(R.string.you_must_enter_some_purity));
                return;
            }*/

            ItemPost item = new ItemPost(itemId,Float.parseFloat(amount.trim()));
            item.setPrice(Float.parseFloat(price.trim()));
            item.setPurities(puritiesPost);
            item.setStoreId(storeId);

            List<ItemPost> itemList = new ArrayList<ItemPost>();
            itemList.add(item);
            invoice.setItems(itemList);

            invoice.setProviderId(currentProvider.getIdProvider());
            invoice.setFreigh(freight);
            invoice.setDispatcherName(dispatcher);
            invoice.setReceiverName(Session.getUserName(context));
            invoice.setObservations(observations);
            invoice.setStartDate(Util.getCurrentDateForInvoice());
            invoice.setBuyOption(Constants.TYPE_PURCHASE);

            Log.d("TEST","getCurrentDateForInvoice "+invoice.getStartDate());

            mRepository.savePurchaseResquest(invoice, true);
        } else {
            invoice = getChanges(storeId,freight,itemId,amount,price, purities, dispatcher, observations);
            if (invoice==null){
                mView.hideWorkingIndicator();
                return;
            }
            invoice.setId(currentDetailsList.get(0).getInvoice().getInvoiceId());
            invoice.setProviderId(currentProvider.getIdProvider());
            invoice.setReceiverName(currentDetailsList.get(0).getReceiverName());
            invoice.setStartDate(currentDetailsList.get(0).getStartDate());
            invoice.setBuyOption(Constants.TYPE_PURCHASE);

            if (invoice.getObservations()==null){
                invoice.setObservations(currentDetailsList.get(0).getObservation());
            }
            if (invoice.getDispatcherName()==null){
                invoice.setDispatcherName(currentDetailsList.get(0).getDispatcherName());
            }
            if (invoice.getItems()==null){
                invoice.setItems(new ArrayList<ItemPost>());
            }

            mRepository.savePurchaseResquest(invoice, false);
        }
    }

    private List<PurityPost> getPurities(List<Purity> purities) {
        List<PurityPost> post = new ArrayList<PurityPost>();
        for (Purity purity: purities) {
            if (!purity.getWeightString().trim().isEmpty()){
                post.add(new PurityPost(purity.getId(), Float.parseFloat(purity.getWeightString().trim())));
            }
        }
        return post;
    }

    @Override
    public InvoicePost getChanges(int storeId, boolean freight, int itemId, String amount, String price, List<Purity> purities, String dispatcher, String observations) {
        InvoicePost invoice = null;

        if (currentDetailsList == null || currentDetailsList.size()<=0)
            return invoice;

        InvoiceDetails details = currentDetailsList.get(0);

        if (details.isFreight()!= freight){
            if (invoice == null){
                invoice = new InvoicePost();
            }
            invoice.setFreigh(freight);
        }

        if ((details.getItemType()!=null && details.getItemType().getId()!=itemId) ||
                details.getPriceItem()!= Float.parseFloat(price.trim()) ||
                details.getAmount()!= Float.parseFloat(amount.trim()) ||
                details.getStore() != null && details.getStore().getId()!=storeId ||
                details.getStore() == null){

            if (invoice == null){
                invoice = new InvoicePost();
            }

            ItemPost item = new ItemPost(itemId,Float.parseFloat(amount.trim()),details.getId());
            item.setPrice(Float.parseFloat(price.trim()));
            item.setPurities(new ArrayList<PurityPost>());
            item.setStoreId(storeId);

            List<ItemPost> itemList = new ArrayList<>();
            itemList.add(item);
            invoice.setItems(itemList);
        }

        if (details.getDispatcherName()!=null && !details.getDispatcherName().equals(dispatcher)){
            if (invoice == null){
                invoice = new InvoicePost();
            }
            invoice.setDispatcherName(dispatcher);
        }

        if (details.getObservation()!=null && !details.getObservation().equals(observations)){
            if (invoice == null){
                invoice = new InvoicePost();
            }
            invoice.setObservations(observations);
        }

        List<PurityPost> postPurities = new ArrayList<>();
        List<InvoiceDetailPurity> detailPurities = details.getDetailPurities();

        for (Purity purity: purities) {
            InvoiceDetailPurity purityDetail = InvoiceDetailPurity.findDetailPurity(detailPurities,purity.getId());

            if (purityDetail != null){
                if (!purity.getWeightString().trim().equals(purityDetail.getRateValue()+"")){
                    postPurities.add(new PurityPost(purity.getId(), Float.parseFloat(purity.getWeightString().trim())));
                }
            }else{
                if (!purity.getWeightString().trim().isEmpty()) {
                    postPurities.add(new PurityPost(purity.getId(), Float.parseFloat(purity.getWeightString().trim())));
                }
            }
        }

        if (postPurities.size()>0){
            if (invoice == null){
                invoice = new InvoicePost();
            }

            if (invoice.getItems()!=null){
                if (invoice.getItems().size()>0){
                    invoice.getItems().get(0).setPurities(postPurities);
                }
            }else{
                ItemPost item = new ItemPost(itemId,details.getAmount(),details.getId());
                item.setPrice(details.getPriceItem());
                item.setPurities(postPurities);
                List<ItemPost> itemList = new ArrayList<>();
                itemList.add(item);
                invoice.setItems(itemList);
            }
        }

        return invoice;
    }

    @Override
    public void onUpdateError() {
        mView.hideWorkingIndicator();
        mView.showUpdateMessage(context.getString(R.string.error_saving_changes));
    }

    @Override
    public void onError(String error) {
        mView.hideWorkingIndicator();
        mView.showUpdateMessage(error);
    }


    @Override
    public void onUpdatePurchase() {
        mView.hideWorkingIndicator();
        mView.showUpdateMessage(context.getString(R.string.data_updated_correctly));
        mView.handleSuccessfulUpdate();
    }

    @DebugLog
    @Override
    public void loadStores(List<Store> storesList) {

        Collections.sort(storesList, new Comparator<Store>() {
            @Override
            public int compare(Store o1, Store o2) {
                String string1 = o1.getName()!=null?o1.getName().toLowerCase():"";
                String string2 = o2.getName()!=null?o2.getName().toLowerCase():"";
                return string1.compareTo(string2);
            }
        });


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

        if (!isAdd && !initializedItems) {
            initializedItems = true;

            if (currentDetailsList.size()<=0){
                mView.updateItems(itemTypeList, -1);
                return;
            }

            ItemType item = currentDetailsList.get(0).getItemType();
            if (item!=null){
                mView.updateItems(itemTypeList, item.getId());
                return;
            }
        }
        mView.updateItems(itemTypeList,-1);
        mView.hideWorkingIndicator();

    }

    @Override
    public void loadPurities(List<Purity> purityList) {
        Collections.sort(purityList, new Comparator<Purity>() {
            @Override
            public int compare(Purity o1, Purity o2) {
                String string1 = o1.getName()!=null?o1.getName().toLowerCase():"";
                String string2 = o2.getName()!=null?o2.getName().toLowerCase():"";
                return string1.compareTo(string2);
            }
        });


        if (!isAdd && !initializedPurities) {
            initializedPurities = true;

            if (currentDetailsList.size()<=0 || currentDetailsList.get(0).getDetailPurities() == null){
                mView.updatePurities(purityList);
                return;
            }

            List<InvoiceDetailPurity> detailPurities = currentDetailsList.get(0).getDetailPurities();
            for (Purity item: purityList) {
                InvoiceDetailPurity purity = InvoiceDetailPurity.findDetailPurity(detailPurities,item.getId());
                if (purity != null){
                    item.setWeightString(purity.getRateValue()+"");
                }
            }
        }
        mView.updatePurities(purityList);
        mView.hideWorkingIndicator();

    }

    @Override
    public void onProviderSelected(Provider provider) {
        currentProvider = provider;
        mView.updateProvider(provider.getFullNameProvider());
    }
}
