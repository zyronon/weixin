package com.example.lenovo.myapplication.activity.find;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.activity.BaseActivity;
import com.example.lenovo.myapplication.adapter.NearPeopleAdapter;
import com.example.lenovo.myapplication.bean.Contacts;
import com.example.lenovo.myapplication.utils.DataUtil;
import com.example.lenovo.myapplication.utils.IntentUtils;
import com.example.lenovo.myapplication.utils.Sp;
import com.example.lenovo.myapplication.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by ttent on 2017/2/14.
 */
public class NearPeopleActivity extends BaseActivity {
	private PopupWindow mPopupWindow;
	private View mView;
	private boolean isShow;
	private RelativeLayout mRl_start_look;
	private Button mBtn_start_look;
	private Handler mHandler = new Handler();
	private ListView mLv;
	private ArrayList<Contacts> friends;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearppeople);
		initData();
		initView();
		setData();
	}

	private void initData() {
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("附近的人");
		friends = new ArrayList<Contacts>();
		DataUtil.addContactsData(friends);
	}
	private void initView() {

		mView = UIUtils.inflate(R.layout.popupwindow_nearpeople);

		mRl_start_look = (RelativeLayout) findViewById(R.id.rl_start_look);
		mBtn_start_look = (Button) findViewById(R.id.btn_start_look);
		mLv = (ListView) findViewById(R.id.lv);
	}

	private void setData() {
		mLv.setAdapter(new NearPeopleAdapter(friends));
		boolean is_first = Sp.getItem("nearpeople", false);
		if (is_first) {
			final ProgressDialog pd = new ProgressDialog(NearPeopleActivity.this);
			pd.setMessage("正在查找附近的人");
			pd.show();
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					pd.dismiss();
					mLv.setVisibility(View.VISIBLE);
				}
			}, 2000);
		}
		if (!is_first) {
			mRl_start_look.setVisibility(View.VISIBLE);
			mBtn_start_look.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mRl_start_look.setVisibility(View.GONE);
					Sp.setItem("nearpeople", true);
					final ProgressDialog pd = new ProgressDialog(NearPeopleActivity.this);
					pd.setMessage("正在查找附近的人");
					pd.show();
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							pd.dismiss();
							mLv.setVisibility(View.VISIBLE);
						}
					}, 2000);
				}
			});
			showDialogHint();
		}
		mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(UIUtils.getContext(), UserInfoActivity.class);
				Contacts friend = friends.get(position);
				intent.putExtra("ContactsFragment_user_name",friend.getName());
				intent.putExtra("ContactsFragment_userChatid","ic_main_bottom_tab_contacts_normal"+position);
				IntentUtils.startActivity(NearPeopleActivity.this,intent);
			}
		});
	}

	private void showDialogHint() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("提示");
		builder.setMessage("查看附近的人功能将获取你的位置信息，" + "你的位置信息会被保留一段时间。通过列表右上角的清除功能" + "可随时手动清除信息");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { // 设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // 关闭dialog
                Sp.getItem("nearpeople", true);
            }
        });
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { // 设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
                overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
            }
        });
		builder.create().show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home :
				IntentUtils.finish(this);
				break;
			case 789 :
				showdialog();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 789, 0, "设置").setEnabled(true).setIcon(R.drawable.ic_actionbar_overflow_three)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch ( keyCode) {
			case KeyEvent.KEYCODE_BACK :
				IntentUtils.finish(this);
				break;
		}
		return true;
	}
	private void showdialog(){
		int[] mWAh=UIUtils.getWidthAndHeight(NearPeopleActivity.this);
		Dialog dialog = new Dialog(this,R.style.dialog_formbottom);
		dialog.setContentView(mView);
		Window dialogWindow = dialog.getWindow();
		//设置位置
		dialogWindow.setGravity(Gravity.BOTTOM);
		//设置dialog的宽高属性
		dialogWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, mWAh[1]/2);
		dialogWindow.setWindowAnimations(R.style.alert_anim);
		dialog.show();
	}
}
