package com.example.myapplication.Activity;

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
    private static final String TAG = "Register Activity";
    private Toast mToast;
    private Button btn_back;
    private Button btn_signUp;
    private Button btn_changeLanguage;
    private EditText et_username;
    private EditText et_email;
    private EditText et_password;
    private EditText et_rePassword;
    private ProgressBar pg_loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Find components from layout
        btn_back = findViewById(R.id.btn_back);
        btn_signUp = findViewById(R.id.btn_signUp);
        btn_changeLanguage = findViewById(R.id.btn_changeLanguage);

        // Set functions for components
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Return main activity on clicked
                RegisterActivity.super.onBackPressed();
            }
        });
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Validate user form, sign up to main dashboard
                boolean isValidInformation = validateForm();

                if (isValidInformation) { // If information is valid
                    // Show loading screen
                    pg_loading = findViewById(R.id.pb_loading);
                    pg_loading.setVisibility(View.VISIBLE);

                    Handler handler = new Handler(); // Add event handler
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // After delay time,
                            pg_loading.setVisibility(View.INVISIBLE); // Set progress bar to invisible
                            onSignUp(); // Call onSignUp method
                        }
                    }, 1000); // Delay time
                }
            }
        });
        btn_changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Change language on clicked
                onLanguageChange();
            }
        });

        WebView webView = new WebView(this);
    }

    private boolean validateForm() {
        //TODO: Validate user information
        et_username = findViewById(R.id.et_username); // Get username Edit Text
        et_email = findViewById(R.id.et_mail); // Get email Edit Text
        et_password = findViewById(R.id.et_password); // Get password Edit Text
        et_rePassword = findViewById(R.id.et_rePassword); //Get rePassword Edit Text

        String username = et_username.getText().toString(); // Extract username
        String email = et_email.getText().toString(); // Extract email
        String password = et_password.getText().toString(); // Extract password
        String rePassword = et_rePassword.getText().toString(); // Extract rePassword

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || rePassword.isEmpty()) { // If one field is empty
            mToast = Toast.makeText(this, R.string.form_warning, Toast.LENGTH_SHORT); // Warning user
            mToast.show();
            return false; // User information is not valid
        }
        if (!password.equals(rePassword)) { // If password retype do not match
            mToast = Toast.makeText(this, R.string.password_warning, Toast.LENGTH_SHORT); // Warning user
            mToast.show();
            return false; // User information is not valid
        }

        mToast.cancel();
        return true; // User information is valid
    }


    private void onSignUp() {
        //TODO: check sign up status, get access permission to dashboard
        /*
            Do something here to be authorized by UIT
            Update isAuthorizedByUIT
        */
        isAuthorizedByUIT = onAuthorize();

        if (isAuthorizedByUIT) {
            openDashboardActivity(); // Go to dashboard
            mToast.cancel();
        }
        else {
            // Pop up message show that "Can not sign up"
            mToast = Toast.makeText(this, R.string.signup_warning, Toast.LENGTH_SHORT); // Warning user
            mToast.show();
        }
    }

    private boolean onAuthorize() {
        //TODO: POST to get authorized by UIT
        boolean isAuthorized = false;
        WebView webView = findViewById(R.id.wv_webView);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

            }
        });

        return isAuthorized;
    }

}