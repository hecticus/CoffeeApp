package com.hecticus.eleta.model_new;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model_new.persistence.ManagerDB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityReference(alwaysAsId = true)
public class Invoice implements Serializable {

    private Long id;
    private Boolean deleted;
    private String createdAt;
    private String updatedAt;
    private Double totalInvoice;
    private String closedDateInvoice;
    private List<InvoiceDetail> invoiceDetails;
    private String startDateInvoice;
    private com.hecticus.eleta.model.response.providers.Provider provider;



    public Invoice(InvoicePost invoicePost) {
        //this.id = (long) invoicePost.getInvoiceId();
        this.provider = ManagerDB.getProviderById(invoicePost.getProviderId());
        this.startDateInvoice = invoicePost.getStartDate();
        //this.invoiceDetails = new ArrayList<>();
        /*for(int i=0; i<invoicePost.getItems().size(); i++ ) {
            invoiceDetails.add(new InvoiceDetail(invoicePost.getItems().get(i), invoicePost));
        }*/
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Double getTotalInvoice() {
        return totalInvoice;
    }

    public void setTotalInvoice(Double totalInvoice) {
        this.totalInvoice = totalInvoice;
    }

    public String getClosedDateInvoice() {
        return closedDateInvoice;
    }

    public void setClosedDateInvoice(String closedDateInvoice) {
        this.closedDateInvoice = closedDateInvoice;
    }

    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public String getStartDateInvoice() {
        return startDateInvoice;
    }

    public void setStartDateInvoice(String startDateInvoice) {
        this.startDateInvoice = startDateInvoice;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
