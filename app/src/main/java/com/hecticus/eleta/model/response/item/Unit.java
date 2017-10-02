package com.hecticus.eleta.model.response.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by roselyn545 on 25/9/17.
 */

public class Unit implements Serializable {

    @SerializedName("statusDelete")
    @Expose
    private int deleteStatus = -1;

    @SerializedName("idUnit")
    @Expose
    private int id = -1;

    @SerializedName("nameUnit")
    @Expose
    private String name = "";

    @SerializedName("statusUnit")
    @Expose
    private int status = -1;

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
