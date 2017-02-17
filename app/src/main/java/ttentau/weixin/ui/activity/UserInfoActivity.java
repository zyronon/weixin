package ttentau.weixin.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import ttentau.weixin.R;
import ttentau.weixin.uitls.UIUtils;

/**
 * 这里写的比较乱 Created by ttent on 2017/2/8.
 */
public class UserInfoActivity extends BaseActivity {

	private TextView mTv_name;
	private Button mSendmessage;
	private Button btn_sendVideo;
	private View mView;
	private PopupWindow mPopupWindow;
	private WindowManager mWindowManager;
	private View mView1;
	private WindowManager.LayoutParams mLp;
	private boolean isShow = false;
	private TextView mTv_userChatid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);

		final String ContactsFragment_user_name = getIntent().getStringExtra("ContactsFragment_user_name");
		final String ContactsFragment_userChatid = getIntent().getStringExtra("ContactsFragment_userChatid");
		final String ChatActivity_search_chatid = getIntent().getStringExtra("ChatActivity_search_chatid");
		mSendmessage = (Button) findViewById(R.id.btn_sendmessage);
		btn_sendVideo = (Button) findViewById(R.id.btn_sendVideo);
		mView = UIUtils.inflate(R.layout.popupwindow);
		mTv_name = (TextView) findViewById(R.id.tv_name);
		mTv_userChatid = (TextView) findViewById(R.id.tv_username_count);
		if (ContactsFragment_user_name != null) {
			mTv_name.setText(ContactsFragment_user_name);
			mTv_userChatid.setText(ContactsFragment_userChatid);
		}
		if (ChatActivity_search_chatid != null) {
			mTv_name.setText(ChatActivity_search_chatid);
			mTv_userChatid.setText(ChatActivity_search_chatid);
		}
		mWindowManager = getWindowManager();

		int height = mWindowManager.getDefaultDisplay().getHeight();
		int width = mWindowManager.getDefaultDisplay().getWidth();

		// 动态设置两个button的宽度
		ViewGroup.LayoutParams lp = mSendmessage.getLayoutParams();
		lp.width = width - 20;
		mSendmessage.setLayoutParams(lp);
		btn_sendVideo.setLayoutParams(lp);

		// 初始化半透明view
		// -height/2是设置y的座标，设置这个WindowManager.LayoutParams.FLAG_SPLIT_TOUCH才
		// 能响应view的触摸事件
		mLp = new WindowManager.LayoutParams(width, height / 2, 0, -height / 2,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_SPLIT_TOUCH | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSPARENT);
		mView1 = new View(this);
		mView1.setBackgroundResource(R.color.transparent_black);
		// mWindowManager.addView(mView1, mLp);
		mView1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isShow) {
					mPopupWindow.dismiss();
					mWindowManager.removeView(mView1);
					isShow = false;
				}
			}
		});

		ActionBar ab = getSupportActionBar();
		ab.setTitle("详细资料");
		ab.setDisplayHomeAsUpEnabled(true);
		int heightPixels = getResources().getDisplayMetrics().heightPixels / 2;

		mPopupWindow = new PopupWindow(mView, LinearLayout.LayoutParams.MATCH_PARENT,
				height - height / 2 - getStatusBarHeight(), true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
		mPopupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
		mSendmessage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                if (ChatActivity_search_chatid!=null){
                    finish();
					overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
                    return;
                }
				Intent intent = new Intent(UserInfoActivity.this, ChatActivity.class);
				intent.putExtra("UserInfoActivity_user_name", ContactsFragment_user_name);
				intent.putExtra("UserInfoActivity_user_Chatid", ContactsFragment_userChatid);
				startActivity(intent);
				overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
				finish();
				overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
			}
		});

	}
	// 获取状态栏的高度
	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
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
				finish();
				overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
				break;
			case R.id.menu_more :
				// mPopupWindow.showAsDropDown(mSendmessage);
				if (!isShow) {
					mPopupWindow.showAtLocation(findViewById(R.id.root), Gravity.BOTTOM, 0, 0);
					mWindowManager.addView(mView1, mLp);
					isShow = true;
				}
				// UIUtils.Toast("吐一下");
		}
		return true;
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
