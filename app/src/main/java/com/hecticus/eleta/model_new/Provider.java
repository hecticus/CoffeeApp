package com.hecticus.eleta.model_new;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityReference(alwaysAsId = true)
public class Provider {

    private Long id;
    private Boolean deleted;
    private String createdAt;
    private String updatedAt;
    private String nitProvider;
    private String nameProvider;
    private String addressProvider;
    private String numberProvider;
    private String emailProvider;
    private String photoProvider;
    private String contactNameProvider;
    private List<Invoice> invoices;

    public Provider() {
    }

    public Provider(com.hecticus.eleta.model.response.providers.Provider provider) {
        this.id = (long) provider.getIdProvider();
        this.nitProvider = provider.getIdentificationDocProvider();
        this.nameProvider = provider.getFullNameProvider();
        this.addressProvider = provider.getAddressProvider();
        this.numberProvider = provider.getPhoneNumberProvider();
        this.emailProvider = provider.getEmailProvider();
        this.contactNameProvider = provider.getContactNameProvider();
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

    public String getNitProvider() {
        return nitProvider;
    }

    public void setNitProvider(String nitProvider) {
        this.nitProvider = nitProvider;
    }

    public String getNameProvider() {
        return nameProvider;
    }

    public void setNameProvider(String nameProvider) {
        this.nameProvider = nameProvider;
    }

    public String getAddressProvider() {
        return addressProvider;
    }

    public void setAddressProvider(String addressProvider) {
        this.addressProvider = addressProvider;
    }

    public String getNumberProvider() {
        return numberProvider;
    }

    public void setNumberProvider(String numberProvider) {
        this.numberProvider = numberProvider;
    }

    public String getEmailProvider() {
        return emailProvider;
    }

    public void setEmailProvider(String emailProvider) {
        this.emailProvider = emailProvider;
    }

    public String getPhotoProvider() {
        return photoProvider;
    }

    public void setPhotoProvider(String photoProvider) {
        this.photoProvider = photoProvider;
    }

    public String getContactNameProvider() {
        return contactNameProvider;
    }

    public void setContactNameProvider(String contactNameProvider) {
        this.contactNameProvider = contactNameProvider;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }
}
