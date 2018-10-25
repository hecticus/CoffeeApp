package com.hecticus.eleta.login;

import com.hecticus.eleta.model.response.AccessTokenResponse;
import com.hecticus.eleta.model.response.LoginResponse;

/**
 * Created by roselyn545 on 15/9/17.
 */

public class LoginContract {

    public interface View {

        void showWorkingIndicator();

        void hideWorkingIndicator();

        void clickOnLoginButton();

        void clickOnRecoveryPasswordButton();

        void handleSuccessfulLogin();

        void handleSuccessfulRecoveryPassword();

        void showErrorMessage(String error);

        void goToLoggedInView();

    }

    public interface Actions {

        void onLogin(String email, String password);

        void onRecoveryPassword();

        void onLoginSuccess();

        void onRecoveryPasswordSuccess();

        void onLoginError(String errorMessageFromServer);

    }

    public interface Repository {

        void loginRequest(String email, String password);

        void recoveryPasswordRequest(String email);

        void onLoginSuccess(AccessTokenResponse accessTokenResponse);

        void onLoginError(String errorMessageFromServer);

        void saveTokens(AccessTokenResponse accessTokenResponse, String email, String name, Long id);

    }

}
