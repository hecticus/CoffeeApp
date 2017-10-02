package com.hecticus.eleta.model.response.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Edwin on 2017-09-25.
 */

public class ProviderImageUpdateResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private Result result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public String getUploadedImageUrl() throws Exception {
        return getResult().getUrlPhoto();
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ProviderImageUpdateResponse{" +
                "message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    class Result {

        @SerializedName("urlPhoto")
        @Expose
        private String urlPhoto;

        public String getUrlPhoto() {
            return urlPhoto;
        }

        public void setUrlPhoto(String urlPhoto) {
            this.urlPhoto = urlPhoto;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "urlPhoto='" + urlPhoto + '\'' +
                    '}';
        }
    }
}