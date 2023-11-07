package com.example.myapplication.Activity;

import android.os.Bundle;
import android.os.Handler;

import com.example.myapplication.R;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            openMainActivity();
            finish();
        },2000);
    }
}