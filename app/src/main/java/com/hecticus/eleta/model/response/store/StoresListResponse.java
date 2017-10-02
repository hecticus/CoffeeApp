package com.hecticus.eleta.model.response.store;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by roselyn545 on 26/9/17.
 */

public class StoresListResponse {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private List<Store> result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Store> getResult() {
        return result;
    }

    public void setResult(List<Store> result) {
        this.result = result;
    }
}
