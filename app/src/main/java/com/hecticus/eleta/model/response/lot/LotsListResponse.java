package com.hecticus.eleta.model.response.lot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model.response.Pager;
import com.hecticus.eleta.model.response.farm.Farm;

import java.util.List;

/**
 * Created by roselyn545 on 25/9/17.
 */

public class LotsListResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private List<Lot> result = null;

    @SerializedName("pager")
    @Expose
    private Pager pager;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Lot> getResult() {
        return result;
    }

    public void setResult(List<Lot> result) {
        this.result = result;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }
}
