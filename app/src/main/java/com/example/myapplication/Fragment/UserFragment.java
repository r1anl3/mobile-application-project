package com.example.myapplication.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.API.ApiManager;
import com.example.myapplication.Activity.DashboardActivity;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserFragment extends Fragment {
    DashboardActivity parentActivity;
    TextView tv_username, tv_firstName, tv_lastName, tv_email, tv_enable, tv_CreateOn, tv_realm;
    Handler handler;
    public UserFragment() {
        // Required empty public constructor
    }

    public UserFragment(DashboardActivity activity) {
        this.parentActivity = activity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
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

    private void setInfo() {
        boolean hasUserInfo = User.getMe() != null;
        if (hasUserInfo) {
            String username = User.getMe().getUsername();
            String firstName = User.getMe().getFirstName();
            String lastName = User.getMe().getLastName();
            String email = User.getMe().getEmail();
            String enable = String.valueOf(User.getMe().isEnabled());
            String createdOn = getCreatedOn();
            String realm = User.getMe().getRealm();

            tv_username.setText(username);
            tv_firstName.setText(firstName);
            tv_lastName.setText(lastName);
            tv_email.setText(email);
            tv_enable.setText(enable);
            tv_CreateOn.setText(createdOn);
            tv_realm.setText(realm);
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String getCreatedOn() {
        Date date = new Date(User.getMe().getCreatedOn()); // Get expired date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm"); // Date formatter
        return dateFormat.format(date); // Return formatted date
    }

    private void InitialEvents() {
        getInfo();
    }

    private void getInfo() {
        new Thread(() -> { // new thread
            if (User.getMe() == null) {
                ApiManager.getUser(); // Get user
            }

            Message msg = handler.obtainMessage(); // Create message
            Bundle bundle = new Bundle(); // Create bundle
            bundle.putBoolean("IS_OK", true); // Put data to bundle
            msg.setData(bundle); // Set message data
            handler.sendMessage(msg);  // Send message through bundle
        }).start();
    }

    private void InitialViews(View view) {
        tv_username = view.findViewById(R.id.tv_username);
        tv_CreateOn = view.findViewById(R.id.tv_createOn);
        tv_firstName = view.findViewById(R.id.tv_firstName);
        tv_lastName = view.findViewById(R.id.tv_lastName);
        tv_email = view.findViewById(R.id.tv_email);
        tv_enable = view.findViewById(R.id.tv_enabled);
        tv_realm = view.findViewById(R.id.tv_realm);
    }
}