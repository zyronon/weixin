package com.example.lenovo.myapplication.activity.find;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.activity.BaseActivity;
import com.example.lenovo.myapplication.adapter.WebActivityMenuAdapter;
import com.example.lenovo.myapplication.utils.IntentUtils;
import com.example.lenovo.myapplication.utils.UIUtils;


/**
 * Created by ttent on 2017/2/14.
 */
public class WebActivity extends BaseActivity {
    private Handler hand = new Handler();
    private PopupWindow mPopupWindow;
    private String[] mUrl = {"http://www.baidu.com", "WebView"};
    private WebView mWeb;
    private ProgressBar mProgressBar;
    private String[] mFrom_shoppings;
    private int[] mWAh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initData();
        initView();
    }

    private void initData() {
        mWAh = UIUtils.getWidthAndHeight(this);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        mFrom_shoppings = intent.getStringArrayExtra("from_shopping");
        if (!UIUtils.isEmpty(mFrom_shoppings)) {
            mUrl = mFrom_shoppings;
            ab.setTitle(mUrl[1]);
        }
        String[] from_games = intent.getStringArrayExtra("from_games");
        if (!UIUtils.isEmpty(from_games)) {
            mUrl = from_games;
            ab.setTitle(mUrl[1]);
        }
        String[] normals = intent.getStringArrayExtra("normal");
        if (!UIUtils.isEmpty(normals)) {
            mUrl = normals;
            ab.setTitle(mUrl[1]);
        }
    }

    private void initView() {
        mWeb = (WebView) findViewById(R.id.webview);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar2);

        WebSettings settings = mWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWeb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.e("tag",""+newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == mProgressBar.getVisibility()) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        mWeb.loadUrl(mUrl[0]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!UIUtils.isEmpty(mFrom_shoppings)) {
                    showAlertOnShop();
                }else {
                    IntentUtils.finish(this);
                }
                break;
            case 789:
                showdialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 789, 0, "设置").setEnabled(true).setIcon(R.drawable.ic_actionbar_overflow_three).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mWeb.canGoBack()){
                mWeb.goBack(); //goBack()表示返回WebView的上一页面
            }else {
                if (!UIUtils.isEmpty(mFrom_shoppings)) {
                    showAlertOnShop();
                }else {
                    IntentUtils.finish(this);
                }
            }
            return true;
        }
        return false;
    }

    private void showAlertOnShop() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("你要关闭购物页面？");
        builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() { // 设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                IntentUtils.finish(WebActivity.this);
            }
        });
        builder.setNegativeButton("再逛逛", new DialogInterface.OnClickListener() { // 设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void showdialog(){
        View mView = UIUtils.inflate(R.layout.web_setting);
        GridView mGv = (GridView) mView.findViewById(R.id.gv);
        mGv.setAdapter(new WebActivityMenuAdapter());
        Dialog dialog = new Dialog(this,R.style.dialog_formbottom);
        dialog.setContentView(mView);
        Window dialogWindow = dialog.getWindow();
        //设置位置
        dialogWindow.setGravity(Gravity.BOTTOM);
        //设置dialog的宽高属性
        dialogWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogWindow.setWindowAnimations(R.style.alert_anim);
        dialog.show();
    }
}
