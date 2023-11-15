package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.myapplication.Fragment.DeviceFragment;
import com.example.myapplication.Fragment.HomeFragment;
import com.example.myapplication.Fragment.MapFragment;
import com.example.myapplication.Fragment.UserFragment;
import com.example.myapplication.R;

public class DashboardActivity extends AppCompatActivity {
    private FragmentManager fm;
    private HomeFragment homeFrag;
    private DeviceFragment devFrag;
    private MapFragment mapFrag;
    private UserFragment userFrag;
    private ImageView ic_home, ic_dev, ic_map, ic_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        InitialVars();
        InitialViews();
        InitialEvents();

        fm.beginTransaction().add(R.id.main_frame, homeFrag, "home").commit(); // Add home fragment on create
//        Fragment fragment = homeFrag;
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
        ic_home.setOnClickListener(view -> replaceFragment(homeFrag, 0)); // Replace home fragment
        ic_dev.setOnClickListener(view -> replaceFragment(devFrag, 1)); // Replace device fragment
        ic_map.setOnClickListener(view -> replaceFragment(mapFrag, 2)); // Replace map fragment
        ic_user.setOnClickListener(view -> replaceFragment(userFrag, 3)); // Replace user fragment
    }

    private void replaceFragment(Fragment fragment, int num) {
        // TODO: Replace fragment, kill old fragment
        Log.d("something", "replaceFragment: " + num);
        FragmentTransaction transaction = fm.beginTransaction(); // Create Fragment transaction
        transaction.replace(R.id.main_frame, fragment); // Replace fragment
        transaction.addToBackStack(null); // Add transaction to backstack
        transaction.commit(); // Perform transaction
    }


}