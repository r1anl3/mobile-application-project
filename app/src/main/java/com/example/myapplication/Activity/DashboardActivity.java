package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.myapplication.API.ApiClient;
import com.example.myapplication.Fragment.DeviceFragment;
import com.example.myapplication.Fragment.HomeFragment;
import com.example.myapplication.Fragment.MapFragment;
import com.example.myapplication.Fragment.UserFragment;
import com.example.myapplication.GlobalVar;
import com.example.myapplication.Manager.LocalDataManager;
import com.example.myapplication.R;

public class DashboardActivity extends BaseActivity {
    private FragmentManager fm;
    private HomeFragment homeFrag;
    private DeviceFragment devFrag;
    private MapFragment mapFrag;
    private UserFragment userFrag;
    private Fragment currFrag;
    private ImageView ic_home, ic_dev, ic_map, ic_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        InitialVars();
        InitialViews();
        InitialEvents();

        fm.beginTransaction().add(R.id.main_frame, homeFrag, "home").commit(); // Add home fragment on create
        currFrag = homeFrag;
    }


    private void InitialVars() {
        // Initial variables
        fm = getSupportFragmentManager(); // Create Fragment manager
        homeFrag = new HomeFragment(DashboardActivity.this); // Create Home fragment
        devFrag = new DeviceFragment(DashboardActivity.this);// Create Device fragment
        mapFrag = new MapFragment(DashboardActivity.this); // Create Map fragment
        userFrag = new UserFragment(DashboardActivity.this); // Create User fragment
    }

    private void InitialViews() {
        // Initial views
        ic_home = findViewById(R.id.ic_home); // Find home icon
        ic_dev = findViewById(R.id.ic_dev); // Find device icon
        ic_map = findViewById(R.id.ic_map); // Find map icon
        ic_user = findViewById(R.id.ic_user); // Find user icon
    }

    private void InitialEvents() {
        // Initial events
        setApiToken(); // Set Api token from local to ApiClient
        ic_home.setOnClickListener(view -> replaceFragment(homeFrag)); // Replace home fragment
        ic_dev.setOnClickListener(view -> replaceFragment(devFrag)); // Replace device fragment
        ic_map.setOnClickListener(view -> replaceFragment(mapFrag)); // Replace map fragment
        ic_user.setOnClickListener(view -> replaceFragment(userFrag)); // Replace user fragment
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

    private void replaceFragment(Fragment fragment) {
        // Replace fragment, kill old fragment
        currFrag = fragment; // Update current fragment
        FragmentTransaction transaction = fm.beginTransaction(); // Create Fragment transaction
        transaction.replace(R.id.main_frame, fragment, null); // Replace fragment
        transaction.addToBackStack(null); // Add transaction to backstack
        transaction.commit(); // Perform transaction
    }

    @Override
    public void onBackPressed() {
        if (currFrag != homeFrag) { // If not home fragment
            replaceFragment(homeFrag); // Replace home fragment
        }
        else { // In home fragment
            openMainActivity(); // open main activity
            finish(); // End Dashboard activity
        }
    }
}