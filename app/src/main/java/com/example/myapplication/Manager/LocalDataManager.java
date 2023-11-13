package com.example.myapplication.Manager;

import android.content.Context;

import com.example.myapplication.LocalData;
import com.example.myapplication.Model.Token;
import com.google.gson.Gson;

public class LocalDataManager {
    private static final String PREF_TOKEN_OBJ = "PREF_TOKEN_OBJ";
    private static LocalDataManager instance;
    private LocalData localData;

    public static void Init(Context context) {
        // Init LocalDataManager
        instance = new LocalDataManager();
        instance.localData = new LocalData(context);
    }

    public static LocalDataManager getInstance() {
        // Get LocalDataManager
        if (instance == null) { // If no instance
            instance = new LocalDataManager(); // Create new instance
        }
        return instance;
    }

    public static void setToken(Token token) {
        // Set token
        Gson gson = new Gson(); // Create new gson
        String strJsonToken = gson.toJson(token); // Convert token to json
        LocalDataManager.getInstance()
                .localData
                .putStringValue(PREF_TOKEN_OBJ, strJsonToken); // Put converted token to SharedPreferences
    }

    public static Token getToken() {
        // Get token
        String strJsonToken = LocalDataManager
                .getInstance()
                .localData
                .getStringValue(PREF_TOKEN_OBJ); // Get token from SharedPreferences
        Gson gson = new Gson(); // Create new gson
        return gson.fromJson(strJsonToken, Token.class); // Convert json to class
    }
}
