package com.hecticus.eleta.model.response.invoice;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.base.BaseModel;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.response.StatusInvoice;
import com.hecticus.eleta.model.response.providers.Provider;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import hugo.weaving.DebugLog;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by roselyn545 on 18/9/17.
 */

public class Invoice extends RealmObject implements BaseModel, JsonSerializer<Invoice> {

    @SerializedName("id")
    @Expose
    private int id = -1;

    @SerializedName("statusInvo")
    @Expose
    private String statusInvo = "Abierta";

    @Ignore
    @SerializedName("provider")
    @Expose
    private Provider provider;

    @SerializedName("statusInvoice")
    @Expose
    private StatusInvoice invoiceStatus;

    @SerializedName("startDateInvoice")//("createdAt")
    @Expose
    private String invoiceStartDate;

    @SerializedName("closedDateInvoice")
    @Expose
    private String invoiceClosedDate;

    @SerializedName("totalInvoice")
    @Expose
    private float invoiceTotal = -1;
    
    private int type = -1;

    private String providerName = "";

    private String identificationDocProvider = "";

    private Integer providerId = -1;

    private int localId = -1;

    @PrimaryKey
    private String id2 = "";

    private String date = "";

    private boolean addOffline;
    private boolean deleteOffline;
    private boolean editOffline;
    private boolean isClosed;

    public Invoice() {

    }

    public Invoice(int id) {
        this.id= id;
    }

    public Invoice(InvoicePost invoicePost) {
        invoiceStatus = new StatusInvoice(11, false, "Abierta", null);
        invoiceStartDate = invoicePost.getStartDate();
        //invoiceClosedDate = invoicePost.getStartDate();
        invoiceTotal = invoicePost.getTotal();
        type = invoicePost.getType();
        providerName = invoicePost.getProviderName();
        identificationDocProvider = invoicePost.getIdentificationDocProvider();
        providerId = invoicePost.getProviderId();
        date = invoiceStartDate.split(" ")[0];
    }

    public String getStatusInvo() {
        return statusInvo;
    }

    public void setStatusInvo(String statusInvo) {
        this.statusInvo = statusInvo;
    }

    @Override
    public String getReadableDescription() {
        if (provider != null) {
            return provider.getFullNameProvider();
        }
        return providerName;
    }

    @Override
    public boolean canDelete() {
        //Log.d("DEBUGGGGGGGG", String.valueOf(!getInvoiceStatus().getDescription().equals("Closed")));
        if(/*getInvoiceStatus().getDescription()*/getStatusInvo().equals("Cerrada")){
            return false;
        }else{
            return true; //invoiceStatus < 3; todo nose
        }

    }

    public int getInvoiceId() {
        return id;
    }

    public void setInvoiceId(int idInvoice) {
        this.id = idInvoice;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public StatusInvoice getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(StatusInvoice invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getInvoiceStartDate() {

        TimeZone tz= Calendar.getInstance().getTimeZone();
        Log.d("TIMEZONE", tz.toString());
        Log.d("TIMEZONE1", tz.getDisplayName());
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getIdentificationDocProvider() {
        return identificationDocProvider;
    }

    public void setIdentificationDocProvider(String identificationDocProvider) {
        this.identificationDocProvider = identificationDocProvider;
    }

    public boolean isAddOffline() {
        return addOffline;
    }

    public void setAddOffline(boolean addOffline) {
        this.addOffline = addOffline;
    }

    public boolean isDeleteOffline() {
        return deleteOffline;
    }

    public void setDeleteOffline(boolean deleteOffline) {
        this.deleteOffline = deleteOffline;
    }

    public boolean isEditOffline() {
        return editOffline;
    }

    @DebugLog
    public void setEditOffline(boolean editOffline) {
        this.editOffline = editOffline;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getBestAvailableProviderName() {
        if (provider != null && provider.getFullNameProvider() != null)
            return provider.getFullNameProvider();
        else
            return providerName;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + id +
                ", provider=" + provider +
                //", invoiceStatus=" + invoiceStatus +
                ", invoiceStartDate='" + invoiceStartDate + '\'' +
                ", invoiceClosedDate='" + invoiceClosedDate + '\'' +
                ", invoiceTotal=" + invoiceTotal +
                ", type=" + type +
                ", providerName='" + providerName + '\'' +
                ", identificationDocProvider='" + identificationDocProvider + '\'' +
                ", providerId=" + providerId +
                ", localId=" + localId +
                ", id='" + id2 + '\'' +
                ", date='" + date + '\'' +
                ", addOffline=" + addOffline +
                ", deleteOffline=" + deleteOffline +
                ", editOffline=" + editOffline +
                ", isClosed=" + isClosed +
                '}';
    }

    @Override
    public JsonElement serialize(Invoice src, Type typeOfSrc, JsonSerializationContext context) {
        /*final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("invoiceId", src.getInvoiceId());
        jsonObject.addProperty("statusInvo", src.getStatusInvo());
        //jsonObject.addProperty("statusInvoice", src.getInvoiceStatus());
        jsonObject.addProperty("identificationDocProvider", src.getIdentificationDocProvider());
        jsonObject.addProperty("startDateInvoice", src.getInvoiceStartDate());
        jsonObject.addProperty("closedDateInvoice", src.getInvoiceClosedDate());
        jsonObject.addProperty("totalInvoice", src.getInvoiceTotal());
        jsonObject.addProperty("type", src.getType());
        jsonObject.addProperty("providerName", src.getProviderName());
        jsonObject.addProperty("providerId", src.getProviderId());
        jsonObject.addProperty("addOffline", src.isAddOffline());
        jsonObject.addProperty("deleteOffline", src.isDeleteOffline());
        jsonObject.addProperty("editOffline", src.isEditOffline());
        jsonObject.addProperty("localId", src.getLocalId());
        jsonObject.addProperty("date", src.getDate());
        jsonObject.addProperty("isClosed", src.isClosed());
        jsonObject.addProperty("id", src.getId2());

        return jsonObject;*/
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("idInvoice", src.getInvoiceId());
        jsonObject.addProperty("statusInvo", src.getStatusInvo());
        //jsonObject.addProperty("statusInvoice", src.getInvoiceStatus());
        jsonObject.addProperty("identificationDocProvider", src.getIdentificationDocProvider());
        jsonObject.addProperty("startDateInvoice", src.getInvoiceStartDate());
        jsonObject.addProperty("closedDateInvoice", src.getInvoiceClosedDate());
        jsonObject.addProperty("totalInvoice", src.getInvoiceTotal());
        jsonObject.addProperty("type", src.getType());
        jsonObject.addProperty("providerName", src.getProviderName());
        jsonObject.addProperty("providerId", src.getProviderId());
        jsonObject.addProperty("addOffline", src.isAddOffline());
        jsonObject.addProperty("deleteOffline", src.isDeleteOffline());
        jsonObject.addProperty("editOffline", src.isEditOffline());
        jsonObject.addProperty("localId", src.getLocalId());
        jsonObject.addProperty("date", src.getDate());
        jsonObject.addProperty("isClosed", src.isClosed());
        jsonObject.addProperty("id2", src.getId2());

        return jsonObject;
    }

}

