
package com.hecticus.eleta.model.response.providers;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model.response.Pager;

public class ProvidersListResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<Provider> result = null;
    @SerializedName("pager")
    @Expose
    private Pager pager;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Provider> getResult() {
        return result;
    }

    public void setResult(List<Provider> result) {
        this.result = result;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

}
