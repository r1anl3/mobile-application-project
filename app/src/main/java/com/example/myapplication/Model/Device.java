package com.example.myapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Device {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("createdOn")
    private String createdOn;

    private static final List<Device> deviceList = new ArrayList<>();

    public static List<Device> getDevicesList() {
        return deviceList;
    }

    public static void setDevicesList(List<Device> list) {
        if (list == null) return;

        deviceList.clear();

        for (Device device : list) {
            if (device.name.equals("Default Weather") || device.name.equals("Light")) {
                deviceList.add(device);
            }
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}
