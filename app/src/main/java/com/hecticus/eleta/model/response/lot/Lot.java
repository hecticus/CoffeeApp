package com.hecticus.eleta.model.response.lot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model_new.ItemSpinnerInterface;
import com.hecticus.eleta.model.response.farm.Farm;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by roselyn545 on 25/9/17.
 */

public class Lot extends RealmObject implements ItemSpinnerInterface, Serializable {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id = -1;

    @SerializedName("nameLot")
    @Expose
    private String name = "";

    @SerializedName("price_lot")
    @Expose
    private float price = -1;

    @Ignore
    @SerializedName("farm")
    @Expose
    private Farm farm = null;

    private int farmId = -1;


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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    @Override
    public String getItemReadableDescription() {
        return name;
    }

    @Override
    public int getItemId() {
        return id;
    }

    public int getFarmId() {
        return farmId;
    }

    public void setFarmId(int farmId) {
        this.farmId = farmId;
    }

    @Override
    public String toString() {
        return name;
    }
}
