package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends BaseActivity {
    Button btn_signIn;
    Button btn_back;
    Button btn_changeLanguage;
    TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check the initial orientation
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Load the landscape layout
            setContentView(R.layout.activity_login_land);
        } else {
            // Load the portrait layout
            setContentView(R.layout.activity_login);
        }
        btn_signIn = findViewById(R.id.btn_signIn);
        btn_back = findViewById(R.id.btn_back);
        btn_changeLanguage = findViewById(R.id.btn_changeLanguage);
        tv_register = findViewById(R.id.tv_register);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignIn();
            }
        });
        btn_changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLanguageChange();
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });
    }

    private void onSignIn() {

        Log.d("something", "onSignIn: ");
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Check the orientation
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Load the landscape layout
            setContentView(R.layout.activity_login_land);
        } else {
            // Load the portrait layout
            setContentView(R.layout.activity_login);
        }
    }
}