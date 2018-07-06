package com.hecticus.eleta.model_new.persistence;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import hugo.weaving.DebugLog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by roselyn545 on 20/10/17.
 */

public class ManagerServices<T> {

    private ServiceListener listener;

    public ManagerServices() {
        this.listener = null;
    }

    @DebugLog
    public ManagerServices(Call<T> call, ServiceListener listener) {
        this.listener = listener;
        doCall(call);
    }

    @DebugLog
    public void doCall(Call<T> call) {
        call.enqueue(new Callback<T>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.d("MANAGER", "--->isSuccessful doCall: " + response.body());
                        if (listener != null) {
                            listener.onSuccess(response);
                        }
                    } catch (Exception e) {
                        Log.d("MANAGER", "--->isSuccessful Exception (" + response.code() + "):" + response.body()+
                                " //Exception: "+e.getMessage());
                        e.printStackTrace();
                        if (listener != null)
                            listener.onError(true, -1, response, null);
                    }
                } else {
                    try {
                        JSONObject errorBody = new JSONObject(response.errorBody().string());

                        if (response != null && response.code() == 400) {
                            Log.d("MANAGER", "--->" + ManagerServices.class.getSimpleName() + " response code 400 (Invalid token?->" + errorBody);
                            if (listener != null)
                                listener.onInvalidToken();
                        } else {

                            Log.d("MANAGER", "--->Unsuccessful (" + response.code() + "):" + response.body()+
                                    " //errorBody: "+errorBody);

                            if (listener != null) {
                                if (errorBody.optString("errorDescription").isEmpty())
                                    listener.onError(false, errorBody.optInt("error"), response, errorBody.optString("message", null));
                                else
                                    listener.onError(false, errorBody.optInt("error"), response, errorBody.optString("errorDescription", null));
                            }
                        }
                    } catch (JSONException | IOException e) {
                        Log.d("MANAGER", "--->fail onError (" + response.code() + "):" + response.body());
                        e.printStackTrace();
                        listener.onError(true, -1, response, null);
                    }
                }
            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.d("MANAGER", "--->ERROR: " + t.getMessage());
                if (listener != null)
                    listener.onError(true, -1, null, null);
            }
        });
    }

    public void setCustomService(ServiceListener listener) {
        this.listener = listener;
    }

    public interface ServiceListener<T> {

        public void onSuccess(Response<T> response);

        public void onError(boolean fail, int code, Response<T> response, String errorMessage);

        public void onInvalidToken();

    }
}
