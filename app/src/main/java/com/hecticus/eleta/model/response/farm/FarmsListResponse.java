package com.hecticus.eleta.model.response.farm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model.response.Pager;
import com.hecticus.eleta.model.response.harvest.Harvest;

import java.util.List;

/**
 * Created by roselyn545 on 25/9/17.
 */

public class FarmsListResponse {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private List<Farm> result = null;

    @SerializedName("pager")
    @Expose
    private Pager pager;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Farm> getResult() {
        return result;
    }

    public void setResult(List<Farm> result) {
        this.result = result;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }
}
