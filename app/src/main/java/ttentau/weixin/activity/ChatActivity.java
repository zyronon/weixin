package ttentau.weixin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.ArrayList;
import java.util.List;

import ttentau.weixin.R;
import ttentau.weixin.bean.Msg;
import ttentau.weixin.db.SaveMsgInfo;
import ttentau.weixin.db.SaveMsgList;
import ttentau.weixin.uitls.ActivityCollector;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/2/8.
 */
public class ChatActivity extends BaseActivity implements View.OnClickListener, EMMessageListener {

	private ListView mLv_content;
	private ViewPager mVp_enjoy;
	private ImageView mIv_add;
	private boolean vp_is_show;
	private PopupWindow mPopupWindow;
	private View mView;
	private Button mBtn_send_message;
	private EditText mEt_input;
	private boolean btn_send_is_show;
	private ArrayList<Msg> mList;
	private myLvAdapter mAdapter;
	private String mUserID;
	private EMConversation mConversation;
	private EMMessageListener mMessageListener;
	private String tag = "ChatActivity";
	private SaveMsgInfo mDb;
	private SaveMsgList mMsgList;
	private ImageView mIv_voice;
	private ImageView mIv_voice_keyboard;
	private Button mBtn_startspeak;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		mDb = SaveMsgInfo.getInstence(ChatActivity.this);
		mMsgList = SaveMsgList.getInstence(ChatActivity.this);
		Intent intent = getIntent();
		String weiChatFragment_userid = intent.getStringExtra("WeiChatFragment_userid");
		if (weiChatFragment_userid != null) {
			mUserID = weiChatFragment_userid;
		}
		String userInfoActivity_user_chatid = intent.getStringExtra("UserInfoActivity_user_Chatid");
		if (userInfoActivity_user_chatid != null) {
			mUserID = userInfoActivity_user_chatid;
		}
		String SearchActivity_userid = intent.getStringExtra("SearchActivity_userid");
		if (SearchActivity_userid != null) {
			mUserID = SearchActivity_userid;
		}
		mMessageListener = this;
		initView();
		initData();
		if (mUserID != null) {
			initConversation();
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		// 添加消息监听
		EMClient.getInstance().chatManager().addMessageListener(mMessageListener);
	}
	@Override
	protected void onStop() {
		super.onStop();
		// 移除消息监听
		EMClient.getInstance().chatManager().removeMessageListener(mMessageListener);
	}
	private void initConversation() {

		/**
		 * 初始化会话对象，这里有三个参数么， 第一个表示会话的当前聊天的 useranme 或者 groupid 第二个是绘画类型可以为空
		 * 第三个表示如果会话不存在是否创建
		 */
		mConversation = EMClient.getInstance().chatManager().getConversation(mUserID, null, true);
		// 设置当前会话未读数为 0
		mConversation.markAllMessagesAsRead();
		int count = mConversation.getAllMessages().size();
		if (count < mConversation.getAllMsgCount() && count < 20) {
			// 获取已经在列表中的最上边的一条消息id
			String msgId = mConversation.getAllMessages().get(0).getMsgId();
			// 分页加载更多消息，需要传递已经加载的消息的最上边一条消息的id，以及需要加载的消息的条数
			mConversation.loadMoreMsgFromDB(msgId, 20 - count);
		}
		// 打开聊天界面获取最后一条消息内容并显示
		if (mConversation.getAllMessages().size() > 0) {
			EMMessage messge = mConversation.getLastMessage();
			EMTextMessageBody body = (EMTextMessageBody) messge.getBody();
			// 将消息内容和时间显示出来
			// mContentText.setText(body.getMessage() + " - " +
			// mConversation.getLastMessage().getMsgTime());
			/*
			 * mList.add(new Msg(0, body.getMessage()));
			 * mAdapter.notifyDataSetChanged();
			 */
		}
	}
	private void initView() {
		final int allHeight = getWindowManager().getDefaultDisplay().getHeight();

		ActionBar ab = getSupportActionBar();
		if (!TextUtils.isEmpty(mUserID)) {
			ab.setTitle(mUserID);
		} else {
			ab.setTitle("昵称");
		}
		ab.setDisplayHomeAsUpEnabled(true);
		mView = UIUtils.inflate(R.layout.vp_item);
		mLv_content = (ListView) findViewById(R.id.Lv_content);
		mVp_enjoy = (ViewPager) findViewById(R.id.vp_enjoy);
		mIv_add = (ImageView) findViewById(R.id.iv_add);
		mBtn_send_message = (Button) findViewById(R.id.btn_send_message);
		mEt_input = (EditText) findViewById(R.id.et_input);
		mIv_voice = (ImageView) findViewById(R.id.iv_voice);
		mIv_voice_keyboard = (ImageView) findViewById(R.id.iv_voice_keyboard);
		mBtn_startspeak = (Button) findViewById(R.id.btn_startspeak);
		mLv_content.requestFocus();
		/*
		 * mPopupWindow = new PopupWindow(mView,
		 * LinearLayout.LayoutParams.MATCH_PARENT, allHeight * 3 / 10, true);
		 * mPopupWindow.setTouchable(true);
		 * mPopupWindow.setOutsideTouchable(true);
		 * mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(),
		 * (Bitmap) null); mPopupWindow.setOnDismissListener(new
		 * PopupWindow.OnDismissListener() {
		 * 
		 * @Override public void onDismiss() {
		 * mVp_enjoy.setVisibility(View.GONE); vp_is_show = false; } });
		 */

		ViewGroup.LayoutParams vp_lp = mVp_enjoy.getLayoutParams();
		vp_lp.height = allHeight * 3 / 10;
		mVp_enjoy.setLayoutParams(vp_lp);
		mIv_add.setOnClickListener(this);

	}
	private void initData() {
		mList = new ArrayList<Msg>();
		getSaveMsgInfo();
		mAdapter = new myLvAdapter();
		mVp_enjoy.setAdapter(new MyVpAdapter());
		mLv_content.setAdapter(mAdapter);
		mLv_content.setSelection(mList.size());
		mLv_content.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction()==MotionEvent.ACTION_DOWN){
					if (vp_is_show){
						mVp_enjoy.setVisibility(View.VISIBLE);
					}
				}
				return true;
			}
		});
		mEt_input.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (count == 0) {
					mBtn_send_message.setVisibility(View.INVISIBLE);
					mIv_add.setVisibility(View.VISIBLE);
					btn_send_is_show = false;
					return;
				}
				if (!btn_send_is_show) {
					mBtn_send_message.setVisibility(View.VISIBLE);
					mIv_add.setVisibility(View.INVISIBLE);
					btn_send_is_show = true;
				}
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		mBtn_send_message.setOnClickListener(this);
		mIv_voice.setOnClickListener(this);
		mIv_voice_keyboard.setOnClickListener(this);
		mBtn_startspeak.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_send_message :
				String content = mEt_input.getText().toString();

				// 创建一条新消息，第一个参数为消息内容，第二个为接受者username
				EMMessage message = EMMessage.createTxtSendMessage(content, mUserID);
				EMClient.getInstance().chatManager().sendMessage(message);
				message.setMessageStatusCallback(new EMCallBack() {
					@Override
					public void onSuccess() {
						// 消息发送成功，打印下日志，正常操作应该去刷新ui
						Log.i("lzan13", "send message on success");
					}

					@Override
					public void onError(int i, String s) {
						// 消息发送失败，打印下失败的信息，正常操作应该去刷新ui
						Log.i("lzan13", "send message on error " + i + " - " + s);
					}

					@Override
					public void onProgress(int i, String s) {
						// 消息发送进度，一般只有在发送图片和文件等消息才会有回调，txt不回调
					}
				});
				mList.add(new Msg(0, content));
				mAdapter.notifyDataSetChanged();
				mEt_input.setText("");
				mBtn_send_message.setVisibility(View.INVISIBLE);
				mIv_add.setVisibility(View.VISIBLE);
				mLv_content.setSelection(mList.size());
				btn_send_is_show = false;
				break;
			case R.id.iv_add :
				if (!vp_is_show) {
					// mPopupWindow.showAtLocation(findViewById(R.id.root_ll),
					// Gravity.BOTTOM, 0, 0);
					mVp_enjoy.setVisibility(View.VISIBLE);
					mLv_content.post(new Runnable() {
						@Override
						public void run() {
							mLv_content.requestFocus();
							mLv_content.setSelection(mList.size());
						}
					});
					vp_is_show = true;
				} else {
					mVp_enjoy.setVisibility(View.GONE);
					vp_is_show = false;
				}
				mIv_voice_keyboard.setVisibility(View.INVISIBLE);
				mIv_voice.setVisibility(View.VISIBLE);
				mEt_input.setVisibility(View.VISIBLE);
				mBtn_startspeak.setVisibility(View.INVISIBLE);
				break;
			case R.id.iv_voice :
				mIv_voice_keyboard.setVisibility(View.VISIBLE);
				mIv_voice.setVisibility(View.INVISIBLE);
				mEt_input.setVisibility(View.INVISIBLE);
				mBtn_startspeak.setVisibility(View.VISIBLE);
				if (vp_is_show) {
					mVp_enjoy.setVisibility(View.GONE);
					vp_is_show = false;
				}
				break;
			case R.id.iv_voice_keyboard :
				mIv_voice_keyboard.setVisibility(View.INVISIBLE);
				mIv_voice.setVisibility(View.VISIBLE);
				mEt_input.setVisibility(View.VISIBLE);
				mBtn_startspeak.setVisibility(View.INVISIBLE);
				break;
			case R.id.btn_startspeak :
				UIUtils.Toast("录音");
		}
	}
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0 :
					EMMessage message = (EMMessage) msg.obj;
					// 这里只是简单的demo，也只是测试文字消息的收发，所以直接将body转为EMTextMessageBody去获取内容
					EMTextMessageBody body = (EMTextMessageBody) message.getBody();
					// 将新的消息内容和时间加入到下边
					// mContentText.setText(mContentText.getText() + "\n" +
					// body.getMessage() + " <- " + message.getMsgTime());
					mList.add(new Msg(1, body.getMessage()));
					mAdapter.notifyDataSetChanged();
					mLv_content.setSelection(mList.size());
					break;
			}
		}
	};

	@Override
	public void onMessageReceived(List<EMMessage> list) {
		// 循环遍历当前收到的消息
		for (EMMessage message : list) {
			if (message.getFrom().equals(mUserID)) {
				// 设置消息为已读
				mConversation.markMessageAsRead(message.getMsgId());

				// 因为消息监听回调这里是非ui线程，所以要用handler去更新ui
				Message msg = mHandler.obtainMessage();
				msg.what = 0;
				msg.obj = message;
				mHandler.sendMessage(msg);
			} else {
				// 如果消息不是当前会话的消息发送通知栏通知
			}
		}
	}

	@Override
	public void onCmdMessageReceived(List<EMMessage> list) {
		for (int i = 0; i < list.size(); i++) {
			// 透传消息
			EMMessage cmdMessage = list.get(i);
			EMCmdMessageBody body = (EMCmdMessageBody) cmdMessage.getBody();
			Log.i("lzan13", body.action());
		}
	}

	@Override
	public void onMessageReadAckReceived(List<EMMessage> list) {

	}

	@Override
	public void onMessageDeliveryAckReceived(List<EMMessage> list) {

	}

	@Override
	public void onMessageChanged(EMMessage emMessage, Object o) {

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.chat_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home :
				Intent intent1 = new Intent(ChatActivity.this, MainActivity.class);
				startActivity(intent1);
				overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
				ActivityCollector.finishAll();
				SaveMsgInfo();
				break;
			case R.id.menu_chat_user :
				Intent intent = new Intent(ChatActivity.this, UserInfoActivity.class);
				intent.putExtra("ChatActivity_search_chatid", mUserID);
				startActivity(intent);
				overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
				break;
		}
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (event.getAction()) {
			case KeyEvent.ACTION_DOWN :
				if (KeyEvent.KEYCODE_BACK == keyCode) {
					Intent intent1 = new Intent(ChatActivity.this, MainActivity.class);
					startActivity(intent1);
					overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
					SaveMsgInfo();
					ActivityCollector.finishAll();
				}
				break;
		}
		return true;
	}
	private void getSaveMsgInfo() {
		ArrayList<Msg> msgs = mDb.query(mUserID);
		if (msgs != null) {
			for (Msg s : msgs) {
				mList.add(s);
			}
		}
	}
	private void SaveMsgInfo() {
		if (mList.size() != 0) {
			ArrayList<Msg> query = mDb.query(mUserID);
			if (query.size() != 0) {
				if (mList.size() != query.size()) {
					int saveSize = mList.size() - query.size();
					for (int i = 0; i < saveSize; i++) {
						String content = mList.get(query.size() + i).getContent();
						int type = mList.get(query.size() + i).getType();
						mDb.insert(mUserID, "ttentau", content, type);
					}
				}
			} else {
				for (int i = 0; i < mList.size(); i++) {
					String content = mList.get(i).getContent();
					int type = mList.get(i).getType();
					mDb.insert(mUserID, "ttentau", content, type);
				}
			}
			boolean isContain = mMsgList.query(mUserID);
			//Log.e("tag", "跑到iscontan了");
			if (!isContain) {
				mMsgList.insert(mUserID, mUserID, mList.get(mList.size() - 1).getContent());
			} else {
				mMsgList.updata(mUserID, mList.get(mList.size() - 1).getContent());
			}
		}
	}

	class myLvAdapter extends BaseAdapter {

		private android.widget.TextView tvrightsend;
		private LinearLayout messagerightsend;
		private android.widget.TextView tvleftreceive;
		private LinearLayout messageleftreceive;

		@Override
		public int getCount() {
			return mList.size();
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
			View view = UIUtils.inflate(R.layout.lv_message_layout);
			this.messageleftreceive = (LinearLayout) view.findViewById(R.id.message_left_receive);
			this.tvleftreceive = (TextView) view.findViewById(R.id.tv_left_receive);
			this.messagerightsend = (LinearLayout) view.findViewById(R.id.message_right_send);
			this.tvrightsend = (TextView) view.findViewById(R.id.tv_right_send);
			Msg msg = mList.get(position);
			if (msg.getType() == Msg.TYPR_SEND) {
				messagerightsend.setVisibility(View.VISIBLE);
				messageleftreceive.setVisibility(View.GONE);
				tvrightsend.setText(msg.getContent());
			} else {
				messagerightsend.setVisibility(View.GONE);
				messageleftreceive.setVisibility(View.VISIBLE);
				tvleftreceive.setText(msg.getContent());
			}
			return view;
		}
	}

	private class MyVpAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return 1;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mView);
			return mView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
}
