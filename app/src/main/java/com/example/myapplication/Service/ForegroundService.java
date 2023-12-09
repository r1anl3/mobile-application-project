package com.example.myapplication.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.util.Log;

import com.example.myapplication.API.ApiClient;
import com.example.myapplication.API.ApiManager;
import com.example.myapplication.GlobalVar;
import com.example.myapplication.Manager.LocalDataManager;
import com.example.myapplication.Model.Asset;
import com.example.myapplication.Model.Attribute;
import com.example.myapplication.Model.Device;
import com.example.myapplication.Model.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ForegroundService extends Service {
    public static ArrayList<Float> aTemp = new ArrayList<>();
    public static ArrayList<Float> aHumid = new ArrayList<>();
    public static ArrayList<Float> aRain = new ArrayList<>();
    public static ArrayList<Float> aSpeed = new ArrayList<>();
    public static boolean isApiOk = false;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(() -> {
            isApiOk = false;

            while (isNetworkConnected()) {
                Log.d(GlobalVar.LOG_TAG, "Collecting in background...");
                try {
                    if (checkCredential()) {
                        if (Device.getDevicesList() == null || Device.getDevicesList().size() == 0) {
                            String queryString = "{ \"realm\": { \"name\": \"master\" }}";
                            JsonObject query = JsonParser.parseString(queryString).getAsJsonObject();
                            ApiManager.queryDevices(query);
                        }

                        assert Device.getDevicesList() != null;
                        String deviceId = Device.getDevicesList().get(0).getId();
                        Log.d(GlobalVar.LOG_TAG, "Try collecting: " + deviceId);

                        if (User.getMe() == null) {
                            ApiManager.getUser();
                        }

                        ApiManager.getAsset(deviceId);

                        if (Asset.getMe() != null) {
                            Attribute attribute = Asset.getMe().getAttributes();
                            String location = attribute.getPlace().getValue();
                            float humid = attribute.getHumidity().getValue();
                            float temp = attribute.getTemperature().getValue();
                            float windSpeed = attribute.getWindSpeed().getValue();
                            float rainFall = attribute.getRainfall().getValue();

                            aTemp.add(temp);
                            aHumid.add(humid);
                            aSpeed.add(windSpeed);
                            aRain.add(rainFall);

                            isApiOk = true;

                            try {
                                ApiManager.postLamp(location, humid, temp, windSpeed, rainFall);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        final String CHANNEL_ID = "Foreground service";
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_LOW);

        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder builder = new Notification.Builder(ForegroundService.this, CHANNEL_ID)
                .setContentText("Data is collecting in background")
                .setContentTitle("Foreground service");
        startForeground(1001, builder.build());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void setApiToken() {
        // Set api token from local database
        LocalDataManager.Init(ForegroundService.this); // Create manager
        ApiClient.token = LocalDataManager
                .getToken()
                .getAccess_token(); // Set token to ApiClient
    }

    private boolean checkCredential() {
        long currTimeStamp = getTimeStamp(); // Get current time stamp
        Log.d(GlobalVar.LOG_TAG, "current timestamp: " + currTimeStamp); // Log current time stamp
        LocalDataManager.Init(ForegroundService.this); // Create local data manager

        boolean havingToken = LocalDataManager.getToken() != null;
        if (havingToken) {
            long remainingTimeStamp = LocalDataManager.getToken().getExpires_in() - currTimeStamp;
            boolean notExpired = remainingTimeStamp > 0;
            if (notExpired) {
                Log.d(GlobalVar.LOG_TAG, "Token expired in: " + remainingTimeStamp); // Log remaining time
                setApiToken();
                return true;
            }
        }
        return false;
    }

    public long getTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Get system timestamp in milliseconds
        return timestamp.getTime();
    }
}