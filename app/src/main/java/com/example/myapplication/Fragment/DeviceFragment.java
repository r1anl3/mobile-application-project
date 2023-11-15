package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Activity.DashboardActivity;
import com.example.myapplication.R;

public class DeviceFragment extends Fragment {
    DashboardActivity parentActivity;
    public DeviceFragment() {
        // Required empty public constructor
    }

    public DeviceFragment(DashboardActivity activity) {
        this.parentActivity = activity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_device, container, false);
    }
}