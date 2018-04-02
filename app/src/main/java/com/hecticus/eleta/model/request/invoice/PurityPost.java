package com.hecticus.eleta.model.request.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by roselyn545 on 28/9/17.
 */

public class PurityPost extends RealmObject {

    @SerializedName("idPurity")
    @Expose
    private int purityId = -1;

    @SerializedName("valueRateInvoiceDetailPurity")
    @Expose
    private float rateValue = 0;

    @PrimaryKey
    private int purityPostLocalId = -1;

    private int itemPostLocalId = -1;

    public PurityPost() {
    }

    public PurityPost(int purityId, float rateValue) {
        this.purityId = purityId;
        this.rateValue = rateValue;
    }

    public int getPurityPostLocalId() {
        return purityPostLocalId;
    }

    public void setPurityPostLocalId(int purityPostLocalId) {
        this.purityPostLocalId = purityPostLocalId;
    }

    public int getItemPostLocalId() {
        return itemPostLocalId;
    }

    public void setItemPostLocalId(int itemPostLocalId) {
        this.itemPostLocalId = itemPostLocalId;
    }

    public int getPurityId() {
        return purityId;
    }

    public void setPurityIdId(int purityId) {
        this.purityId = purityId;
    }

    public float getRateValue() {
        return rateValue;
    }

    public void setRateValue(float rateValue) {
        this.rateValue = rateValue;
    }

    @Override
    public String toString() {
        return "PurityPost{" +
                "purityId=" + purityId +
                ", rateValue=" + rateValue +
                ", purityPostLocalId=" + purityPostLocalId +
                ", itemPostLocalId=" + itemPostLocalId +
                '}';
    }
}
