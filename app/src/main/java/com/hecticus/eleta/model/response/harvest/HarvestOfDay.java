package com.hecticus.eleta.model.response.harvest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.base.BaseDetailModel;

import hugo.weaving.DebugLog;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by roselyn545 on 18/9/17.
 */

public class HarvestOfDay extends RealmObject implements BaseDetailModel {

    @SerializedName("startDateInvoiceDetail")
    @Expose
    private String startDate = "";

    @SerializedName("amountTotal")
    @Expose
    private float totalAmount = -1;

    @Ignore
    private String date = null;

    @Ignore
    private String time = null;

    @Ignore
    private String dateTime = null;

    @PrimaryKey
    private String id = "";
    private int invoiceId = -1;

    //Remember to update toString if uncommented
    //private String invoiceWholeId = "";

    private boolean addOffline;
    private boolean deleteOffline;
    private boolean editOffline;

    public HarvestOfDay(String startDate, float totalAmount) {
        this.startDate = startDate;
        this.totalAmount = totalAmount;
    }

    public HarvestOfDay() {
    }

    @DebugLog
    public String getStartDate() {
        return startDate;
    }

    @DebugLog
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @DebugLog
    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    @DebugLog
    public String getDateTime() {
        if (dateTime == null)
            initDateTime();
        return dateTime;
    }

    @DebugLog
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getId() {
        return id;
    }

    @DebugLog
    public void setId(String id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    /*public String getInvoiceWholeId() {
        return invoiceWholeId;
    }

    public void setInvoiceWholeId(String invoiceWholeId) {
        this.invoiceWholeId = invoiceWholeId;
    }*/

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
        return totalAmount + "";
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

    public void setEditOffline(boolean editOffline) {
        this.editOffline = editOffline;
    }

    @Override
    public String toString() {
        return "HarvestOfDay{" +
                "startDate='" + startDate + '\'' +
                ", totalAmount=" + totalAmount +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", id='" + id + '\'' +
                ", invoiceId=" + invoiceId +
                ", addOffline=" + addOffline +
                ", deleteOffline=" + deleteOffline +
                ", editOffline=" + editOffline +
                '}';
    }
}
