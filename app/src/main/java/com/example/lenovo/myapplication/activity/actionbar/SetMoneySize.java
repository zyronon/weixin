package com.example.lenovo.myapplication.activity.actionbar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.activity.BaseActivity;
import com.example.lenovo.myapplication.utils.IntentUtils;
import com.example.lenovo.myapplication.utils.UIUtils;

/**
 * Created by ttent on 2017/3/17.
 */
public class SetMoneySize extends BaseActivity implements View.OnClickListener {

	private EditText mEt_moneysize;
	private TextView tv_addexplain;
	private TextView tv_explain;
	private StringBuffer sb;
	private Editable mText;
	private LinearLayout mLl_container;
	private boolean isHide;
	private boolean isOk = true;
	private int mSelectionEnd=0;
	private int mIntMoneySize=-1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setmoneysize);
		initActionBar();
		initView();
		initData();
	}

	private void initData() {
		mEt_moneysize.requestFocus();
		sb = new StringBuffer();
	}

	private void initView() {
		mLl_container = (LinearLayout) findViewById(R.id.ll_container);
		mEt_moneysize = (EditText) findViewById(R.id.et_moneysize);
		mEt_moneysize.setOnClickListener(this);
		// mEt_moneysize.addTextChangedListener(this);
		tv_addexplain = (TextView) findViewById(R.id.tv_addexplain);
		tv_explain = (TextView) findViewById(R.id.tv_explain);
		findViewById(R.id.btn_ok).setOnClickListener(this);
		tv_addexplain.setOnClickListener(this);

		findViewById(R.id.btn_0).setOnClickListener(this);
		findViewById(R.id.btn_1).setOnClickListener(this);
		findViewById(R.id.btn_2).setOnClickListener(this);
		findViewById(R.id.btn_3).setOnClickListener(this);
		findViewById(R.id.btn_4).setOnClickListener(this);
		findViewById(R.id.btn_5).setOnClickListener(this);
		findViewById(R.id.btn_6).setOnClickListener(this);
		findViewById(R.id.btn_7).setOnClickListener(this);
		findViewById(R.id.btn_8).setOnClickListener(this);
		findViewById(R.id.btn_9).setOnClickListener(this);
		findViewById(R.id.btn_double).setOnClickListener(this);
		findViewById(R.id.btn_back).setOnClickListener(this);
		findViewById(R.id.btn_toogle).setOnClickListener(this);
	}

	private void initActionBar() {
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("设置金额");
	}

	@Override
	public void onClick(View v) {
		mText = mEt_moneysize.getText();
		mSelectionEnd = mEt_moneysize.getSelectionEnd();
		switch (v.getId()) {
			case R.id.et_moneysize :
				InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); // 强制隐藏键盘
				mLl_container.setVisibility(View.VISIBLE);
				break;
			case R.id.btn_0 :
				insertText("0");
				break;
			case R.id.btn_1 :
				insertText("1");
				break;
			case R.id.btn_2 :
				insertText("2");
				break;
			case R.id.btn_3 :
				insertText("3");
				break;
			case R.id.btn_4 :
				insertText("4");
				break;
			case R.id.btn_5 :
				insertText("5");
				break;
			case R.id.btn_6 :
				insertText("6");
				break;
			case R.id.btn_7 :
				insertText("7");
				break;
			case R.id.btn_8 :
				insertText("8");
				break;
			case R.id.btn_9 :
				insertText("9");
				break;
			case R.id.btn_back :
				if (mText.length() == 0||mSelectionEnd==0) {
					return;
				}
				if (mSelectionEnd!=mText.length()){
					mText.delete(mSelectionEnd - 1, mSelectionEnd);
					mEt_moneysize.setText(mText.toString());
					mEt_moneysize.setSelection(mSelectionEnd-1);
					mSelectionEnd=mEt_moneysize.getSelectionEnd();
				}else {
					mText.delete(mText.length() - 1, mText.length());
					mEt_moneysize.setText(mText.toString());
					mEt_moneysize.setSelection(mText.length());
				}
				break;
			case R.id.btn_double :
				if (mText.toString().contains(".")) {
					return;
				}
				mText.append(".");
				upDataEt();
				break;
			case R.id.btn_toogle :
				mLl_container.setVisibility(View.INVISIBLE);
				break;
			case R.id.tv_addexplain :
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setTitle("添加收钱说明");
				View view = UIUtils.inflate(R.layout.dialog_calculter_et);
				final EditText editText = (EditText) view.findViewById(R.id.et_dialog);
				dialog.setView(view);
				dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						tv_explain.setText(editText.getText().toString());
						String explain = tv_explain.getText().toString();
						if (UIUtils.isEmpty(explain)) {
							tv_addexplain.setText("添加收钱说明");
						} else {
							tv_addexplain.setText(" 修改");
						}
						dialog.dismiss();
					}
				});
				dialog.show();
				break;
			case R.id.btn_ok :
				isOk = true;

				final String moneySize = mEt_moneysize.getText().toString().trim();
				final String explain = tv_explain.getText().toString();
				if (UIUtils.isEmpty(moneySize)) {
					UIUtils.Toast("请输入正确的金额");
					return;
				}else {
					mIntMoneySize = Integer.parseInt(moneySize);
				}

				View dialog_anim_load = UIUtils.inflate(R.layout.dialog_anim_load);
				ImageView iv_anim_load = (ImageView) dialog_anim_load.findViewById(R.id.iv_anim_load);
				iv_anim_load.setBackgroundResource(R.drawable.anim_load);
				final AnimationDrawable anim = (AnimationDrawable) iv_anim_load.getBackground();
				final Dialog dialog1 = new Dialog(this, R.style.dialog_center_load);
				dialog1.setCancelable(false);
				dialog1.setContentView(dialog_anim_load);
				Window dialogWindow1 = dialog1.getWindow();
				dialogWindow1.setGravity(Gravity.CENTER);
				dialogWindow1.setLayout(UIUtils.dip2px(180), UIUtils.dip2px(180));
				anim.start();
				dialog1.show();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						anim.stop();
						dialog1.dismiss();
						if (mIntMoneySize > 50000) {
							AlertDialog.Builder builder = new AlertDialog.Builder(SetMoneySize.this);
							builder.setMessage("暂只支持最高50000.00元");
							builder.setCancelable(false);
							builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							});
							builder.show();
							isOk = false;
						}
					}
				}, 500);
				dialog1.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						if (isOk) {
							Intent intent = new Intent(SetMoneySize.this, ReceiveMoneyActivity.class);
							intent.putExtra("moneysize", moneySize);
							if (!UIUtils.isEmpty(explain)) {
								intent.putExtra("explain", explain);
							}
							setResult(999, intent);
							IntentUtils.finish(SetMoneySize.this);
						}
					}
				});
				break;
		}
	}

	private void insertText(String value) {
		if (mSelectionEnd != mText.length()) {
			mText.insert(mSelectionEnd, value);
			mEt_moneysize.setText(mText.toString());
			mEt_moneysize.setSelection(mSelectionEnd+1);
			mSelectionEnd=mEt_moneysize.getSelectionEnd();
		} else {
			mText.append(value);
			mEt_moneysize.setText(mText.toString());
			mEt_moneysize.setSelection(mText.length());
		}
	}

	private void upDataEt() {
		mEt_moneysize.setText(mText.toString());
		mEt_moneysize.setSelection(mText.length());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		IntentUtils.finish(this);
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			IntentUtils.finish(this);
		}
		return super.onKeyDown(keyCode, event);
	}
}
