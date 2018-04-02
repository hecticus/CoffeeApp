package com.hecticus.eleta.base;

/**
 * Created by roselyn545 on 14/10/17.
 */

public class DescriptionAndValueModel {

    private String description = "";
    private String value = "";

    public DescriptionAndValueModel(String description, String value){
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DescriptionAndValueModel{" +
                "description='" + description + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
