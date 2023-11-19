package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.API.ApiManager;
import com.example.myapplication.Activity.DashboardActivity;
import com.example.myapplication.GlobalVar;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

public class HomeFragment extends Fragment {
    DashboardActivity parentActivity;
    private TextView tv_greed;
    private TextView tv_weaSta;
    private ImageView img_currWea;
    private TextView tv_dateTime;
    private TextView tv_temp;
    private TextView tv_highLow;
    private TextView tv_rainfall;
    private TextView tv_humid;
    private TextView tv_windSpeed;
    private TextView tv_windDir;
    private TextView tv_manufacture;
    private TextView tv_location;
    Handler handler;
    public HomeFragment() {
    }

    public HomeFragment(DashboardActivity activity) {
        this.parentActivity = activity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        InitialViews(view);
        InitialEvents();

        handler = new Handler(message -> { // Handle message
            Bundle bundle = message.getData(); // Get message
            boolean isOk = bundle.getBoolean("IS_OK"); // Get message data
            if (!isOk) return false; // If not ok

            setInfo(); // Set info

            return false;
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void InitialViews(View view) {
        // Initial views
        tv_dateTime = view.findViewById(R.id.tv_dateTime);
        tv_greed = view.findViewById(R.id.tv_greed);
        tv_weaSta = view.findViewById(R.id.tv_weaSta);
        img_currWea = view.findViewById(R.id.img_currWea);
        tv_temp = view.findViewById(R.id.tv_temp);
        tv_highLow = view.findViewById(R.id.tv_highLow);
        tv_rainfall = view.findViewById(R.id.tv_rainfallPercent);
        tv_humid = view.findViewById(R.id.tv_humidPercent);
        tv_windSpeed = view.findViewById(R.id.tv_windSpeed);
        tv_windDir = view.findViewById(R.id.tv_windDir);
        tv_manufacture = view.findViewById(R.id.tv_manufacture);
        tv_location = view.findViewById(R.id.tv_location);
    }

    private void InitialEvents() {
        // Initial events
        getInfo(); // Get information
    }

    private void getInfo() {
        // TODO: Get information about user, weather assests
        new Thread(() -> { // new thread
            if (User.getMe() == null) { // If not user
                ApiManager.getUser();
            }

            Message msg = handler.obtainMessage(); // Create message
            Bundle bundle = new Bundle(); // Create bundle
            bundle.putBoolean("IS_OK", true); // Put data to bundle
            msg.setData(bundle); // Set message data
            handler.sendMessage(msg);  // Send message through bundle
        }).start();
    }

    private void setInfo() {
        // TODO: Set information about user, weather assets
        tv_greed.setText("Hi " + User.getMe().getUsername()); // Set username
    }
}