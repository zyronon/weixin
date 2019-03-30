package com.example.lenovo.myapplication.utils;

import android.content.SharedPreferences;

/**
 * Created by ttent on 2017/2/10.
 */

public class Sp {
    public static void setItem(String key, String value) {
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("wx", 0);
        sp.edit().putString(key, value).commit();
    }
    public static void setItem(String key, boolean value) {
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("wx", 0);
        sp.edit().putBoolean(key, value).commit();
    }

    public static String getItem(String key, String defvalue) {
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("wx", 0);
        String result = sp.getString(key, defvalue);
        return result;
    }

    public static boolean getItem(String key, boolean defvalue) {
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("wx", 0);
        boolean result = sp.getBoolean(key, defvalue);
        return result;
    }
}
