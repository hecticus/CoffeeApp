package com.hecticus.eleta.model_new;

public class Unit {

    private Long id;
    private Boolean deleted;
    private String createdAt;
    private String updatedAt;
    private String nameUnit;

    public Unit() {
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

    public String getNameUnit() {
        return nameUnit;
    }

    public void setNameUnit(String nameUnit) {
        this.nameUnit = nameUnit;
    }
}
