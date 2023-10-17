package com.example.myapplication.Activity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageManager {
    private Context ct;

    public LanguageManager(Context ctx) {
        this.ct = ctx;
    }

    public String checkResource() {
        // Check current language
        String langCode = Locale.getDefault().getLanguage();
        return langCode;
    }

    public void updateResource(String code) {
        // Update language
        Locale locale = new Locale(code);
        Locale.setDefault(locale);
        Resources resources = ct.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
