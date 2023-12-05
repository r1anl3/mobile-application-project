package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.GlobalVar;
import com.example.myapplication.LoadingAlert;
import com.example.myapplication.Manager.LocalDataManager;
import com.example.myapplication.R;

public class MainActivity extends BaseActivity {
    private long backPressedTime;
    private Toast mToast;
    private Button btn_signUp, btn_signIn, btn_noSignIn, btn_resetPassword;
    private ImageButton btn_changeLanguage;
    private LoadingAlert loadingAlert;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Lock orientation

        InitialView();
        InitialEvent();
    }

    private void InitialView() {
        // Initial all views
        setLangIcon();
        btn_signUp = findViewById(R.id.btn_signUp);
        btn_signIn = findViewById(R.id.btn_signIn);
        btn_changeLanguage = findViewById(R.id.btn_changeLanguage);
        btn_noSignIn = findViewById(R.id.btn_noSignIn);
        btn_resetPassword = findViewById(R.id.btn_resetPassword);
        loadingAlert = new LoadingAlert(MainActivity.this);
    }

    private void InitialEvent() {
        // Initial all events
        btn_signUp.setOnClickListener(view -> {
            // Open register activity
            openRegisterActivity();
        });

        btn_signIn.setOnClickListener(view -> {
            // Open login activity
            openLogInActivity();
        });

        btn_changeLanguage.setOnClickListener(view -> {
            // Open change language method
            loadingAlert.startAlertDialog();

            new Handler().postDelayed(() -> {
                loadingAlert.closeAlertDialog();
                onLanguageChange();
            },1000);
        });

        btn_resetPassword.setOnClickListener(view -> {
            // Open reset password method
            onPasswordReset();
        });

        btn_noSignIn.setOnClickListener(view -> {
            // Open sign in with Google method
            loadingAlert.startAlertDialog();
            
            boolean isValidCredential = checkCredential(); // Check credential validity
            new Handler().postDelayed(() -> {
                loadingAlert.closeAlertDialog();

                if (isValidCredential) {
                    openDashboardActivity();
                    finish();
                } else {
                    Toast.makeText(this, R.string.continue_log, Toast.LENGTH_SHORT).show();
                }
            }, 1000);
        });
    }

    private boolean checkCredential() {
        long currTimeStamp = getTimeStamp(); // Get current time stamp
        Log.d(GlobalVar.LOG_TAG, "current timestamp: " + currTimeStamp); // Log current time stamp
        LocalDataManager.Init(MainActivity.this); // Create local data manager

        boolean havingToken = LocalDataManager.getToken() != null;
        if (havingToken) {
            long remainingTimeStamp = LocalDataManager.getToken().getExpires_in() - currTimeStamp;
            boolean notExpired = remainingTimeStamp > 0;
            if (notExpired) {
                Log.d(GlobalVar.LOG_TAG, "Token expired in: " + remainingTimeStamp); // Log remaining time
                return true;
            }
        }
        return false;
    }

    private void onPasswordReset() {
        openChangePasswordActivity();
        finish();
    }

    @Override
    public void onBackPressed() {
        long delayTime = 2000;
        if (backPressedTime + delayTime > System.currentTimeMillis()) { // backPressedTime = delayTime
            mToast.cancel(); // Cancel toast after exit
            super.onBackPressed();
        }
        else {
           mToast = Toast.makeText(this, R.string.press_alert, Toast.LENGTH_SHORT); // Set text for toast
           mToast.show();
        }

        backPressedTime = System.currentTimeMillis(); // backPressedTime = System
    }
}