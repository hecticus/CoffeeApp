package com.hecticus.eleta.model.response.purity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.base.BaseEditableModel;

import java.io.Serializable;

/**
 * Created by roselyn545 on 28/9/17.
 */

public class Purity implements Serializable, BaseEditableModel {

    @SerializedName("idPurity")
    @Expose
    private int id = -1;

    @SerializedName("valueRateInvoiceDetailPurity")
    @Expose
    private float rateValue = 0;

    @SerializedName("namePurity")
    @Expose
    private String name = "";

    @SerializedName("discountRatePurity")
    @Expose
    private int discountRate = 0;

    private String weightString = "";

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }

    public String getWeightString() {
        return weightString;
    }

    public void setWeightString(String weightString) {
        this.weightString = weightString;
    }

    @Override
    public String getReadableDescription() {
        return name;
    }

    @Override
    public String getInputValue() {
        return weightString;
    }

    @Override
    public void setInputValue(String value) {
        weightString = value;
    }
}
