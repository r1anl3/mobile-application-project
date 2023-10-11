package com.example.myapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    public void onLanguageChange() {
        Log.d("something", "onLanguageChange: EN");
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivities(new Intent[]{intent});
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivities(new Intent[]{intent});
    }

    public void openLogInActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivities(new Intent[]{intent});
    }
}
