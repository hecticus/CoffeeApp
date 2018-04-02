package com.hecticus.eleta.base;

/**
 * Created by roselyn545 on 18/9/17.
 */

public class BaseDetailListContract {

    public interface View {
        void invalidToken();

        boolean hasLocationPermissions();

        void requestLocationPermissions();

        void finishWithErrorMessage(String errorMessage);
    }

    public interface Actions {

        void onClickEditButton(BaseDetailModel model);

        void onClickItem(BaseDetailModel model);

        void onClickDeleteButton(BaseDetailModel model);

        void invalidToken();

    }
}
