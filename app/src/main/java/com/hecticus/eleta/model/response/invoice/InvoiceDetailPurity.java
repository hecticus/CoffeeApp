package com.hecticus.eleta.model.response.invoice;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.base.BaseEditableModel;
import com.hecticus.eleta.model.response.purity.Purity;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import hugo.weaving.DebugLog;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by roselyn545 on 29/9/17.
 */

public class InvoiceDetailPurity extends RealmObject implements JsonSerializer<InvoiceDetailPurity>, Serializable , BaseEditableModel {

    @SerializedName("idInvoiceDetailPurity")
    @Expose
    private int id = -1;

    @SerializedName("valueRateInvoiceDetailPurity")
    @Expose
    private float rateValue = 0;

    @PrimaryKey
    private String localId = "";

    private String detailId = "";

    private int purityId = -1;

    private int purityPostLocalId = -1;

    @Ignore
    @SerializedName("purity")
    @Expose
    private Purity purity = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRateValue() {
        return rateValue;
    }

    @DebugLog
    public void setRateValue(float rateValue) {
        this.rateValue = rateValue;
    }

    public Purity getPurity() {
        return purity;
    }

    public void setPurity(Purity purity) {
        this.purity = purity;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public int getPurityId() {
        return purityId;
    }

    public void setPurityId(int purityId) {
        this.purityId = purityId;
    }

    public boolean isSameType(int itemTypeId) {
        return purity != null && purity.getId() == itemTypeId;
    }



    public int getPurityPostLocalId() {
        return purityPostLocalId;
    }

    public void setPurityPostLocalId(int purityPostLocalId) {
        this.purityPostLocalId = purityPostLocalId;
    }

    public InvoiceDetailPurity() {
    }

    public InvoiceDetailPurity(int id, String rateValue, int purityPostLocalId) {
        this.purityId = id;
        this.rateValue = Float.valueOf(rateValue);
        this.purityPostLocalId = purityPostLocalId;
    }

    @DebugLog
    public static InvoiceDetailPurity findInvoiceDetailPurityInListGivenPurityId(final List<InvoiceDetailPurity> list, int id) {

        Log.d("PURITIES", "--->findDetailPurity in list of size " + list.size());

        for (InvoiceDetailPurity detailPurity : list) {
            if (detailPurity != null && detailPurity.isSameType(id)) {
                return detailPurity;
            }
        }
        return null;
    }


    @Override
    public JsonElement serialize(InvoiceDetailPurity src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("idInvoiceDetailPurity", src.getId());
        jsonObject.addProperty("valueRateInvoiceDetailPurity", src.getRateValue());
        jsonObject.addProperty("localId", src.getLocalId());
        jsonObject.addProperty("detailId", src.getDetailId());

        return jsonObject;
    }

    @Override
    public String toString() {
        return "InvoiceDetailPurity{" +
                "id=" + id +
                ", rateValue=" + rateValue +
                ", localId='" + localId + '\'' +
                ", detailId='" + detailId + '\'' +
                ", purityId=" + purityId +
                ", purityPostLocalId=" + purityPostLocalId +
                ", purity=" + purity +
                '}';
    }

    @Override
    public String getReadableDescription() {
        return  "";
    }

    @Override
    public String getInputValue() {
        return String.valueOf(rateValue);
    }

    @Override
    public void setInputValue(String value) {
        rateValue = Float.valueOf(value);
    }
}
