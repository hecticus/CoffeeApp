package com.hecticus.eleta.model.request.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by roselyn545 on 28/9/17.
 */

public class PurityPost {
    @SerializedName("idPurity")
    @Expose
    private int id = -1;

    @SerializedName("valueRateInvoiceDetailPurity")
    @Expose
    private float rateValue = 0;

    public PurityPost(int purityId, float rateValue){
        id = purityId;
        this.rateValue = rateValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRateValue() {
        return rateValue;
    }

    public void setRateValue(float rateValue) {
        this.rateValue = rateValue;
    }
}
