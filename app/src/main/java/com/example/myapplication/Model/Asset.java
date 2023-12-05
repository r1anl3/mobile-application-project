package com.example.myapplication.Model;

import com.google.gson.annotations.SerializedName;

public class Asset {
    private Attribute attributes;
    private static Asset me;
    public Attribute getAttributes() {
        return attributes;
    }

    public void setAttributes(Attribute attributes) {
        this.attributes = attributes;
    }

    public static Asset getMe() {
        return me;
    }

    public static void setMe(Asset me) {
        Asset.me = me;
    }
}
