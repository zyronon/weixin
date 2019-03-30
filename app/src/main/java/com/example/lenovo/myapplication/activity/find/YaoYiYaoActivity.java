package com.example.lenovo.myapplication.activity.find;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.activity.BaseActivity;

/**
 * Created by ttent on 2017/2/13.
 */

public class YaoYiYaoActivity extends BaseActivity {
    private RelativeLayout mYao_yi_yao_top;
    private RelativeLayout yao_yi_yao_bottom;
    private View mView_top;
    private View mView_bottom;
    private RelativeLayout mYao_people;
    private LinearLayout mPeogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yao_yi_yao);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("摇一摇");
        final int height = getWindowManager().getDefaultDisplay().getHeight();
        mYao_yi_yao_top = (RelativeLayout) findViewById(R.id.yao_yi_yao_top);
        yao_yi_yao_bottom = (RelativeLayout) findViewById(R.id.yao_yi_yao_bottom);
        mView_top = (View) findViewById(R.id.view_top);
        mYao_people = (RelativeLayout) findViewById(R.id.yao_people);
        mPeogress = (LinearLayout) findViewById(R.id.progress);
        mView_top.setVisibility(View.INVISIBLE);
        mView_bottom = (View) findViewById(R.id.view_bottom);
        mView_bottom.setVisibility(View.INVISIBLE);

        mYao_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(UIUtils.getContext(), UserInfoActivity.class);
//                intent.putExtra("ContactsFragment_user_name","真是无奈");
//                intent.putExtra("ContactsFragment_userChatid","contacts2043");
//                startActivity(intent);
//                overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
            }
        });

        TranslateAnimation ta = new TranslateAnimation(0, 0, 0, -100);
        ta.setDuration(1000);
        ta.setRepeatCount(1);
        ta.setRepeatMode(Animation.REVERSE);
        mYao_yi_yao_top.startAnimation(ta);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mView_top.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mPeogress.setVisibility(View.VISIBLE);
                mView_top.setVisibility(View.INVISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPeogress.setVisibility(View.INVISIBLE);
                        int top = mYao_people.getTop();
                        TranslateAnimation ta1 = new TranslateAnimation(0, 0, -(top - height / 2), 0);
                        ta1.setDuration(500);
                        mYao_people.startAnimation(ta1);
                        mYao_people.setVisibility(View.VISIBLE);
                    }
                }, 2000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        TranslateAnimation ta1 = new TranslateAnimation(0, 0, 0, 100);
        ta1.setDuration(1000);
        ta1.setRepeatCount(1);
        ta1.setRepeatMode(Animation.REVERSE);
        yao_yi_yao_bottom.startAnimation(ta1);
        ta1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mView_bottom.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mView_bottom.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
                break;
            case 789:
                startActivity(new Intent(YaoYiYaoActivity.this,YaoYiYaoSettingActivity.class));
                overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,789,0,"设置").setEnabled(true).setIcon(R.drawable.ic_actionbar_setting).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch ( keyCode) {
            case KeyEvent.KEYCODE_BACK :
                finish();
                overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
                break;
        }
        return true;
    }
}
