package com.hecticus.eleta.home;

import android.content.Context;

import com.hecticus.eleta.model.SessionManager;

import hugo.weaving.DebugLog;

/**
 * Created by Edwin on 2017-12-30.
 */

public class HomePresenter implements HomeContract.Actions {

    private HomeContract.View mView;
    private HomeContract.Repository mRepository;

    Context context;

    public HomePresenter(HomeContract.View viewParam, Context contextParam) {
        mView = viewParam;
        context = contextParam;
        mRepository = new HomeRepository(this);
    }

    @DebugLog
    @Override
    public void logOut() {
        if (SessionManager.isValidSession(context))
            mRepository.logOutRequest();
        else
            onLogoutRequestSuccess();
    }

    @DebugLog
    @Override
    public void onLogoutRequestSuccess() {
        SessionManager.clearPreferences(context);
        mView.onLogoutSuccess();
    }

    @DebugLog
    @Override
    public void onLogoutRequestError() {
        mView.onLogoutSuccess();
    }
}
