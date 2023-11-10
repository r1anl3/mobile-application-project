package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.API.ApiManager;
import com.example.myapplication.API.ApiService;
import com.example.myapplication.API.ApiClient;
import com.example.myapplication.GlobalVar;
import com.example.myapplication.LoadingAlert;
import com.example.myapplication.Model.Token;
import com.example.myapplication.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
//        webView = findViewById(R.id.wv_browser);
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
                loadingAlert.startAlertDialog();
//                    getToken(user, password);
                getTokenByInfo(user, password);
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
    private void getToken(String user, String password) {
        // Get login token
        CookieManager.getInstance().removeAllCookies(null); // Remove old cookies

//        webView.setVisibility(View.VISIBLE);
        webView = new WebView(LoginActivity.this); // Create new web view
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
                        loadingAlert.closeAlertDialog(); // Close loading
                        Log.d(GlobalVar.LOG_TAG, "err: " + err);
                        signInLog(err); // Log error
                    }
                });

                if (url.contains("manager/#state=")) { // Login success, open dashboard
                    loadingAlert.closeAlertDialog(); // Close loading
                    signInLog(getString(R.string.success_warning));
//                    String code = url.split("&code=")[1];
//                    getTokenByInfo(user, password);
//                    Log.d(GlobalVar.LOG_TAG, "success: " + code);
                    openDashboardActivity();
                    finish();
                }

                String cookies = CookieManager.getInstance().getCookie(url); // Log cookies
                Log.d(GlobalVar.LOG_TAG, "return cookie: " + cookies);
                Log.d(GlobalVar.LOG_TAG, "url : " + url);
                super.onPageFinished(view, url);
            }
        });

        webView.loadUrl(GlobalVar.baseUrl); // Loading url
    }

    private void getTokenByInfo(String user, String pass) {
        ApiManager manager = new ApiManager();
        manager.getToken(user, pass);
        loadingAlert.closeAlertDialog();
    }

    private void signInLog(String s) {
        Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
    }

}