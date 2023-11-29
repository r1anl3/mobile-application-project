package com.example.myapplication.Manager;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.widget.ImageButton;

import com.example.myapplication.R;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Locale;

public class LanguageManager {
    private Context ct;
    private final Dictionary<String, Integer> langDic;
    private Resources resources;
    private Configuration configuration;

    public LanguageManager(Context ctx) {
        this.ct = ctx;
        resources = ct.getResources();
        configuration = resources.getConfiguration();
        langDic = new Hashtable<>();
        langDic.put("vi", R.mipmap.icon_vn);
        langDic.put("en", R.mipmap.icon_eng);
    }

    public String checkResource() {
        // Check current language
        return configuration.getLocales().toLanguageTags().split("-")[0];
    }

    public void updateResource(String code) {
        // Update language
        Locale locale = new Locale(code);
        Locale.setDefault(locale);
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public void updateIcon(String code, ImageButton button) {
        button.setImageResource(langDic.get(code));
    }
}
