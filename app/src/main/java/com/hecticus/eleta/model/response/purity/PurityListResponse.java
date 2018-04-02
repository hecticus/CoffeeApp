package com.hecticus.eleta.model.response.purity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by roselyn545 on 28/9/17.
 */

public class PurityListResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private List<Purity> result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Purity> getResult() {
        return result;
    }

    public void setResult(List<Purity> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "PurityListResponse{" +
                "message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
