package com.hecticus.eleta.model.response.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by roselyn545 on 9/10/17.
 */

public class ReceiptResponse {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private Result result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Invoice getInvoice() {
        return result.getInvoice();
    }

    public String getCompanyName() {
        return result.getCompanyName();
    }

    public String getInvoiceDescription() {
        return result.getInvoiceDescription();
    }

    public String getInvoiceType() {
        return result.getInvoiceType();
    }

    public String getRUC() {
        return result.getRuc();
    }

    public String getCompanyTelephone() {
        return result.getCompanyTelephone();
    }

    public void setInvoice(Invoice invoiceParam) {
        if (result == null)
            result = new Result();

        result.setInvoice(invoiceParam);
    }

    public void setCompanyName(String companyName) {
        if (result == null)
            result = new Result();

        result.setCompanyName(companyName);
    }

    public void setInvoiceDescription(String invoiceDescription) {
        if (result == null)
            result = new Result();

        result.setInvoiceDescription(invoiceDescription);
    }

    public void setInvoiceType(String invoiceType) {
        if (result == null)
            result = new Result();

        result.setInvoiceType(invoiceType);
    }

    public void setRuc(String ruc) {
        if (result == null)
            result = new Result();

        result.setRuc(ruc);
    }

    public void setCompanyTelephone(String companyTelephone) {
        if (result == null)
            result = new Result();

        result.setCompanyTelephone(companyTelephone);
    }

    @Override
    public String toString() {
        return "ReceiptResponse{" +
                "message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    public class Result {

        @SerializedName("invoice")
        @Expose
        private Invoice invoice = null;

        @SerializedName("nameCompany")
        @Expose
        private String companyName;

        @SerializedName("invoiceDescription")
        @Expose
        private String invoiceDescription;

        @SerializedName("invoiceType")
        @Expose
        private String invoiceType;

        @SerializedName("RUC")
        @Expose
        private String ruc;

        @SerializedName("telephonoCompany")
        @Expose
        private String companyTelephone;

        public Invoice getInvoice() {
            return invoice;
        }

        public void setInvoice(Invoice invoice) {
            this.invoice = invoice;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getInvoiceDescription() {
            return invoiceDescription;
        }

        public void setInvoiceDescription(String invoiceDescription) {
            this.invoiceDescription = invoiceDescription;
        }

        public String getInvoiceType() {
            return invoiceType;
        }

        public void setInvoiceType(String invoiceType) {
            this.invoiceType = invoiceType;
        }

        public String getRuc() {
            return ruc;
        }

        public void setRuc(String ruc) {
            this.ruc = ruc;
        }

        public String getCompanyTelephone() {
            return companyTelephone;
        }

        public void setCompanyTelephone(String companyTelephone) {
            this.companyTelephone = companyTelephone;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "invoice=" + invoice +
                    ", companyName='" + companyName + '\'' +
                    ", invoiceDescription='" + invoiceDescription + '\'' +
                    ", invoiceType='" + invoiceType + '\'' +
                    ", ruc='" + ruc + '\'' +
                    ", companyTelephone='" + companyTelephone + '\'' +
                    '}';
        }
    }

}

