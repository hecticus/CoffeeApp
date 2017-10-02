package com.hecticus.eleta.model.response.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model.response.Pager;

import java.util.List;

/**
 * Created by roselyn545 on 18/9/17.
 */

public class InvoiceListResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private List<Invoice> result = null;

    @SerializedName("pager")
    @Expose
    private Pager pager;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Invoice> getResult() {
        return result;
    }

    public void setResult(List<Invoice> result) {
        this.result = result;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }
}
