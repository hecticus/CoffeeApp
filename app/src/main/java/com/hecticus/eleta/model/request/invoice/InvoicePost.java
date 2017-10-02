package com.hecticus.eleta.model.request.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by roselyn545 on 27/9/17.
 */

public class InvoicePost {

    @SerializedName("idInvoice")
    @Expose
    private int id = -1;

    @SerializedName("idProvider")
    @Expose
    private int providerId = -1;

    @SerializedName("idLot")
    @Expose
    private int lotId = -1;

    @SerializedName("freigh")
    @Expose
    private boolean freigh = true;

    @SerializedName("idStore")
    @Expose
    private int storeId = -1;

    @SerializedName("nameReceivedInvoiceDetail")
    @Expose
    private String receiverName = null;

    @SerializedName("nameDeliveredInvoiceDetail")
    @Expose
    private String dispatcherName = null;

    @SerializedName("startDateInvoiceDetail")
    @Expose
    private String startDate = null;

    @SerializedName("note")
    @Expose
    private String observations = null;

    @SerializedName("buyOption")
    @Expose
    private int buyOption = -1;

    @SerializedName("itemtypes")
    @Expose
    private List<ItemPost> items = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public boolean isFreigh() {
        return freigh;
    }

    public void setFreigh(boolean freigh) {
        this.freigh = freigh;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getDispatcherName() {
        return dispatcherName;
    }

    public void setDispatcherName(String dispatcherName) {
        this.dispatcherName = dispatcherName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public int getBuyOption() {
        return buyOption;
    }

    public void setBuyOption(int buyOption) {
        this.buyOption = buyOption;
    }

    public List<ItemPost> getItems() {
        return items;
    }

    public void setItems(List<ItemPost> items) {
        this.items = items;
    }
}
