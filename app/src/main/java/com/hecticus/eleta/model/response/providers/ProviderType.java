
package com.hecticus.eleta.model.response.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import hugo.weaving.DebugLog;

public class ProviderType implements Serializable {

    @SerializedName("id")//("idProviderType")
    @Expose
    private Integer idProviderType;

    @DebugLog
    public ProviderType(Integer idProviderType) {
        this.idProviderType = idProviderType;
    }

    public Integer getIdProviderType() {
        return idProviderType;
    }

    public void setIdProviderType(Integer idProviderType) {
        this.idProviderType = idProviderType;
    }



    @Override
    public String toString() {
        return "ProviderType{" +
                "idProviderType=" + idProviderType +
                '}';
    }
}
