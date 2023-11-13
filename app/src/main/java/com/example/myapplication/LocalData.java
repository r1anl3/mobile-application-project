package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalData {
    private static final String LOCAL_DATA = "LOCAL_DATA";
    private Context ct;

    public LocalData(Context ct) {
        // Initial LocalData
        this.ct = ct;
    }

    public void putStringValue(String key, String value) {
        // Put string value to SharedPreferences
        SharedPreferences preferences = ct.getSharedPreferences(LOCAL_DATA, Context.MODE_PRIVATE); // Create SharedPreferences
        SharedPreferences.Editor editor = preferences.edit(); // Create editor
        editor.putString(key, value); // Put string to SharedPreferences
        editor.apply(); // Save
    }

    public String getStringValue(String key) {
        // Get string value from SharedPreferences
        SharedPreferences preferences = ct.getSharedPreferences(LOCAL_DATA, Context.MODE_PRIVATE); // Create SharedPreferences
        return preferences.getString(key, ""); // Return string, null if not found
    }
}
