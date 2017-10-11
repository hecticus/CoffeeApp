package com.hecticus.eleta.model.response.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model.request.invoice.PurityPost;
import com.hecticus.eleta.model.response.purity.Purity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by roselyn545 on 29/9/17.
 */

public class InvoiceDetailPurity implements Serializable {

    @SerializedName("idInvoiceDetailPurity")
    @Expose
    private int id = -1;

    @SerializedName("valueRateInvoiceDetailPurity")
    @Expose
    private float rateValue = 0;


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

    public void setRateValue(float rateValue) {
        this.rateValue = rateValue;
    }

    public Purity getPurity() {
        return purity;
    }

    public void setPurity(Purity purity) {
        this.purity = purity;
    }

    public boolean isSameType(int itemTypeId) {
        if (purity!=null && purity.getId() == itemTypeId) {
            return true;
        }
        return false;
    }

    public static InvoiceDetailPurity findDetailPurity(final List<InvoiceDetailPurity> list, int id){
        for(InvoiceDetailPurity detailPurity : list) {
            if(detailPurity != null && detailPurity.isSameType(id)) {
                return detailPurity;
            }
        }
        return null;
    }
}
