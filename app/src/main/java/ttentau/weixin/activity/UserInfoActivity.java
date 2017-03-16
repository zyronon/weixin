package ttentau.weixin.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import ttentau.weixin.R;
import ttentau.weixin.uitls.IntentUtils;
import ttentau.weixin.uitls.UIUtils;

/**
 * 这里写的比较乱 Created by ttent on 2017/2/8.
 */
public class UserInfoActivity extends BaseActivity {

	private TextView mTv_name;
	private Button mSendmessage;
	private Button btn_sendVideo;

	private PopupWindow mPopupWindow;
	private TextView mTv_userChatid;
	private String mContactsFragment_user_name;
	private String mContactsFragment_userChatid;
	private String mChatActivity_search_chatid;
	private int[] mWAh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);

		mWAh = UIUtils.getWidthAndHeight(this);
		initView();
		initData();
	}

	private void initData() {
		mContactsFragment_user_name = getIntent().getStringExtra("ContactsFragment_user_name");
		mContactsFragment_userChatid = getIntent().getStringExtra("ContactsFragment_userChatid");
		mChatActivity_search_chatid = getIntent().getStringExtra("ChatActivity_search_chatid");
		if (mContactsFragment_user_name != null) {
			mTv_name.setText(mContactsFragment_user_name);
			mTv_userChatid.setText(mContactsFragment_userChatid);
		}
		if (mChatActivity_search_chatid != null) {
			mTv_name.setText(mChatActivity_search_chatid);
			mTv_userChatid.setText(mChatActivity_search_chatid);
		}
	}

	private void initView() {

		ActionBar ab = getSupportActionBar();
		ab.setTitle("详细资料");
		ab.setDisplayHomeAsUpEnabled(true);

		mSendmessage = (Button) findViewById(R.id.btn_sendmessage);
		btn_sendVideo = (Button) findViewById(R.id.btn_sendVideo);
		mTv_userChatid = (TextView) findViewById(R.id.tv_username_count);
		mTv_name = (TextView) findViewById(R.id.tv_name);

		mSendmessage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mChatActivity_search_chatid!=null){
					finish();
					overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
					return;
				}
				Intent intent = new Intent(UserInfoActivity.this, ChatActivity.class);
				intent.putExtra("UserInfoActivity_user_name", mContactsFragment_user_name);
				intent.putExtra("UserInfoActivity_user_Chatid", mContactsFragment_userChatid);
				startActivity(intent);
				overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.user_info_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home :
				IntentUtils.finish(this);
				break;
			case R.id.menu_more :
				showdialog();
		}
		return true;
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
		Dialog dialog = new Dialog(this,R.style.dialog_formbottom);
		View mView = UIUtils.inflate(R.layout.dialog_larg_userinfo);
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
