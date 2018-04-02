package com.hecticus.eleta.model.response.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.model.response.Pager;
import com.hecticus.eleta.model.response.harvest.Harvest;

import java.util.List;

/**
 * Created by roselyn545 on 30/1/18.
 */

public class CreateInvoiceResponse {
        @SerializedName("message")
        @Expose
        private String message;

        @SerializedName("result")
        @Expose
        private Invoice result = null;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Invoice getResult() {
            return result;
        }

        public void setResult(Invoice result) {
            this.result = result;
        }

    @Override
    public String toString() {
        return "CreateInvoiceResponse{" +
                "message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
