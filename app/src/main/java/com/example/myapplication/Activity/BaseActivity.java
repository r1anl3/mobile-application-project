package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Manager.LanguageManager;
import com.example.myapplication.R;

import java.util.Objects;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "Base Activity";
    public boolean isAuthorizedByGoogle = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivities(new Intent[]{intent});
    }

    public void signInWithGoogle() {
        // Authenticate by google
        /*
            Do something here to be authorized by Google
            Update isAuthorizedByGoogle
         */
        isAuthorizedByGoogle = true;

        if (isAuthorizedByGoogle) {
            openDashboardActivity();
            finish();
        }
        else {
            // Pop up message show that "Can not sign in"
            Toast mToast = Toast.makeText(this, R.string.signup_warning, Toast.LENGTH_SHORT); // Warning user
            mToast.show();
        }
    }

    boolean isEmail(EditText text) {
        // Check email format
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

}

