package com.example.lenovo.myapplication.utils;

import android.widget.Toast;

import com.example.lenovo.myapplication.global.WeixinApplication;

public class ToastUtil {
    public static void s(String msg) {
        Toast.makeText(WeixinApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void l(String msg) {
        Toast.makeText(WeixinApplication.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
