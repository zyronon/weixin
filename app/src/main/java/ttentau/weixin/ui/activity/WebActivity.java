package ttentau.weixin.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import ttentau.weixin.R;
import ttentau.weixin.uitls.UIUtils;

import static ttentau.weixin.R.id.webview;

/**
 * Created by ttent on 2017/2/14.
 */
public class WebActivity extends BaseActivity {
    private Handler hand = new Handler();
    private PopupWindow mPopupWindow;
    private View mView;
    private String[] mUrl = {"http://www.baidu.com", "WebView"};
    private WebView mWeb;
    private ProgressBar mProgressBar;
    private GridView mGv;
    private int[] resources=new int[]{R.drawable.sic1,R.drawable.sic2,R.drawable.sic3,
            R.drawable.sic4,R.drawable.sic5,
            R.drawable.sic6,R.drawable.sic7,
            R.drawable.sic8,R.drawable.sic9,
            R.drawable.sic10,R.drawable.sic11};

    private String[] text={"发送给朋友","分享到朋友圈","收藏","搜索页面内容",
            "复制链接","查看公众号","在聊天中置顶","在浏览器打开","调整字体","刷新","设拆"};
    private String[] mFrom_shoppings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
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
        initView();
    }

    private void initView() {
        mView = UIUtils.inflate(R.layout.web_setting);
        mWeb = (WebView) findViewById(webview);
        mGv = (GridView) mView.findViewById(R.id.gv);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar2);

        mGv.setAdapter(new Myadapter());
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
        mPopupWindow = new PopupWindow(mView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!UIUtils.isEmpty(mFrom_shoppings)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("你要关闭购物页面？");
                    builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() { // 设置确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                            overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
                        }
                    });
                    builder.setNegativeButton("再逛逛", new DialogInterface.OnClickListener() { // 设置取消按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }else {
                    finish();
                    overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
                }
                break;
            case 789:
                mPopupWindow.showAtLocation(findViewById(R.id.root), Gravity.BOTTOM, 0, 0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 789, 0, "设置").setEnabled(true).setIcon(R.drawable.rg).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mWeb.canGoBack()){
                mWeb.goBack(); //goBack()表示返回WebView的上一页面
            }else {
                if (!UIUtils.isEmpty(mFrom_shoppings)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("你要关闭购物页面？");
                    builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() { // 设置确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                            overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
                        }
                    });
                    builder.setNegativeButton("再逛逛", new DialogInterface.OnClickListener() { // 设置取消按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }else {
                    finish();
                    overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
                }
            }
            return true;
        }
        return false;
    }
    private class Myadapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 11;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(WebActivity.this, R.layout.gridview_item, null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv);
            TextView tv = (TextView) view.findViewById(R.id.tv);
            iv.setImageResource(resources[position]);
            tv.setText(text[position]);
            return view;
        }
    }
}
