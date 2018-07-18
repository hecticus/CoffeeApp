package com.hecticus.eleta.recovery_password;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseActivity;
import com.hecticus.eleta.custom_views.CustomEditText;
import com.hecticus.eleta.model.response.Message;
import com.hecticus.eleta.model_new.retrofit_interface.UserRetrofitInterface;
import com.hecticus.eleta.util.Constants;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecoveryPasswordActivity extends BaseActivity {

    @BindView(R.id.recovery_text_edit_email)
    CustomEditText emailEditText;

    @BindView(R.id.custom_send_button)
    Button recoveryButton;

    private String emailInLogin;
    private UserRetrofitInterface userApi = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password);
        ButterKnife.bind(this);

        emailInLogin = getIntent().getStringExtra("email");

        initRetrofit();
        initViews();
    }

    @Override
    public void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        emailEditText.initWithTypeAndDescription(CustomEditText.Type.EMAIL, getString(R.string.email));
        initString();
    }

    @Override
    public void initString() {
        if (emailInLogin != null)
            emailEditText.setText(emailInLogin);

        recoveryButton.setText(getString(R.string.recovery));

        emailEditText.setTextColor(R.color.colorAccent);
        emailEditText.setHint(getString(R.string.email));
    }

    private void setEnableViews(boolean enable) {
        emailEditText.setEnabled(enable);
        recoveryButton.setEnabled(enable);
    }

    public void showWorkingIndicator() {
        setEnableViews(false);
        mainLinearLayout.setAlpha(0.5f);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideWorkingIndicator() {
        setEnableViews(true);
        mainLinearLayout.setAlpha(1f);
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.custom_send_button)
    public void clickOnLoginButton() {
        recoverPasswordRequest(emailEditText.getText());
    }

    public void handleSuccessfulLogin() {
        Snackbar.make(mainLinearLayout, getString(R.string.recovery_successful), Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    public void showErrorMessage() {
        Snackbar.make(mainLinearLayout, getString(R.string.error_in_recovery), Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    public void initRetrofit() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
                                        .addHeader("Content-Type", "application/json").build();
                                return chain.proceed(request);
                            }
                        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        userApi = retrofit.create(UserRetrofitInterface.class);
    }

    @DebugLog
    public void recoverPasswordRequest(String email) {
        Call<ResponseBody> call = userApi.recoverPasswordRequest(email);

        /*Call<Message> call = userApi.recoverPasswordRequest(email);
        call.enqueue(new Callback<Message>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.isSuccessful()) {
                    try {
                        if ("Sent".equals(response.body().getMessage())) {
                            handleSuccessfulLogin();
                        } else {
                            showErrorMessage();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showErrorMessage();
                    }
                } else
                    showErrorMessage();
            }

            @DebugLog
            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                t.printStackTrace();
                Log.e("RETRO", "--->" + RecoveryPasswordActivity.class.getSimpleName() + " onFailure +" + call + " + " + t);
                showErrorMessage();
            }
        });*/
    }

}
