package ttentau.weixin.activity.found;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import ttentau.weixin.R;
import ttentau.weixin.activity.BaseActivity;
import ttentau.weixin.adapter.recyclerview.FriendCircleAdapter;
import ttentau.weixin.bean.CommentConfig;
import ttentau.weixin.bean.CommentItem;
import ttentau.weixin.bean.FavortItem;
import ttentau.weixin.bean.MyCircleItem;
import ttentau.weixin.db.FriendDb;
import ttentau.weixin.mvp.contract.CircleContract;
import ttentau.weixin.mvp.presenter.CirclePresenter;
import ttentau.weixin.widgets.CommentListView;
import ttentau.weixin.uitls.CommonUtils;
import ttentau.weixin.uitls.Data2Data;
import ttentau.weixin.uitls.UIUtils;

import static ttentau.weixin.uitls.UIUtils.getStatusBarHeight;


/**
 * Created by ttent on 2017/3/15.
 */

public class FriendActivity extends BaseActivity implements CircleContract.View, View.OnClickListener {

	private static final String TAG = "FriendActivity";
	private RecyclerView mRlv;
	private CirclePresenter mCp;
	private FriendCircleAdapter mMyadapter;
	private static final int LOAD = 1;
	private CommentConfig commentConfig;
	private LinearLayout mEditTextBodyLl;
	private LinearLayout edittextbody;
	private EditText editText;
	private Button sendIv;
	private RelativeLayout bodyLayout;
	private int currentKeyboardH;
	private int screenHeight;
	private int editTextBodyHeight;
	private LinearLayoutManager mLm;
	private int selectCircleItemH;
	private int selectCommentItemOffset;
	private myBroadCast mMyBroadCast;
	private static String ACTION="notifyFriendUpdate";
	private AlertDialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		mCp = new CirclePresenter(this, this);
		getSupportActionBar().setTitle("朋友圈");
		initView();
		initData();
		initBroadCast();
	}
	private void initBroadCast() {
		mMyBroadCast = new myBroadCast();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION);
		registerReceiver(mMyBroadCast,filter);
	}
	class myBroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			//Toast.makeText(context, "666", Toast.LENGTH_SHORT).show();
			MyCircleItem mMsgcInfo = (MyCircleItem) intent.getSerializableExtra("msgInfo");
			//如果从FriendAddActivity(也就是发送朋友圈那个Acvtivity)传过来的数据不为空就添加到当前list中
			if (mMsgcInfo!=null){
				Log.e("atg",mMsgcInfo.getContent());
				//mTfi.add(0,mMsgInfo);
				//mAdapter.notifyDataSetChanged();
//				mMyadapter.setDatas();
				mCp.upData(mMsgcInfo);
			}
		}
	}

	private void initData() {
		mMyadapter = new FriendCircleAdapter(this);
		mMyadapter.setPresenter(mCp);
		mCp.loadData(LOAD);
	}

	private void initView() {
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		mRlv = (RecyclerView) findViewById(R.id.rlv);
		mLm = new LinearLayoutManager(this);
		mLm.setOrientation(OrientationHelper.VERTICAL);
		mRlv.setLayoutManager(mLm);

		mRlv.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (edittextbody.getVisibility() == View.VISIBLE) {
					updateEditTextBodyVisible(View.GONE, null);
					return true;
				}
				return false;
			}
		});
		edittextbody = (LinearLayout) findViewById(R.id.editTextBodyLl);
		editText = (EditText) findViewById(R.id.circleEt);
		sendIv = (Button) findViewById(R.id.sendIv);
		sendIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mCp != null) {
					// 发布评论
					String content = editText.getText().toString().trim();
					if (TextUtils.isEmpty(content)) {
						Toast.makeText(FriendActivity.this, "评论内容不能为空...", Toast.LENGTH_SHORT).show();
						return;
					}
					mCp.addComment(content, commentConfig);
				}
				updateEditTextBodyVisible(View.GONE, null);
			}
		});

		setViewTreeObserver();

	}

	@Override
	public void upData(MyCircleItem mMsgcInfo) {
		ArrayList<MyCircleItem> datas = mMyadapter.getDatas();
		datas.add(0,mMsgcInfo);
		mMyadapter.notifyDataSetChanged();
	}

	@Override
	public void update2DeleteCircle(int pos) {
		ArrayList<MyCircleItem> datas = mMyadapter.getDatas();
		datas.remove(pos);
		mMyadapter.notifyDataSetChanged();
	}

	@Override
	public void update2AddFavorite(int circlePosition, FavortItem addItem) {
		MyCircleItem myCircleItem = mMyadapter.getDatas().get(circlePosition);
		ArrayList<FavortItem> favorters = myCircleItem.getFavorters();
		if (favorters == null) {
			favorters = new ArrayList<>();
			myCircleItem.setFavorters(favorters);
		}
		favorters.add(addItem);
		myCircleItem.setIsPraise(true);
		mMyadapter.notifyDataSetChanged();
	}

	@Override
	public void update2DeleteFavort(int circlePosition, String favortId) {
		MyCircleItem myCircleItem = mMyadapter.getDatas().get(circlePosition);
		ArrayList<FavortItem> favorters = myCircleItem.getFavorters();
		for (int i = 0; i < favorters.size(); i++) {
			if (favorters.size() == 1) {
				favorters.remove(0);
				myCircleItem.setIsPraise(false);
				mMyadapter.notifyDataSetChanged();
				return;
			}
			String id = favorters.get(i).getUser().getId();
			if (favortId.equals(id)) {
				favorters.remove(i);
				myCircleItem.setIsPraise(false);
                mMyadapter.notifyDataSetChanged();
                return;
			}
		}
	}

	@Override
	public void update2AddComment(int circlePosition, CommentItem addItem) {
		MyCircleItem myCircleItem = mMyadapter.getDatas().get(circlePosition);
		ArrayList<CommentItem> comments = myCircleItem.getComments();
		if (comments==null){
			comments=new ArrayList<>();
			myCircleItem.setComments(comments);
		}
		addItem.setId(comments.size()+"");
		comments.add(addItem);
		mMyadapter.notifyDataSetChanged();
		editText.setText("");
	}

	@Override
	public void update2DeleteComment(int circlePosition, String commentId) {
		//Log.e("tag>>>>updnt",commentId+"");
		MyCircleItem myCircleItem = mMyadapter.getDatas().get(circlePosition);
		ArrayList<CommentItem> comments = myCircleItem.getComments();
		//Log.e("tag>>>>updnt",comments.size()+"");
		for (int i = 0; i < comments.size(); i++) {
			CommentItem commentItem = comments.get(i);
			if (commentItem.getId().equals(commentId)){
				comments.remove(i);
			}
		}
		//Log.e("tag>>>>updnt",comments.size()+"");
		mMyadapter.notifyDataSetChanged();

	}

	@Override
	public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {
		this.commentConfig = commentConfig;
		edittextbody.setVisibility(visibility);

		measureCircleItemHighAndCommentItemOffset(commentConfig);

		if (View.VISIBLE == visibility) {
			editText.requestFocus();
			// 弹出键盘
			CommonUtils.showSoftInput(editText.getContext(), editText);

		} else if (View.GONE == visibility) {
			// 隐藏键盘
			CommonUtils.hideSoftInput(editText.getContext(), editText);
		}
	}

	@Override
	public void update2loadData(int loadType, ArrayList<MyCircleItem> datas) {
		mMyadapter.setDatas(datas);
		mRlv.setAdapter(mMyadapter);
	}
	private void setViewTreeObserver() {
		bodyLayout = (RelativeLayout) findViewById(R.id.bodyLayout);
		final ViewTreeObserver swipeRefreshLayoutVTO = bodyLayout.getViewTreeObserver();
		swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {

				Rect r = new Rect();
				bodyLayout.getWindowVisibleDisplayFrame(r);
				int statusBarH = getStatusBarHeight();// 状态栏高度
				int screenH = bodyLayout.getRootView().getHeight();
				if (r.top != statusBarH) {
					// 在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
					r.top = statusBarH;
				}
				int keyboardH = screenH - (r.bottom - r.top);
				Log.d(TAG, "screenH＝ " + screenH + " &keyboardH = " + keyboardH + " &r.bottom=" + r.bottom + " &top="
						+ r.top + " &statusBarH=" + statusBarH);

				if (keyboardH == currentKeyboardH) {// 有变化时才处理，否则会陷入死循环
					return;
				}

				currentKeyboardH = keyboardH;
				screenHeight = screenH;// 应用屏幕的高度
				editTextBodyHeight = edittextbody.getHeight();

				if (keyboardH < 150) {// 说明是隐藏键盘的情况
					updateEditTextBodyVisible(View.GONE, null);
					return;
				}
				// 偏移listview
				if (mLm != null && commentConfig != null) {
					mLm.scrollToPositionWithOffset(commentConfig.circlePosition, getListviewOffset(commentConfig));
				}
			}
		});
	}
	private void measureCircleItemHighAndCommentItemOffset(CommentConfig commentConfig) {
		if (commentConfig == null)
			return;

		int firstPosition = mLm.findFirstVisibleItemPosition();
		// 只能返回当前可见区域（列表可滚动）的子项
		View selectCircleItem = mLm.getChildAt(commentConfig.circlePosition - firstPosition);

		if (selectCircleItem != null) {
			selectCircleItemH = selectCircleItem.getHeight();
		}

		if (commentConfig.commentType == CommentConfig.Type.REPLY) {
			// 回复评论的情况
			CommentListView commentLv = (CommentListView) selectCircleItem.findViewById(R.id.tv_friend_comment);
			if (commentLv != null) {
				// 找到要回复的评论view,计算出该view距离所属动态底部的距离
				View selectCommentItem = commentLv.getChildAt(commentConfig.commentPosition);
				if (selectCommentItem != null) {
					// 选择的commentItem距选择的CircleItem底部的距离
					selectCommentItemOffset = 0;
					View parentView = selectCommentItem;
					do {
						int subItemBottom = parentView.getBottom();
						parentView = (View) parentView.getParent();
						if (parentView != null) {
							selectCommentItemOffset += (parentView.getHeight() - subItemBottom);
						}
					} while (parentView != null && parentView != selectCircleItem);
				}
			}
		}
	}
	private int getListviewOffset(CommentConfig commentConfig) {
		if (commentConfig == null)
			return 0;
		// 这里如果你的listview上面还有其它占高度的控件，则需要减去该控件高度，listview的headview除外。
		// int listviewOffset = mScreenHeight - mSelectCircleItemH -
		// mCurrentKeyboardH - mEditTextBodyHeight;
		int listviewOffset = screenHeight - selectCircleItemH - currentKeyboardH - editTextBodyHeight
				- UIUtils.dip2px(44);
		if (commentConfig.commentType == CommentConfig.Type.REPLY) {
			// 回复评论的情况
			listviewOffset = listviewOffset + selectCommentItemOffset;
		}
		Log.i(TAG, "listviewOffset : " + listviewOffset);
		return listviewOffset;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mMyBroadCast);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 789, 0, "设置").setEnabled(true).setIcon(R.drawable.ic_actionbar_camera)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home :
				FriendDb.getInstence(this).saveData(Data2Data.TestBean2MyCiecle(mMyadapter.getDatas()));
				finish();
				overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
				break;
			case 789 :
				View mView = View.inflate(this, R.layout.dialogview, null);
				RelativeLayout mRl_dialog_camera = (RelativeLayout) mView.findViewById(R.id.rl_dialog_camera);
				RelativeLayout mRl_dialog_form_photo = (RelativeLayout) mView.findViewById(R.id.rl_dialog_form_photo);
				mRl_dialog_camera.setOnClickListener(this);
				mRl_dialog_form_photo.setOnClickListener(this);
				AlertDialog.Builder mMBuilder = new AlertDialog.Builder(this);
				mMBuilder.setView(mView);
				mDialog=mMBuilder.show();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode){
			case KeyEvent.KEYCODE_BACK:
				FriendDb.getInstence(this).saveData(Data2Data.TestBean2MyCiecle(mMyadapter.getDatas()));
				finish();
				overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
				break;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rl_dialog_camera :
				Toast.makeText(this, "目前还没实现呢！！", Toast.LENGTH_SHORT).show();
				break;
			case R.id.rl_dialog_form_photo :
				//FriendDb.getInstence(this).saveData(mTfi);
				//	UIUtils.Toast("这是从相册选择");
				startActivity(new Intent(this,AblumActivity.class));
				overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
				mDialog.dismiss();
				break;
		}
	}
}
