package com.hecticus.eleta.util;

import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.discovery.DiscoveredPrinterBluetooth;

/**
 * Created by roselyn545 on 15/9/17.
 */

public class Constants {
    //public static final String BASE_URL = "http://10.0.3.105:9000/";
    public static final String BASE_URL = "https://dev.api.coffee.hecticus.com/";
    public static final int VERSION_DB_PROD = 2;

    /*public static final String BASE_URL = "https://dev.api.coffee.hecticus.com/";
    public static final int VERSION_DB_DEV = 2;*/

    public static final String FAKE_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6ImVkd2luZGVyZXB1ZXN0b0BnbWFpbC5jb20iLCJqdGkiOiI1IiwiaWF0IjoxNTE2Njc4MTQzLCJzdWIiOiJsb2dpbi8iLCJpc3MiOiJodHRwczovL2Rldi5jb2ZmZWUuaGVjdGljdXMuY29tLyMifQ.wkwffOAzge8Y7jlOCnjhfH3iEv-tjrlsS2o9Z3MpGb4T7cuUF-55BDhoUbeq84NSebHUN2UJlsxjH4AS9TXUXQ";


    public static final int INITIAL_PAGE_IN_PAGER = 0;

    public static final int ITEMS_BEFORE_LOAD_MORE = 2;

    public static final int TYPE_HARVESTER = 2;
    public static final int TYPE_SELLER = 1;

    public static final Boolean BUY_OPTION_HARVEST = true;
    public static final Boolean BUY_OPTION_PURCHASE = false;

    public static final int REQUEST_CODE_PROVIDER_CREATION = 6666;

    public static final String PRINT_TEXT_FOR_ZPL = "PRINT_TEXT_FOR_ZPL";
    public static final String PRINT_TEXT_FOR_PREVIEW = "PRINT_TEXT_FOR_PREVIEW";

    // Only for using when there is no connection,
    // otherwise get these values in the receipt details from the server:
    public class PrintingHeaderFallback {
        public static final String COMPANY_NAME = "Cafe de Eleta, S.A";
        public static final String COMPANY_TELEPHONE = "6679-4752";
        public static final String HARVEST_INVOICE_DESCRIPTION = "Recibo Diario de Cafe";
        public static final String HARVEST_INVOICE_TYPE = "Cosecha Propia";
        public static final String PURCHASE_RUC = "R.U.C 1727-188-34109 D.V. 69";
    }

    public enum ErrorType {
        GENERIC_ERROR_DURING_OPERATION, ERROR_UPDATING_IMAGE, DNI_EXISTING, RUC_EXISTING, NAME_EXISTING
    }


}
