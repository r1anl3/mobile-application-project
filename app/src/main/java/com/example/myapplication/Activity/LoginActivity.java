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
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.API.ApiManager;
import com.example.myapplication.GlobalVar;
import com.example.myapplication.LoadingAlert;
import com.example.myapplication.Manager.LocalDataManager;
import com.example.myapplication.Model.Token;
import com.example.myapplication.R;

public class LoginActivity extends BaseActivity {
    private Button btn_signIn, btn_back;
    private ImageButton btn_changeLanguage, iBtn_google;
    private TextView tv_register;
    private LoadingAlert loadingAlert;
    private EditText et_user, et_password;
    private Handler handler;
    private ProgressBar pg_loading;
    private static final String tokenUser = "user";
    private static final String tokenPass = "123";

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
        // Initial all views
        setLangIcon();
        btn_signIn = findViewById(R.id.btn_signIn);
        btn_back = findViewById(R.id.btn_back);
        btn_changeLanguage = findViewById(R.id.btn_changeLanguage);
        tv_register = findViewById(R.id.tv_register);
        iBtn_google = findViewById(R.id.ib_google);
        loadingAlert = new LoadingAlert(LoginActivity.this);
        et_user = findViewById(R.id.et_user);
        et_password = findViewById(R.id.et_password);
        pg_loading = findViewById(R.id.pg_loading);
    }

    private void InitialEvent() {
        // Initial all even
        btn_back.setOnClickListener(view -> { // Back button
            // Open main activity
            openMainActivity();
            finish();
        });

        btn_signIn.setOnClickListener(view -> { // Sign in button
            // Open sign in method
            String user = String.valueOf(et_user.getText());
            String password = String.valueOf(et_password.getText());

            boolean isValidInformation = validateForm(user, password);
            if (isValidInformation) {
                btn_signIn.setVisibility(View.INVISIBLE);
                pg_loading.setVisibility(View.VISIBLE);
                authenticateUser(user, password);

                handler = new Handler(message -> { // Handle message
                    Bundle bundle = message.getData(); // Get message
                    boolean isOk = bundle.getBoolean("IS_OK"); // Get message data
                    if (!isOk) return false; // If not ok return

                    btn_signIn.setVisibility(View.VISIBLE);
                    pg_loading.setVisibility(View.INVISIBLE);
                    signInLog(getString(R.string.success_warning)); // Print message to user
                    openDashboardActivity(); // Open dashboard activity
                    finish(); // Finish Login activity

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
            },300);
        });

        tv_register.setOnClickListener(view -> { // Register text view
            // Open register activity
            openRegisterActivity();
            finish();
        });

        iBtn_google.setOnClickListener(view -> { // Sign in with google button
            // Open sign in with Google method
            loadingAlert.startAlertDialog();

            new Handler().postDelayed(() -> {
                loadingAlert.closeAlertDialog();
                signInWithGoogle();
            },1000);
        });
    }

    private boolean validateForm(String username, String password) {
        // Validate user input
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
    private void authenticateUser(String user, String password) {
        // Authentication
        CookieManager.getInstance().removeAllCookies(null); // Remove old cookies

//        webView.setVisibility(View.VISIBLE);
        WebView webView = new WebView(LoginActivity.this); // Create new web view
        webView.getSettings().setJavaScriptEnabled(true); // Enable evaluate javascript
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(GlobalVar.LOG_TAG, "onPageFinished: ");
                String dataError = "document.getElementsByClassName('helper-text')[0].getAttribute('data-error');"; // Appear when error

                view.evaluateJavascript(dataError, err -> {
                    if (err.equals("null")) { // If error
                        // Fill up sign in form
                        String usrScript = "document.getElementById('username').value='" + user + "';";
                        String pwdScript = "document.getElementById('password').value='" + password + "';";

                        view.evaluateJavascript(usrScript, null);
                        view.evaluateJavascript(pwdScript, null);
                        view.evaluateJavascript("document.getElementsByTagName('form')[0].submit();", null);
                    }
                    else {
                        Log.d(GlobalVar.LOG_TAG, "err: " + err);

                        btn_signIn.setVisibility(View.VISIBLE);
                        pg_loading.setVisibility(View.INVISIBLE);

                        signInLog(err); // Log error
                    }
                });

                if (url.contains("manager/#state=")) { // Login success, open dashboard
                    String code = url.split("&code=")[1];
                    Log.d(GlobalVar.LOG_TAG, "success: " + code);
                    getTokenByInfo();

                }

                Log.d(GlobalVar.LOG_TAG, "url : " + url);// Log url
                super.onPageFinished(view, url);
            }
        });

        webView.loadUrl(GlobalVar.baseUrl); // Loading url
    }

    private void getTokenByInfo() {
        // Get token if no token in local or token expired
        new Thread(() -> {
            long currTimeStamp = getTimeStamp(); // Get current time stamp
            Log.d(GlobalVar.LOG_TAG, "current timestamp: " + currTimeStamp); // Log current time stamp
            LocalDataManager.Init(LoginActivity.this); // Create local data manager

            boolean havingToken = LocalDataManager.getToken() != null;
            if (havingToken) {
                long remainingTimeStamp = LocalDataManager.getToken().getExpires_in() - currTimeStamp;
                boolean notExpired = remainingTimeStamp > 0;
                if (notExpired) {
                    Log.d(GlobalVar.LOG_TAG, "Token expired in: " + remainingTimeStamp); // Log remaining time
                }
                else { // If token expired
                    Token token = ApiManager.getToken(LoginActivity.tokenUser, LoginActivity.tokenPass); // Get token
                    assert token != null; // No null token
                    long expired = (token.getExpires_in() * 1000) + currTimeStamp; // Expired timestamp in milliseconds
                    token.setExpires_in(expired); // Set expired timestamp
                    LocalDataManager.setToken(token); // Save token to local
                }
            }
            else { // If no token found
                Token token = ApiManager.getToken(LoginActivity.tokenUser, LoginActivity.tokenPass); // Get token
                assert token != null; // No null token
                long expired = (token.getExpires_in() * 1000) + currTimeStamp; // Expired timestamp in milliseconds
                token.setExpires_in(expired); // Set expired timestamp
                LocalDataManager.setToken(token); // Save token to local
            }

            Message msg = handler.obtainMessage(); // Create message
            Bundle bundle = new Bundle(); // Create bundle
            bundle.putBoolean("IS_OK", true); // Put true to bundle
            msg.setData(bundle); // Set message data
            handler.sendMessage(msg);  // Send message through bundle
        }).start();
    }

    private void signInLog(String s) {
        Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
    }
}