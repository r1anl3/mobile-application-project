package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.myapplication.R;

public class RegisterActivity extends BaseActivity {
    private EditText et_username;
    private EditText et_email;
    private EditText et_password;
    private EditText et_rePassword;
    private ProgressBar pg_loading;
    private String username;
    private String email;
    private String password;
    private String rePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Find components from layout
        Button btn_back = findViewById(R.id.btn_back);
        Button btn_signUp = findViewById(R.id.btn_signUp);
        Button btn_changeLanguage = findViewById(R.id.btn_changeLanguage);
        et_username = findViewById(R.id.et_username); // Get username Edit Text
        et_email = findViewById(R.id.et_mail); // Get email Edit Text
        et_password = findViewById(R.id.et_password); // Get password Edit Text
        et_rePassword = findViewById(R.id.et_rePassword); //Get rePassword Edit Text


        // Set functions for components
        btn_back.setOnClickListener(view -> {
            //TODO: Return main activity on clicked
            RegisterActivity.super.onBackPressed();
        });
        btn_signUp.setOnClickListener(view -> {
            //TODO: Validate user form, sign up to main dashboard
            boolean isValidInformation = validateForm();

            if (isValidInformation) { // If information is valid
                // Show loading screen
                pg_loading = findViewById(R.id.pb_loading);
                pg_loading.setVisibility(View.VISIBLE);

                Handler handler = new Handler(); // Add event handler
                handler.postDelayed(() -> {
                    // After delay time,
                    pg_loading.setVisibility(View.INVISIBLE); // Set progress bar to invisible
                    onSignUp(); // Call onSignUp method
                }, 1000); // Delay time
            }
        });
        btn_changeLanguage.setOnClickListener(view -> {
            //TODO: Change language on clicked
            onLanguageChange();
        });

    }

    private boolean validateForm() {
        //TODO: Validate user information
        int score = 4;
        username = et_username.getText().toString(); // Extract username
        email = et_email.getText().toString(); // Extract email
        password = et_password.getText().toString(); // Extract password
        rePassword = et_rePassword.getText().toString(); // Extract rePassword

        if (username.isEmpty()) { // If one field is empty
            score -= 1;
            et_username.setError(getString(R.string.form_warning));
        }
        if (email.isEmpty() || !isEmail(et_email)) { // If one field is empty
            score -= 1;
            et_email.setError(getString(R.string.email_invalid));
        }
        if (password.isEmpty()) { // If one field is empty
            score -= 1;
            et_password.setError(getString(R.string.form_warning));
        }
        if (rePassword.isEmpty() || !password.equals(rePassword)) { // If one field is empty
            score -= 1;
            et_rePassword.setError(getString(R.string.password_warning));
        }

        if (score != 4) {
            return false;
        }
        return true; // User information is valid
    }


    private void onSignUp() {
        //TODO: check sign up status, get access permission to dashboard
        /*
            Do something here to be authorized by UIT
            Update isAuthorizedByUIT
        */
        isAuthorizedByUIT = onAuthorizeByUIT();

        if (isAuthorizedByUIT) {
            openDashboardActivity(); // Go to dashboard
        }
        else {
            // Pop up message show that "Can not sign up"
            //    private static final String TAG = "Register Activity";
            Toast mToast = Toast.makeText(this, R.string.signup_warning, Toast.LENGTH_SHORT); // Warning user
            mToast.show();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private boolean onAuthorizeByUIT() {
        //TODO: POST to get authorized by UIT
        String url = "https://uiot.ixxc.dev/auth/realms/master/login-actions/registration";

        WebView webView = findViewById(R.id.wv_browser);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.contains("login-actions/registration")) {
                    String usrScript = "document.getElementById('username').value=" + username + ";";
                    String emailScript = "document.getElementById('email').value=" + email + ";";
                    String pwdScript = "document.getElementById('password').value=" + password + ";";
                    String rePwdScript = "document.getElementById('password-confirm').value=" + rePassword + ";";

                    view.evaluateJavascript(usrScript, null);
                    view.evaluateJavascript(emailScript, null);
                    view.evaluateJavascript(pwdScript, null);
                    view.evaluateJavascript(rePwdScript, null);
                    view.evaluateJavascript("document.getElementById('kc-register-form').submit();", null);
                }
            }
        });
        webView.loadUrl(url);
        return false;
    }

}