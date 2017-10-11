package com.hecticus.eleta.harvest.detail;

import android.content.Context;
import android.util.Log;

import com.hecticus.eleta.R;
import com.hecticus.eleta.model.Session;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.request.invoice.ItemPost;
import com.hecticus.eleta.model.response.farm.Farm;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.item.ItemType;
import com.hecticus.eleta.model.response.lot.Lot;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.purity.Purity;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 16/9/17.
 */

public class HarvestDetailsPresenter implements HarvestDetailsContract.Actions{

    Context context;
    private HarvestDetailsContract.View mView;
    private HarvestDetailsContract.Repository mRepository;
    private boolean isAdd = false;
    private boolean canEdit = false;

    //edit
    private Provider currentProvider = null;
    private List<InvoiceDetails> currentDetailsList = null;

    private boolean initializedFarm = false;
    private boolean initializedLot = false;
    private boolean initializedItems = false;

    @DebugLog
    public HarvestDetailsPresenter(Context context, HarvestDetailsContract.View mView, //HarvestModel currentHarvestParam,
                                    boolean isAddParam, boolean canEditParam, Provider provider, List<InvoiceDetails> details) {
        this.context = context;
        this.mView = mView;
        mRepository = new HarvestDetailsRepository(this);
        isAdd = isAddParam;
        canEdit = canEditParam;
        currentProvider = provider;
        currentDetailsList = details;
    }

    @Override
    public void initFields() {
        mView.showWorkingIndicator();
        mView.enableEdition(canEdit);
        mRepository.getFarmsRequest();
        mRepository.getItemTypesRequest();

        if (!isAdd) {
            if (currentProvider!=null) {
                mView.loadHeader(currentProvider.getFullNameProvider(),currentProvider.getPhotoProvider());
                if (currentDetailsList != null && currentDetailsList.size()>0) {
                    mView.loadObservation(currentDetailsList.get(0).getObservation());
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
    public void onSaveChanges(int lotId, List<ItemType> items, String observations) {
        mView.showWorkingIndicator();

        if (currentProvider == null) {
            mView.hideWorkingIndicator();
            mView.showUpdateMessage(context.getString(R.string.you_must_select_a_harvester));
            return;
        }

        if (lotId == -1) {
            mView.hideWorkingIndicator();
            mView.showUpdateMessage(context.getString(R.string.you_must_select_a_valid_lot));
            return;
        }

        if (items == null || items.size() == 0) {
            mView.hideWorkingIndicator();
            mView.showUpdateMessage(context.getString(R.string.you_must_enter_some_weight));
            return;
        }

        InvoicePost invoice = new InvoicePost();

        if (isAdd()) {

            invoice.setItems(getItems(items));

            if (invoice.getItems().size() == 0) {
                mView.hideWorkingIndicator();
                mView.showUpdateMessage(context.getString(R.string.you_must_enter_some_weight));
                return;
            }

            invoice.setProviderId(currentProvider.getIdProvider());
            invoice.setLotId(lotId);
            invoice.setDispatcherName(currentProvider.getFullNameProvider());
            invoice.setReceiverName(Session.getUserName(context));
            invoice.setObservations(observations);
            invoice.setStartDate(Util.getCurrentDateForInvoice());
            invoice.setBuyOption(Constants.TYPE_HARVEST);

            Log.d("TEST","getCurrentDateForInvoice "+invoice.getStartDate());

            mRepository.saveHarvestResquest(invoice, true);
        } else {
            invoice = getChanges(lotId,items,observations);
            if (invoice==null){
                mView.hideWorkingIndicator();
                return;
            }
            invoice.setId(currentDetailsList.get(0).getInvoice().getInvoiceId());
            invoice.setProviderId(currentProvider.getIdProvider());
            invoice.setDispatcherName(currentProvider.getFullNameProvider());
            invoice.setReceiverName(currentDetailsList.get(0).getReceiverName());
            invoice.setStartDate(currentDetailsList.get(0).getStartDate());
            invoice.setBuyOption(Constants.TYPE_HARVEST);

            if (invoice.getLotId()==-1 && currentDetailsList.get(0).getLot() != null){
                invoice.setLotId(currentDetailsList.get(0).getLot().getId());
            }
            if (invoice.getObservations()==null){
                invoice.setObservations(currentDetailsList.get(0).getObservation());
            }
            if (invoice.getItems()==null){
                invoice.setItems(new ArrayList<ItemPost>());
            }

            mRepository.saveHarvestResquest(invoice, false);
        }
    }

    private List<ItemPost> getItems(List<ItemType> items) {
        List<ItemPost> post = new ArrayList<ItemPost>();
        for (ItemType item: items) {
            if (!item.getWeightString().trim().isEmpty()){
                post.add(new ItemPost(item.getId(), Float.parseFloat(item.getWeightString().trim())));
            }
        }
        return post;
    }

    @Override
    public InvoicePost getChanges(int lotId, List<ItemType> items, String observations) {
        InvoicePost invoice = null;

        if (currentDetailsList == null || currentDetailsList.size()<=0)
            return invoice;

        if (currentDetailsList.get(0).getLot() != null && currentDetailsList.get(0).getLot().getId()!=lotId){
            invoice = new InvoicePost();
            invoice.setLotId(lotId);
        }

        if (currentDetailsList.get(0).getObservation()!=null && !currentDetailsList.get(0).getObservation().equals(observations)){
            if (invoice == null){
                invoice = new InvoicePost();
            }
            invoice.setObservations(observations);
        }

        List<ItemPost> postItems = new ArrayList<ItemPost>();
        for (ItemType item: items) {
            InvoiceDetails details = InvoiceDetails.findItem(currentDetailsList,item.getId());
            if (details != null){
                if (!item.getWeightString().trim().equals(details.getAmount()+"")){
                    postItems.add(new ItemPost(item.getId(), Float.parseFloat(item.getWeightString().trim()), details.getId()));
                }
            }else{
                if (!item.getWeightString().trim().isEmpty()) {
                    postItems.add(new ItemPost(item.getId(), Float.parseFloat(item.getWeightString().trim())));
                }
            }
        }

        if (postItems.size()>0){
            if (invoice == null){
                invoice = new InvoicePost();
            }
            invoice.setItems(postItems);
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
    public void onUpdateHarvest() {
        mView.hideWorkingIndicator();
        mView.showUpdateMessage(context.getString(R.string.data_updated_correctly));
        mView.handleSuccessfulUpdate();
    }

    @Override
    public void loadFarms(List<Farm> farmsList) {
        /*Collections.sort(farmsList, new Comparator<Farm>() {
            @Override
            public int compare(Farm o1, Farm o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });*/

        if (!isAdd && !initializedFarm) {
            initializedFarm = true;
            if (currentDetailsList.size()<=0){
                mView.updateFarms(farmsList, -1);
                return;
            }
            Lot lot = currentDetailsList.get(0).getLot();
            if (lot!=null && lot.getFarm()!=null){
                mView.updateFarms(farmsList, lot.getFarm().getId());
                return;
            }
        }

        mView.updateFarms(farmsList, -1);

    }

    @Override
    public void loadLots(List<Lot> lotsList) {
        /*Collections.sort(lotsList, new Comparator<Lot>() {
            @Override
            public int compare(Lot o1, Lot o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });*/

        if (!isAdd && !initializedLot) {
            initializedLot = true;
            if (currentDetailsList.size()<=0){
                mView.updateLots(lotsList, -1);
                return;
            }
            if (currentDetailsList.get(0).getLot()!=null) {
                mView.updateLots(lotsList, currentDetailsList.get(0).getLot().getId());
                return;
            }
        }
        mView.updateLots(lotsList, -1);
    }

    @Override
    public void loadItems(List<ItemType> itemTypeList) {
        Collections.sort(itemTypeList, new Comparator<ItemType>() {
            @Override
            public int compare(ItemType o1, ItemType o2) {
                String string1 = o1.getName()!=null?o1.getName().toLowerCase():"";
                String string2 = o2.getName()!=null?o2.getName().toLowerCase():"";
                return string1.compareTo(string2);
            }
        });

        if (!isAdd && !initializedItems) {
            initializedItems = true;

            for (ItemType item: itemTypeList) {
                InvoiceDetails invoice = InvoiceDetails.findItem(currentDetailsList,item.getId());
                if (invoice != null){
                    item.setWeightString(invoice.getAmount()+"");
                }
            }
        }
        mView.updateItems(itemTypeList);

        mView.hideWorkingIndicator();

    }

    @Override
    public void getLotsByFarm(int idFarm) {
        mRepository.getLotsByFarmRequest(idFarm);
    }

    @Override
    public void onProviderSelected(Provider provider) {
        currentProvider = provider;
        mView.updateProvider(provider.getFullNameProvider());
    }
}
