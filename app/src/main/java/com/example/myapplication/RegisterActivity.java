package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends BaseActivity {
    private static final String TAG = "Register Activity";
    private Button btn_back;
    private Button btn_signUp;
    private Button btn_changeLanguage;
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
                // Return main activity on clicked
                //openMainActivity();
                RegisterActivity.super.onBackPressed();
            }
        });
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do sign up
                onSignUp();
            }
        });
        btn_changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change language on clicked
                onLanguageChange();
            }
        });
    }


    private void onSignUp() {
        //TODO: check sign up status, get access permission to dashboard
        /*
            Do something here to be authorized by UIT
            Update isAuthorizedByUIT
        */
        isAuthorizedByUIT = true;

        if (isAuthorizedByUIT) {
            openDashboardActivity();
        }
        else {
            // Pop up message show that "Can not sign up"
        }
    }

}