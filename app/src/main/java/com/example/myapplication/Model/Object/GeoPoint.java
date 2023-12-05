package com.example.myapplication.Model.Object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeoPoint {
    @SerializedName("coordinates")
    List<Double> coordinate;

    public double getLong() {
        return coordinate.get(0);
    }
    public double getLat() {
        return coordinate.get(1);
    }
}
