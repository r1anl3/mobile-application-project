package com.example.myapplication.Manager;

import android.content.Context;

import com.example.myapplication.LocalData;

public class LocalDataManager {
    private static LocalDataManager instance;
    private LocalData localData;

    public static void Init(Context context) {
        instance = new LocalDataManager();
        instance.localData = new LocalData(context);
    }

    public static LocalDataManager getInstance() {
        if (instance == null) {
            instance = new LocalDataManager();
        }
        return instance;
    }


}
