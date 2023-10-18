package com.example.myapplication.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;

public class MainActivity extends BaseActivity {
    private static final String TAG = "Main Activity";
    private long backPressedTime;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find components from layout
        Button btn_signUp = findViewById(R.id.btn_signUp);
        Button btn_signIn = findViewById(R.id.btn_signIn);
        Button btn_changeLanguage = findViewById(R.id.btn_changeLanguage);
        Button btn_signInWithGoogle = findViewById(R.id.btn_signIpWithGoogle);
        Button btn_resetPassword = findViewById(R.id.btn_resetPassword);

        // Set functions for components
        btn_signUp.setOnClickListener(view -> {
            // Open register activity on clicked
            openRegisterActivity();
        });

        btn_signIn.setOnClickListener(view -> {
            // Open login activity on clicked
            openLogInActivity();
        });

        btn_changeLanguage.setOnClickListener(view -> {
            // Change language on clicked
            onLanguageChange();
        });
        btn_resetPassword.setOnClickListener(view -> {
            // Reset password on clicked
            onPasswordReset();
        });
        btn_signInWithGoogle.setOnClickListener(view -> {
            // Open sign in with Google method
            signInWithGoogle();
        });
    }

    private void onPasswordReset() {
        //TODO: reset password
        Log.d(TAG, "onPasswordReset: ");
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