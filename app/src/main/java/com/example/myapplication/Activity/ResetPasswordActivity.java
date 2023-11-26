package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.GlobalVar;
import com.example.myapplication.LoadingAlert;
import com.example.myapplication.R;

public class ResetPasswordActivity extends BaseActivity {
    ImageButton btn_language;
    EditText et_user;
    Button btn_reset;
    Button btn_back;
    WebView wv_browser;
    LoadingAlert loadingAlert;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_change_password);
        setContentView(R.layout.activity_reset_password);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Lock orientation
        
        InitialView();
        InitialEvent();
    }

    private void InitialView() {
        // Initial all views
        btn_language = findViewById(R.id.btn_changeLanguage);
        et_user = findViewById(R.id.et_user);
        btn_reset = findViewById(R.id.btn_resetPassword);
        btn_back = findViewById(R.id.btn_back);
        wv_browser = findViewById(R.id.wv_browser);
        loadingAlert = new LoadingAlert(ResetPasswordActivity.this);
    }

    private void InitialEvent() {
        // Initial all events
        btn_language.setOnClickListener(view -> {
            loadingAlert.startAlertDialog();

            new Handler().postDelayed(() -> {
                loadingAlert.closeAlertDialog();
                onLanguageChange();
            }, 1000);
        });
        btn_back.setOnClickListener(view -> {
            openMainActivity();
            finish();
        });
//        btn_change.setOnClickListener(view -> {
//            signIn();
//        });
        btn_reset.setOnClickListener(view -> {
            loadingAlert.startAlertDialog();
            resetPws();
            openMainActivity();
            finish();
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void resetPws() {
        // Reset password with email or username
        String username = et_user.getText().toString();
        if (username.isEmpty()) {
            et_user.setError("Not null");
            return;
        }
        CookieManager.getInstance().removeAllCookies(null); // Remove old cookies

//        wv_browser.setVisibility(View.VISIBLE);
        wv_browser = new WebView(ResetPasswordActivity.this); // Create new web view
        wv_browser.getSettings().setJavaScriptEnabled(true); // Enable evaluate javascript
        wv_browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(GlobalVar.LOG_TAG, "onPageFinished: ");
                String greenText = "document.getElementsByClassName('green-text')[1].textContent;"; // Appear when username already exist.
                if (url.contains(GlobalVar.signInUrl)) {
                    view.evaluateJavascript(greenText, green -> {
                        loadingAlert.closeAlertDialog();
                        Log.d(GlobalVar.LOG_TAG, "green: " + green);
                        changePassLog(green);
                    });
                }
                else if (url.contains(GlobalVar.resetPwsUrl)) {
                    String usrScript = "document.getElementById('username').value='" + username + "';";
                    String formScript = "document.getElementsByTagName('form')[0].submit();";

                    view.evaluateJavascript(usrScript, null);
                    view.evaluateJavascript(formScript, null);
                }

                String cookies = CookieManager.getInstance().getCookie(url); // Log cookies
                Log.d(GlobalVar.LOG_TAG, "return cookie: " + cookies);
                Log.d(GlobalVar.LOG_TAG, "url: " + url);
                super.onPageFinished(view, url);
            }
        });

        wv_browser.loadUrl(GlobalVar.resetPwsUrl); // Loading reset password url
        wv_browser.removeAllViews();
    }

    private void changePassLog(String msg) {
        Toast.makeText(ResetPasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}