package com.hecticus.eleta.of_day;

import com.hecticus.eleta.base.BaseDetailListContract;
import com.hecticus.eleta.model.response.Pager;
import com.hecticus.eleta.model.response.harvest.HarvestOfDay;
import com.hecticus.eleta.model.response.harvest.HarvestsListResponse;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailsResponse;
import com.hecticus.eleta.model.response.providers.Provider;

import java.util.List;

/**
 * Created by roselyn545 on 18/9/17.
 */

public class InvoicesOfDayListContract extends BaseDetailListContract {

    public interface View {

        void showWorkingIndicator();

        void hideWorkingIndicator();

        void showMessage(String message);

        void showError(String error);

        void onClickPrintInvoice();

        void onClickCMakePurchase();

        void goToHarvestDetail(Provider provider, List<InvoiceDetails> detailsList);

        void updateHarvestsList(List<HarvestOfDay> harvestsList);

        //void addMoreHarvestsToTheList(List<HarvestOfDay> harvestsList);

        void refreshList();

        void initHeader(String name, String imageUrl);

    }

    public interface Actions extends BaseDetailListContract.Actions{

        void getInitialData();

        void refreshHarvestsList();

        void handleSuccessfulHarvestsRequest(InvoiceDetailsResponse invoiceDetailsResponse);

        //void updatePager(Pager pager);

        void onHarvestDeleted(InvoiceDetailsResponse invoiceDetailsResponse);

        //void getMoreHarvests();

        void onError(String error);

        //boolean canLoadMore();
    }

    public interface Repository {

        void onError(String error);

        void harvestsRequest(int invoiceId);

        void deleteHarvest(int invoiceId, String date);

        void onGetHarvestsSuccess(InvoiceDetailsResponse invoiceDetailsResponse);
    }
}
