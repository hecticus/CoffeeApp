package com.hecticus.eleta.harvest.list;

import com.hecticus.eleta.base.BaseListContract;
import com.hecticus.eleta.base.BaseModel;
import com.hecticus.eleta.model.response.Pager;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.model.response.invoice.InvoiceListResponse;

import java.util.List;

/**
 * Created by roselyn545 on 15/9/17.
 */

public class HarvestsListContract extends BaseListContract {

    public interface View {

        void showWorkingIndicator();

        void hideWorkingIndicator();

        void showMessage(String message);

        void showError(String error);

        void onClickAddHarvest();

        void goToHarvestsListByHarvester(Invoice invoice);

        void updateHarvestsList(List<Invoice> invoicesList);

        void addMoreHarvestsToTheList(List<Invoice> invoicesList);

        void refreshList();

        void printHarvest(String text);
    }

    public interface Actions extends BaseListContract.Actions{

        void getInitialData();

        void refreshHarvestsList();

        void handleSuccessfulHarvestsRequest(List<Invoice> invoicesList);

        void updatePager(Pager pager);

        void onHarvestDeleted();

        void getMoreHarvests();

        void onError(String error);

        boolean canLoadMore();
    }

    public interface Repository {

        void onError(String error);

        void harvestsRequest(int index);

        void deleteHarvest(int harvestId);

        void onGetHarvestsSuccess(InvoiceListResponse invoicesList);
    }
}
