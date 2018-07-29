package com.hecticus.eleta.model.response.invoice;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model.response.StatusInvoice;
import com.hecticus.eleta.model.response.harvest.Harvest;
import com.hecticus.eleta.model.response.harvest.HarvestOfDay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roselyn545 on 26/9/17.
 */

public class InvoiceDetailsResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private List<InvoiceDetails> listInvoiceDetails;

    private Boolean controlLocal = false;

    private List<HarvestOfDay> harvests = new ArrayList<HarvestOfDay>();

    public InvoiceDetailsResponse(){
        setListInvoiceDetails(new ArrayList<InvoiceDetails>());
        setHarvests(new ArrayList<HarvestOfDay>());
    }

    public InvoiceDetailsResponse(List<InvoiceDetails> listInvoiceDetails) {
        setListInvoiceDetails(listInvoiceDetails);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<InvoiceDetails> getListInvoiceDetails() {
        return listInvoiceDetails;
    }

    public void setListInvoiceDetails(List<InvoiceDetails> listInvoiceDetails) {
        this.listInvoiceDetails = listInvoiceDetails;
    }

    public List<HarvestOfDay> getHarvests(Boolean control) {
        //todo crash
        if(control) {
            if (!controlLocal) {
                for (final InvoiceDetails invoiceDetail : listInvoiceDetails) {
                    Log.d("DEBUG total", String.valueOf(invoiceDetail.getTotalInvoiceDetail()));
                    harvests.add(new HarvestOfDay(invoiceDetail.getStartDate(), invoiceDetail.getTotalInvoiceDetail(), invoiceDetail.getId()));
                }
                controlLocal = true;
            }
        }
        return harvests;
    }

    public void setHarvests(List<HarvestOfDay> harvests) {
        this.harvests = harvests;
    }

    /*
    for(final InvoiceDetails invoiceDetail : listInvoiceDetails){
            harvests.add(new HarvestOfDay(invoiceDetail.getInvoice().getInvoiceStartDate(), invoiceDetail.getInvoice().getInvoiceTotal()));
    }
    */

    /*@SerializedName("result")
    @Expose
    @JsonIgnore
    private Result result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<InvoiceDetails> getDetails() {
        return result.getDetails();
    }

    public List<HarvestOfDay> getHarvests() {
        return result.getHarvests();
    }

    public void setHarvestsList(List<HarvestOfDay> harvestsList){
        result.setHarvests(harvestsList);
    }

    public void setDetailsList(List<InvoiceDetails> invoiceDetailsList){
        result.setDetails(invoiceDetailsList);
    }

    public InvoiceDetailsResponse(){
        result = new Result();
        result.setDetails(new ArrayList<InvoiceDetails>());
        result.setHarvests(new ArrayList<HarvestOfDay>());
    }

    public InvoiceDetailsResponse(List<InvoiceDetails> detailsList){
        Result result = new Result();
        result.setDetails(detailsList);
        setResult(result);
    }

    class Result {

        @SerializedName("summary")
        @Expose
        private List<HarvestOfDay> harvests = null;

        @SerializedName("deatils")
        @Expose
        private List<InvoiceDetails> details = null;

        public List<InvoiceDetails> getDetails() {
            return details;
        }

        public void setDetails(List<InvoiceDetails> details) {
            this.details = details;
        }

        public List<HarvestOfDay> getHarvests() {
            return harvests;
        }

        public void setHarvests(List<HarvestOfDay> harvests) {
            this.harvests = harvests;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "harvests=" + harvests +
                    ", details=" + details +
                    '}';
        }
    }*/

    /*@Override
    public String toString() {
        return "InvoiceDetailsResponse{" +
                "message='" + message + '\'' +
                ", result=" + result +
                '}';
    }*/
}

