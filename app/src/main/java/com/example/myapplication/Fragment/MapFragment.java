package com.example.myapplication.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
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

import com.example.myapplication.Activity.DashboardActivity;
import com.example.myapplication.Model.Asset;
import com.example.myapplication.Model.Attribute;
import com.example.myapplication.Model.Device;
import com.example.myapplication.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

public class MapFragment extends Fragment {
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private DashboardActivity parentActivity;
    private MapView mapView;
    private ImageButton btn_zoomIn, btn_zoomOut;
    private double aLat, aLat2;
    private double aLong, aLong2;
    private Attribute attribute;
    private Dictionary<Integer, Integer> iconMarker;

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

        super.onViewCreated(view, savedInstanceState);
    }

    private void InitialViews(View view) {
        mapView = view.findViewById(R.id.mv_mapView);
        btn_zoomIn = view.findViewById(R.id.btn_zoomIn);
        btn_zoomOut = view.findViewById(R.id.btn_zoomOut);
        aLat2 = 10.869905172970164;
        aLong2 = 106.80345028525176;
        iconMarker = new Hashtable<>();
        iconMarker.put(0, R.mipmap.marker_weather);
        iconMarker.put(1, R.mipmap.marker_light_bulb);
    }

    private void InitialEvents() {
        getInfo();
        btn_zoomIn.setOnClickListener(view -> mapView.getController().zoomIn());
        btn_zoomOut.setOnClickListener(view -> mapView.getController().zoomOut());
        setMap(aLat, aLong, 0);
        setMap(aLat2, aLong2, 1);
    }
    private void getInfo() {
        // Get information about user, weather assets
        if (Asset.getMe() != null) {
            attribute = Asset.getMe().getAttributes();
            aLat = attribute.getLocation()
                    .getGeoPoint()
                    .getLat();
            aLong = attribute.getLocation()
                    .getGeoPoint()
                    .getLong();
        }
    }

    private void setMap(double aLat, double aLong, int deviceId) {
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
        startMarker.setIcon(getResources().getDrawable(iconMarker.get(deviceId))); // Set marker icon
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER); // Set anchor center by width, height
        startMarker.setOnMarkerClickListener((marker, mapView) -> {
            showInfo(deviceId); // Show device info on click marker
            return true;
        });
        mapView.getOverlays().add(startMarker); // Show marker

        mapView.getController().setCenter(point); // Center geo point
    }

    private void showInfo(int deviceId) {
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


        String id = "Asset ID: " + Device.getDevicesList().get(deviceId).getId() + "\n";
        String name = "Device name: " + Device.getDevicesList().get(deviceId).getName() + "\n";
        String createdOn = "Create on: " + getCreatedOn(Device.getDevicesList().get(deviceId).getCreatedOn())+ "\n";
        String info = id + name + createdOn;
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

    @SuppressLint("SimpleDateFormat")
    private String getCreatedOn(long timestamp) {
        Date date = new Date(timestamp); // Get expired date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm"); // Date formatter
        return dateFormat.format(date); // Return formatted date
    }
}