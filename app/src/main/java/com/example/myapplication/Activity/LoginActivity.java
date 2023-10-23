package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.GlobalVar;
import com.example.myapplication.LoadingAlert;
import com.example.myapplication.R;

public class LoginActivity extends BaseActivity {
    private Button btn_signIn;
    private Button btn_back;
    private ImageButton btn_changeLanguage;
    private TextView tv_register;
    private ImageButton iBtn_google;
    private LoadingAlert loadingAlert;
    private EditText et_user;
    private EditText et_password;
    private WebView webView;
    private String user, password;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Lock orientation

        InitialView();
        InitialEvent();
    }

    private void InitialView() {
        //TODO: Initial all views
        btn_signIn = findViewById(R.id.btn_signIn);
        btn_back = findViewById(R.id.btn_back);
        btn_changeLanguage = findViewById(R.id.btn_changeLanguage);
        tv_register = findViewById(R.id.tv_register);
        iBtn_google = findViewById(R.id.ib_google);
        loadingAlert = new LoadingAlert(LoginActivity.this);
        et_user = findViewById(R.id.et_user);
        et_password = findViewById(R.id.et_password);
        webView = findViewById(R.id.wv_browser);
    }

    private void InitialEvent() {
        //TODO: Initial all evens
        btn_back.setOnClickListener(view -> {
            // Open main activity
            openMainActivity();
            finish();
        });

        btn_signIn.setOnClickListener(view -> {
            // Open sign in method
            user = String.valueOf(et_user.getText());
            password = String.valueOf(et_password.getText());
            boolean isValidInformation = validateForm(user, password);

            if (isValidInformation) {
                loadingAlert.startAlertDialog();
                onSignIn();
            }
        });

        btn_changeLanguage.setOnClickListener(view -> {
            // Open change language method
            loadingAlert.startAlertDialog();

            new Handler().postDelayed(() -> {
                loadingAlert.closeAlertDialog();
                onLanguageChange();
            },300);
        });

        tv_register.setOnClickListener(view -> {
            // Open register activity
            openRegisterActivity();
        });

        iBtn_google.setOnClickListener(view -> {
            // Open sign in with Google method
            loadingAlert.startAlertDialog();

            new Handler().postDelayed(() -> {
                loadingAlert.closeAlertDialog();
                signInWithGoogle();
            },1000);
        });
    }

    private void onSignIn() {
        //TODO: Use token to sign in
        getToken(user, password);
    }

    private boolean validateForm(String username, String password) {
        //TODO: Validate user information
        boolean isValid = true;

        // Validate username
        if (username.isEmpty()) {
            et_user.setError(getString(R.string.form_warning));
            isValid = false;
        }

        // Validate password
        if (password.isEmpty()) {
            et_password.setError(getString(R.string.form_warning));
            isValid = false;
        }
        return isValid;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void getToken(String user, String password) {
        CookieManager.getInstance().removeAllCookies(null);

        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                String dataError = "document.getElementsByClassName('helper-text')[0].getAttribute('data-error');"; // Appear when error
                String redText= "document.getElementsByClassName('red-text')[1].innerText;"; // Appear with data-error

                view.evaluateJavascript(dataError, err -> {
                    Log.d(GlobalVar.LOG_TAG, "error: " + err);
                    if (!err.equals("null")) {
                        signInLog("2");
                    }
                    else {
                        String usrScript = "document.getElementById('username').value='" + user + "';";
                        String pwdScript = "document.getElementById('password').value='" + password + "';";
                        view.evaluateJavascript(usrScript, null);
                        view.evaluateJavascript(pwdScript, null);
                        view.evaluateJavascript("document.getElementsByTagName('form')[0].submit();", null);
                        loadingAlert.closeAlertDialog();
                    }
                });

                String cookies = CookieManager.getInstance().getCookie(url);
                Log.d(GlobalVar.LOG_TAG, "return cookie: " + cookies);
                super.onPageFinished(view, url);
            }
        });

        webView.loadUrl(GlobalVar.baseUrl);
        loadingAlert.closeAlertDialog();
//        openDashboardActivity();
    }

    private void signInLog(String s) {
        String msg = "";
        switch (s) {
            case "1":
                msg = "Success!";
                break;
            case "2":
                msg = "Fail!";
                break;
        }
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

}