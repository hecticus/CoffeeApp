package com.hecticus.eleta.model;

import com.hecticus.eleta.model.response.item.ItemType;

import java.util.List;

/**
 * Created by roselyn545 on 22/9/17.
 */

public class PurchaseModel {

    private int storeId = -1;
    private boolean selectedFreight = false;
    private String observations = "";

    private List<ItemType> items;

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public boolean isSelectedFreight() {
        return selectedFreight;
    }

    public void setSelectedFreight(boolean selectedFreight) {
        this.selectedFreight = selectedFreight;
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
