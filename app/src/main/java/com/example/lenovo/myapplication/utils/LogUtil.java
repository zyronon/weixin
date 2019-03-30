package com.example.lenovo.myapplication.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by ttentau on 2017/6/13.
 */

public class LogUtil {

    public static final String TAG = "LogUtil";

    public static boolean isBeta = true;

    public static void v(String msg) {
        if (isBeta) Log.v(TAG, msg);
    }

    public static void d(String msg) {
        if (isBeta) Log.d(TAG, msg);
    }

    public static void i(String msg) {
        if (isBeta) Log.i(TAG, msg);
    }

    public static void w(String msg) {
        if (isBeta) Log.w(TAG, msg);
    }

    public static void e(String... msg) {
        String temp = "";
        for (int i = 0; i < msg.length; i++) {
            temp += msg[i] + "------";
        }
        if (isBeta) Log.e(TAG, temp);
    }

    public static void e(float... msg) {
        String temp = "";
        for (int i = 0; i < msg.length; i++) {
            temp += msg[i] + "------";
        }
        if (isBeta) Log.e(TAG, temp);
    }

    public static void e(int... msg) {
        String temp = "";
        for (int i = 0; i < msg.length; i++) {
            temp += msg[i] + "------";
        }
        if (isBeta) Log.e(TAG, temp);
    }

    public static void e(int msg) {
        if (isBeta) Log.e(TAG, msg + "");

    }
    public static void e(boolean msg) {
        if (isBeta) Log.e(TAG, msg + "");

    }

    public static void e(String msg, Error tr) {
        if (isBeta) Log.e(TAG, msg, tr);
    }

    public static void e(Class t, String msg) {
        if (isBeta) Log.e(t.getName(), msg);
    }

    public static void init(Context context) {
        isBeta = isApkInDebug(context);
    }

    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }
}
