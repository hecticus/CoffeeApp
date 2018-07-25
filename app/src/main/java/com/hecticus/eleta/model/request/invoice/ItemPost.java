package com.hecticus.eleta.model.request.invoice;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model.response.purity.Purity;

import java.lang.reflect.Type;
import java.util.List;

import hugo.weaving.DebugLog;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by roselyn545 on 27/9/17.
 */

public class ItemPost extends RealmObject implements JsonSerializer<ItemPost> {

    @SerializedName("idInvoiceDetail")
    @Expose
    private int invoiceDetailId = -1;

    @SerializedName("idItemType")
    @Expose
    private int itemTypeId = -1;

    @SerializedName("amountInvoiceDetail")
    @Expose
    private float amount = 0;

    @SerializedName("price")//("priceItemTypeByLot")
    @Expose
    private float price = 0;

    @Ignore
    @SerializedName("purities")
    @Expose
    private List<PurityPost> purities = null;

    @SerializedName("valueRateInvoiceDetailPurity")
    @Expose
    private float rateValue = 0;

    @SerializedName("idStore")
    @Expose
    private int storeId = -1;

    @PrimaryKey
    private int itemPostLocalId = -1;
    private int invoicePostLocalId = -1;

    public ItemPost(){}

    @DebugLog
    public ItemPost(int itemTypeId, float amount){
        this.itemTypeId = itemTypeId;
        this.amount = amount;
    }

    @DebugLog
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

    public int getItemPostLocalId() {
        return itemPostLocalId;
    }

    public void setItemPostLocalId(int itemPostLocalId) {
        this.itemPostLocalId = itemPostLocalId;
    }

    public int getInvoicePostLocalId() {
        return invoicePostLocalId;
    }

    public void setInvoicePostLocalId(int invoicePostLocalId) {
        this.invoicePostLocalId = invoicePostLocalId;
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

    @Override
    public String toString() {
        return "ItemPost{" +
                "invoiceDetailId=" + invoiceDetailId +
                ", itemTypeId=" + itemTypeId +
                ", amount=" + amount +
                ", price=" + price +
                ", purities=" + purities +
                ", rateValue=" + rateValue +
                ", storeId=" + storeId +
                ", itemPostLocalId=" + itemPostLocalId +
                ", invoicePostLocalId=" + invoicePostLocalId +
                '}';
    }

    @Override
    public JsonElement serialize(ItemPost src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("idInvoiceDetail", src.getInvoiceDetailId());
        jsonObject.addProperty("idItemType", src.getItemTypeId());
        jsonObject.addProperty("amount", src.getAmount());
        jsonObject.addProperty("price", src.getPrice());
        //jsonObject.addProperty("purities", src.getPurities());
        jsonObject.addProperty("valueRateInvoiceDetailPurity", src.getRateValue());
        jsonObject.addProperty("id_store", src.getStoreId());

        return jsonObject;
    }
}
