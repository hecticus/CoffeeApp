package com.hecticus.eleta.harvest.detail;

import android.content.Context;
import android.util.Log;

import com.hecticus.eleta.R;
import com.hecticus.eleta.model_new.SessionManager;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.request.invoice.ItemPost;
import com.hecticus.eleta.model.response.farm.Farm;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.item.ItemType;
import com.hecticus.eleta.model.response.lot.Lot;
import com.hecticus.eleta.model.response.providers.Provider;
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

public class HarvestDetailsPresenter implements HarvestDetailsContract.Actions {

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

    private InvoicePost invoicePost = null;

    @DebugLog
    public HarvestDetailsPresenter(Context context, HarvestDetailsContract.View mView, //HarvestModel currentHarvestParam,
                                   boolean isAddParam, boolean canEditParam, Provider provider, List<InvoiceDetails> details) {
        this.context = context;
        this.mView = mView;
        mRepository = new HarvestDetailsRepository(this);
        isAdd = isAddParam;
        canEdit = canEditParam;
        currentProvider = provider;
        Log.d("BUG", "--->currentProvider onCreate: " + currentProvider);
        currentDetailsList = details;
    }

    @DebugLog
    @Override
    public void initFields() {
        mView.showWorkingIndicator();
        mView.enableEdition(canEdit);
        mRepository.getFarmsRequest();
        mRepository.getItemTypesRequest();

        if (!isAdd) {
            if (currentProvider != null) {
                Log.d("BUG", "--->currentProvider initFields: " + currentProvider);

                mView.loadHeader(currentProvider.getFullNameProvider(), currentProvider.getPhotoProvider());
                if (currentDetailsList != null && currentDetailsList.size() > 0) {
                    mView.loadObservation(currentDetailsList.get(0).getObservation());
                }
            } else {
                Log.d("BUG", "--->currentProvider init fields NULL");
            }
        }
    }

    @DebugLog
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
    public void onSaveChanges(Lot selectedLot, List<ItemType> items, String observations) {
        mView.showWorkingIndicator();

        if (currentProvider == null) {
            mView.hideWorkingIndicator();
            mView.showMessage(context.getString(R.string.you_must_select_a_harvester));
            return;
        }

        if (selectedLot == null) {
            mView.hideWorkingIndicator();
            mView.showMessage(context.getString(R.string.you_must_select_a_valid_lot));
            return;
        }

        if (items == null || items.size() == 0) {
            mView.hideWorkingIndicator();
            mView.showMessage(context.getString(R.string.you_must_enter_some_weight));
            return;
        }

        Log.d("OFFLINE", "--->invoicePost BEFORE1 getChanges(): " + invoicePost);

        invoicePost = new InvoicePost();

        invoicePost.setPriceByLot(selectedLot.getPrice());

        if (isAdd()) {
            invoicePost.setItems(getItems(items));

            if (invoicePost.getItems().size() == 0) {
                mView.hideWorkingIndicator();
                mView.showMessage(context.getString(R.string.you_must_enter_some_weight));
                return;
            }

            invoicePost.setLot(selectedLot.getId());
            invoicePost.setObservations(observations);
        } else {
            Log.d("OFFLINE", "--->invoicePost BEFORE2 getChanges()" + invoicePost);
            invoicePost = getChanges(selectedLot, items, observations);
            Log.d("OFFLINE", "--->invoicePost AFTER getChanges()" + invoicePost);
            if (invoicePost == null) {
                mView.hideWorkingIndicator();
                return;
            }
        }

        mView.hideWorkingIndicator();
        mView.showDialogConfirmation();
    }

    @DebugLog
    private List<ItemPost> getItems(List<ItemType> items) {
        List<ItemPost> post = new ArrayList<>();
        for (ItemType item : items) {
            if (!item.getWeightString().trim().isEmpty()) {
                post.add(new ItemPost(item.getId(), Float.parseFloat(item.getWeightString().trim())));
            }
        }
        return post;
    }

    @DebugLog
    @Override
    public InvoicePost getChanges(Lot lotId, List<ItemType> itemsTypesList, String observations) {
        InvoicePost invoicePostWithChanges = null;

        if (currentDetailsList == null || currentDetailsList.size() <= 0)
            return invoicePostWithChanges;

        if (currentDetailsList.get(0).getLot() != null && currentDetailsList.get(0).getLot().getId() != lotId.getId()) {
            invoicePostWithChanges = new InvoicePost();
            invoicePostWithChanges.setLot(lotId.getId());
        }

        if (currentDetailsList.get(0).getObservation() != null && !currentDetailsList.get(0).getObservation().equals(observations)) {
            if (invoicePostWithChanges == null) {
                invoicePostWithChanges = new InvoicePost();
            }
            invoicePostWithChanges.setObservations(observations);
        }

        List<ItemPost> postItems = new ArrayList<>();
        for (ItemType currentItemType : itemsTypesList) {
            InvoiceDetails details = InvoiceDetails.findItem(currentDetailsList, currentItemType.getId());
            if (details != null) {
                if (!currentItemType.getWeightString().trim().equals(details.getAmount() + "")) {
                    postItems.add(new ItemPost(
                            currentItemType.getId(),
                            currentItemType.getWeightString().trim().isEmpty() ? 0 : Float.parseFloat(currentItemType.getWeightString().trim()),
                            details.getId() == -1 ? details.getLocalId() : details.getId()));
                }
            } else {
                if (!currentItemType.getWeightString().trim().isEmpty()) {
                    postItems.add(new ItemPost(currentItemType.getId(), Float.parseFloat(currentItemType.getWeightString().trim())));
                }
            }
        }

        if (postItems.size() > 0) {
            if (invoicePostWithChanges == null) {
                invoicePostWithChanges = new InvoicePost();
            }
            invoicePostWithChanges.setItems(postItems);
        }

        return invoicePostWithChanges;
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
    public void onHarvestUpdated() {
        mView.hideWorkingIndicator();
        mView.showMessage(context.getString(R.string.data_updated_correctly));
        mView.handleSuccessfulUpdate();
    }

    @DebugLog
    @Override
    public void loadFarms(List<Farm> farmsList) {
        /*Collections.sort(farmsList, new Comparator<Farm>() {
            @Override
            public int compare(Farm o1, Farm o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });*/

        if (!isAdd && !initializedFarm) {
            Log.d("DEBUG ", "1");
            initializedFarm = true;
            if (currentDetailsList.size() <= 0) {
                Log.d("DEBUG ", "2");
                mView.updateFarms(farmsList, -1);
                return;
            }
            Log.d("DEBUG ", "3");
            Lot lot = currentDetailsList.get(0).getLot();
            if (lot != null && lot.getFarm() != null) {
                Log.d("DEBUG ", "4");
                mView.updateFarms(farmsList, lot.getFarm().getId());
                return;
            } else if (currentDetailsList.get(0).getLotId() != -1) {
                Log.d("DEBUG ", "5");
                Lot lotAux = mRepository.getLotById(currentDetailsList.get(0).getLotId());
                if (lotAux != null) {
                    Log.d("DEBUG ", "6");
                    currentDetailsList.get(0).setLot(lotAux);
                    if (lotAux.getFarmId() != -1) {
                        Log.d("DEBUG ", "7");
                        mView.updateFarms(farmsList, lotAux.getFarmId());
                        return;
                    }
                }
            }
        }
        Log.d("DEBUG ", "8");
        mView.updateFarms(farmsList, -1);

    }

    @DebugLog
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
            if (currentDetailsList.size() <= 0) {
                mView.updateLots(lotsList, -1);
                return;
            }
            if (currentDetailsList.get(0).getLot() != null) {
                mView.updateLots(lotsList, currentDetailsList.get(0).getLot().getId());
                return;
            } else if (currentDetailsList.get(0).getLotId() != -1) {
                Lot lotAux = mRepository.getLotById(currentDetailsList.get(0).getLotId());
                if (lotAux != null) {
                    currentDetailsList.get(0).setLot(lotAux);
                    mView.updateLots(lotsList, lotAux.getId());
                    return;
                }
            }
        }
        mView.updateLots(lotsList, -1);
    }

    @DebugLog
    private void initItems() {
        for (InvoiceDetails detail : currentDetailsList) {
            if (detail.getItemType() == null && detail.getItemTypeId() != -1) {
                ItemType item = mRepository.getItemTypeById(detail.getItemTypeId());
                if (item != null) {
                    detail.setItemType(item);
                }
            }
        }
    }

    @DebugLog
    @Override
    public void loadItems(List<ItemType> itemTypeList) {
        Collections.sort(itemTypeList, new Comparator<ItemType>() {
            @Override
            public int compare(ItemType o1, ItemType o2) {
                String string1 = o1.getName() != null ? o1.getName().toLowerCase() : "";
                String string2 = o2.getName() != null ? o2.getName().toLowerCase() : "";
                return string1.compareTo(string2);
            }
        });

        if (!isAdd && !initializedItems) {
            initializedItems = true;

            if (currentDetailsList.get(0).getItemType() == null) {
                initItems();
            }

            for (ItemType item : itemTypeList) {
                InvoiceDetails invoice = InvoiceDetails.findItem(currentDetailsList, item.getId());
                if (invoice != null) {
                    item.setWeightString(invoice.getAmount() + "");
                }
            }
        }
        mView.updateItems(itemTypeList);

        mView.hideWorkingIndicator();

    }

    @DebugLog
    @Override
    public void loadSortedItems(List<ItemType> itemTypeList) {
        if (!isAdd && !initializedItems) {
            initializedItems = true;

            if (currentDetailsList.get(0).getItemType() == null) {
                initItems();
            }

            for (ItemType item : itemTypeList) {
                InvoiceDetails invoice = InvoiceDetails.findItem(currentDetailsList, item.getId());
                if (invoice != null) {
                    item.setWeightString(invoice.getAmount() + "");
                }
            }
        }
        mView.updateItems(itemTypeList);

        mView.hideWorkingIndicator();
    }

    @DebugLog
    @Override
    public void getLotsByFarm(int idFarm) {
        mRepository.getLotsByFarmRequest(idFarm);
    }

    @DebugLog
    @Override
    public void onProviderSelected(Provider provider) {
        currentProvider = provider;
        Log.d("BUG", "--->currentProvider onProviderSelected: " + currentProvider);
        mView.updateProvider(provider.getFullNameProvider());
    }

    @DebugLog
    @Override
    public void invalidToken() {
        mView.invalidToken();
    }

    @DebugLog
    @Override
    public void acceptSave() {
        Log.d("BUG", "--->currentProvider onSaveConfirmedInDialog: " + currentProvider);

        mView.showWorkingIndicator();
        invoicePost.setIdentificationDocProvider(currentProvider.getIdentificationDocProvider());
        invoicePost.setProviderName(currentProvider.getFullNameProvider());
        invoicePost.setType(Constants.TYPE_HARVESTER);

        if (isAdd()) {
            invoicePost.setProviderId(currentProvider.getIdProvider());
            invoicePost.setDispatcherName(currentProvider.getFullNameProvider());
            invoicePost.setReceiverName(SessionManager.getUserName(context));
            invoicePost.setStartDate(Util.getCurrentDateForInvoice());
            invoicePost.setDate(invoicePost.getStartDate().split(" ")[0]);
            invoicePost.setBuyOption(Constants.BUY_OPTION_HARVEST);

            Log.d("TEST", "--->getCurrentDateForInvoice " + invoicePost.getStartDate());

            mRepository.saveHarvestRequest(invoicePost, true);
        } else {
            boolean createdOffline = false;
            if (currentDetailsList.get(0).getInvoice() == null) {
                invoicePost.setInvoiceId(currentDetailsList.get(0).getInvoiceId());
                createdOffline = true;
            } else {
                invoicePost.setInvoiceId(currentDetailsList.get(0).getInvoice().getId());
            }
            invoicePost.setProviderId(currentProvider.getIdProvider());
            invoicePost.setDispatcherName(currentProvider.getFullNameProvider());
            invoicePost.setReceiverName(currentDetailsList.get(0).getReceiverName());
            invoicePost.setStartDate(currentDetailsList.get(0).getStartDate());
            invoicePost.setBuyOption(Constants.BUY_OPTION_HARVEST);

            if (invoicePost.getLot() == -1 && currentDetailsList.get(0).getLot() != null) {
                invoicePost.setLot(currentDetailsList.get(0).getLot().getId());
            }
            if (invoicePost.getObservations() == null) {
                invoicePost.setObservations(currentDetailsList.get(0).getObservation());
            }
            if (invoicePost.getItems() == null) {
                invoicePost.setItems(new ArrayList<ItemPost>());
            }

            invoicePost.setDate(invoicePost.getStartDate().split(" ")[0]);

            mRepository.saveHarvestRequest(invoicePost, false);
        }
    }
}
