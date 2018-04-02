package com.hecticus.eleta.harvest.list;

import android.support.annotation.NonNull;

import com.hecticus.eleta.base.BaseListContract;
import com.hecticus.eleta.base.BaseModel;
import com.hecticus.eleta.model.response.Pager;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailsResponse;
import com.hecticus.eleta.model.response.invoice.InvoiceListResponse;
import com.hecticus.eleta.model.response.invoice.ReceiptResponse;

import java.util.List;

/**
 * Created by roselyn545 on 15/9/17.
 */

public class HarvestsListContract extends BaseListContract {

    public interface View extends BaseListContract.View {

        void showWorkingIndicator();

        void hideWorkingIndicator();

        void showMessage(String message);

        void showError(String error);

        void onClickAddHarvest();

        void goToHarvestsListByHarvester(Invoice invoice);

        void updateHarvestsList(List<Invoice> invoicesList);

        void addMoreHarvestsToTheList(List<Invoice> invoicesList);

        void refreshList();

        void showHarvestPrintPreview(String textToPrint, String textToShow);

        void showDeleteConfirmation(BaseModel model);
    }

    public interface Actions extends BaseListContract.Actions {

        void getInitialData();

        void refreshHarvestsList();

        void handleSuccessfulSortedHarvestsRequest(List<Invoice> invoicesList);

        void handleSuccessfulMixedHarvestsRequest(List<Invoice> invoicesList);

        void updatePager(Pager pager);

        void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults);

        void deleteHarvest(BaseModel model);

        void onHarvestDeleted();

        void getMoreHarvests();

        void onError(String error);

        boolean canLoadMore();

        void getReceiptOfInvoiceForPrinting(Invoice invoiceParam);

        void onGetReceiptSuccess(ReceiptResponse receiptResponse);

        void onGetReceiptDetailsSuccess(InvoiceDetailsResponse invoiceDetailsResponse);
    }

    public interface Repository {

        void onError(String error);

        void getHarvestsRequest(int index);

        void deleteHarvest(Invoice invoice);

        void onGetHarvestsSuccess(InvoiceListResponse invoicesList);

        void getReceiptDetails(Invoice invoiceParam);

        void onGetReceiptDetailsSuccess(InvoiceDetailsResponse detailsResponse);

        void getReceiptOfInvoiceForPrinting(Invoice invoiceParam);

        void onGetReceiptSuccess(ReceiptResponse receiptResponse);
    }
}
