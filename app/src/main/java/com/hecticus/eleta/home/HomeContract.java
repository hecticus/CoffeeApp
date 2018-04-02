package com.hecticus.eleta.home;

/**
 * Created by Edwin on 2017-12-30.
 */

public class HomeContract {
    public interface View {
        void onLogOutClicked();

        void onLogoutSuccess();

        void onLogoutError();
    }

    public interface Actions {
        void logOut();

        void onLogoutRequestSuccess();

        void onLogoutRequestError();
    }

    public interface Repository {
        void logOutRequest();

        void onLogoutSuccess();

        void onLogoutError();
    }
}
