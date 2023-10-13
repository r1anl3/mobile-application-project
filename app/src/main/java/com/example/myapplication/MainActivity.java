package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
    private static final String TAG = "Main Activity";
    private boolean isBackPressedOnce = false;
    Button btn_signUp;
    Button btn_signIn;
    Button btn_changeLanguage;
    Button btn_signInWithGoogle;
    Button btn_resetPassword;

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
        // Override back button press
        if (isBackPressedOnce) {
            // Disable back button press once
            super.onBackPressed();
            return;
        }

        Toast.makeText(this, R.string.press_alert, Toast.LENGTH_SHORT).show(); // Alert on double press
        isBackPressedOnce = true; // Second press can use onBackPressed

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // After 2 second without second press, reset value
                isBackPressedOnce = false;
            }
        }, 2000); // Delay 2 second
    }

}