package com.hecticus.eleta.model_new;

import android.content.Context;
import android.content.SharedPreferences;

import com.hecticus.eleta.model.response.AccessTokenResponse;
import com.hecticus.eleta.model.response.LoginResponse;
import com.hecticus.eleta.util.Constants;

import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 15/9/17.
 */

public class SessionManager {

    private static String SESSION_PREFERENCE = "SESSION_PREFERENCE";
    private static String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static String USER_NAME = "USER_NAME";
    private static String USER_ID = "USER_ID";
    private static String USER_EMAIL = "USER_EMAIL";


    @DebugLog
    public static boolean isValidSession(Context context) {
        if (getAccessToken(context).isEmpty()) {
            clearPreferences(context);
            return false;
        }
        return true;
    }

    @DebugLog
    public static String getAccessToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ACCESS_TOKEN, "");
        //return Constants.FAKE_TOKEN;
    }

    @DebugLog
    public static String getUserName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_NAME, "");
    }

    @DebugLog
    public static String getUserEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_EMAIL, "");
    }

    @DebugLog
    public static Long getUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(USER_ID, -1);
    }

    @DebugLog
    public static void clearPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    @DebugLog
    public static void updateSession(Context context, AccessTokenResponse accessTokenResponse,
                                     String email, String name, long id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN, accessTokenResponse.getAccess_token());
        editor.putString(USER_EMAIL, /*response.getEmail()*/ email);
        editor.putString(USER_NAME, /*response.getName()*/ name);
        editor.putLong(USER_ID, /*response.getName()*/ id);
        editor.commit();
    }
}
