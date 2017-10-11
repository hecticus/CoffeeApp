package com.hecticus.eleta.of_day;

import android.content.Context;
import android.util.Log;

import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseDetailModel;
import com.hecticus.eleta.model.request.invoice.CloseInvoicePost;
import com.hecticus.eleta.model.response.harvest.HarvestOfDay;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailsResponse;
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

    //private int lastPage = Constants.INITIAL_PAGE_IN_PAGER;
    //private int currentPage = Constants.INITIAL_PAGE_IN_PAGER;


    private Invoice currentInvoice = null;
    private List<HarvestOfDay> harvestList = null;
    private List<InvoiceDetails> detailsList = null;
    private boolean needReloadMainList = false;

    @DebugLog
    public InvoicesOfDayListPresenter(Context context, InvoicesOfDayListContract.View mView, Invoice currentInvoiceParam) {
        this.context = context;
        this.mView = mView;
        currentInvoice = currentInvoiceParam;
        mRepository = new InvoicesOfDayListRepository(this);
    }


    @Override
    public void onClickEditButton(BaseDetailModel model) {
        List<InvoiceDetails> detailsOfHarvest = new ArrayList<InvoiceDetails>();
        HarvestOfDay harvestOfDay = (HarvestOfDay) model;
        for (InvoiceDetails detail : detailsList) {
            if (harvestOfDay.getDateTime().equals(detail.getStartDate())) {
                detailsOfHarvest.add(detail);
            }
        }
        mView.goToHarvestDetail(currentInvoice.getProvider(), detailsOfHarvest);
    }

    @Override
    public void onClickItem(BaseDetailModel model) {
    }

    @Override
    public void onClickDeleteButton(BaseDetailModel model) {
        mView.showWorkingIndicator();
        HarvestOfDay harvest = (HarvestOfDay) model;
        mRepository.deleteHarvest(currentInvoice.getInvoiceId(), harvest.getStartDate());
    }

    @Override
    public void getInitialData() {
        mView.showWorkingIndicator();
        //lastPage = Constants.INITIAL_PAGE_IN_PAGER;
        //currentPage = Constants.INITIAL_PAGE_IN_PAGER;
        if (currentInvoice != null) {
            Log.d("TEST", "provider invoice " + currentInvoice.getProvider());
            mView.initHeader(currentInvoice.getProvider().getFullNameProvider(), currentInvoice.getProvider().getPhotoProvider());
            mRepository.harvestsRequest(currentInvoice.getInvoiceId());
        }
    }

    @Override
    public void refreshHarvestsList() {
        getInitialData();
    }

    @Override
    public boolean isCurrentClosedInvoice() {
        return currentInvoice.getInvoiceStatus() == 3;
    }

    @Override
    public void handleSuccessfulHarvestsRequest(InvoiceDetailsResponse invoiceDetailsResponse) {
        detailsList = invoiceDetailsResponse.getDetails();
        harvestList = invoiceDetailsResponse.getHarvests();
        mView.hideWorkingIndicator();
        /*if (currentPage == Constants.INITIAL_PAGE_IN_PAGER) {
            mView.updateHarvestsList(harvestsList);
        } else {
            mView.addMoreHarvestsToTheList(harvestsList);
        }*/
        if (harvestList != null) {
            mView.updateHarvestsList(harvestList);
        }
    }

    /*@Override
    public void updatePager(Pager pager) {
        lastPage = pager.getEndIndex();
        currentPage = pager.getPageIndex();
    }*/

    @Override
    public void onHarvestDeleted(InvoiceDetailsResponse invoiceDetailsResponse) {
        detailsList = invoiceDetailsResponse.getDetails();
        harvestList = invoiceDetailsResponse.getHarvests();
        mView.hideWorkingIndicator();
        mView.showMessage(context.getString(R.string.harvest_deleted_successful));

        if (harvestList != null && harvestList.size() > 0) {
            if (harvestList != null) {
                mView.updateHarvestsList(harvestList);
            }
        } else {
            needReloadMainList = true;
            mView.doBack();
        }
    }

    @Override
    public void closeInvoice() {
        mView.showWorkingIndicator();
        CloseInvoicePost closePost = new CloseInvoicePost(currentInvoice.getInvoiceId(), Util.getTomorrowDate());
        mRepository.closeInvoiceRequest(closePost);
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
    public boolean needReloadMainList() {
        return needReloadMainList;
    }

    /*@Override
    public void getMoreHarvests() {
        mView.showWorkingIndicator();
        mRepository.harvestsRequest(++currentPage);
    }*/

    @Override
    public void onError(String error) {
        mView.hideWorkingIndicator();
        mView.showError(error);
    }

    /*@Override
    public boolean canLoadMore() {
        return currentPage + 1 <= lastPage;
    }*/
}
