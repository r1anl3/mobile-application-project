package com.example.myapplication.Activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "Login Activity";
    private Button btn_signIn;
    private Button btn_back;
    private Button btn_changeLanguage;
    private TextView tv_register;
    private ImageButton iBtn_google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check the initial orientation
        int recentOrient = getResources().getConfiguration().orientation;

        if (recentOrient == Configuration.ORIENTATION_LANDSCAPE) {
            // Load the landscape layout
            setContentView(R.layout.activity_login_land);
        } else {
            // Load the portrait layout
            setContentView(R.layout.activity_login);
        }

        // Find components from layout
        btn_signIn = findViewById(R.id.btn_signIn);
        btn_back = findViewById(R.id.btn_back);
        btn_changeLanguage = findViewById(R.id.btn_changeLanguage);
        tv_register = findViewById(R.id.tv_register);
        iBtn_google = findViewById(R.id.ib_google);

        // Set functions for components
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Return main activity
                // openMainActivity();
                LoginActivity.super.onBackPressed();
            }
        });
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do sign in
                onSignIn();
            }
        });
        btn_changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change language on click
                onLanguageChange();
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open register activity
                openRegisterActivity();
            }
        });
        iBtn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open sign in with Google method
                signInWithGoogle();
            }
        });
    }

    private void onSignIn() {
        //TODO: check sign in status, get access permission to dashboard
        /*
            Do something here to be authorized by UIT
            Update isAuthorizedByUIT
        */
        isAuthorizedByUIT = true;

        if (isAuthorizedByUIT) {
            openDashboardActivity();
        }
        else {
            // Pop up message show that "Can not sign in"
        }
    }

}