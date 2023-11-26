package com.example.myapplication.Fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.API.ApiManager;
import com.example.myapplication.Activity.DashboardActivity;
import com.example.myapplication.Model.Asset;
import com.example.myapplication.Model.Attribute;
import com.example.myapplication.Model.Device;
import com.example.myapplication.R;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;

import java.util.ArrayList;

public class MapFragment extends Fragment {
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    DashboardActivity parentActivity;
    Handler handler;
    MapView mapView;
    ImageButton btn_zoomIn, btn_zoomOut;
    double aLat;
    double aLong;
    Attribute attribute;

    public MapFragment() {
        // Required empty public constructor
    }

    public MapFragment(DashboardActivity activity) {
        this.parentActivity = activity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        InitialViews(view);
        InitialEvents();

        handler = new Handler(message -> { // Handle message
            Bundle bundle = message.getData(); // Get message
            boolean isOk = bundle.getBoolean("DEVICE_OK"); // Get message data
            if (!isOk) return false; // If not ok

            setMap(10.869905172970164,106.80345028525176);
            setMap(aLat, aLong);

            return false;
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void InitialViews(View view) {
        mapView = view.findViewById(R.id.mv_mapView);
        btn_zoomIn = view.findViewById(R.id.btn_zoomIn);
        btn_zoomOut = view.findViewById(R.id.btn_zoomOut);
    }

    private void InitialEvents() {
        getInfo();
        btn_zoomIn.setOnClickListener(view -> mapView.getController().zoomIn());
        btn_zoomOut.setOnClickListener(view -> mapView.getController().zoomOut());
    }
    private void getInfo() {
        // Get information about user, weather assets
        new Thread(() -> { // new thread
            if (Device.getDevicesList() == null || Device.getDevicesList().size() == 0) {
                String queryString = "{ \"realm\": { \"name\": \"master\" }}";
                JsonObject query = JsonParser.parseString(queryString).getAsJsonObject();
                ApiManager.queryDevices(query);
            }

            if (Asset.getMe() == null) {
                assert Device.getDevicesList() != null;
                ApiManager.getAsset(Device.getDevicesList().get(0).getId());
            }

            attribute = Asset.getMe().getAttributes();
            aLat = attribute.getLocation()
                    .getGeoPoint()
                    .getLat();
            aLong = attribute.getLocation()
                    .getGeoPoint()
                    .getLong();

            Message msg = handler.obtainMessage(); // Create message
            Bundle bundle = new Bundle(); // Create bundle
            bundle.putBoolean("DEVICE_OK", true); // Put data to bundle
            msg.setData(bundle); // Set message data
            handler.sendMessage(msg);  // Send message through bundle
        }).start();
    }

    private void setMap(double aLat, double aLong) {
        Context ctx = parentActivity.getApplicationContext(); // Get parent activity context
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx)); // Get configuration

        mapView.setTileSource(TileSourceFactory.MAPNIK); // Set title source
        mapView.getController().setZoom(18.0); // Set zoom

        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.INTERNET
        }); // Required permissions
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER); // Set zoom controller
        mapView.setMultiTouchControls(true); // Enable multitouch


        CompassOverlay compassOverlay = new CompassOverlay(parentActivity.getApplicationContext(), mapView); // Create compass
        compassOverlay.enableCompass(); // Enable compass
        mapView.getOverlays().add(compassOverlay); // Add compass

        GeoPoint point = new GeoPoint(aLat, aLong); // Create geo point

        Marker startMarker = new Marker(mapView); // Create maker
        startMarker.setPosition(point); // Set marker position
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER); // Set anchor center by width, height
        startMarker.setOnMarkerClickListener((marker, mapView) -> {
            showInfo(); // Show info bottom sheet on click marker
            return true;
        });
        mapView.getOverlays().add(startMarker); // Show marker

        mapView.getController().setCenter(point); // Center geo point
    }

    private void showInfo() {
        // Show assets information
        final Dialog dialog = new Dialog(parentActivity); // Create dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // No title
        dialog.setContentView(R.layout.bottomsheet); // Set bottom sheet

        LinearLayout editLayout = dialog.findViewById(R.id.layoutEdit); // Get edit layout
        Button btn_view = dialog.findViewById(R.id.btn_view); // Get view button
        TextView tv_info = editLayout.findViewById(R.id.tv_info); // Get textview info

        btn_view.setOnClickListener(view -> {
            parentActivity.replaceFragment(new FeatureFragment()); // Replace feature fragment on click
            dialog.dismiss(); // Hide bottom sheet
        });


        String temp = "Temperature: " + attribute.getTemperature().getValue() + "\n";
        String humid = "Humid: " + attribute.getHumidity().getValue() + "\n";
        String geo = "Geo: " + aLat + " " + aLong + "\n";
        String info = temp + humid + geo;
        tv_info.setText(info); // Set info to bottom sheet

        dialog.show(); // Show bottom sheet
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT); // Match parent, wrap content
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Background color
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; // Animation
        dialog.getWindow().setGravity(Gravity.BOTTOM); // Layout gravity

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        // Request permissions
        ArrayList<String> permissionsToRequest = new ArrayList<>(); // Permission array
        for (int i = 0; i < grantResults.length; i++) { // Permission index
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    parentActivity,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(parentActivity.getApplicationContext(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    parentActivity,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
}