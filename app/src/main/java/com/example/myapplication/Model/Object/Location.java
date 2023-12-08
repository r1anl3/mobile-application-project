package com.example.myapplication.Model.Object;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("type")
    private String type;
    @SerializedName("value")
    private GeoPoint geoPoint;
    @SerializedName("name")
    private String name;
    @SerializedName("timestamp")
    private long timestamp;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
