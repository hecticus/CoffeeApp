package com.hecticus.eleta.model.request.invoice;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.List;

import hugo.weaving.DebugLog;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by roselyn545 on 27/9/17.
 */

public class InvoicePost extends RealmObject implements JsonSerializer<InvoicePost> {

    @PrimaryKey
    private int invoicePostLocalId = -1;
    private String invoiceLocalId = "";

    @SerializedName("idInvoice")
    @Expose
    private int invoiceId = -1;

    @SerializedName("idProvider")
    @Expose
    private Integer providerId = -1;

    @SerializedName("idLot")
    @Expose
    private int lotId = -1;

    @SerializedName("freigh")
    @Expose
    private boolean freigh = true;

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

    private int type = -1;
    private String date = "";
    private String providerName = "";

    @SerializedName("identificationDocProvider")
    @Expose
    private String identificationDocProvider;

    private float priceByLot = 0;
    private float total = 0;
    private boolean isClosed = false;

    @Ignore
    @SerializedName("itemtypes")
    @Expose
    private List<ItemPost> items = null;

    public int getInvoicePostLocalId() {
        return invoicePostLocalId;
    }

    public void setInvoicePostLocalId(int invoicePostLocalId) {
        this.invoicePostLocalId = invoicePostLocalId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
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

    @DebugLog
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    @DebugLog
    public void setDate(String date) {
        this.date = date;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getIdentificationDocProvider() {
        return identificationDocProvider;
    }

    public void setIdentificationDocProvider(String identificationDocProvider) {
        this.identificationDocProvider = identificationDocProvider;
    }

    public float getPriceByLot() {
        return priceByLot;
    }

    public void setPriceByLot(float priceByLot) {
        this.priceByLot = priceByLot;
    }

    public float getTotal() {
        return total;
    }

    @DebugLog
    public void setTotal(float total) {
        this.total = total;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public String getInvoiceLocalId() {
        return invoiceLocalId;
    }

    public void setInvoiceLocalId(String invoiceLocalId) {
        this.invoiceLocalId = invoiceLocalId;
    }

    @Override
    public String toString() {
        return "InvoicePost{" +
                "invoicePostLocalId=" + invoicePostLocalId +
                ", invoiceLocalId='" + invoiceLocalId + '\'' +
                ", invoiceId=" + invoiceId +
                ", providerId=" + providerId +
                ", lotId=" + lotId +
                ", freigh=" + freigh +
                ", receiverName='" + receiverName + '\'' +
                ", dispatcherName='" + dispatcherName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", observations='" + observations + '\'' +
                ", buyOption=" + buyOption +
                ", type=" + type +
                ", date='" + date + '\'' +
                ", providerName='" + providerName + '\'' +
                ", identificationDocProvider='" + identificationDocProvider + '\'' +
                ", priceByLot=" + priceByLot +
                ", total=" + total +
                ", isClosed=" + isClosed +
                ", items=" + items +
                '}';
    }

    @Override
    public JsonElement serialize(InvoicePost src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("idInvoice", src.getInvoiceId());
        jsonObject.addProperty("idProvider", src.getProviderId());
        jsonObject.addProperty("identificationDocProvider", src.getIdentificationDocProvider());
        jsonObject.addProperty("idLot", src.getLotId());
        jsonObject.addProperty("freigh", src.isFreigh());
        jsonObject.addProperty("nameReceivedInvoiceDetail", src.getReceiverName());
        jsonObject.addProperty("nameDeliveredInvoiceDetail", src.getDispatcherName());
        jsonObject.addProperty("startDateInvoiceDetail", src.getStartDate());
        jsonObject.addProperty("note", src.getObservations());
        jsonObject.addProperty("buyOption", src.getBuyOption());
        //jsonObject.addProperty("itemtypes", src.getItems());

        return jsonObject;
    }
}
