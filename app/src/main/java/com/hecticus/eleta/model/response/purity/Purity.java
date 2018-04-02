package com.hecticus.eleta.model.response.purity;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.base.BaseEditableModel;

import java.io.Serializable;

import hugo.weaving.DebugLog;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by roselyn545 on 28/9/17.
 */

public class Purity extends RealmObject implements Serializable, BaseEditableModel {

    @PrimaryKey
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

    public String getWeightString() {
        return weightString;
    }

    public Float getWeightStringAsFloat() {
        try {
            return Float.parseFloat(weightString);
        } catch (Exception e) {
            Log.e("PURITIES", "--->getWeightStringAsFloat Exception: " + e);
            e.printStackTrace();
        }
        return null;
    }

    @DebugLog
    public void setRateValueAndWeightString(float rateValueFloatParam) {
        this.rateValue = rateValueFloatParam;
        this.weightString = rateValueFloatParam + "";
    }

    @DebugLog
    public void setRateValueAndWeightString(String rateValueStringParam) {

        if (rateValueStringParam == null) {
            this.rateValue = 0;
            this.weightString = "";
        } else {
            try {
                this.rateValue = Float.parseFloat(rateValueStringParam);
                this.weightString = rateValueStringParam;
            } catch (Exception e) {
                Log.e("PURITIES", "--->setRateValueAndWeightString Exception: " + e);
                e.printStackTrace();
            }
        }
    }

    /*public void setWeightString(String weightString) {
        this.weightString = weightString;
    }*/

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

    @Override
    public String toString() {
        return "Purity{" +
                "id=" + id +
                ", rateValue=" + rateValue +
                ", name='" + name + '\'' +
                ", discountRate=" + discountRate +
                ", weightString='" + weightString + '\'' +
                '}';
    }
}
