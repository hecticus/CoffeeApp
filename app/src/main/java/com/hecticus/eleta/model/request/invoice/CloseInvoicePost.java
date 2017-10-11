package com.hecticus.eleta.model.request.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by roselyn545 on 2/10/17.
 */

public class CloseInvoicePost {

    @SerializedName("idInvoice")
    @Expose
    private int id = -1;

    @SerializedName("closedDate")
    @Expose
    private String closedDate = "";

    @SerializedName("status")
    @Expose
    private int status = 3;

    public CloseInvoicePost(int id, String closedDate){
        this.id=id;
        this.closedDate=closedDate;
    }

    public CloseInvoicePost(int id, String closedDate,int status){
        this(id,closedDate);
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(String closedDate) {
        this.closedDate = closedDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
