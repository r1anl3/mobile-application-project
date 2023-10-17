package com.example.myapplication.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;

public class MainActivity extends BaseActivity {
    private static final String TAG = "Main Activity";
    private final long delayTime = 2000;
    private long backPressedTime;
    private Toast mToast;
    private Button btn_signUp;
    private Button btn_signIn;
    private Button btn_changeLanguage;
    private Button btn_signInWithGoogle;
    private Button btn_resetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find components from layout
        btn_signUp = findViewById(R.id.btn_signUp);
        btn_signIn = findViewById(R.id.btn_signIn);
        btn_changeLanguage = findViewById(R.id.btn_changeLanguage);
        btn_signInWithGoogle = findViewById(R.id.btn_signIpWithGoogle);
        btn_resetPassword = findViewById(R.id.btn_resetPassword);

        // Set functions for components
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open register activity on clicked
                openRegisterActivity();
            }
        });

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open login activity on clicked
                openLogInActivity();
            }
        });

        btn_changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change language on clicked
                onLanguageChange();
            }
        });
        btn_resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Reset password on clicked
                onPasswordReset();
            }
        });
        btn_signInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open sign in with Google method
                signInWithGoogle();
            }
        });
    }

    private void onPasswordReset() {
        //TODO: reset password
        Log.d(TAG, "onPasswordReset: ");
    }

    @Override
    public void onBackPressed() {
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