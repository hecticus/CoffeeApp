package com.hecticus.eleta.model.response.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model.response.Pager;
import com.hecticus.eleta.model.response.lot.Lot;

import java.util.List;

/**
 * Created by roselyn545 on 25/9/17.
 */

public class ItemTypesListResponse {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private List<ItemType> result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ItemType> getResult() {
        return result;
    }

    public void setResult(List<ItemType> result) {
        this.result = result;
    }
}
