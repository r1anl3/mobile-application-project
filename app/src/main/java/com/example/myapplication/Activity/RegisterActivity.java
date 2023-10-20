package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
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
import android.widget.Toast;


import com.example.myapplication.GlobalVar;
import com.example.myapplication.R;

public class RegisterActivity extends BaseActivity {
    private EditText et_username;
    private EditText et_email;
    private EditText et_password;
    private EditText et_rePassword;
    private Button btn_back;
    private Button btn_signUp;
    private ImageButton btn_changeLanguage;
    private WebView webView;
    private ProgressBar pg_loading;
    private String username;
    private String email;
    private String password;
    private String rePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        InitView();
        InitEvent();
    }

    private void InitView() {
        //TODO: Initial all views
        btn_back = findViewById(R.id.btn_back);
        btn_signUp = findViewById(R.id.btn_signUp);
        btn_changeLanguage = findViewById(R.id.btn_changeLanguage);
        et_username = findViewById(R.id.et_username); // Get username Edit Text
        et_email = findViewById(R.id.et_mail); // Get email Edit Text
        et_password = findViewById(R.id.et_password); // Get password Edit Text
        et_rePassword = findViewById(R.id.et_rePassword); //Get rePassword Edit Text
        webView = findViewById(R.id.wv_browser);
        pg_loading = findViewById(R.id.pb_loading);
    }

    private void InitEvent() {
        //TODO: Initial all events
        btn_back.setOnClickListener(view -> {
            // Open main activity
            openMainActivity();
            finish();
        });

        btn_signUp.setOnClickListener(view -> {
            // Validate user form, open sign up method
            boolean isValidInformation = validateForm();

            if (isValidInformation) { // If information is valid
                pg_loading.setVisibility(View.VISIBLE);

                new Handler().postDelayed(() -> {
                    pg_loading.setVisibility(View.INVISIBLE);
                    onSignUp();
                }, 1000);
            }
        });

        btn_changeLanguage.setOnClickListener(view -> {
            // Open change language method
            pg_loading.setVisibility(View.VISIBLE);

            new Handler().postDelayed(() -> {
                pg_loading.setVisibility(View.INVISIBLE);
                onLanguageChange();
            },1000);
        });
    }

    private boolean validateForm() {
        //TODO: Validate user information
        boolean isValid = true; // Total scores = 4
        username = et_username.getText().toString(); // Extract username
        email = et_email.getText().toString(); // Extract email
        password = et_password.getText().toString(); // Extract password
        rePassword = et_rePassword.getText().toString(); // Extract rePassword

        // Validate username
        if (username.isEmpty()) {
            isValid = false;
            et_username.setError(getString(R.string.form_warning));
        }else {
            et_username.setError(null);
        }

        // Validate email
        if (email.isEmpty() || !isEmail(et_email)) {
            isValid = false;
            et_email.setError(getString(R.string.email_invalid));
        }else {
            et_email.setError(null);
        }

        // Validate password
        if (password.isEmpty()) {
            isValid = false;
            et_password.setError(getString(R.string.form_warning));
        } else {
            et_password.setError(null);
        }

        // Validate password confirm
        if (rePassword.isEmpty() || !password.equals(rePassword)) {
            isValid = false;
            et_rePassword.setError(getString(R.string.password_warning));
        }

        return isValid; // User information is valid
    }


    private void onSignUp() {
        //TODO: Check sign up status, get access permission to dashboard
        /*
            Do something here to be authorized by UIT
            Update isAuthorizedByUIT
        */
        isAuthorizedByUIT = true;

        if (isAuthorizedByUIT) {
            openDashboardActivity(); // Go to dashboard
            finish();
        }
        else {
            // Pop up message show that "Can not sign up"
            Toast mToast = Toast.makeText(this, R.string.signup_warning, Toast.LENGTH_SHORT); // Warning user
            mToast.show();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void getToken() {
        //TODO: Get register token

        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.contains("login-actions/registration")) {
                    Log.d(GlobalVar.LOG_TAG, "onPageFinished: KO");
                    String usrScript = "document.getElementById('username').value='" + username + "';";
                    String emailScript = "document.getElementById('email').value='" + email + "';";
                    String pwdScript = "document.getElementById('password').value='" + password + "';";
                    String rePwdScript = "document.getElementById('password-confirm').value='" + rePassword + "';";

                    view.evaluateJavascript(usrScript, null);
                    view.evaluateJavascript(emailScript, null);
                    view.evaluateJavascript(pwdScript, null);
                    view.evaluateJavascript(rePwdScript, null);
                    view.evaluateJavascript("document.getElementById('kc-register-form').submit();", null);
                }

                String cookies = CookieManager.getInstance().getCookie(url);
                Log.d(GlobalVar.LOG_TAG, "this cookie: "+ cookies);
                super.onPageFinished(view,url);
            }
        });

        webView.loadUrl(GlobalVar.signUpUrl);
    }

}