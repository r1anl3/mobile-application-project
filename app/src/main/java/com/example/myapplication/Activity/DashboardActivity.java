package com.example.myapplication.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.Fragment.ChartFragment;
import com.example.myapplication.Fragment.ErrorFragment;
import com.example.myapplication.Fragment.FeatureFragment;
import com.example.myapplication.Fragment.MapFragment;
import com.example.myapplication.Fragment.UserFragment;
import com.example.myapplication.R;
import com.example.myapplication.Service.ForegroundService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DashboardActivity extends BaseActivity {
    private FragmentManager fm;
    private FeatureFragment featureFrag;
    private ChartFragment chartFrag;
    private MapFragment mapFrag;
    private UserFragment userFrag;
    private ErrorFragment errorFrag;
    private Fragment currFrag;
    private BottomNavigationView bottomNavigationView;
    private LottieAnimationView pg_loading;
    private FloatingActionButton btn_scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        InitialVars();
        InitialViews();
        InitialEvents();
    }


    private void InitialVars() {
        // Initial variables
        fm = getSupportFragmentManager(); // Create Fragment manager
        chartFrag = new ChartFragment(DashboardActivity.this);// Create Device fragment
        mapFrag = new MapFragment(DashboardActivity.this); // Create Map fragment
        userFrag = new UserFragment(DashboardActivity.this); // Create User fragment
        errorFrag = new ErrorFragment(DashboardActivity.this);
    }

    private void InitialViews() {
        // Initial views
        bottomNavigationView = findViewById(R.id.bottom_nav);
        pg_loading = findViewById(R.id.pg_loading);
        btn_scanner = findViewById(R.id.btn_scanner);
    }

    private void InitialEvents() {
        // Initial events
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                replaceFragment(mapFrag);
            } else if (itemId == R.id.feature) {
                featureFrag = new FeatureFragment(DashboardActivity.this); // Create Home fragment
                replaceFragment(featureFrag);
            } else if (itemId == R.id.chart) {
                replaceFragment(chartFrag);
            } else if (itemId == R.id.user) {
                replaceFragment(userFrag);
            }
            return true;
        });
        btn_scanner.setEnabled(false);
        new Handler().postDelayed(() -> {
            pg_loading.setVisibility(View.INVISIBLE);

            if (ForegroundService.isApiOk) {
                bottomNavigationView.setVisibility(View.VISIBLE);
                btn_scanner.setEnabled(true);
                replaceFragment(mapFrag);
            } else {
                replaceFragment(errorFrag);
            }
        }, 4000);
    }

    public void replaceFragment(Fragment fragment) {
        // Replace fragment, kill old fragment
        currFrag = fragment; // Update current fragment
        FragmentTransaction transaction = fm.beginTransaction(); // Create Fragment transaction
        transaction.replace(R.id.main_frame, fragment, null); // Replace fragment
        transaction.addToBackStack(null); // Add transaction to backstack
        transaction.commit(); // Perform transaction
    }

    @Override
    public void onBackPressed() {
        if (currFrag != null) {
            openMainActivity();
            finish();
        }
    }
}