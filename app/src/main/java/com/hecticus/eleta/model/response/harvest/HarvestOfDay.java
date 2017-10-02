package com.hecticus.eleta.model.response.harvest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.base.BaseDetailModel;
import com.hecticus.eleta.model.response.providers.Provider;

/**
 * Created by roselyn545 on 18/9/17.
 */

public class HarvestOfDay extends BaseDetailModel {

    @SerializedName("startDateInvoiceDetail")
    @Expose
    private String startDate = "";

    @SerializedName("amountTotal")
    @Expose
    private float totalAmount = -1;

    private String date = null;
    private String time = null;
    private String dateTime = null;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void initDateTime(){
        String [] dateTimeArray = startDate.split(" ");
        if (dateTimeArray!=null && dateTimeArray.length>=2){
            date = dateTimeArray[0];
            date=date.replace('-','/');
            String timeAux =  dateTimeArray[1];
            if (timeAux.contains(".")){
                time = timeAux.substring(0,timeAux.indexOf('.'));
            } else{
                time = dateTimeArray[1];
            }
            dateTime = dateTimeArray[0]+" "+time;
        }else{
            date = time = dateTime = "";
        }
    }

    @Override
    public String getReadableHeader() {
        if (date==null){
            initDateTime();
        }
        return date;
    }

    @Override
    public String getReadableFirstInfo() {
        if (time==null){
            initDateTime();
        }
        return time;
    }

    @Override
    public String getReadableSecondInfo() {
        return totalAmount+"";
    }
}
