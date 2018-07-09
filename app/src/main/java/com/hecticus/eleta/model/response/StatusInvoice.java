package com.hecticus.eleta.model.response;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.base.BaseModel;
import com.hecticus.eleta.model.StatusProvider;

import java.lang.reflect.Type;

import io.realm.RealmObject;

public class StatusInvoice extends RealmObject implements BaseModel, JsonSerializer<StatusInvoice> {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("deleted")
    @Expose
    private Boolean deleted;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String  description;

    /*"createdAt": "2018-07-03 15:22:43",
      "updatedAt": "2018-07-03 15:22:43",
      "dtype": null,*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusInvoice() {
    }

    public StatusInvoice(Integer id, Boolean deleted, String name, String description) {
        this.id = id;
        this.deleted = deleted;
        this.name = name;
        this.description = description;
    }

    @Override
    public String getReadableDescription() {
        return null;
    }

    @Override
    public boolean canDelete() {
        return false;
    }

    @Override
    public JsonElement serialize(StatusInvoice src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("name", src.getName());
        jsonObject.addProperty("description", src.getDescription());
        return jsonObject;
    }
}
