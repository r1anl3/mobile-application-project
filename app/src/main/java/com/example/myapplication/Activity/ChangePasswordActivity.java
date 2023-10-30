package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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
            changePassword();
        });
    }

    private void changePassword() {
        // TODO: go to resetUrl, fill up change password form, submit form
    }

    private void signIn() {
        // TODO: sign in with username and password
    }
}