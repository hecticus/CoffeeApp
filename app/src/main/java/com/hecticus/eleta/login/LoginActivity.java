package com.hecticus.eleta.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseActivity;
import com.hecticus.eleta.custom_views.CustomEditText;
import com.hecticus.eleta.home.HomeActivity;
import com.hecticus.eleta.model.SessionManager;
import com.hecticus.eleta.recovery_password.RecoveryPasswordActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import io.fabric.sdk.android.Fabric;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    private LoginContract.Actions mPresenter;

    @BindView(R.id.login_text_edit_email)
    CustomEditText emailEditText;

    @BindView(R.id.login_text_edit_password)
    CustomEditText passwordEditText;

    @BindView(R.id.custom_send_button)
    Button loginButton;

    @BindView(R.id.custom_link_button)
    Button recoveryPasswordButton;

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BUG", "--->Initializing Crashlytics");
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mPresenter = new LoginPresenter(this, this);

        if (SessionManager.isValidSession(this)) {
            goToLoggedInView();
        } else {
            initViews();
        }
    }

    @DebugLog
    @Override
    public void initViews() {
        emailEditText.initWithTypeAndDescription(CustomEditText.Type.EMAIL, getString(R.string.email));
        passwordEditText.initWithTypeAndDescription(CustomEditText.Type.PASSWORD, getString(R.string.password));

        initString();
    }

    @Override
    public void initString() {
        loginButton.setText(getString(R.string.login));
        recoveryPasswordButton.setText(getString(R.string.recover_password));


        emailEditText.setTextColor(R.color.colorAccent);
        passwordEditText.setTextColor(R.color.colorAccent);

        emailEditText.setHint(getString(R.string.email));
        passwordEditText.setHint(getString(R.string.password));

    }

    private void setEnableViews(boolean enable) {
        emailEditText.setEnabled(enable);
        passwordEditText.setEnabled(enable);
        loginButton.setEnabled(enable);
    }

    @Override
    public void showWorkingIndicator() {
        setEnableViews(false);
        mainLinearLayout.setAlpha(0.5f);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideWorkingIndicator() {
        setEnableViews(true);
        mainLinearLayout.setAlpha(1f);
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.custom_send_button)
    @Override
    public void clickOnLoginButton() {
        mPresenter.onLogin(emailEditText.getText().toLowerCase().trim(), passwordEditText.getText());
    }

    @OnClick(R.id.custom_link_button)
    @Override
    public void clickOnRecoveryPasswordButton() {
        Intent intent = new Intent(this, RecoveryPasswordActivity.class);
        intent.putExtra("email", emailEditText.getId());
        startActivity(intent);
    }

    @Override
    public void handleSuccessfulLogin() {
        Snackbar.make(mainLinearLayout, getString(R.string.login_successful), Snackbar.LENGTH_LONG).setAction("Action", null).show();
        goToLoggedInView();
    }

    @Override
    public void handleSuccessfulRecoveryPassword() {

    }

    @Override
    public void showErrorMessage(String error) {
        Snackbar.make(mainLinearLayout, error, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @DebugLog
    @Override
    public void goToLoggedInView() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

}
