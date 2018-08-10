package com.hecticus.eleta.model.response.invoice;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.base.BaseDetailModel;
import com.hecticus.eleta.model.StatusInvoiceDetail;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.request.invoice.ItemPost;
import com.hecticus.eleta.model.response.item.ItemType;
import com.hecticus.eleta.model.response.lot.Lot;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.store.Store;
import com.hecticus.eleta.model_new.persistence.ManagerDB;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hugo.weaving.DebugLog;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by roselyn545 on 26/9/17.
 */

public class InvoiceDetails extends RealmObject implements JsonSerializer<InvoiceDetails>, Serializable, Cloneable, BaseDetailModel {

    @SerializedName("id")
    @Expose
    private int id = -1;

    private int localId = -1;

    @PrimaryKey
    private String wholeId = "";

    @SerializedName("statusDelete")
    @Expose
    private int deleteStatus = -1;

    private int invoiceId = -1;

    @Ignore
    @SerializedName("invoice")
    @Expose
    private Invoice invoice = null;

    private int itemTypeId = -1;

    @Ignore
    @SerializedName("itemType")
    @Expose
    private ItemType itemType = null;

    @Ignore
    @SerializedName("invoiceDetailPurity")
    @Expose
    private List<InvoiceDetailPurity> detailPurities = null;

    private int lotId = -1;

    @Ignore
    @SerializedName("lot")
    @Expose
    private Lot lot = null;

    private int storeId = -1;

    @Ignore
    @SerializedName("store")
    @Expose
    private Store store = null;

    @SerializedName("priceItemTypeByLot")//("price")//
    @Expose
    private float priceByLot =-1;

    @SerializedName("costItemType")
    @Expose
    private float priceItem =-1;

    @SerializedName("createdAt")
    @Expose
    private String startDate = "";

    @SerializedName("amountInvoiceDetail")
    @Expose
    private float amount =-1;

    @SerializedName("totalInvoiceDetail")
    @Expose
    private float totalInvoiceDetail =-1;

    @SerializedName("freightInvoiceDetail")
    @Expose
    private boolean freight = false;

    @SerializedName("noteInvoiceDetail")
    @Expose
    private String observation = "";

    @SerializedName("nameReceived")
    @Expose
    private String receiverName = "";

    @SerializedName("nameDelivered")
    @Expose
    private String dispatcherName ="";

    @SerializedName("statusInvoiceDetail")
    @Expose
    private StatusInvoiceDetail status;

    private int itemPostLocalId = -1;

    private boolean addOffline = false;
    private boolean editOffline = false;
    private boolean deleteOffline = false;

    @Ignore
    private String date = null;

    @Ignore
    private String time = null;

    @Ignore
    private String dateTime = null;

    public InvoiceDetails() {}

    public String parseDateToString(Date fecha, String format){
        Format formatter = new SimpleDateFormat(format);
        return formatter.format(fecha);
    }

    public int getItemPostLocalId() {
        return itemPostLocalId;
    }

    public void setItemPostLocalId(int itemPostLocalId) {
        this.itemPostLocalId = itemPostLocalId;
    }

    public InvoiceDetails(ItemPost itemPost, InvoicePost invoicePost) {
        localId = itemPost.getItemPostLocalId();
        itemTypeId = itemPost.getItemTypeId();
        lotId = invoicePost.getLot();
        priceByLot = invoicePost.getPriceByLot();
        startDate = invoicePost.getStartDate();
        amount = itemPost.getAmount();
        observation = invoicePost.getObservations();
        receiverName = invoicePost.getReceiverName();
        dispatcherName = invoicePost.getDispatcherName();
        storeId = itemPost.getStoreId();
        freight = invoicePost.isFreigh();
        priceItem = itemPost.getPrice();
        if(invoicePost.getBuyOption()) {
            totalInvoiceDetail = amount * ManagerDB.getLotById(lotId).getPrice();
        } else {
            totalInvoiceDetail = amount * priceItem;
        }
        addOffline = true;
    }

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public float getTotalInvoiceDetail() {
        return totalInvoiceDetail;
    }

    public void setTotalInvoiceDetail(float totalInvoiceDetail) {
        this.totalInvoiceDetail = totalInvoiceDetail;
    }

    public String getWholeId() {
        return wholeId;
    }

    public void setWholeId(String wholeId) {
        this.wholeId = wholeId;
    }

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

    @DebugLog
    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Lot getLot() {
        return lot;
    }

    @DebugLog
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

    public StatusInvoiceDetail getStatus() {
        return status;
    }

    public void setStatus(StatusInvoiceDetail status) {
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

    public boolean isAddOffline() {
        return addOffline;
    }

    public void setAddOffline(boolean addOffline) {
        this.addOffline = addOffline;
    }

    public boolean isEditOffline() {
        return editOffline;
    }

    public void setEditOffline(boolean editOffline) {
        this.editOffline = editOffline;
    }

    public boolean isDeleteOffline() {
        return deleteOffline;
    }

    public void setDeleteOffline(boolean deleteOffline) {
        this.deleteOffline = deleteOffline;
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

    @DebugLog
    public static InvoiceDetails findItem(final List<InvoiceDetails> list, int id){
        for(InvoiceDetails detail : list) {
            if(detail != null && detail.isSameType(id)) {
                return detail;
            }
        }
        return null;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    @DebugLog
    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(int itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    @Override
    public InvoiceDetails clone() throws CloneNotSupportedException {
        return (InvoiceDetails) super.clone();
    }

    @Override
    public JsonElement serialize(InvoiceDetails src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("localId", src.getLocalId());
        //jsonObject.addProperty("statusDelete", src.getDeleteStatus());
        jsonObject.addProperty("invoiceId", src.getInvoiceId());
        jsonObject.addProperty("itemTypeId", src.getItemTypeId());
        jsonObject.addProperty("lotId", src.getLotId());
        jsonObject.addProperty("wholeId", src.getWholeId());
        jsonObject.addProperty("storeId", src.getStoreId());
        jsonObject.addProperty("priceItemTypeByLot", src.getPriceByLot());
        jsonObject.addProperty("costItemType", src.getPriceItem());
        jsonObject.addProperty("createdAt", src.getStartDate());
        jsonObject.addProperty("amountInvoiceDetail", src.getAmount());
        jsonObject.addProperty("freightInvoiceDetail", src.isFreight());
        jsonObject.addProperty("noteInvoiceDetail", src.getObservation());
        jsonObject.addProperty("nameReceived", src.getReceiverName());
        jsonObject.addProperty("nameDelivered", src.getDispatcherName());
        //jsonObject.addProperty("statusInvoiceDetail", src.getStatus());

        return jsonObject;
    }

    @Override
    public String toString() {
        return "InvoiceDetails{" +
                "id=" + id +
                ", localId=" + localId +
                ", wholeId='" + wholeId + '\'' +
                ", deleteStatus=" + deleteStatus +
                ", invoiceId=" + invoiceId +
                ", invoice=" + invoice +
                ", itemTypeId=" + itemTypeId +
                ", itemType=" + itemType +
                ", detailPurities=" + detailPurities +
                ", lotId=" + lotId +
                ", lot=" + lot +
                ", storeId=" + storeId +
                ", store=" + store +
                ", priceByLot=" + priceByLot +
                ", priceItem=" + priceItem +
                ", startDate='" + startDate + '\'' +
                ", amount=" + amount +
                ", freight=" + freight +
                ", observation='" + observation + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", dispatcherName='" + dispatcherName + '\'' +
                //", status=" + status +
                ", addOffline=" + addOffline +
                ", editOffline=" + editOffline +
                ", deleteOffline=" + deleteOffline +
                '}';
    }

    @Override
    public String getReadableHeader() {
        if (date == null) {
            initDateTime();
        }
        return date;
    }

    @Override
    public String getReadableFirstInfo() {
        if (time == null) {
            initDateTime();
        }
        return time;
    }

    @Override
    public String getReadableSecondInfo() {
        return totalInvoiceDetail + "";
    }

    public void initDateTime() {
        String[] dateTimeArray = startDate.split(" ");
        if (dateTimeArray != null && dateTimeArray.length >= 2) {
            date = dateTimeArray[0];
            date = date.replace('-', '/');
            String timeAux = dateTimeArray[1];
            if (timeAux.contains(".")) {
                time = timeAux.substring(0, timeAux.indexOf('.'));
            } else {
                time = dateTimeArray[1];
            }
            dateTime = dateTimeArray[0] + " " + time;
        } else {
            date = time = dateTime = "";
        }
    }

    public int indexByIdIn(final List<InvoiceDetails> list) {
        for (int i = 0; i < list.size(); i++) {
            InvoiceDetails invoiceDetails = list.get(i);
            if (invoiceDetails != null && invoiceDetails.getId() == getId()) {
                return i;
            }
        }
        return -1;
    }

    /*public void initDateTime() {
        String[] dateTimeArray = startDate.split(" ");
        if (dateTimeArray != null && dateTimeArray.length >= 2) {
            date = dateTimeArray[0];
            date = date.replace('-', '/');
            String timeAux = dateTimeArray[1];
            if (timeAux.contains(".")) {
                time = timeAux.substring(0, timeAux.indexOf('.'));
            } else {
                time = dateTimeArray[1];
            }
            dateTime = dateTimeArray[0] + " " + time;
        } else {
            date = time = dateTime = "";
        }
    }

    @Override
    public String getReadableHeader() {
        if (date == null) {
            initDateTime();
        }
        return date;
    }

    @Override
    public String getReadableFirstInfo() {
        if (time == null) {
            initDateTime();
        }
        return time;
    }*/
}
