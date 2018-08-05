
package com.hecticus.eleta.model.response.providers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.base.BaseModel;
import com.hecticus.eleta.model.StatusProvider;
import com.hecticus.eleta.model_new.MultimediaProfile;
import com.hecticus.eleta.util.Constants;

import java.lang.reflect.Type;
import java.util.List;

import hugo.weaving.DebugLog;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Provider extends RealmObject implements BaseModel, JsonSerializer<Provider> {

    // Important: Negative ids are used for local storage (before sync). Starting with -1.
    @SerializedName("id")//("idProvider")
    @Expose
    private Integer idProvider;

    private long unixtime = -1;

    @SerializedName("statusDelete")
    @Expose
    private int statusDelete;

    @PrimaryKey
    @SerializedName("nitProvider")//("identificationDocProvider")
    @Expose
    private String identificationDocProvider;

    @SerializedName("nameProvider")//("fullNameProvider")
    @Expose
    private String fullNameProvider;
    @SerializedName("addressProvider")
    @Expose
    private String addressProvider;
    @SerializedName("numberProvider")//("phoneNumberProvider")
    @Expose
    private String phoneNumberProvider;
    @SerializedName("emailProvider")
    @Expose
    private String emailProvider;

    @SerializedName("photoProvider")
    @Expose
    private String photoProvider;

    @Ignore
    @SerializedName("providerType")
    @Expose
    private ProviderType providerType;
    @SerializedName("contactNameProvider")
    @Expose
    private String contactNameProvider;
    @SerializedName("statusProvider")
    @Expose
    private StatusProvider statusProvider;

    @SerializedName("multimediaProfile")
    @Expose
    private MultimediaProfile multimediaProfile;

    private boolean addOffline;
    private boolean deleteOffline;
    private boolean editOffline;

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
    private int idProviderType = -1;

    public Integer getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    public long getUnixtime() {
        return unixtime;
    }

    public void setUnixtime(long unixtime) {
        this.unixtime = unixtime;
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

    public StatusProvider getStatusProvider() {
        return statusProvider;
    }

    public void setStatusProvider(StatusProvider statusProvider) {
        this.statusProvider = statusProvider;
    }

    public MultimediaProfile getMultimediaProfile() {
        return multimediaProfile;
    }

    public void setMultimediaProfile(MultimediaProfile multimediaProfile) {
        this.multimediaProfile = multimediaProfile;
    }

    @Override
    public String getReadableDescription() {
        return getFullNameProvider();
    }

    @Override
    public boolean canDelete() {
        return true;
    }

    public int getIdProviderType() {
        return idProviderType;
    }

    public void setIdProviderType(int idProviderType) {
        this.idProviderType = idProviderType;
    }

    public int getStatusDelete() {
        return statusDelete;
    }

    public void setStatusDelete(int statusDelete) {
        this.statusDelete = statusDelete;
    }

    public boolean isAddOffline() {
        return addOffline;
    }

    public void setAddOffline(boolean addOffline) {
        this.addOffline = addOffline;
    }

    public boolean isDeleteOffline() {
        return deleteOffline;
    }

    public void setDeleteOffline(boolean deleteOffline) {
        this.deleteOffline = deleteOffline;
    }

    public boolean isEditOffline() {
        return editOffline;
    }

    public void setEditOffline(boolean editOffline) {
        this.editOffline = editOffline;
    }

    @Override
    public String toString() {
        return "Provider{" +
                "idProvider=" + idProvider +
                ", unixtime=" + unixtime +
                ", status=" + statusProvider +
                ", statusDelete=" + statusDelete +
                ", addOffline=" + addOffline +
                ", editOffline=" + editOffline +
                ", deleteOffline=" + deleteOffline +
                ", identificationDocProvider='" + identificationDocProvider + '\'' +
                ", fullNameProvider='" + fullNameProvider + '\'' +
                ", addressProvider='" + addressProvider + '\'' +
                ", phoneNumberProvider='" + phoneNumberProvider + '\'' +
                ", emailProvider='" + emailProvider + '\'' +
                //", photoProvider='" + photoProvider + '\'' +
                ", providerType=" + providerType +
                ", contactNameProvider='" + contactNameProvider + '\'' +
                ", statusProvider=" + statusProvider +
                ", identificationDocProviderChange='" + identificationDocProviderChange + '\'' +
                ", idProviderType=" + idProviderType +
                '}';
    }

    @DebugLog
    public boolean isHarvester() {
        if (getProviderType() == null) {
            return getIdProviderType() == Constants.TYPE_HARVESTER;
        }
        return (getProviderType().getIdProviderType() == Constants.TYPE_HARVESTER);
    }

    @Override
    public JsonElement serialize(Provider src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("idProvider", src.getIdProvider());
        jsonObject.addProperty("unixtime", src.getUnixtime());
        jsonObject.addProperty("statusDelete", src.getStatusDelete());
        jsonObject.addProperty("identificationDocProvider", src.getIdentificationDocProvider());
        jsonObject.addProperty("identificationDocProviderChange", src.getIdentificationDocProviderChange());
        jsonObject.addProperty("fullNameProvider", src.getFullNameProvider());
        jsonObject.addProperty("addressProvider", src.getAddressProvider());
        jsonObject.addProperty("phoneNumberProvider", src.getPhoneNumberProvider());
        jsonObject.addProperty("emailProvider", src.getEmailProvider());
        //jsonObject.addProperty("photoProvider", src.getPhotoProvider());
        jsonObject.addProperty("contactNameProvider", src.getContactNameProvider());
        //jsonObject.addProperty("statusProvider", src.getStatusProvider());
        jsonObject.addProperty("addOffline", src.isAddOffline());
        jsonObject.addProperty("editOffline", src.isEditOffline());
        jsonObject.addProperty("deleteOffline", src.isDeleteOffline());
        jsonObject.addProperty("id_ProviderType", src.getIdProviderType());

        return jsonObject;
    }

    public int indexIn(final List<Provider> list) {
        for (int i = 0; i < list.size(); i++) {
            Provider provider = list.get(i);
            if (provider != null && provider.getIdentificationDocProvider().equals(getIdentificationDocProvider())) {
                return i;
            }
        }
        return -1;
    }

    public int indexByIdIn(final List<Provider> list) {
        for (int i = 0; i < list.size(); i++) {
            Provider provider = list.get(i);
            if (provider != null && provider.getIdProvider() == getIdProvider()) {
                return i;
            }
        }
        return -1;
    }

    public int indexByUnixtimeIn(final List<Provider> list) {
        for (int i = 0; i < list.size(); i++) {
            Provider provider = list.get(i);
            if (provider != null && provider.getUnixtime() == getUnixtime()) {
                return i;
            }
        }
        return -1;
    }
}
