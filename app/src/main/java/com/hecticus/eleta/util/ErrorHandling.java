package com.hecticus.eleta.util;

import com.crashlytics.android.Crashlytics;

public class ErrorHandling {

    public static String errorCodeInServerResponseProcessing = "code #1: ";
    public static String errorCodeWebServiceNotSuccess = "code #2: ";
    public static String errorCodeWebServiceFailed = "code #3: ";
    public static String errorCodeBDLocal = "code #4: ";

    public static void errorCodeInServerResponseProcessing(Exception e){
        Crashlytics.logException(e);
        Crashlytics.log(1, errorCodeInServerResponseProcessing, "error en el procesamiento de la respuesta del servidor");
    }

    public static void syncErrorCodeInServerResponseProcessing(Exception e){
        Crashlytics.logException(e);
        Crashlytics.log(1, "sync" + errorCodeInServerResponseProcessing, "error en el procesamiento de la respuesta del servidor");
    }

    public static void syncErrorCodeWebServiceFailed(Throwable t){
        Crashlytics.logException(t);
        Crashlytics.log(1, "sync" + errorCodeWebServiceFailed, "error en el web service");
    }

    public static void errorCodeBdLocal(Exception e){
        Crashlytics.logException(e);
        Crashlytics.log(1, errorCodeBDLocal, "error en el procesamiento de la respuesta del servidor");
    }
}
