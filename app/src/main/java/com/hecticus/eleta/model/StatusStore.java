package com.hecticus.eleta.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hecticus.eleta.base.BaseModel;

import java.lang.reflect.Type;

import io.realm.RealmObject;

public class StatusStore extends RealmObject implements BaseModel, JsonSerializer<StatusStore> {

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

    public StatusStore() {
    }

    @Override
    public JsonElement serialize(StatusStore src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("name", src.getName());
        jsonObject.addProperty("description", src.getDescription());
        return jsonObject;
    }

    @Override
    public String getReadableDescription() {
        return null;
    }

    @Override
    public boolean canDelete() {
        return false;
    }
}
