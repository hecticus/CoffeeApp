package com.hecticus.eleta.model_new;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

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

    public Invoice() {
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
}
