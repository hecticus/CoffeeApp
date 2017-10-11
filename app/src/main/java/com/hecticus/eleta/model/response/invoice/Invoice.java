package com.hecticus.eleta.model.response.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.base.BaseModel;
import com.hecticus.eleta.model.response.providers.Provider;

import java.io.Serializable;
import java.util.List;

/**
 * Created by roselyn545 on 18/9/17.
 */

public class Invoice  extends BaseModel implements Serializable {

    @SerializedName("idInvoice")
    @Expose
    private int invoiceId = -1;

    @SerializedName("provider")
    @Expose
    private Provider provider;

    @SerializedName("statusInvoice")
    @Expose
    private int invoiceStatus =-1;

    @SerializedName("startDateInvoice")
    @Expose
    private String invoiceStartDate;

    @SerializedName("closedDateInvoice")
    @Expose
    private String invoiceClosedDate;

    @SerializedName("totalInvoice")
    @Expose
    private float invoiceTotal =-1;


    @Override
    public String getReadableDescription() {
        if (provider!=null){
            return provider.getFullNameProvider();
        }
        return "";
    }

    @Override
    public boolean canDelete() {
        return invoiceStatus<3;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public int getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(int invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getInvoiceStartDate() {
        return invoiceStartDate;
    }

    public void setInvoiceStartDate(String invoiceStartDate) {
        this.invoiceStartDate = invoiceStartDate;
    }

    public String getInvoiceClosedDate() {
        return invoiceClosedDate;
    }

    public void setInvoiceClosedDate(String invoiceClosedDate) {
        this.invoiceClosedDate = invoiceClosedDate;
    }

    public float getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(float invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }
}
