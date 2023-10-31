package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.GlobalVar;
import com.example.myapplication.LoadingAlert;
import com.example.myapplication.R;

public class ChangePasswordActivity extends BaseActivity {
    ImageButton btn_language;
    EditText et_user;
    EditText et_oldPassword;
    EditText et_newPassword;
    EditText et_confirmPassword;
    Button btn_change;
    Button btn_back;
    WebView wv_browser;
    LoadingAlert loadingAlert;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Lock orientation
        
        InitialView();
        InitialEvent();
    }

    private void InitialView() {
        // Initial all views
        btn_language = findViewById(R.id.btn_changeLanguage);
        et_user = findViewById(R.id.et_user);
        et_oldPassword = findViewById(R.id.et_password);
        et_newPassword = findViewById(R.id.et_newPassword);
        et_confirmPassword = findViewById(R.id.et_newPasswordConfirmation);
        btn_change = findViewById(R.id.btn_changePassword);
        btn_back = findViewById(R.id.btn_back);
        wv_browser = findViewById(R.id.wv_browser);
        loadingAlert = new LoadingAlert(ChangePasswordActivity.this);
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
        btn_change.setOnClickListener(view -> {
            signIn();
        });
    }


    private void signIn() {
        // TODO: sign in with username and password
        String username = et_user.getText().toString();
        String oldPassword = et_oldPassword.getText().toString();
        String newPassword = et_newPassword.getText().toString();
        String confirmPassword = et_confirmPassword.getText().toString();
//        boolean isValid = validateForm(username, oldPassword, newPassword, confirmPassword);

        loadingAlert.startAlertDialog();
        getToken(username, oldPassword, newPassword, confirmPassword);
//        if (isValid) {
//        }

    }
    private boolean validateForm(String username, String oldPassword, String newPassword, String confirmPassword) {
        // Validate user input
        boolean isValid = true;

        // Validate username
        if (username.isEmpty()) {
            et_user.setError(getString(R.string.form_warning));
            isValid = false;
        }

        // Validate old password
        if (oldPassword.isEmpty()) {
            et_oldPassword.setError(getString(R.string.form_warning));
            isValid = false;
        }

        // Validate new password
        if (newPassword.isEmpty()) {
            et_newPassword.setError(getString(R.string.form_warning));
            isValid = false;
        }

        // Validate password confirmation
        if (!newPassword.equals(confirmPassword) || confirmPassword.isEmpty()) {
            et_confirmPassword.setError(getString(R.string.confirm_password));
            isValid = false;
        }

        return isValid;
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void getToken(String user, String password, String newPassword, String confirmPassword) {
        // Get login token
        CookieManager.getInstance().removeAllCookies(null); // Remove old cookies

//        wv_browser.setVisibility(View.VISIBLE);
        wv_browser = new WebView(ChangePasswordActivity.this); // Create new web view
        wv_browser.getSettings().setJavaScriptEnabled(true); // Enable evaluate javascript
        wv_browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(GlobalVar.LOG_TAG, "onPageFinished: ");
                String dataError = "document.getElementsByClassName('helper-text')[0].getAttribute('data-error');"; // Appear when error

                if (url.contains("account/password")) { // go to change password page
                    changePassword(wv_browser, password, newPassword, confirmPassword);
                }
                else { // not log in
                    view.evaluateJavascript(dataError, err -> {
                        if (err.equals("null")) {
                            String usrScript = "document.getElementById('username').value='" + user + "';";
                            String pwdScript = "document.getElementById('password').value='" + password + "';";

                            view.evaluateJavascript(usrScript, null);
                            view.evaluateJavascript(pwdScript, null);
                            view.evaluateJavascript("document.getElementsByTagName('form')[0].submit();", null);
                            loadingAlert.closeAlertDialog(); // Close loading
                        }
                        else { // If error
                            Log.d(GlobalVar.LOG_TAG, "err: " + err);
                            changePassLog(err);
                        }
                    });
                }

                String cookies = CookieManager.getInstance().getCookie(url); // Log cookies
                Log.d(GlobalVar.LOG_TAG, "return cookie: " + cookies);
                Log.d(GlobalVar.LOG_TAG, "url: " + url);
                super.onPageFinished(view, url);
            }
        });

        wv_browser.loadUrl(GlobalVar.resetUrl); // Loading reset password url
        wv_browser.removeAllViews();
    }

    private void changePassword(WebView view, String oldPws, String newPws, String confPws) {
        // Go to resetUrl, fill up change password form, submit form
        Log.d(GlobalVar.LOG_TAG, "change password");
        String redText = "document.getElementsByClassName('red-text')[1].innerText";
        String greenText = "document.getElementsByClassName('green-text')[1].innerText";

        view.evaluateJavascript(redText, red -> {
            if (red.equals("null")) { // If not error
                // Fill up sign in form
                String pwdOldScript = "document.getElementById('password').value='" + oldPws + "';";
                String pwdNewScript = "document.getElementById('password-new').value='" + newPws + "';";
                String pwdConfScript = "document.getElementById('password-confirm').value='" + confPws + "';";

                view.evaluateJavascript(pwdOldScript, null);
                view.evaluateJavascript(pwdNewScript, null);
                view.evaluateJavascript(pwdConfScript, null);
                view.evaluateJavascript("document.getElementsByTagName('form')[0].submit();", null);
                view.evaluateJavascript(greenText, green -> { // Find success message

                    if (!green.equals("null")) { // is success
                        Log.d(GlobalVar.LOG_TAG, "success: " + green);
                        changePassLog(green);
                        openLogInActivity(); // Open login
                        finish();
                    }
                });
            }
            else {
                changePassLog(red);
                Log.d(GlobalVar.LOG_TAG, "change password error: " + red);
            }
        });
    }

    private void changePassLog(String msg) {
        Toast.makeText(ChangePasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}