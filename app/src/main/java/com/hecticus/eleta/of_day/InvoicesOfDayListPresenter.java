package com.hecticus.eleta.of_day;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseDetailModel;
import com.hecticus.eleta.model.response.StatusInvoice;
import com.hecticus.eleta.model_new.persistence.ManagerDB;
import com.hecticus.eleta.model.request.invoice.CloseInvoicePost;
import com.hecticus.eleta.model.response.harvest.HarvestOfDay;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailsResponse;
import com.hecticus.eleta.model.response.invoice.ReceiptResponse;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.PermissionUtil;
import com.hecticus.eleta.util.Util;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 18/9/17.
 */

public class InvoicesOfDayListPresenter implements InvoicesOfDayListContract.Actions {

    Context context;
    private InvoicesOfDayListContract.View mView;
    private InvoicesOfDayListContract.Repository mRepository;

    private Invoice currentInvoice = null;
    private List<HarvestOfDay> harvestsOrPurchasesOfDayList = null;
    private List<InvoiceDetails> detailsList = null;
    private boolean needReloadMainList = false;

    @DebugLog
    public InvoicesOfDayListPresenter(Context context, InvoicesOfDayListContract.View mView, Invoice currentInvoiceParam, boolean isForHarvest) {
        this.context = context;
        this.mView = mView;
        currentInvoice = currentInvoiceParam;
        mRepository = new InvoicesOfDayListRepository(this, isForHarvest);
    }

    @DebugLog
    @Override
    public void onClickEditButton(BaseDetailModel model) {

        List<InvoiceDetails> detailsOfHarvest = new ArrayList<>();

        HarvestOfDay harvestOfDay = (HarvestOfDay) model;
        for (InvoiceDetails detail : detailsList) {
            if (harvestOfDay.getDateTime().equals(detail.getStartDate())) {
                Log.d("OFFLINE", "--->onClickEditButton adding detail (" + detail.getItemType() + ") to next view: " + detail);

                //FIXME: We use a clone and remove these as they will be properly populated when creating the view
                try {
                    InvoiceDetails detailClone = detail.clone();
                    // For harvests:
                    detailClone.setLot(null);
                    // For purchases:
                    detailClone.setItemType(null);
                    detailClone.setDetailPurities(null);
                    detailClone.setStore(null);

                    detailsOfHarvest.add(detailClone);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            } else
                Log.d("OFFLINE", "--->onClickEditButton NOT adding detail (" + detail.getItemType() + ")to next view: " + detail);
        }
        mView.goToHarvestOrPurchaseDetailsView(currentInvoice.getProvider(), detailsOfHarvest, ManagerDB.invoiceHasOfflineOperation(currentInvoice));
    }

    @Override
    public void onClickItem(BaseDetailModel model) {
    }

    @Override
    public void onClickDeleteButton(BaseDetailModel model) {
        mView.showDeleteConfirmation(model);
    }

    @DebugLog
    @Override
    public void deleteHarvestOrPurchase(BaseDetailModel model) {
        mView.showWorkingIndicator();
        HarvestOfDay harvest = (HarvestOfDay) model;
        mRepository.deleteHarvestOrPurchase(currentInvoice, harvest.getStartDate(), harvest);
    }

    @DebugLog
    @Override
    public void invalidToken() {
        mView.invalidToken();
    }

    @DebugLog
    @Override
    public void getInitialData() {
        try {
            mView.showWorkingIndicator();

            if (currentInvoice != null) {

                Log.d("BUG", "--->Invoice to request harvests of day" + currentInvoice);

                Log.d("BUG", "--->Provider of invoice to request HOD: " + currentInvoice.getProvider());
                if (currentInvoice.getProvider() == null) {
                    if (currentInvoice.getProviderId() == -1) {
                        currentInvoice.setProvider(mRepository.getProviderByIdentificationDoc(currentInvoice.getIdentificationDocProvider()));
                    } else {
                        Provider provider = mRepository.getProviderById(currentInvoice.getProviderId());
                        Log.d("TEST", "--->getProviderId " + currentInvoice.getProviderId() + " - getIdentificationDocProvider:" + currentInvoice.getIdentificationDocProvider());
                        Log.d("TEST", "--->provider: " + provider);

                        currentInvoice.setProvider(provider);
                    }
                }

                if (currentInvoice.getProvider() != null) {
                    mView.initHeader(currentInvoice.getProvider().getFullNameProvider(), currentInvoice.getProvider().getPhotoProvider());
                    mRepository.getHarvestsOrPurchasesOfInvoiceRequest(currentInvoice);
                } else {
                    mView.finishWithErrorMessage(context.getString(R.string.provider_info_not_yet_loaded));
                }
            } else {
                Log.e("BUG", "--->No invoice to request harvests of day");
                mView.finishWithErrorMessage(context.getString(R.string.error));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (mView != null)
                mView.finishWithErrorMessage(context.getString(R.string.error_during_operation));
        }
    }

    @DebugLog
    @Override
    public void refreshHarvestsList() {
        getInitialData();
    }

    @Override
    public boolean isCurrentClosedInvoice() {//3 = a factura cerrada
        /*Gson g= new Gson();
        Log.d("DEBUGGGGGGGG", g.toJson(currentInvoice));*/
        return currentInvoice.getStatusInvo().equals("Closed");//false; //<-currentInvoice.getInvoiceStatus() == 3;todo nose
    }

    @DebugLog
    @Override
    public void handleSuccessfulHarvestsOrPurchasesOfInvoiceRequest(InvoiceDetailsResponse invoiceDetailsResponse) {

        Log.d("HOD", "--->handleSuccessfulHarvestsOrPurchasesOfInvoiceRequest: " + invoiceDetailsResponse.getHarvests().size());//invoiceDetailsResponse.getListInvoiceDetails().size());

        detailsList = invoiceDetailsResponse.getListInvoiceDetails();
        harvestsOrPurchasesOfDayList = invoiceDetailsResponse.getHarvests();
        mView.hideWorkingIndicator();
        /*if (currentPage == Constants.INITIAL_PAGE_IN_PAGER) {
            mView.updateHarvestsOrPurchasesList(harvestsList);
        } else {
            mView.addMoreHarvestsToTheList(harvestsList);
        }*/
        if (harvestsOrPurchasesOfDayList != null) {
            mView.updateHarvestsOrPurchasesList(harvestsOrPurchasesOfDayList);
        }
    }

    @DebugLog
    @Override
    public void onHarvestDeleted(InvoiceDetailsResponse invoiceDetailsResponse) {
        detailsList = invoiceDetailsResponse.getListInvoiceDetails();
        harvestsOrPurchasesOfDayList = invoiceDetailsResponse.getHarvests();
        mView.hideWorkingIndicator();
        mView.showMessage(context.getString(R.string.harvest_deleted_successful));

        if (harvestsOrPurchasesOfDayList != null && harvestsOrPurchasesOfDayList.size() > 0) {
            if (harvestsOrPurchasesOfDayList != null) {
                mView.updateHarvestsOrPurchasesList(harvestsOrPurchasesOfDayList);
            }
        } else {
            needReloadMainList = true;
            mView.doBack();
        }
    }

    @DebugLog
    @Override
    public void closeInvoice() {
        mView.showWorkingIndicator();
        //CloseInvoicePost closePost = new CloseInvoicePost(currentInvoice.getId(), Util.getTomorrowDate());
        Invoice invoice = ManagerDB.getInvoiceById(currentInvoice.getId());
        ManagerDB.updateStatusInvoice(invoice);
        com.hecticus.eleta.model_new.Invoice invoice1
                = new com.hecticus.eleta.model_new.Invoice(invoice,
                invoice.getProvider(),
                new StatusInvoice(12, false, "Closed", null));
        mRepository.closeInvoiceRequest(invoice1);
    }

    @DebugLog
    @Override
    public void onCloseInvoiceSuccessful() {
        needReloadMainList = true;
        mView.closedInvoice();
        mView.hideWorkingIndicator();
        mView.showMessage(context.getString(R.string.purchase_closed_successful));
    }

    @DebugLog
    @Override
    public void onClickPrintButton() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (mView.hasLocationPermissions()) {
                getReceiptOfCurrentInvoiceForPrinting();
            } else {
                mView.requestLocationPermissions();
            }
        } else {
            getReceiptOfCurrentInvoiceForPrinting();
        }
    }

    @DebugLog
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionUtil.LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getReceiptOfCurrentInvoiceForPrinting();
                } else {
                    mView.showError(context.getString(R.string.location_permission_needed_for_correct_bluetooth_scan));
                }
                break;
        }
    }

    @DebugLog
    @Override
    public void getReceiptOfCurrentInvoiceForPrinting() {
        mView.showWorkingIndicator();
        mRepository.getReceiptOfInvoiceForPrinting(currentInvoice);
    }

    @DebugLog
    @Override
    public void onGetReceiptSuccess(ReceiptResponse receiptResponse) {
        mView.hideWorkingIndicator();

        boolean isHarvest;

        if (receiptResponse.getInvoice().getProvider() != null) {
            //Remote invoice
            isHarvest = receiptResponse.getInvoice().getProvider().isHarvester();
        } else {
            //Local invoice
            isHarvest = receiptResponse.getInvoice().getType() == Constants.TYPE_HARVESTER;
        }

        InvoiceDetailsResponse invoiceDetailsResponse = new InvoiceDetailsResponse(detailsList);

        float totalOfInvoiceIncludingLocalOperations = 0;

        for (InvoiceDetails currentDetail : invoiceDetailsResponse.getListInvoiceDetails()) {
            if (isHarvest)
                totalOfInvoiceIncludingLocalOperations += (currentDetail.getPriceByLot() * currentDetail.getAmount());
            else
                totalOfInvoiceIncludingLocalOperations += (currentDetail.getPriceItem() * currentDetail.getAmount());
        }

        receiptResponse.getInvoice().setInvoiceTotal(totalOfInvoiceIncludingLocalOperations);

        String textToShow = Util.formatTextForPreview(context, receiptResponse, invoiceDetailsResponse);

        String textToPrint = Util.formatTextForPrinting(textToShow);

        mView.showHarvestPrintPreview(textToPrint, textToShow);
    }

    @DebugLog
    @Override
    public boolean needReloadMainList() {
        return needReloadMainList;
    }

    @Override
    public void onError(String error) {
        mView.hideWorkingIndicator();
        mView.showError(error);
    }
}
