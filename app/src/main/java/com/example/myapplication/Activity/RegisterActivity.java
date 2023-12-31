package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.example.myapplication.LoadingAlert;
import com.example.myapplication.R;

public class RegisterActivity extends BaseActivity {
    private EditText et_username, et_email, et_password, et_rePassword;
    private Button btn_back, btn_signUp;
    private ImageButton btn_changeLanguage;
    private LoadingAlert loadingAlert;
    private ProgressBar pg_loading;
    private Handler handler;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Lock orientation

        InitView();
        InitEvent();
    }

    private void InitView() {
        // Initial all views
        setLangIcon();
        btn_back = findViewById(R.id.btn_back);
        btn_signUp = findViewById(R.id.btn_signUp);
        btn_changeLanguage = findViewById(R.id.btn_changeLanguage);
        et_username = findViewById(R.id.et_username); // Get username Edit Text
        et_email = findViewById(R.id.et_mail); // Get email Edit Text
        et_password = findViewById(R.id.et_password); // Get password Edit Text
        et_rePassword = findViewById(R.id.et_rePassword); //Get rePassword Edit Text
        loadingAlert = new LoadingAlert(RegisterActivity.this);
        pg_loading = findViewById(R.id.pg_loading);
    }

    private void InitEvent() {
        // Initial all event
        btn_back.setOnClickListener(view -> { // Back button
            // Open main activity
            onBackPressed();
        });

        btn_signUp.setOnClickListener(view -> { // Sign up button
            // Validate user form, open sign up method
            String username = et_username.getText().toString(); // Extract username
            String email = et_email.getText().toString(); // Extract email
            String password = et_password.getText().toString(); // Extract password
            String rePassword = et_rePassword.getText().toString(); // Extract rePassword
            boolean isValidInformation = validateForm(username, email, password, rePassword);

            et_username.setEnabled(false);
            et_email.setEnabled(false);
            et_password.setEnabled(false);
            et_rePassword.setEnabled(false);

            if (isValidInformation) { // If information is valid
                btn_signUp.setVisibility(View.INVISIBLE);
                pg_loading.setVisibility(View.VISIBLE);
                authenticateUser(username, email, password, rePassword);

                handler = new Handler(message -> { // Handle message
                    Bundle bundle = message.getData(); // Get message
                    boolean isOk = bundle.getBoolean("IS_OK"); // Get message data
                    if (!isOk) return false; // If not ok return

                    signUpLog(getString(R.string.success_warning));
                    openLogInActivity(); // Open login
                    finish();

                    return false;
                });
            }
        });

        btn_changeLanguage.setOnClickListener(view -> { // Change language button
            // Open change language method
            loadingAlert.startAlertDialog();

            new Handler().postDelayed(() -> {
                loadingAlert.closeAlertDialog();
                onLanguageChange();
            },1000);
        });
    }

    private boolean validateForm(String username, String email, String password, String rePassword) {
        // Validate user input
        boolean isValid = true;

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

    @SuppressLint("SetJavaScriptEnabled")
    private void authenticateUser(String username, String email, String password, String rePassword) {
        // Get sign up token
        CookieManager.getInstance().removeAllCookies(null);

//        webView.setVisibility(View.VISIBLE);
        WebView webView = new WebView(RegisterActivity.this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(GlobalVar.LOG_TAG, "onPageFinished: ");

                if (url.contains("openid-connect/auth")) { // Url is now in sign in page
                    String redirect = "document.getElementsByTagName('a')[0].click();"; // Click on sign up button
                    view.evaluateJavascript(redirect, null);
                }
                else if (url.contains("login-actions/registration")) { // Url is now in sign up page
                    Log.d(GlobalVar.LOG_TAG, "Enter registration");
                    String dataError = "document.getElementsByClassName('helper-text')[0].getAttribute('data-error');"; // Appear when email is exist
                    String redText = "document.getElementsByClassName('red-text')[1].textContent;"; // Appear when username already exist.

                    view.evaluateJavascript(dataError, dErr-> {
                        if (dErr.equals("null")) { // No error in form
                            view.evaluateJavascript(redText, red -> {
                                if (red.equals("null")) {
                                    String usrScript = "document.getElementById('username').value='" + username + "';";
                                    String emailScript = "document.getElementById('email').value='" + email + "';";
                                    String pwdScript = "document.getElementById('password').value='" + password + "';";
                                    String rePwdScript = "document.getElementById('password-confirm').value='" + rePassword + "';";

                                    view.evaluateJavascript(usrScript, null);
                                    view.evaluateJavascript(emailScript, null);
                                    view.evaluateJavascript(pwdScript, null);
                                    view.evaluateJavascript(rePwdScript, null);
                                    view.evaluateJavascript("document.getElementsByTagName('form')[0].submit();", null); // Submit form
                                }
                                else {
                                    Log.d(GlobalVar.LOG_TAG, "red: " + red);

                                    btn_signUp.setVisibility(View.VISIBLE);
                                    pg_loading.setVisibility(View.INVISIBLE);

                                    signUpLog(red);
                                }
                            });
                        }
                        else { //
                            Log.d(GlobalVar.LOG_TAG, "error: " + dErr);

                            btn_signUp.setVisibility(View.VISIBLE);
                            pg_loading.setVisibility(View.INVISIBLE);

                            signUpLog(dErr);
                        }
                    });
                }
                else if (url.contains("manager/#state=")) { // Sign up success, open log in
                    Log.d(GlobalVar.LOG_TAG, getString(R.string.success_warning));

                    btn_signUp.setVisibility(View.VISIBLE);
                    pg_loading.setVisibility(View.INVISIBLE);

                    Message msg = handler.obtainMessage(); // Create message
                    Bundle bundle = new Bundle(); // Create bundle
                    bundle.putBoolean("IS_OK", true); // Put true to bundle
                    msg.setData(bundle); // Set message data
                    handler.sendMessage(msg);  // Send message through bundle
                }

                Log.d(GlobalVar.LOG_TAG, "url: " + url);
                super.onPageFinished(view,url);
            }
        });

        webView.loadUrl(GlobalVar.baseUrl);
    }

    private void signUpLog(String msg) {
        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        openMainActivity();
        finish();
    }
}