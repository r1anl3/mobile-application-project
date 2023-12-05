package com.example.myapplication.Model.Object;

import com.example.myapplication.Model.Attribute;
import com.google.gson.annotations.SerializedName;


public class Manufacturer extends Attribute {
    @SerializedName("type")
    private String type;
    @SerializedName("value")
    private String value;
    @SerializedName("name")
    private String name;
    @SerializedName("timestamp")
    private long timestamp;
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
