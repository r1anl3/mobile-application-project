package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.Activity.DashboardActivity;
import com.example.myapplication.R;
public class ErrorFragment extends Fragment {
    DashboardActivity parentActivity;
    private Button btn_retry;
    private LottieAnimationView pg_loading;
    private TextView tv_error;

    public ErrorFragment() {
        // Required empty public constructor
    }

    public ErrorFragment(DashboardActivity activity) {
        this.parentActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_error, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        InitialView(view);
        InitialEvent();

        super.onViewCreated(view, savedInstanceState);
    }

    private void InitialView(View view) {
        pg_loading = view.findViewById(R.id.pg_loading);
        btn_retry = view.findViewById(R.id.btn_retry);
        tv_error = view.findViewById(R.id.tv_error);
    }

    private void InitialEvent() {
        btn_retry.setOnClickListener(view -> {
            Log.d("something", "retry onClick: ");
            pg_loading.setVisibility(View.INVISIBLE);
            tv_error.setVisibility(View.INVISIBLE);
            btn_retry.setVisibility(View.INVISIBLE);
            parentActivity.openDashboardActivity();
            parentActivity.finish();
        });
    }
}