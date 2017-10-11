package com.hecticus.eleta.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Edwin on 2017-09-17.
 */

public class Util {

    public static Gson gson = null;

    public static Gson getGson() throws ClassNotFoundException {
        if (gson != null)
            return gson;

        return gson = new GsonBuilder()
                //TODO Check
                /*.setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .registerTypeAdapter(Class.forName("io.realm.DoctorRealmProxy"), new Doctor())*/
                .create();
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static String getCurrentDateForInvoice() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String getTomorrowDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, 1);
        Date tomorrowDate = calendar.getTime();

        String finalDate = sdf.format(tomorrowDate) + " 00:00:00";
        return finalDate;
    }
}
