package com.hecticus.eleta.base;

/**
 * Created by roselyn545 on 28/9/17.
 */

public interface BaseEditableModel {

    public abstract String getReadableDescription();
    public abstract String getInputValue();
    public abstract void setInputValue(String value);
}
