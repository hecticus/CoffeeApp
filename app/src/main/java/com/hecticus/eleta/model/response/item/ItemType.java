package com.hecticus.eleta.model.response.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.base.BaseEditableModel;
import com.hecticus.eleta.model_new.ItemSpinnerInterface;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by roselyn545 on 25/9/17.
 */

public class ItemType extends RealmObject implements ItemSpinnerInterface, Serializable, BaseEditableModel {

    @SerializedName("statusDelete")
    @Expose
    private int deleteStatus = -1;

    @PrimaryKey
    @SerializedName("idItemType")
    @Expose
    private int id = -1;

    @SerializedName("nameItemType")
    @Expose
    private String name = "";

    @SerializedName("costItemType")
    @Expose
    private float cost = 0;

    @SerializedName("statusItemType")
    @Expose
    private int status = -1;

    private String weightString = "";

    @Ignore
    @SerializedName("unit")
    @Expose
    private Unit unit = null;

    private String unitName = "";
    private int providerType = -1;

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

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWeightString() {
        return weightString;
    }

    public void setWeightString(String weightString) {
        this.weightString = weightString;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getProviderType() {
        return providerType;
    }

    public void setProviderType(int providerType) {
        this.providerType = providerType;
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

    @Override
    public String getReadableDescription() {
        return name;
    }

    @Override
    public String getInputValue() {
        return weightString;
    }

    @Override
    public void setInputValue(String value) {
        weightString = value;
    }
}
