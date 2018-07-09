package com.hecticus.eleta.model.response.farm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model_new.ItemSpinnerInterface;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by roselyn545 on 25/9/17.
 */

public class Farm extends RealmObject implements ItemSpinnerInterface, Serializable {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id = -1;

    @SerializedName("nameFarm")
    @Expose
    private String name = "";

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
