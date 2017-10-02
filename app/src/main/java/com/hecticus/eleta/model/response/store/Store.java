package com.hecticus.eleta.model.response.store;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model.ItemSpinnerInterface;

import java.io.Serializable;

/**
 * Created by roselyn545 on 26/9/17.
 */

public class Store extends ItemSpinnerInterface implements Serializable {

    @SerializedName("idStore")
    @Expose
    private int id = -1;

    @SerializedName("nameStore")
    @Expose
    private String name = "";

    @SerializedName("statusDelete")
    @Expose
    private int deleteStatus = -1;

    @SerializedName("statusStore")
    @Expose
    private int status = -1;

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

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getItemReadableDescription() {
        return name;
    }

    @Override
    public int getItemId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
