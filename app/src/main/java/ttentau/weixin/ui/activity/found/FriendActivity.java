package ttentau.weixin.ui.activity.found;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import ttentau.weixin.R;
import ttentau.weixin.ui.activity.BaseActivity;
import ttentau.weixin.ui.activity.UserInfoActivity;
import ttentau.weixin.ui.activity.WebActivity;
import ttentau.weixin.ui.view.FriendRefreshView;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/2/14.
 */
public class FriendActivity extends BaseActivity implements View.OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend);
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("朋支圈");
		initView();
	}

	private void initView() {
		final FriendRefreshView frv = (FriendRefreshView) findViewById(R.id.frv);
		frv.setAdapter(new MyAdapter());
		frv.setOnRefreshListener(new FriendRefreshView.OnRefreshListener() {
			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						frv.stopRefresh();
					}
				}, 2000);
			}
		});

	}
	private class MyAdapter extends BaseAdapter implements View.OnClickListener {
		ViewHolder mHolder = null;
		@Override
		public int getCount() {
			return 5;
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
			if (convertView == null) {
				convertView = View.inflate(FriendActivity.this, R.layout.friend_item, null);
			}
			mHolder = ViewHolder.getInstence(convertView);
			mHolder.mIv_friend_photo.setOnClickListener(this);
			mHolder.mTv_friend_name.setOnClickListener(this);
			mHolder.tv_friend_dianzan_name.setOnClickListener(this);

			mHolder.ll_friend_share.setOnClickListener(this);
			mHolder.ll_friend_pinglun.setOnClickListener(this);
			mHolder.ll_friend_dianzan.setOnClickListener(this);

			return convertView;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(UIUtils.getContext(), UserInfoActivity.class);
			Random random = new Random();
			switch (v.getId()) {
				case R.id.iv_friend_photo :
				case R.id.tv_friend_name :
					intent.putExtra("ContactsFragment_user_name", "真是无奈");
					intent.putExtra("ContactsFragment_userChatid", "contacts" + 2043);
					startActivity(intent);
					overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
					break;
				case R.id.tv_friend_dianzan_name :
					String name = mHolder.tv_friend_dianzan_name.getText().toString();
					int i = random.nextInt(999);
					intent.putExtra("ContactsFragment_user_name", name);
					intent.putExtra("ContactsFragment_userChatid", "contacts" + i);
					startActivity(intent);
					overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
					break;
				case R.id.ll_friend_share :
					Intent intent1 = new Intent(UIUtils.getContext(), WebActivity.class);
					String title = mHolder.tv_friend_content_web_title.getText().toString();
					// TODO
					break;
				case R.id.iv_friend_choose :
					break;
			}
		}
	}
	static class ViewHolder {

		private final ImageView mIv_friend_photo;
		private final ImageView iv_friend_choose;
		private final TextView mTv_friend_name;
		private final TextView tv_friend_dianzan_name;
		private final TextView tv_friend_content_web_title;
		private final LinearLayout ll_friend_share;
		private final LinearLayout ll_friend_pinglun;
		private final LinearLayout ll_friend_dianzan;

		private ViewHolder(View convertView) {
			View view = UIUtils.inflate(R.layout.popupwindow_friend_choose);
			final PopupWindow popupWindow = new PopupWindow(view, UIUtils.dip2px(180), UIUtils.dip2px(38));
			popupWindow.setOutsideTouchable(true);
			popupWindow.setTouchable(true);
			popupWindow.setFocusable(true);
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setAnimationStyle(R.style.anim_choose_pop);
			final int[] location = new int[2];
			mIv_friend_photo = (ImageView) convertView.findViewById(R.id.iv_friend_photo);
			iv_friend_choose = (ImageView) convertView.findViewById(R.id.iv_friend_choose);
			mTv_friend_name = (TextView) convertView.findViewById(R.id.tv_friend_name);
			tv_friend_content_web_title = (TextView) convertView.findViewById(R.id.tv_friend_content_web_title);

			tv_friend_dianzan_name = (TextView) convertView.findViewById(R.id.tv_friend_dianzan_name);
			ll_friend_share = (LinearLayout) convertView.findViewById(R.id.ll_friend_share);
			ll_friend_pinglun = (LinearLayout) convertView.findViewById(R.id.ll_friend_pinglun);
			ll_friend_dianzan = (LinearLayout) convertView.findViewById(R.id.ll_friend_dianzan);
			iv_friend_choose.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (popupWindow.isShowing()) {
						popupWindow.dismiss();
					} else {
						v.getLocationOnScreen(location);
						popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0] - popupWindow.getWidth(),
								location[1]);
					}
				}
			});
		}
		public static ViewHolder getInstence(View convertView) {

			ViewHolder viewHolder = (ViewHolder) convertView.getTag();
			if (viewHolder == null) {
				viewHolder = new ViewHolder(convertView);
				convertView.setTag(viewHolder);
			}
			return viewHolder;

		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home :
				finish();
				overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
				break;
			case 789 :
				View mView = View.inflate(this, R.layout.dialogview, null);
				RelativeLayout mRl_dialog_camera = (RelativeLayout) mView.findViewById(R.id.rl_dialog_camera);
				RelativeLayout mRl_dialog_form_photo = (RelativeLayout) mView.findViewById(R.id.rl_dialog_form_photo);
				mRl_dialog_camera.setOnClickListener(this);
				mRl_dialog_form_photo.setOnClickListener(this);
				AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
				mBuilder.setView(mView);
				mBuilder.show();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 789, 0, "设置").setEnabled(true).setIcon(R.drawable.actionbar_icon_camera)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK :
				finish();
				overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
				break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rl_dialog_camera :
				UIUtils.Toast("这是拍摄");
				break;
			case R.id.rl_dialog_form_photo :
				UIUtils.Toast("这是从相册选择");
				break;
		}
	}
}
