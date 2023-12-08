package com.example.myapplication.Service;

import android.content.Context;
import android.content.Intent;

import java.util.Objects;

public class BroadcastReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isActionCompleted = Objects.equals(intent.getAction(), Intent.ACTION_BOOT_COMPLETED);

        if (isActionCompleted) {
            context.startForegroundService(new Intent(context, ForegroundService.class));
        }
    }
}
