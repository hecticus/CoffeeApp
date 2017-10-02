package com.hecticus.eleta.model.response.harvest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model.response.Pager;

import java.util.List;

/**
 * Created by roselyn545 on 15/9/17.
 */

public class HarvestsListResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private List<Harvest> result = null;
    @SerializedName("pager")
    @Expose
    private Pager pager;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Harvest> getResult() {
        return result;
    }

    public void setResult(List<Harvest> result) {
        this.result = result;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }
}
