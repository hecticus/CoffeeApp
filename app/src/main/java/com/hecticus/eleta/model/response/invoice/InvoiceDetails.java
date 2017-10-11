package com.hecticus.eleta.model.response.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model.response.item.ItemType;
import com.hecticus.eleta.model.response.lot.Lot;
import com.hecticus.eleta.model.response.store.Store;

import java.io.Serializable;
import java.util.List;

/**
 * Created by roselyn545 on 26/9/17.
 */

public class InvoiceDetails implements Serializable {
    @SerializedName("idInvoiceDetail")
    @Expose
    private int id = -1;

    @SerializedName("statusDelete")
    @Expose
    private int deleteStatus = -1;

    @SerializedName("invoice")
    @Expose
    private Invoice invoice = null;

    @SerializedName("itemType")
    @Expose
    private ItemType itemType = null;

    @SerializedName("invoiceDetailPurity")
    @Expose
    private List<InvoiceDetailPurity> detailPurities = null;

    @SerializedName("lot")
    @Expose
    private Lot lot = null;

    @SerializedName("store")
    @Expose
    private Store store = null;

    @SerializedName("priceItemTypeByLot")
    @Expose
    private float priceByLot =-1;

    @SerializedName("costItemType")
    @Expose
    private float priceItem =-1;

    @SerializedName("startDateInvoiceDetail")
    @Expose
    private String startDate = "";

    @SerializedName("amountInvoiceDetail")
    @Expose
    private float amount =-1;

    @SerializedName("freightInvoiceDetail")
    @Expose
    private boolean freight = false;

    @SerializedName("noteInvoiceDetail")
    @Expose
    private String observation = "";

    @SerializedName("nameReceivedInvoiceDetail")
    @Expose
    private String receiverName = "";

    @SerializedName("nameDeliveredInvoiceDetail")
    @Expose
    private String dispatcherName ="";

    @SerializedName("statusInvoiceDetail")
    @Expose
    private int status = -1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public float getPriceByLot() {
        return priceByLot;
    }

    public void setPriceByLot(float priceByLot) {
        this.priceByLot = priceByLot;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public boolean isFreight() {
        return freight;
    }

    public void setFreight(boolean freight) {
        this.freight = freight;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getPriceItem() {
        return priceItem;
    }

    public void setPriceItem(float priceItem) {
        this.priceItem = priceItem;
    }

    public List<InvoiceDetailPurity> getDetailPurities() {
        return detailPurities;
    }

    public void setDetailPurities(List<InvoiceDetailPurity> detailPurities) {
        this.detailPurities = detailPurities;
    }

    public boolean isSameType(int itemTypeId) {
        if (getItemType()!=null && getItemType().getId() == itemTypeId) {
            return true;
        }
        return false;
    }

    public static InvoiceDetails findItem(final List<InvoiceDetails> list, int id){
        for(InvoiceDetails detail : list) {
            if(detail != null && detail.isSameType(id)) {
                return detail;
            }
        }
        return null;
    }
}
