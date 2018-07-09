package com.hecticus.eleta.model.response.store;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model.StatusStore;
import com.hecticus.eleta.model_new.ItemSpinnerInterface;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by roselyn545 on 26/9/17.
 */

public class Store extends RealmObject implements ItemSpinnerInterface, Serializable {

    @PrimaryKey
    @SerializedName("id")
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
    private StatusStore status;

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

    public StatusStore getStatus() {
        return status;
    }

    public void setStatus(StatusStore status) {
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
