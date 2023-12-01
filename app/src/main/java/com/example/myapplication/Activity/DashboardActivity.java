package com.example.myapplication.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.API.ApiClient;
import com.example.myapplication.Fragment.ChartFragment;
import com.example.myapplication.Fragment.FeatureFragment;
import com.example.myapplication.Fragment.MapFragment;
import com.example.myapplication.Fragment.UserFragment;
import com.example.myapplication.GlobalVar;
import com.example.myapplication.Manager.LocalDataManager;
import com.example.myapplication.R;
import com.example.myapplication.Service.ForegroundService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends BaseActivity {
    private FragmentManager fm;
    private FeatureFragment featureFrag;
    private ChartFragment chartFrag;
    private MapFragment mapFrag;
    private UserFragment userFrag;
    private Fragment currFrag;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        InitialVars();
        InitialViews();
        InitialEvents();

        fm.beginTransaction().add(R.id.main_frame, mapFrag, null).commit(); // Add home fragment on create
        currFrag = mapFrag;
    }


    private void InitialVars() {
        // Initial variables
        fm = getSupportFragmentManager(); // Create Fragment manager
        featureFrag = new FeatureFragment(DashboardActivity.this); // Create Home fragment
        chartFrag = new ChartFragment(DashboardActivity.this);// Create Device fragment
        mapFrag = new MapFragment(DashboardActivity.this); // Create Map fragment
        userFrag = new UserFragment(DashboardActivity.this); // Create User fragment
    }

    private void InitialViews() {
        // Initial views
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setBackground(null);
    }

    private void InitialEvents() {
        // Initial events
        setApiToken(); // Set Api token from local to ApiClient
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                replaceFragment(mapFrag);
            } else if (itemId == R.id.feature) {
                replaceFragment(featureFrag);
            } else if (itemId == R.id.chart) {
                replaceFragment(chartFrag);
            } else if (itemId == R.id.user) {
                replaceFragment(userFrag);
            }
            return true;
        });
        startForegroundService(new Intent(this, ForegroundService.class));
        checkForegroundServiceRunning();
    }

    public boolean checkForegroundServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo info : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (ForegroundService.class.getName().equals(info.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void setApiToken() {
        // Set api token from local database
        LocalDataManager.Init(DashboardActivity.this); // Create manager
        Log.d(GlobalVar.LOG_TAG, "Token local: " + LocalDataManager
                .getToken()
                .getAccess_token());  // Log token
        ApiClient.token = LocalDataManager
                .getToken()
                .getAccess_token(); // Set token to ApiClient
        Log.d(GlobalVar.LOG_TAG, "ApiClient Token: " + ApiClient.token); // Log ApiClient Token
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
        if (currFrag != mapFrag) { // If not home fragment
            replaceFragment(mapFrag); // Replace home fragment
        }
        else { // In home fragment
            openMainActivity(); // open main activity
            finish(); // End Dashboard activity
        }
    }
}