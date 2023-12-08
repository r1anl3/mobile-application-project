package com.example.myapplication.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Manager.LanguageManager;
import com.example.myapplication.R;

import java.sql.Timestamp;
import java.util.Objects;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "Base Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLanguageChange() {
        // Change language
        LanguageManager manager = new LanguageManager(BaseActivity.this);
        String currLang = manager.checkResource();

        if (Objects.equals(currLang, "en")) {
            // if language is English, change to Vietnamese
            manager.updateResource("vi");
        }
        else if (Objects.equals(currLang, "vi")){
            // if language is Vietnamese, change to English
            manager.updateResource("en");
        }
        recreate(); // recreate the build
    }

    public void setLangIcon() {
        // Synchronize with app current language
        LanguageManager manager = new LanguageManager(this);
        ImageButton button = findViewById(R.id.btn_changeLanguage);
        String devLang = manager.checkResource();
        manager.updateIcon(devLang, button);
        Log.d(TAG, "setLangIcon: " + devLang);
    }

    public void openMainActivity() {
        // Open main activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void openRegisterActivity() {
        // Open register activity
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivities(new Intent[]{intent});
    }

    public void openLogInActivity() {
        // Open login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivities(new Intent[]{intent});
    }

    public void openDashboardActivity() {
        // Open dashboard activity
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivities(new Intent[]{intent});
    }

    public void openChangePasswordActivity() {
        // Open Change password activity
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        startActivities(new Intent[]{intent});
    }

    boolean isEmail(EditText text) {
        // Check email format
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public long getTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Get system timestamp in milliseconds
        return timestamp.getTime();
    }
}

