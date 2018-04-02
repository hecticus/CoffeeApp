package com.hecticus.eleta.purchases.list;

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
 * Created by roselyn545 on 18/9/17.
 */

public class PurchasesListContract {

    public interface View extends BaseListContract.View {

        void showWorkingIndicator();

        void hideWorkingIndicator();

        void showMessage(String message);

        void showError(String error);

        void onClickAddPurchase();

        void goToPurchasesListByProvider(Invoice invoice);

        void updatePurchasesList(List<Invoice> invoicesList);

        void addMorePurchasesToTheList(List<Invoice> invoicesList);

        void refreshList();

        void printPurchase(String textToShow, String textToPrint);

        void showDeleteConfirmation(BaseModel model);
    }

    public interface Actions extends BaseListContract.Actions {

        void getInitialData();

        void refreshPurchasesList();

        void handleSuccessfulPurchasesRequest(List<Invoice> invoicesList);

        void updatePager(Pager pager);

        void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults);

        void onPurchaseDeleted();

        void getMorePurchases();

        void onError(String error);

        void onError(String error, String cause);

        boolean canLoadMore();

        void getReceiptOfInvoiceForPrinting(Invoice invoiceParam);

        void onGetReceiptSuccess(ReceiptResponse receiptResponse);

        void onGetReceiptDetailsSuccess(InvoiceDetailsResponse invoiceDetailsResponse);

        void deletePurchase(BaseModel model);
    }

    public interface Repository {

        void onError(String error, String cause);

        void getPurchasesRequest(int index);

        void deletePurchase(int remoteInvoiceId, int localInvoiceId);

        void onGetPurchasesSuccess(InvoiceListResponse invoicesList);

        void getReceiptDetails(Invoice invoiceParam);

        void onGetReceiptDetailsSuccess(InvoiceDetailsResponse detailsResponse);

        void getReceiptOfInvoiceForPrinting(Invoice invoiceParam);

        void onGetReceiptSuccess(ReceiptResponse receiptResponse);

    }
}
