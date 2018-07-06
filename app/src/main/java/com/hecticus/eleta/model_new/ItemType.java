package com.hecticus.eleta.model_new;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityReference(alwaysAsId = true)
public class ItemType {

    private Long id;
    private Boolean deleted;
    private String createdAt;
    private String updatedAt;
    private String nameItemType;
    private Double costItemType;
    private Unit unit;

    public ItemType() {
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

    public String getNameItemType() {
        return nameItemType;
    }

    public void setNameItemType(String nameItemType) {
        this.nameItemType = nameItemType;
    }

    public Double getCostItemType() {
        return costItemType;
    }

    public void setCostItemType(Double costItemType) {
        this.costItemType = costItemType;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
