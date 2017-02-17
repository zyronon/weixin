package ttentau.weixin.ui.activity.found;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ttentau.weixin.R;
import ttentau.weixin.bean.Friend;
import ttentau.weixin.ui.activity.BaseActivity;
import ttentau.weixin.ui.activity.UserInfoActivity;
import ttentau.weixin.uitls.Sp;
import ttentau.weixin.uitls.UIUtils;

import static ttentau.weixin.R.id.iv_user_photo;

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
	private ArrayList<Friend> friends;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearppeople);
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("附近的人");
		friends = new ArrayList<Friend>();
        fillList();
		initView();
	}
	private class Myadpater extends BaseAdapter {
		@Override
		public int getCount() {
			return friends.size();
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
				convertView = View.inflate(UIUtils.getContext(), R.layout.lv_item_weichat, null);
			}
			ViewHolder holder = ViewHolder.getInstence(convertView);
			Friend friend = friends.get(position);
			holder.tvusername.setText(friend.getName());
			holder.tvusercontent.setText("43公里以内-陆家嘴");
			holder.tvuserlasttime.setText("加个好友呗！");
			return convertView;
		}
	}

	private void initView() {

		mView = UIUtils.inflate(R.layout.popupwindow_nearpeople);
		mRl_start_look = (RelativeLayout) findViewById(R.id.rl_start_look);
		mBtn_start_look = (Button) findViewById(R.id.btn_start_look);
		mLv = (ListView) findViewById(R.id.lv);
		mLv.setAdapter(new Myadpater());
		boolean is_first = Sp.getNearPeople("nearpeople", false);
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
					Sp.putNearPeople("nearpeople", true);
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
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("查看附近的人功能将获取你的位置信息，" + "你的位置信息会被保留一段时间。通过列表右上角的清除功能" + "可随时手动清除信息");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { // 设置确定按钮
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss(); // 关闭dialog
					Sp.putNearPeople("nearpeople", true);
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
		mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(UIUtils.getContext(), UserInfoActivity.class);
				Friend friend = friends.get(position);
				intent.putExtra("ContactsFragment_user_name",friend.getName());
				intent.putExtra("ContactsFragment_userChatid","contacts"+position);
				startActivity(intent);
				overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);

			}
		});
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
			case android.R.id.home :
				finish();
				overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
				break;
			case 789 :
				mPopupWindow.showAtLocation(findViewById(R.id.root), Gravity.BOTTOM, 0, 0);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 789, 0, "设置").setEnabled(true).setIcon(R.drawable.rg)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}
	private void fillList() {
		// 虚拟数据
		friends.add(new Friend("李伟"));
		friends.add(new Friend("张三"));
		friends.add(new Friend("阿三"));
		friends.add(new Friend("阿四"));
		friends.add(new Friend("段誉"));
		friends.add(new Friend("段正淳"));
		friends.add(new Friend("张三丰"));
		friends.add(new Friend("陈坤"));
		friends.add(new Friend("林俊杰1"));
		friends.add(new Friend("陈坤2"));
		friends.add(new Friend("林俊杰a"));
		friends.add(new Friend("张四"));
		friends.add(new Friend("林俊杰"));
		friends.add(new Friend("赵四"));
		friends.add(new Friend("杨坤"));
		friends.add(new Friend("赵子龙"));
		friends.add(new Friend("子龙"));
		friends.add(new Friend("龙"));
		friends.add(new Friend("一子龙"));
		friends.add(new Friend("上子龙"));
		friends.add(new Friend("赵有子龙"));
		friends.add(new Friend("不子龙"));
		friends.add(new Friend("中子龙"));
		friends.add(new Friend("杨坤1"));
		friends.add(new Friend("李伟1"));
		friends.add(new Friend("人宋江"));
		friends.add(new Friend("灶宋江1"));
		friends.add(new Friend("仍李伟3"));
		friends.add(new Friend("二李伟3"));
		friends.add(new Friend("李呻伟3"));
		friends.add(new Friend("入李伟3"));
		friends.add(new Friend("写好伟3"));
	}
	static class ViewHolder {
		public android.widget.ImageView ivuserphoto;
		public android.widget.TextView tvusername;
		public android.widget.TextView tvusercontent;
		public android.widget.TextView tvuserlasttime;

		private ViewHolder(View convertView) {
			this.tvuserlasttime = (TextView) convertView.findViewById(R.id.tv_user_last_time);
			this.tvusercontent = (TextView) convertView.findViewById(R.id.tv_user_content);
			this.tvusername = (TextView) convertView.findViewById(R.id.tv_user_name);
			this.ivuserphoto = (ImageView) convertView.findViewById(iv_user_photo);
		}

		public static ViewHolder getInstence(View convertView) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			}
			return holder;
		}
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
