package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingAlert {
    Activity activity;
    AlertDialog dialog;
    public LoadingAlert(Activity thisActivity) {
        activity = thisActivity;
    }

    public void startAlertDialog() {
        //TODO: Start alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.arlert_loading, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    public void closeAlertDialog() {
        //TODO: End alert dialog
        dialog.dismiss();
    }
}
