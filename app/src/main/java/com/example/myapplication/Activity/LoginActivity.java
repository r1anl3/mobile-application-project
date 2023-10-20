package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.LoadingAlert;
import com.example.myapplication.R;

public class LoginActivity extends BaseActivity {
    private Button btn_signIn;
    private Button btn_back;
    private ImageButton btn_changeLanguage;
    private TextView tv_register;
    private ImageButton iBtn_google;
    private LoadingAlert loadingAlert;

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
            loadingAlert.startAlertDialog();

            new Handler().postDelayed(() -> {
                loadingAlert.closeAlertDialog();
                onSignIn();
            },1000);
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
        //TODO: Check sign in status, get access permission to dashboard
        /*
            Do something here to be authorized by UIT
            Update isAuthorizedByUIT
        */
        isAuthorizedByUIT = false;

        if (isAuthorizedByUIT) {
            openDashboardActivity();
            finish();
        }
        else {
            // Pop up message show that "Can not sign in"
            Toast mToast = Toast.makeText(this, R.string.signup_warning, Toast.LENGTH_SHORT); // Warning user
            mToast.show();
        }
    }

}