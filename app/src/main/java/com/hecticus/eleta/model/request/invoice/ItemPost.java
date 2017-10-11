package com.hecticus.eleta.model.request.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model.response.purity.Purity;

import java.util.List;

/**
 * Created by roselyn545 on 27/9/17.
 */

public class ItemPost {

    @SerializedName("idInvoiceDetail")
    @Expose
    private int invoiceDetailId = -1;

    @SerializedName("idItemType")
    @Expose
    private int itemTypeId = -1;

    @SerializedName("amount")
    @Expose
    private float amount = 0;

    @SerializedName("price")
    @Expose
    private float price = 0;

    @SerializedName("purities")
    @Expose
    private List<PurityPost> purities = null;

    @SerializedName("valueRateInvoiceDetailPurity")
    @Expose
    private float rateValue = 0;

    @SerializedName("id_store")
    @Expose
    private int storeId = -1;

    public ItemPost(int itemTypeId, float amount){
        this.itemTypeId = itemTypeId;
        this.amount = amount;
    }

    public ItemPost(int itemTypeId, float amount, int invoiceDetailId){
        this(itemTypeId,amount);
        this.invoiceDetailId = invoiceDetailId;
    }

    public ItemPost(int itemTypeId, float amount, int price, List<PurityPost> purities){
        this(itemTypeId,amount);
        this.price = price;
        this.purities = purities;
    }

    public ItemPost(int itemTypeId, float amount, int price, List<PurityPost> purities, int invoiceDetailId){
        this(itemTypeId,amount, price, purities);
        this.invoiceDetailId = invoiceDetailId;
    }

    public int getInvoiceDetailId() {
        return invoiceDetailId;
    }

    public void setInvoiceDetailId(int invoiceDetailId) {
        this.invoiceDetailId = invoiceDetailId;
    }

    public int getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(int itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getRateValue() {
        return rateValue;
    }

    public void setRateValue(float rateValue) {
        this.rateValue = rateValue;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public List<PurityPost> getPurities() {
        return purities;
    }

    public void setPurities(List<PurityPost> purities) {
        this.purities = purities;
    }
}
