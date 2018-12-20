package com.hecticus.eleta.model_new;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.response.StatusInvoice;

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
    private String closedDate;
    private Boolean buyOption;
    private StatusInvoice statusInvoice;
    private List<InvoiceDetail> itemtypes;
    private String startDate;
    private Provider provider;

    public Invoice() {
    }

    public Invoice(Long id) {
        this.id = id;
    }

    public Invoice(InvoicePost invoicePost, /*com.hecticus.eleta.model.response.providers.Provider provider*/ long id) {
        //this.id = (long) invoicePost.getInvoiceId();
        this.buyOption = invoicePost.getBuyOption();
        this.provider = new Provider(id);//provider.getIdProvider().longValue());//Provider(provider, invoicePost.getProviderId());
        this.startDate = invoicePost.getStartDate();
        Log.d("DEBUG", "currentDate invoice"+startDate);
        this.itemtypes = new ArrayList<>();
        for(int i=0; i<invoicePost.getItems().size(); i++ ) {
            itemtypes.add(new InvoiceDetail(invoicePost.getItems().get(i), invoicePost));
        }
    }

    public Invoice(com.hecticus.eleta.model.response.invoice.Invoice invoicePost, com.hecticus.eleta.model.response.providers.Provider provider, StatusInvoice statusInvoice) {
        this.id = (long) invoicePost.getInvoiceId();
        this.statusInvoice = statusInvoice;
        this.provider = new Provider(invoicePost.getProviderId().longValue());
    }

    public Invoice(com.hecticus.eleta.model.response.invoice.Invoice invoicePost, String closedDate, com.hecticus.eleta.model.response.providers.Provider provider, StatusInvoice statusInvoice) {
        this.id = (long) invoicePost.getInvoiceId();
        this.statusInvoice = statusInvoice;
        this.provider = new Provider(invoicePost.getProviderId().longValue());
        this.closedDate = closedDate;
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

    public String getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(String closedDateInvoice) {
        this.closedDate = closedDateInvoice;
    }

    public Boolean getBuyOption() {
        return buyOption;
    }

    public void setBuyOption(Boolean buyOption) {
        this.buyOption = buyOption;
    }

    public List<InvoiceDetail> getItemtypes() {
        return itemtypes;
    }

    public void setItemtypes(List<InvoiceDetail> itemtypes) {
        this.itemtypes = itemtypes;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public StatusInvoice getStatusInvoice() {
        return statusInvoice;
    }

    public void setStatusInvoice(StatusInvoice statusInvoice) {
        this.statusInvoice = statusInvoice;
    }
}
