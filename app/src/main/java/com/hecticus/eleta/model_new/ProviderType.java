package com.hecticus.eleta.model_new;

import java.util.List;

public class ProviderType {

    private Long id;
    private Boolean deleted;
    private String createdAt;
    private String updatedA;
    private String nameProviderType;
    private List<Provider> providers;
    private List<ItemType> itemTypes;

    public ProviderType() {
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

    public String getUpdatedA() {
        return updatedA;
    }

    public void setUpdatedA(String updatedA) {
        this.updatedA = updatedA;
    }

    public String getNameProviderType() {
        return nameProviderType;
    }

    public void setNameProviderType(String nameProviderType) {
        this.nameProviderType = nameProviderType;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    public List<ItemType> getItemTypes() {
        return itemTypes;
    }

    public void setItemTypes(List<ItemType> itemTypes) {
        this.itemTypes = itemTypes;
    }
}
