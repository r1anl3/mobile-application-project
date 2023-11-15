package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.myapplication.Fragment.HomeFragment;
import com.example.myapplication.R;

public class DashboardActivity extends AppCompatActivity {
    private FragmentManager fm;
    public HomeFragment homeFrag;
    private Fragment fragment = null;
    int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        InitialVars();
        InitialViews();
        InitialEvents();
    }

    private void InitialEvents() {
        fm.beginTransaction().add(R.id.main_frame, homeFrag, "home").commit();
        fragment = homeFrag;
    }

    private void InitialViews() {
    }

    private void InitialVars() {
        fm = getSupportFragmentManager();
        homeFrag = new HomeFragment(DashboardActivity.this);
    }
}