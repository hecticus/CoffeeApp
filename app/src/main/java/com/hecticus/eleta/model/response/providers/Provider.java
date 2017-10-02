
package com.hecticus.eleta.model.response.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.base.BaseModel;
import com.hecticus.eleta.util.Constants;

import java.io.Serializable;

import hugo.weaving.DebugLog;

public class Provider extends BaseModel implements Serializable {

    @SerializedName("idProvider")
    @Expose
    private Integer idProvider;
    @SerializedName("statusDelete")
    @Expose
    private Integer statusDelete;
    @SerializedName("identificationDocProvider")
    @Expose
    private String identificationDocProvider;
    @SerializedName("fullNameProvider")
    @Expose
    private String fullNameProvider;
    @SerializedName("addressProvider")
    @Expose
    private String addressProvider;
    @SerializedName("phoneNumberProvider")
    @Expose
    private String phoneNumberProvider;
    @SerializedName("emailProvider")
    @Expose
    private String emailProvider;

    @SerializedName("photoProvider")
    @Expose
    private String photoProvider;

    @SerializedName("providerType")
    @Expose
    private ProviderType providerType;
    @SerializedName("contactNameProvider")
    @Expose
    private String contactNameProvider;
    @SerializedName("statusProvider")
    @Expose
    private Integer statusProvider;

    public String getIdentificationDocProviderChange() {
        return identificationDocProviderChange;
    }

    @DebugLog
    public void setIdentificationDocProviderChange(String identificationDocProviderChange) {
        this.identificationDocProviderChange = identificationDocProviderChange;
    }

    @SerializedName("identificationDocProviderChange")
    @Expose
    private String identificationDocProviderChange;

    @SerializedName("id_ProviderType")
    @Expose
    private Integer idProviderType;

    public Integer getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    public Integer getStatusDelete() {
        return statusDelete;
    }

    public void setStatusDelete(Integer statusDelete) {
        this.statusDelete = statusDelete;
    }

    public String getIdentificationDocProvider() {
        return identificationDocProvider;
    }

    public void setIdentificationDocProvider(String identificationDocProvider) {
        this.identificationDocProvider = identificationDocProvider;
    }

    public String getFullNameProvider() {
        return fullNameProvider;
    }

    public void setFullNameProvider(String fullNameProvider) {
        this.fullNameProvider = fullNameProvider;
    }

    public String getAddressProvider() {
        return addressProvider;
    }

    public void setAddressProvider(String addressProvider) {
        this.addressProvider = addressProvider;
    }

    public String getPhoneNumberProvider() {
        return phoneNumberProvider;
    }

    public void setPhoneNumberProvider(String phoneNumberProvider) {
        this.phoneNumberProvider = phoneNumberProvider;
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

    @DebugLog
    public void setPhotoProvider(String photoProvider) {
        this.photoProvider = photoProvider;
    }

    public ProviderType getProviderType() {
        return providerType;
    }

    public void setProviderType(ProviderType providerType) {
        this.providerType = providerType;
    }

    public String getContactNameProvider() {
        return contactNameProvider;
    }

    public void setContactNameProvider(String contactNameProvider) {
        this.contactNameProvider = contactNameProvider;
    }

    public Integer getStatusProvider() {
        return statusProvider;
    }

    public void setStatusProvider(Integer statusProvider) {
        this.statusProvider = statusProvider;
    }

    @Override
    public String getReadableDescription() {
        return getFullNameProvider();
    }

    public Integer getIdProviderType() {
        return idProviderType;
    }

    public void setIdProviderType(Integer idProviderType) {
        this.idProviderType = idProviderType;
    }

    @Override
    public String toString() {
        return "Provider{" +
                "idProvider=" + idProvider +
                ", statusDelete=" + statusDelete +
                ", identificationDocProvider='" + identificationDocProvider + '\'' +
                ", fullNameProvider='" + fullNameProvider + '\'' +
                ", addressProvider='" + addressProvider + '\'' +
                ", phoneNumberProvider='" + phoneNumberProvider + '\'' +
                ", emailProvider='" + emailProvider + '\'' +
                ", photoProvider='" + photoProvider + '\'' +
                ", providerType=" + providerType +
                ", contactNameProvider='" + contactNameProvider + '\'' +
                ", statusProvider=" + statusProvider +
                ", identificationDocProviderChange='" + identificationDocProviderChange + '\'' +
                ", idProviderType=" + idProviderType +
                '}';
    }

    @DebugLog
    public boolean isHarvester() {
        return (getProviderType().getIdProviderType() == Constants.TYPE_HARVESTER);
    }
}
