package com.example.myapplication.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.R;

public class MainActivity extends BaseActivity {
    private static final String TAG = "Main Activity";
    private long backPressedTime;
    private Toast mToast;
    private Button btn_signUp;
    private Button btn_signIn;
    private Button btn_signInWithGoogle;
    private Button btn_resetPassword;
    private ImageButton btn_changeLanguage;
    private ProgressBar pg_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitialView();
        InitialEven();
    }

    private void InitialView() {
        //TODO: Initial all views
        btn_signUp = findViewById(R.id.btn_signUp);
        btn_signIn = findViewById(R.id.btn_signIn);
        btn_changeLanguage = findViewById(R.id.btn_changeLanguage);
        btn_signInWithGoogle = findViewById(R.id.btn_signIpWithGoogle);
        btn_resetPassword = findViewById(R.id.btn_resetPassword);
        pg_loading = findViewById(R.id.pb_loading);
    }

    private void InitialEven() {
        //TODO: Initial all events
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
            pg_loading.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                pg_loading.setVisibility(View.INVISIBLE);
                onLanguageChange();
            },300);
        });

        btn_resetPassword.setOnClickListener(view -> {
            // Open reset password method
            onPasswordReset();
        });

        btn_signInWithGoogle.setOnClickListener(view -> {
            // Open sign in with Google method
            pg_loading.setVisibility(View.VISIBLE);

            new Handler().postDelayed(() -> {
                pg_loading.setVisibility(View.INVISIBLE);
                signInWithGoogle();
            },1000);
        });
    }

    private void onPasswordReset() {
        //TODO: Reset password
        Log.d(TAG, "onPasswordReset: ");
    }

    @Override
    public void onBackPressed() {
        //TODO: Double click to exit app
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