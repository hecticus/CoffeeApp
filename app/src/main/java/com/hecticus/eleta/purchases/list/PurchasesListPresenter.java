package com.hecticus.eleta.purchases.list;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

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

import java.util.List;

import hugo.weaving.DebugLog;
import io.realm.Realm;

/**
 * Created by roselyn545 on 18/9/17.
 */

public class PurchasesListPresenter implements PurchasesListContract.Actions {

    Context context;
    private PurchasesListContract.View mView;
    private PurchasesListContract.Repository mRepository;

    private int lastPage = Constants.INITIAL_PAGE_IN_PAGER;
    private int currentPage = Constants.INITIAL_PAGE_IN_PAGER;
    private ReceiptResponse receiptResponse = null;

    private BaseModel currentModel;

    @DebugLog
    public PurchasesListPresenter(Context context, PurchasesListContract.View mView) {
        this.context = context;
        this.mView = mView;
        mRepository = new PurchasesListRepository(this);
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
    public void onClickEditButton(BaseModel model) {
    }

    @DebugLog
    @Override
    public void onClickItem(BaseModel model) {
        mView.goToPurchasesListByProvider((Invoice) model);
    }

    @DebugLog
    @Override
    public void onClickDeleteButton(BaseModel model) {
        mView.showDeleteConfirmation(model);
    }

    @DebugLog
    @Override
    public void deletePurchase(BaseModel model) {
        mView.showWorkingIndicator();
        Invoice invoice = (Invoice) model;
        mRepository.deletePurchase(invoice.getId(), invoice.getLocalId());
    }

    @DebugLog
    @Override
    public void invalidToken() {
        mView.invalidToken();
    }

    @DebugLog
    @Override
    public void getInitialData() {
        mView.showWorkingIndicator();
        lastPage = Constants.INITIAL_PAGE_IN_PAGER;
        currentPage = Constants.INITIAL_PAGE_IN_PAGER;
        mRepository.getPurchasesRequest(currentPage);
    }

    @DebugLog
    @Override
    public void refreshPurchasesList() {
        getInitialData();
    }

    @DebugLog
    @Override
    public void handleSuccessfulPurchasesRequest(List<Invoice> invoicesList) {
        mView.hideWorkingIndicator();
        if (currentPage == Constants.INITIAL_PAGE_IN_PAGER) {
            mView.updatePurchasesList(invoicesList);
        } else {
            mView.addMorePurchasesToTheList(invoicesList);
        }
    }

    @Override
    public void updatePager(Pager pager) {
        lastPage = pager.getEndIndex();
        currentPage = pager.getPageIndex();
    }

    @DebugLog
    @Override
    public void onPurchaseDeleted() {
        mView.hideWorkingIndicator();
        mView.showMessage(context.getString(R.string.purchase_deleted_successful));
        refreshPurchasesList();
    }

    @DebugLog
    @Override
    public void getMorePurchases() {
        mView.showWorkingIndicator();
        mRepository.getPurchasesRequest(++currentPage);
    }

    @Override
    public void onError(String error) {
        mView.hideWorkingIndicator();
        mView.showError(error);
    }

    @Override
    public void onError(String error, String cause) {
        mView.hideWorkingIndicator();
        mView.showError(error + "\nCause: " + cause);
    }

    @Override
    public boolean canLoadMore() {
        return currentPage + 1 <= lastPage;
    }

    @DebugLog
    @Override
    public void getReceiptOfInvoiceForPrinting(Invoice invoiceParam) {
        mView.showWorkingIndicator();
        mRepository.getReceiptOfInvoiceForPrinting(invoiceParam);
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
            totalOfInvoiceIncludingLocalOperations += (currentDetail.getPriceItem() * currentDetail.getAmount());
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

        mView.printPurchase(textToPrint, textToShow);
    }
}
