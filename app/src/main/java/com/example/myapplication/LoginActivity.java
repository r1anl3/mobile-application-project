package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    Button btn_signIn;
    Button btn_back;
    Button btn_changeLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        btn_signIn = findViewById(R.id.btn_signIn);
//        btn_back = findViewById(R.id.btn_back);
        btn_changeLanguage = findViewById(R.id.btn_changeLanguage);

//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openMainActivity();
//            }
//        });
//        btn_signIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onSignIn();
//            }
//        });
        btn_changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLanguageChange();
            }
        });
    }

    private void onLanguageChange() {
        Log.d("something", "onLanguageChange: ");
    }

    private void onSignIn() {
        Log.d("something", "onSignIn: ");
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivities(new Intent[]{intent});
    }
}