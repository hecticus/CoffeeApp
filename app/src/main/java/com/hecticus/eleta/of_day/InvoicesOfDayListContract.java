package com.hecticus.eleta.of_day;

import android.support.annotation.NonNull;

import com.hecticus.eleta.base.BaseDetailListContract;
import com.hecticus.eleta.base.BaseDetailModel;
import com.hecticus.eleta.base.BaseModel;
import com.hecticus.eleta.model.request.invoice.CloseInvoicePost;
import com.hecticus.eleta.model.response.harvest.HarvestOfDay;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailsResponse;
import com.hecticus.eleta.model.response.invoice.ReceiptResponse;
import com.hecticus.eleta.model.response.providers.Provider;

import java.util.List;

/**
 * Created by roselyn545 on 18/9/17.
 */

public class InvoicesOfDayListContract extends BaseDetailListContract {

    public interface View extends BaseDetailListContract.View {

        void showWorkingIndicator();

        void hideWorkingIndicator();

        void showMessage(String message);

        void showError(String error);

        void onClickPrintButton();

        void onClickCMakePurchase();

        void closedInvoice();

        void goToHarvestOrPurchaseDetailsView(Provider provider, List<InvoiceDetails> detailsList, boolean invoiceHasOfflineOperation);

        void updateHarvestsOrPurchasesList(List<HarvestOfDay> harvestsOrPurchasesList);

        void initHeader(String name, String imageUrl);

        void doBack();

        void showHarvestPrintPreview(String textToPrint, String textToShow);

        void showDeleteConfirmation(BaseDetailModel model);
    }

    public interface Actions extends BaseDetailListContract.Actions {

        void onClickPrintButton();

        void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults);

        void deleteHarvestOrPurchase(BaseDetailModel model);

        void getInitialData();

        void refreshHarvestsList();

        boolean isCurrentClosedInvoice();

        void handleSuccessfulHarvestsOrPurchasesOfInvoiceRequest(InvoiceDetailsResponse invoiceDetailsResponse);

        //void updatePager(Pager pager);

        void onHarvestDeleted(InvoiceDetailsResponse invoiceDetailsResponse);

        void closeInvoice();

        void onCloseInvoiceSuccessful();

        void getReceiptOfCurrentInvoiceForPrinting();

        void onGetReceiptSuccess(ReceiptResponse receiptResponse);

        boolean needReloadMainList();

        void onError(String error);
    }

    public interface Repository {

        Provider getProviderById(int id);

        Provider getProviderByIdentificationDoc(String identificationDoc);

        void onError(String error);

        void getHarvestsOrPurchasesOfInvoiceRequest(Invoice invoice);

        void deleteHarvestOrPurchase(Invoice invoice, String date, String harvestOrPurchaseId);

        void closeInvoiceRequest(com.hecticus.eleta.model_new.Invoice post);

        void onGetHarvestsSuccess(InvoiceDetailsResponse invoiceDetailsResponse);

        void getReceiptOfInvoiceForPrinting(Invoice invoiceParam);

        void onGetReceiptSuccess(ReceiptResponse receiptResponse);
    }
}
