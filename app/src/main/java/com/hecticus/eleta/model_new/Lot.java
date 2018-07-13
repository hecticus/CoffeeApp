package com.hecticus.eleta.model_new;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityReference(alwaysAsId = true)
public class Lot {

    private Long id;
    private Boolean deleted;
    private String createdAt;
    private String updatedAt;
    private String nameLot;
    private String areaLot;
    private Integer heighLot;
    private Integer priceLot;
    private Integer price_lot;
    private Farm farm;

    public Lot() {
    }

    public Lot(Long id) {
        this.id = id;
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

    public String getNameLot() {
        return nameLot;
    }

    public void setNameLot(String nameLot) {
        this.nameLot = nameLot;
    }

    public String getAreaLot() {
        return areaLot;
    }

    public void setAreaLot(String areaLot) {
        this.areaLot = areaLot;
    }

    public Integer getHeighLot() {
        return heighLot;
    }

    public void setHeighLot(Integer heighLot) {
        this.heighLot = heighLot;
    }

    public Integer getPriceLot() {
        return priceLot;
    }

    public void setPriceLot(Integer priceLot) {
        this.priceLot = priceLot;
    }

    public Integer getPrice_lot() {
        return price_lot;
    }

    public void setPrice_lot(Integer price_lot) {
        this.price_lot = price_lot;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }
}
