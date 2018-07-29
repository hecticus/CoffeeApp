package com.hecticus.eleta.harvest.list;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseModel;
import com.hecticus.eleta.model.response.Pager;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailsResponse;
import com.hecticus.eleta.model.response.invoice.ReceiptResponse;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.PermissionUtil;
import com.hecticus.eleta.util.Util;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
import io.realm.Realm;

/**
 * Created by roselyn545 on 15/9/17.
 */

public class HarvestsListPresenter implements HarvestsListContract.Actions {

    Context context;
    private HarvestsListContract.View mView;
    private HarvestsListContract.Repository mRepository;

    private int lastPage = Constants.INITIAL_PAGE_IN_PAGER;
    private int currentPage = Constants.INITIAL_PAGE_IN_PAGER;
    private ReceiptResponse receiptResponse = null;
    private BaseModel currentModel;

    @DebugLog
    public HarvestsListPresenter(Context context, HarvestsListContract.View mView) {
        this.context = context;
        this.mView = mView;
        mRepository = new HarvestsListRepository(this);
    }

    @DebugLog
    @Override
    public void onClickPrintButton(BaseModel model) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (mView.hasLocationPermissions()) {
                getReceiptOfInvoiceForPrinting((Invoice) model);
            } else {
                currentModel = model;
                mView.requestLocationPermissions();
            }
        } else {
            getReceiptOfInvoiceForPrinting((Invoice) model);
        }
    }

    @DebugLog
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionUtil.LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getReceiptOfInvoiceForPrinting((Invoice) currentModel);
                } else {
                    mView.showError(context.getString(R.string.location_permission_needed_for_correct_bluetooth_scan));
                }
                break;
        }
    }

    @DebugLog
    @Override
    public void getReceiptOfInvoiceForPrinting(Invoice invoiceParam) {
        mView.showWorkingIndicator();
        mRepository.getReceiptOfInvoiceForPrinting(invoiceParam);
    }

    @DebugLog
    @Override
    public void onClickEditButton(BaseModel model) {
    }

    @DebugLog
    @Override
    public void onClickItem(BaseModel model) {
        mView.goToHarvestsListByHarvester((Invoice) model);
    }

    @DebugLog
    @Override
    public void onClickDeleteButton(BaseModel model) {
        mView.showDeleteConfirmation(model);
    }

    @DebugLog
    @Override
    public void deleteHarvest(BaseModel model) {
        mView.showWorkingIndicator();
        Invoice invoice = (Invoice) model;
        mRepository.deleteHarvest(invoice);
    }

    @DebugLog
    @Override
    public void getInitialData() {
        mView.showWorkingIndicator();
        lastPage = Constants.INITIAL_PAGE_IN_PAGER;
        currentPage = Constants.INITIAL_PAGE_IN_PAGER;
        mRepository.getHarvestsRequest(currentPage);
    }

    @DebugLog
    @Override
    public void refreshHarvestsList() {
        getInitialData();
    }

    @DebugLog
    @Override
    public void handleSuccessfulSortedHarvestsRequest(List<Invoice> invoicesList) {
        mView.hideWorkingIndicator();
        if (currentPage == Constants.INITIAL_PAGE_IN_PAGER) {
            mView.updateHarvestsList(invoicesList);
        } else {
            mView.addMoreHarvestsToTheList(invoicesList);
        }
    }

    @DebugLog
    @Override
    public void handleSuccessfulMixedHarvestsRequest(List<Invoice> nonSortableInvoicesList) {

        //If nonSortableInvoicesList comes from realm, it cannot be properly sorted, so we use a new list.

        List<Invoice> sortableInvoicesList = new ArrayList<>();
        sortableInvoicesList.addAll(nonSortableInvoicesList);

        Log.d("SORT", "--->SORTING:");

        for (int i = 0; i < sortableInvoicesList.size() - 1; i++) {
            for (int j = i + 1; j < sortableInvoicesList.size(); j++) {
                String string1 = sortableInvoicesList.get(j).getBestAvailableProviderName() != null ? sortableInvoicesList.get(j).getBestAvailableProviderName().toLowerCase() : "";
                String string2 = sortableInvoicesList.get(i).getBestAvailableProviderName() != null ? sortableInvoicesList.get(i).getBestAvailableProviderName().toLowerCase() : "";

                if (string1.compareTo(string2) < 0) {
                    Log.d("SORT", "--->" + string1 + " -> " + string2 + " is WRONG");
                    Invoice aux = sortableInvoicesList.get(j);
                    sortableInvoicesList.set(j, sortableInvoicesList.get(i));
                    sortableInvoicesList.set(i, aux);
                } else
                    Log.d("SORT", "--->" + string1 + " -> " + string2 + " is OK");
            }
        }

        Log.d("SORT", "--->SORTED:");
        for (int i = 0; i < sortableInvoicesList.size(); i++) {
            Log.d("SORT", "--->" + i + ": " + sortableInvoicesList.get(i).getBestAvailableProviderName());
        }
        //Log.d("DEBUG status1", String.valueOf(sortableInvoicesList.get(0).getStatusInvo()));
        //Log.d("DEBUG status2", String.valueOf(sortableInvoicesList.get(1).getStatusInvo()));
        handleSuccessfulSortedHarvestsRequest(sortableInvoicesList);
    }

    @Override
    public void updatePager(Pager pager) {
        lastPage = pager.getEndIndex();
        currentPage = pager.getPageIndex();
    }

    @DebugLog
    @Override
    public void onHarvestDeleted() {
        mView.hideWorkingIndicator();
        mView.showMessage(context.getString(R.string.harvest_deleted_successful));
        refreshHarvestsList();
    }

    @DebugLog
    @Override
    public void getMoreHarvests() {
        mView.showWorkingIndicator();
        mRepository.getHarvestsRequest(++currentPage);
    }

    @DebugLog
    @Override
    public void onError(String error) {
        mView.hideWorkingIndicator();
        mView.showError(error);
    }

    @Override
    public boolean canLoadMore() {
        return currentPage + 1 <= lastPage;
    }

    @DebugLog
    @Override
    public void onGetReceiptSuccess(ReceiptResponse receiptResponse) {
        this.receiptResponse = receiptResponse;
        mRepository.getReceiptDetails(receiptResponse.getInvoice());
    }

    @DebugLog
    @Override
    public void onGetReceiptDetailsSuccess(InvoiceDetailsResponse invoiceDetailsResponse) {
        mView.hideWorkingIndicator();

        float totalOfInvoiceIncludingLocalOperations = 0;

        for (InvoiceDetails currentDetail : invoiceDetailsResponse.getListInvoiceDetails()) {
            totalOfInvoiceIncludingLocalOperations += (currentDetail.getPriceByLot() * currentDetail.getAmount());
        }

        if (receiptResponse.getInvoice().isManaged()) // It's managed by realm
        {
            Invoice invoiceCopy = Realm.getDefaultInstance().copyFromRealm(receiptResponse.getInvoice());
            invoiceCopy.setInvoiceTotal(totalOfInvoiceIncludingLocalOperations);
            receiptResponse.setInvoice(invoiceCopy);
        } else {
            receiptResponse.getInvoice().setInvoiceTotal(totalOfInvoiceIncludingLocalOperations);
        }

        String textToShow = Util.formatTextForPreview(context, receiptResponse, invoiceDetailsResponse);
        String textToPrint = Util.formatTextForPrinting(textToShow);
        mView.showHarvestPrintPreview(textToPrint, textToShow);
    }

    @Override
    public void invalidToken() {
        mView.invalidToken();
    }
}
