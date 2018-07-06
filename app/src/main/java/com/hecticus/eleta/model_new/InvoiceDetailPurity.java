package com.hecticus.eleta.model_new;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityReference(alwaysAsId = true)
public class InvoiceDetailPurity {

    private Long id;
    private Boolean deleted;
    private String createdAt;
    private String updatedAt;
    private Integer valueRateInvoiceDetailPurity;
    private Integer discountRatePurity;
    private Integer totalDiscountPurity;
    private Purity purity;

    public InvoiceDetailPurity() {
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

    public Integer getValueRateInvoiceDetailPurity() {
        return valueRateInvoiceDetailPurity;
    }

    public void setValueRateInvoiceDetailPurity(Integer valueRateInvoiceDetailPurity) {
        this.valueRateInvoiceDetailPurity = valueRateInvoiceDetailPurity;
    }

    public Integer getDiscountRatePurity() {
        return discountRatePurity;
    }

    public void setDiscountRatePurity(Integer discountRatePurity) {
        this.discountRatePurity = discountRatePurity;
    }

    public Integer getTotalDiscountPurity() {
        return totalDiscountPurity;
    }

    public void setTotalDiscountPurity(Integer totalDiscountPurity) {
        this.totalDiscountPurity = totalDiscountPurity;
    }

    public Purity getPurity() {
        return purity;
    }

    public void setPurity(Purity purity) {
        this.purity = purity;
    }
}
