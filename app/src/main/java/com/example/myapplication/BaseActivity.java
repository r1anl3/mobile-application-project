package com.example.myapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "Base Activity";
    public boolean isAuthorizedByUIT = false;
    public boolean isAuthorizedByGoogle = false;

    //BASE CLASS FOR INHERITED FUNCTIONS
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onLanguageChange() {
        //TODO: change language
        LanguageManager manager = new LanguageManager(this);
        String currLang = manager.checkResource(); // check current device language

        if (Objects.equals(currLang, "en")) {
            // if language is English, change to Vietnamese
            manager.updateResource("vi");
        }
        else {
            // if language is Vietnamese, change to English
            manager.updateResource("en");
        }
        recreate(); // recreate the build
    }

    public void openMainActivity() {
        //TODO: open main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivities(new Intent[]{intent});
    }

    public void openRegisterActivity() {
        //TODO: open register activity
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivities(new Intent[]{intent});
    }

    public void openLogInActivity() {
        //TODO: open login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivities(new Intent[]{intent});
    }

    public void  openDashboardActivity() {
        //TODO: open dashboard activity
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivities(new Intent[]{intent});
    }
    public void signInWithGoogle() {
        /*
            Do something here to be authorized by Google
            Update isAuthorizedByGoogle
         */
        isAuthorizedByGoogle = true;

        if (isAuthorizedByGoogle) {
            openDashboardActivity();
        }
        else {
            // Pop up message show that "Can not sign in"
        }
    }
}
