package com.hecticus.eleta.purchases.list;

import android.content.Context;
import android.util.Log;

import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseModel;
import com.hecticus.eleta.model.response.Pager;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.util.Constants;

import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 18/9/17.
 */

public class PurchasesListPresenter implements PurchasesListContract.Actions {

    Context context;
    private PurchasesListContract.View mView;
    private PurchasesListContract.Repository mRepository;

    private int lastPage = Constants.INITIAL_PAGE_IN_PAGER;
    private int currentPage = Constants.INITIAL_PAGE_IN_PAGER;

    @DebugLog
    public PurchasesListPresenter(Context context, PurchasesListContract.View mView) {
        this.context = context;
        this.mView = mView;
        mRepository = new PurchasesListRepository(this);
    }

    @Override
    public void onClickPrintButton(BaseModel model) {
        //TODO
        mView.printPurchase("Prueba");
    }

    @Override
    public void onClickEditButton(BaseModel model) {}

    @Override
    public void onClickItem(BaseModel model) {
        mView.goToPurchasesListByProvider((Invoice)model);
    }

    @DebugLog
    @Override
    public void onClickDeleteButton(BaseModel model) {
        mView.showWorkingIndicator();
        Invoice invoice = (Invoice) model;
        mRepository.deletePurchase(invoice.getInvoiceId());
    }

    @DebugLog
    @Override
    public void getInitialData() {
        mView.showWorkingIndicator();
        lastPage = Constants.INITIAL_PAGE_IN_PAGER;
        currentPage = Constants.INITIAL_PAGE_IN_PAGER;
        mRepository.purchasesRequest(currentPage);
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
        }else {
            mView.addMorePurchasesToTheList(invoicesList);
        }
    }

    @Override
    public void updatePager(Pager pager) {
        //TODO
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
        mRepository.purchasesRequest(++currentPage);
    }

    @Override
    public void onError(String error) {
        mView.hideWorkingIndicator();
        mView.showError(error);
    }

    @Override
    public boolean canLoadMore() {
        return currentPage + 1 <= lastPage;
    }

}
