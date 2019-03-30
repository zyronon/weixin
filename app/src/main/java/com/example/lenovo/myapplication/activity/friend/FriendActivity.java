package com.example.lenovo.myapplication.activity.friend;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.activity.BaseActivity;
import com.example.lenovo.myapplication.utils.LogUtil;

public class FriendActivity extends BaseActivity {
    private WebView mWeb;

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        mWeb = findViewById(R.id.webview);
        WebSettings settings = mWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        mWeb.addJavascriptInterface(new JSHook(), "android"); //在JSHook类里实现javascript想调用的方法，并将其实例化传入webview, "hello"这个字串告诉javascript调用哪个实例的方法

        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWeb.loadUrl("file:///android_asset/friend.html");
    }


    public class JSHook {
        public void javaMethod(String p) {
            LogUtil.e("JSHook.JavaMethod() called! + " + p);
        }

        public void showAndroid() {
            String info = "来自手机内的内容！！！";
//            webView.loadUrl("javascript:show('"+info+"')");
        }

        public String getInfo() {
            return "获取手机内的信息！！";
        }
    }
}
