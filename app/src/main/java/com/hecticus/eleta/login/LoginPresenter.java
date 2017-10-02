package com.hecticus.eleta.login;

import android.content.Context;

import com.hecticus.eleta.R;

import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 15/9/17.
 */

public class LoginPresenter implements LoginContract.Actions {

    Context context;
    private LoginContract.View mView;
    private LoginContract.Repository mRepository;

    public LoginPresenter(Context context, LoginContract.View mView) {
        this.context = context;
        this.mView = mView;
        mRepository = new LoginRepository(this);
    }

    private boolean validateLoginFields(String email, String password) {
        return (!email.trim().isEmpty() && !password.isEmpty());
    }

    @Override
    public void onLogin(String email, String password) {
        if (validateLoginFields(email, password)) {
            mView.showWorkingIndicator();
            mRepository.loginRequest(email,password);
        }else {
            mView.showErrorMessage(context.getString(R.string.invalid_email_or_password));
        }
    }

    @Override
    public void onRecoveryPassword() {

    }

    @Override
    public void onLoginSuccess() {
        mView.hideWorkingIndicator();
        mView.handleSuccessfulLogin();
    }

    @Override
    public void onRecoveryPasswordSuccess() {

    }

    @Override
    public void onLoginError() {
        mView.hideWorkingIndicator();
        mView.showErrorMessage(context.getString(R.string.login_error));
    }
}
