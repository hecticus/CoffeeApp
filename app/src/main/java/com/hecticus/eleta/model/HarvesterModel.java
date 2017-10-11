package com.hecticus.eleta.model;

import com.hecticus.eleta.base.BaseModel;

/**
 * Created by roselyn545 on 15/9/17.
 */

public class HarvesterModel extends BaseModel {

    @Override
    public String getReadableDescription() {
        return "prueba";
    }

    @Override
    public boolean canDelete() {
        return true;
    }
}
