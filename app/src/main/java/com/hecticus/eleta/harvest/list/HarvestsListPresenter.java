package com.hecticus.eleta.harvest.list;

import android.content.Context;
import android.util.Log;

import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseModel;
import com.hecticus.eleta.model.response.Pager;
import com.hecticus.eleta.model.response.harvest.Harvest;
import com.hecticus.eleta.model.response.harvest.HarvestsListResponse;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.util.Constants;

import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 15/9/17.
 */

public class HarvestsListPresenter implements HarvestsListContract.Actions {

    Context context;
    private HarvestsListContract.View mView;
    private HarvestsListContract.Repository mRepository;

    private int lastPage = Constants.INITIAL_PAGE_IN_PAGER;
    private int currentPage = Constants.INITIAL_PAGE_IN_PAGER;

    @DebugLog
    public HarvestsListPresenter(Context context, HarvestsListContract.View mView) {
        this.context = context;
        this.mView = mView;
        mRepository = new HarvestsListRepository(this);
    }

    @Override
    public void onClickPrintButton(BaseModel model) {
        //TODO
        Invoice invoice = (Invoice) model;
        String text = "Nombre: "+invoice.getProvider().getFullNameProvider()+"\n"+
                    "Fecha: "+invoice.getInvoiceClosedDate()+"\n"+
                "Total: "+invoice.getInvoiceTotal();
        Log.d("TEST","imprimir: "+text);
        //mView.printHarvest("Prueba");
    }

    @Override
    public void onClickEditButton(BaseModel model) {}

    @Override
    public void onClickItem(BaseModel model) {
        mView.goToHarvestsListByHarvester((Invoice)model);
    }

    @Override
    public void onClickDeleteButton(BaseModel model) {
        mView.showWorkingIndicator();
        Invoice invoice = (Invoice) model;
        mRepository.deleteHarvest(invoice.getInvoiceId());
    }

    @Override
    public void getInitialData() {
        mView.showWorkingIndicator();
        lastPage = Constants.INITIAL_PAGE_IN_PAGER;
        currentPage = Constants.INITIAL_PAGE_IN_PAGER;
        mRepository.harvestsRequest(currentPage);
    }

    @DebugLog
    @Override
    public void refreshHarvestsList() {
        getInitialData();
    }

    @DebugLog
    @Override
    public void handleSuccessfulHarvestsRequest(List<Invoice> invoicesList) {
        mView.hideWorkingIndicator();
        if (currentPage == Constants.INITIAL_PAGE_IN_PAGER) {
            mView.updateHarvestsList(invoicesList);
        } else {
            mView.addMoreHarvestsToTheList(invoicesList);
        }
    }

    @Override
    public void updatePager(Pager pager) {
        lastPage = pager.getEndIndex();
        currentPage = pager.getPageIndex();
    }

    @Override
    public void onHarvestDeleted() {
        mView.hideWorkingIndicator();
        mView.showMessage(context.getString(R.string.harvest_deleted_successful));
        refreshHarvestsList();
    }

    @Override
    public void getMoreHarvests() {
        mView.showWorkingIndicator();
        mRepository.harvestsRequest(++currentPage);
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
