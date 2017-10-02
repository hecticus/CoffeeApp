package com.hecticus.eleta.model.response.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model.response.harvest.Harvest;
import com.hecticus.eleta.model.response.harvest.HarvestOfDay;

import java.util.List;

/**
 * Created by roselyn545 on 26/9/17.
 */

public class InvoiceDetailsResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private Result result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<InvoiceDetails> getDetails() {
        return result.getDetails();
    }

    public List<HarvestOfDay> getHarvests() {
        return result.getHarvests();
    }
}

class Result {

    @SerializedName("summary")
    @Expose
    private List<HarvestOfDay> harvests = null;

    @SerializedName("deatils")
    @Expose
    private List<InvoiceDetails> details = null;

    public List<InvoiceDetails> getDetails() {
        return details;
    }

    public void setDetails(List<InvoiceDetails> details) {
        this.details = details;
    }

    public List<HarvestOfDay> getHarvests() {
        return harvests;
    }

    public void setHarvests(List<HarvestOfDay> harvests) {
        this.harvests = harvests;
    }
}