package com.hecticus.eleta.base;

/**
 * Created by roselyn545 on 18/9/17.
 */

public class BaseDetailListContract {

    public interface Actions {

        void onClickEditButton(BaseDetailModel model);

        void onClickItem(BaseDetailModel model);

        void onClickDeleteButton(BaseDetailModel model);
    }
}
