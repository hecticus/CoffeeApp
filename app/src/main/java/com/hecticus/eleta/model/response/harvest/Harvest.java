package com.hecticus.eleta.model.response.harvest;

import com.hecticus.eleta.base.BaseModel;

/**
 * Created by roselyn545 on 17/9/17.
 */

public class Harvest extends BaseModel {

    @Override
    public String getReadableDescription() {
        return "nada";
    }

    @Override
    public boolean canDelete() {
        return true;
    }
}
