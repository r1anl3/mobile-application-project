package com.example.myapplication;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.widget.ImageButton;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;

public class LanguageManager {
    private Context ct;
    private Dictionary<String, Integer> langDic;

    public LanguageManager(Context ctx) {
        this.ct = ctx;
        langDic = new Hashtable<>();
        langDic.put("vi", R.mipmap.icon_vn);
        langDic.put("en", R.mipmap.icon_eng);
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

    public void updateIcon(String code, ImageButton button) {
        button.setImageResource(langDic.get(code));
    }
}
