package com.hecticus.eleta.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model.response.item.ItemType;

import java.util.List;

/**
 * Created by roselyn545 on 16/9/17.
 */

public class HarvestModel {

    private int lotId = -1;
    private int farmId = -1;
    private String observations = "";

    private List<ItemType> items;

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public int getFarmId() {
        return farmId;
    }

    public void setFarmId(int farmId) {
        this.farmId = farmId;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public List<ItemType> getItems() {
        return items;
    }

    public void setItems(List<ItemType> items) {
        this.items = items;
    }
}
