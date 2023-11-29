package com.example.myapplication.Fragment;

import android.annotation.SuppressLint;
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
import com.example.myapplication.Manager.LocalDataManager;
import com.example.myapplication.Model.Asset;
import com.example.myapplication.Model.Device;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FeatureFragment extends Fragment {
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
    private TextView tv_key;
    Handler handler;
    public FeatureFragment() {
    }

    public FeatureFragment(DashboardActivity activity) {
        this.parentActivity = activity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feature, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        InitialViews(view);
        InitialEvents();

        handler = new Handler(message -> { // Handle message
            Bundle bundle = message.getData(); // Get message
            boolean isOk = bundle.getBoolean("IS_OK"); // Get message data
            if (!isOk) return false; // If not ok

            try {
                setInfo(); // Set info
            }
            catch (NullPointerException e) {
                e.printStackTrace();
            }

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
        tv_key = view.findViewById(R.id.tv_key);
    }

    private void InitialEvents() {
        // Initial events
        getInfo(); // Get information
    }

    private void getInfo() {
        // Get information about user, weather assets
        new Thread(() -> { // new thread
            if (User.getMe() == null) { // If not user
                ApiManager.getUser(); // Get user
            }

            ApiManager.getAsset(Device.getDevicesList().get(0).getId()); // Get asset

            Message msg = handler.obtainMessage(); // Create message
            Bundle bundle = new Bundle(); // Create bundle
            bundle.putBoolean("IS_OK", true); // Put data to bundle
            msg.setData(bundle); // Set message data
            handler.sendMessage(msg);  // Send message through bundle
        }).start();
    }

    private void setInfo() {
        // Set information about user, weather assets
        boolean hasUserInfo = User.getMe() != null;
        boolean hasAsset = Asset.getMe() != null;
        if (hasUserInfo && hasAsset)  {
            float tempVal = Asset.getMe()
                    .getAttributes()
                    .getTemperature()
                    .getValue();
            float rainFallVal = Asset.getMe()
                    .getAttributes()
                    .getRainfall()
                    .getValue();
            float humidVal = Asset.getMe()
                    .getAttributes()
                    .getHumidity()
                    .getValue();
            float windSpeedVal = Asset.getMe()
                    .getAttributes()
                    .getWindSpeed()
                    .getValue();
            float windDirVal = Asset.getMe()
                    .getAttributes()
                    .getWindDirection()
                    .getValue();
            String userName = User.getMe().getUsername(); // Get username
            String temperature = tempVal + "";  // Get current temperature
            String rainFall = rainFallVal + " mm"; // Get current rainfall
            String humidity = humidVal + "%"; // Get current humidity
            String windSpeed = windSpeedVal + " km/h"; // Get current wind speed
            String windDir = windDirVal + ""; // Get current wind direction
            String manufacturer = Asset.getMe()
                    .getAttributes()
                    .getManufacturer()
                    .getValue(); // Get current manufacturer
            String location = Asset.getMe()
                    .getAttributes()
                    .getPlace()
                    .getValue(); // Get current location
            String dateTime = getCurrDatetime(); // Get current datetime
            String validity = getKeyValidity();
            String weatherStatus = getWeatherStatus(tempVal, humidVal, windSpeedVal, rainFallVal);

            tv_greed.setText(userName); // Set username
            tv_temp.setText(temperature); // Set temperature
            tv_rainfall.setText(rainFall); // Set rainfall
            tv_humid.setText(humidity); // Set humidity
            tv_windSpeed.setText(windSpeed); // Set wind speed
            tv_windDir.setText(windDir); // Set wind direction
            tv_manufacture.setText(manufacturer); // Set manufacturer
            tv_location.setText(location); // Set location
            tv_dateTime.setText(dateTime); // Set datetime
            tv_key.setText(validity);
            setCurrWea(weatherStatus, img_currWea, tv_weaSta);
        }
    }

    private void setCurrWea(String weatherStatus, ImageView imgCurrWea, TextView tvWeaSta) {
        // Update img according to weather status
        switch (weatherStatus) {
            case "cloudy":
                tvWeaSta.setText(R.string.cloudy);
                imgCurrWea.setImageResource(R.drawable.cloudy);
                break;
            case "cloudy_sunny":
                tvWeaSta.setText(R.string.sunny_cloudy);
                imgCurrWea.setImageResource(R.drawable.cloudy_sunny);
                break;
            case "rainy":
                tvWeaSta.setText(R.string.rainy);
                imgCurrWea.setImageResource(R.drawable.rainy);
                break;
            case "sunny":
                tvWeaSta.setText(R.string.sunny);
                imgCurrWea.setImageResource(R.drawable.sunny);
                break;
            case "windy":
                tvWeaSta.setText(R.string.windy);
                imgCurrWea.setImageResource(R.drawable.windy);
                break;
        }
    }

    private String getWeatherStatus(float temp, float humid, float windSpeed, float rainFall) {
        // Process temperature, humidity, wind speed values and return weather status
        String[] weaSta = {"cloudy", "cloudy_sunny", "rainy", "sunny", "windy"};
        String currSta = "";
        if (humid > 60 && temp < 30) {
            currSta = weaSta[0];
        }
        else if (temp >= 30 && humid < 60) {
            currSta = weaSta[3];
        }
        else if (humid > 60 && temp > 30) {
            currSta = weaSta[1];
        }
        if (windSpeed > 10) {
            currSta = weaSta[4];
        }
        if (rainFall > 50) {
            currSta = weaSta[2];
        }

        return currSta;
    }
    @SuppressLint("SimpleDateFormat")
    private String getKeyValidity() {
        Date date = new Date(LocalDataManager.getToken().getExpires_in()); // Get expired date
        Log.d(GlobalVar.LOG_TAG, "getKeyValidity: " + date); // Log date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm"); // Date formatter
        return dateFormat.format(date); // Return formatted date
    }

    @SuppressLint("SimpleDateFormat")
    private String getCurrDatetime() {
        // Get current datetime and format it
        DateFormat dateFormat = new SimpleDateFormat("EE MMM dd | HH:mm aaa"); // Datetime formatter
        Calendar date = Calendar.getInstance(); // Create calendar
        return dateFormat.format(date.getTime()); // Formatted calendar time
    }
}