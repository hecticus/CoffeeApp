package com.hecticus.eleta.base;

/**
 * Created by roselyn545 on 16/9/17.
 */

public abstract class BaseListContract {

    public interface View {

        void invalidToken();

        boolean hasLocationPermissions();

        void requestLocationPermissions();
    }

    public interface Actions {

        void onClickPrintButton(BaseModel model);

        void onClickEditButton(BaseModel model);

        void onClickItem(BaseModel model);

        void onClickDeleteButton(BaseModel model);

        void invalidToken();
    }
}
