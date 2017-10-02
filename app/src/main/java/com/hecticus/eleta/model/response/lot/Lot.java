package com.hecticus.eleta.model.response.lot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model.ItemSpinnerInterface;
import com.hecticus.eleta.model.response.farm.Farm;

import java.io.Serializable;

/**
 * Created by roselyn545 on 25/9/17.
 */

public class Lot  extends ItemSpinnerInterface implements Serializable {

    @SerializedName("idLot")
    @Expose
    private int id = -1;

    @SerializedName("nameLot")
    @Expose
    private String name = "";

    @SerializedName("price_lot")
    @Expose
    private float price = -1;

    @SerializedName("farm")
    @Expose
    private Farm farm = null;


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

    @Override
    public String toString() {
        return name;
    }
}
