package com.example.lenovo.myapplication.activity.actionbar;

import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.activity.BaseActivity;
import com.example.lenovo.myapplication.utils.IntentUtils;
import com.example.lenovo.myapplication.utils.UIUtils;

/**
 * Created by ttent on 2017/3/16.
 */

public class PayActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIv_pay_tiao_code;
    private ImageView mIv_pay_code;
    private ImageView mIv_tiao_code_big;
    private ImageView mIv_code_big;
    private FrameLayout mFl_big_iv;
    private RadioButton mRb1;
    private RadioButton mRb2;
    private Dialog mDialog;
    private TextView mTv_user;
    private TextView tv_lookdetails;
    private LinearLayout mLl_float;
    private boolean mIsMoney=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pay);
        initView();
        initData();
    }

    private void initData() {
        mFl_big_iv.setVisibility(View.INVISIBLE);
        mIv_tiao_code_big.setVisibility(View.INVISIBLE);
        mIv_code_big.setVisibility(View.INVISIBLE);
    }

    private void initView() {
        findViewById(R.id.iv_title_up).setOnClickListener(this);
        findViewById(R.id.iv_pay_overflow).setOnClickListener(this);
        findViewById(R.id.ll_pay_bnottom).setOnClickListener(this);
        findViewById(R.id.tv_pay_exchange).setOnClickListener(this);
        findViewById(R.id.btn_ok).setOnClickListener(this);
        mLl_float = (LinearLayout) findViewById(R.id.ll_float);
        mTv_user = (TextView) findViewById(R.id.tv_user);
        tv_lookdetails = (TextView) findViewById(R.id.tv_lookdetails);
        mIv_pay_tiao_code = (ImageView) findViewById(R.id.iv_pay_tiao_code);
        mIv_pay_code = (ImageView) findViewById(R.id.iv_pay_code);

        mIv_tiao_code_big = (ImageView) findViewById(R.id.iv_tiao_code_big);
        mIv_code_big = (ImageView) findViewById(R.id.iv_code_big);

        mFl_big_iv = (FrameLayout) findViewById(R.id.fl_big_iv);

        mIv_pay_tiao_code.setOnClickListener(this);
        mIv_pay_code.setOnClickListener(this);
        mIv_tiao_code_big.setOnClickListener(this);
        mIv_code_big.setOnClickListener(this);
        mFl_big_iv.setOnClickListener(this);
        tv_lookdetails.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tv_lookdetails:
                //这是tv查看详情
                //TODO
                break;
            case R.id.btn_ok:
                mLl_float.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_title_up:
                IntentUtils.finish(this);
                break;
            case R.id.iv_pay_overflow:
                View mView = UIUtils.inflate(R.layout.dialog_pay_iv_overflow);
                TextView tv1 = (TextView) mView.findViewById(R.id.tv1);
                TextView tv2 = (TextView) mView.findViewById(R.id.tv2);
                tv1.setOnClickListener(this);
                tv2.setOnClickListener(this);
                showdialog(mView);
                break;
            case R.id.tv1:
                //TODO
                break;
            case R.id.tv2:
                //TODO
                break;
            case R.id.ll_pay_bnottom:
                View dialog_anim_load = UIUtils.inflate(R.layout.dialog_anim_load);
                ImageView iv_anim_load = (ImageView) dialog_anim_load.findViewById(R.id.iv_anim_load);
                iv_anim_load.setBackgroundResource(R.drawable.anim_load);
                final AnimationDrawable anim = (AnimationDrawable) iv_anim_load.getBackground();
                final Dialog dialog = new Dialog(this,R.style.dialog_center_load);
                dialog.setCancelable(false);
                dialog.setContentView(dialog_anim_load);
                Window dialogWindow1 = dialog.getWindow();
                dialogWindow1.setGravity(Gravity.CENTER);
                dialogWindow1.setLayout(UIUtils.dip2px(180),UIUtils.dip2px(180));
                anim.start();
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        anim.stop();
                        dialog.dismiss();
                        IntentUtils.startActivity(PayActivity.this,ReceiveMoneyActivity.class);
                    }
                },1000);
                break;
            case R.id.iv_pay_tiao_code:
                mFl_big_iv.setVisibility(View.VISIBLE);
                mIv_tiao_code_big.setVisibility(View.VISIBLE);
                mIv_code_big.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_pay_code:
                mFl_big_iv.setVisibility(View.VISIBLE);
                mIv_tiao_code_big.setVisibility(View.INVISIBLE);
                mIv_code_big.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_pay_exchange:
                View view = UIUtils.inflate(R.layout.dialog_pay_exchange);
                RelativeLayout rl_pay_rb1 = (RelativeLayout) view.findViewById(R.id.rl_pay_rb1);
                RelativeLayout rl_pay_rb2 = (RelativeLayout) view.findViewById(R.id.rl_pay_rb2);
                rl_pay_rb1.setOnClickListener(this);
                rl_pay_rb2.setOnClickListener(this);
                mRb1 = (RadioButton) view.findViewById(R.id.rb1);
                mRb2 = (RadioButton) view.findViewById(R.id.rb2);
                if (mIsMoney){
                    mRb1.setChecked(true);
                    mRb2.setChecked(false);
                }else {
                    mRb1.setChecked(false);
                    mRb2.setChecked(true);
                }
                mRb1.setChecked(true);
                mDialog = new Dialog(this, R.style.dialog_formbottom);
                mDialog.setContentView(view);
                Window dialogWindow = mDialog.getWindow();
                //设置位置
                dialogWindow.setGravity(Gravity.BOTTOM);
                //设置dialog的宽高属性
                dialogWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                dialogWindow.setWindowAnimations(R.style.alert_anim);
                mDialog.show();
                break;
            case R.id.iv_tiao_code_big:
            case R.id.iv_code_big:
            case R.id.fl_big_iv:
                mFl_big_iv.setVisibility(View.INVISIBLE);
                mIv_tiao_code_big.setVisibility(View.INVISIBLE);
                mIv_code_big.setVisibility(View.INVISIBLE);
                break;
            case R.id.rl_pay_rb1:
                mRb1.setChecked(true);
                mRb2.setChecked(false);
                mDialog.dismiss();
                mTv_user.setText("使用零钱");
                mIsMoney=true;
                break;
            case R.id.rl_pay_rb2:
                mRb1.setChecked(false);
                mRb2.setChecked(true);
                mDialog.dismiss();
                mTv_user.setText("使用中国银行储蓄卡(6666)");
                mIsMoney=false;
                break;
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            IntentUtils.finish(this);
        }
        return true;
    }
    private void showdialog(View mView){
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
