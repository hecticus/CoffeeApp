package com.hecticus.eleta.purchases.list;

import com.hecticus.eleta.base.BaseListContract;
import com.hecticus.eleta.model.response.Pager;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.model.response.invoice.InvoiceListResponse;

import java.util.List;

/**
 * Created by roselyn545 on 18/9/17.
 */

public class PurchasesListContract {
    
    public interface View {

        void showWorkingIndicator();

        void hideWorkingIndicator();

        void showMessage(String message);

        void showError(String error);

        void onClickAddPurchase();

        void goToPurchasesListByProvider(Invoice invoice);

        void updatePurchasesList(List<Invoice> invoicesList);

        void addMorePurchasesToTheList(List<Invoice> invoicesList);

        void refreshList();

        void printPurchase(String text);
    }

    public interface Actions extends BaseListContract.Actions{

        void getInitialData();

        void refreshPurchasesList();

        void handleSuccessfulPurchasesRequest(List<Invoice> invoicesList);

        void updatePager(Pager pager);

        void onPurchaseDeleted();

        void getMorePurchases();

        void onError(String error);

        boolean canLoadMore();

    }

    public interface Repository {

        void onError(String error);

        void purchasesRequest(int index);

        void deletePurchase(int purchaseId);

        void onGetPurchasesSuccess(InvoiceListResponse invoicesList);
    }
}
