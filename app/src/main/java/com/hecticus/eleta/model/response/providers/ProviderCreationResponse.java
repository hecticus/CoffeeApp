package com.hecticus.eleta.model.response.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model.response.providers.Provider;

/**
 * Created by Edwin on 2017-09-17.
 */

public class ProviderCreationResponse {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private Provider provider;

    @SerializedName("error")
    @Expose
    private String error;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ProviderCreationResponse{" +
                "message='" + message + '\'' +
                ", provider=" + provider +
                ", error='" + error + '\'' +
                '}';
    }
}
