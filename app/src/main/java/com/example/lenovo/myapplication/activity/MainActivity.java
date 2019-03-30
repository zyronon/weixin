package com.example.lenovo.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.activity.actionbar.AddFriendActivity;
import com.example.lenovo.myapplication.activity.actionbar.PayActivity;
import com.example.lenovo.myapplication.activity.actionbar.ScannerActivity;
import com.example.lenovo.myapplication.activity.actionbar.SearchActionBarActivity;
import com.example.lenovo.myapplication.activity.find.WebActivity;
import com.example.lenovo.myapplication.adapter.MainFrgmentAdapter;
import com.example.lenovo.myapplication.fragment.AboutMeFragment;
import com.example.lenovo.myapplication.fragment.BaseFragment;
import com.example.lenovo.myapplication.fragment.ContactsFragment;
import com.example.lenovo.myapplication.fragment.FoundFragment;
import com.example.lenovo.myapplication.fragment.WeiChatFragment;
import com.example.lenovo.myapplication.utils.IntentUtils;
import com.example.lenovo.myapplication.utils.LogUtil;
import com.example.lenovo.myapplication.utils.Sp;
import com.example.lenovo.myapplication.widgets.WxinRadioGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private android.support.v4.view.ViewPager vp;
    private android.support.v7.app.ActionBar mAb;
    private ArrayList<BaseFragment> mBf;
    private WxinRadioGroup rootradiogroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isLogin = Sp.getItem("isLogin", false);
        LogUtil.e(isLogin);
        if (!isLogin) {
//            startActivity(new Intent(this,SplashActivity.class));
        }
        setContentView(R.layout.activity_main);

        initView();
        initData();
        setData();
    }

    private void initView() {
        this.rootradiogroup = findViewById(R.id.radiogroup_main);
        this.vp = findViewById(R.id.vp_main);
        mAb = getSupportActionBar();
        mAb.setTitle("微信");
    }

    private void initData() {
        mBf = new ArrayList<>();
        mBf.add(new WeiChatFragment());
        mBf.add(new ContactsFragment());
        mBf.add(new FoundFragment());
        mBf.add(new AboutMeFragment());
    }

    private void setData() {
        vp.setAdapter(new MainFrgmentAdapter(getSupportFragmentManager(), mBf));
        rootradiogroup.setViewPager(vp);
        rootradiogroup.setCurrentItem(0);
        getOverflowMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_search:
                IntentUtils.startActivity(this, SearchActionBarActivity.class);
                break;
            case R.id.home_add_friend:
                IntentUtils.startActivity(this, AddFriendActivity.class);
                break;
            case R.id.home_add_sys:
                IntentUtils.startActivity(this, ScannerActivity.class);
                break;
            case R.id.home_add_help:
                Intent intent = IntentUtils.getIntent(this, WebActivity.class);
                String[] gamesValue = {"https://kf.qq.com/touch/product/wechat_app.html?scene_id=kf338", "帮助与反馈"};
                intent.putExtra("normal", gamesValue);
                IntentUtils.startActivity(this, intent);
                break;
            case R.id.home_add_money:
                IntentUtils.startActivity(this, PayActivity.class);
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        setIconsVisible(menu, true);
        return super.onCreateOptionsMenu(menu);
    }

    //通过反射得到menu的方法，强制显示icon
    private void setIconsVisible(Menu menu, boolean flag) {
        // 判断menu是否为空
        if (menu != null) {
            try {
                // 如果不为空,就反射拿到menu的setOptionalIconsVisible方法
                Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                // 暴力访问该方法
                method.setAccessible(true);
                // 调用该方法显示icon
                method.invoke(menu, flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //强制显示overflowMenu
    private void getOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 这是设置overmenu文字顔色为白色
    public boolean onPrepareOptionsMenu(Menu menu) {
        // return super.onPrepareOptionsMenu(menu);
        try {
            for (int i = 0; i < menu.size(); i++) {
                MenuItem mi = menu.getItem(i);
                // mi.setIcon(R.drawable.ic_action_new);
                String title = mi.getTitle().toString();
                Spannable spannable = new SpannableString(title);
                spannable.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spannable.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mi.setTitle(spannable);
            }
        } catch (Exception ex) {

        }
        return true;
    }

}
