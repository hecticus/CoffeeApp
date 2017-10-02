package com.hecticus.eleta.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.hecticus.eleta.model.response.LoginResponse;
import com.hecticus.eleta.util.Constants;

import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 15/9/17.
 */

public class Session {

    private static String SESSION_PREFERENCE = "SESSION_PREFERENCE";
    private static String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static String USER_NAME = "USER_NAME";


    @DebugLog
    public static boolean isValidSession(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(ACCESS_TOKEN, "").isEmpty()) {
            clearPreferences(context);
            return false;
        }
        return true;
    }

    @DebugLog
    public static String getAccessToken(Context context) {
        //SharedPreferences sharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        //return sharedPreferences.getString(ACCESS_TOKEN, "");
        return Constants.FAKE_TOKEN;
    }

    @DebugLog
    public static String getUserName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_NAME, "");
    }

    @DebugLog
    public static void clearPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    @DebugLog
    public static void updateSession(Context context, LoginResponse response) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN, response.getToken());
        editor.commit();
    }

    @DebugLog
    public static void saveUserData(Context context, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, name);
        editor.commit();
    }

}
