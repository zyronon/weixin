package ttentau.weixin.activity.found;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import ttentau.weixin.R;
import ttentau.weixin.activity.BaseActivity;
import ttentau.weixin.bean.CommentItem;
import ttentau.weixin.bean.PhotoInfo;
import ttentau.weixin.bean.TestFriendInfo;
import ttentau.weixin.bean.User;
import ttentau.weixin.db.FriendDb;
import ttentau.weixin.ui.widgets.CommentListView;
import ttentau.weixin.ui.widgets.ExpandTextView;
import ttentau.weixin.ui.widgets.FriendRefreshView;
import ttentau.weixin.ui.widgets.MyGridView;
import ttentau.weixin.ui.widgets.MyPraiseTextView;
import ttentau.weixin.ui.widgets.OnDetectScrollListener;
import ttentau.weixin.uitls.CommonUtils;
import ttentau.weixin.uitls.DataUtil;
import ttentau.weixin.uitls.UIUtils;

/**
 * Created by ttent on 2017/3/8.
 */

public class FriendActivity extends BaseActivity implements View.OnClickListener {

	private ArrayList<TestFriendInfo> mTfi;
	private int mYear;
	private int mHour;
	private int mMinute;
	private int mMouth;
	private int mDay;
	private LinearLayout editTextBodyLl;
	private EditText circleEt;
	private Button btn_send_blue;
	private int mCountPos = 1;
	private MyAdapter mAdapter;
	private String[] mSplitCountInfo;
	private AlertDialog.Builder mMBuilder;
	private AlertDialog mDialog;
	private FriendRefreshView mFrv;
	private TestFriendInfo mMsgInfo;
	private ArrayList<Integer> alint;
	private static String ACTION="notifyFriendUpdate";
	private myBroadCast mMyBroadCast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend);
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("朋支圈");

		initData();
		initView();
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
			mMsgInfo = (TestFriendInfo) intent.getSerializableExtra("msgInfo");
			//如果从FriendAddActivity(也就是发送朋友圈那个Acvtivity)传过来的数据不为空就添加到当前list中
			if (mMsgInfo!=null){
				mTfi.add(0,mMsgInfo);
				mAdapter.notifyDataSetChanged();
			}
		}
	}
	private void initData() {
		alint=new ArrayList<>() ;
		//获取当前时间年月日
		int time = (int) System.currentTimeMillis();
		Calendar instance = Calendar.getInstance();
		// instance.setTimeInMillis(time);
		mYear = instance.get(Calendar.YEAR);
		mMouth = instance.get(Calendar.MONTH) + 1;
		mDay = instance.get(Calendar.DAY_OF_MONTH);
		mHour = instance.get(Calendar.HOUR_OF_DAY);
		mMinute = instance.get(Calendar.MINUTE);
		Log.e("tag", mYear + "|" + mMouth + "|" + mDay + "|" + mHour + "|" + mMinute);

		//从数据生成类获得数据
		mTfi= FriendDb.getInstence(this).query();
		if (mTfi.size()==0){
			mTfi = DataUtil.getListInfo(this);
		}
		//给list排序
		Collections.sort(mTfi, new Comparator<TestFriendInfo>() {
			@Override
			public int compare(TestFriendInfo o1, TestFriendInfo o2) {
				if (o1.getCompareData()>o2.getCompareData()){
				//	Log.e("tag",-1+"");
					return -1;
				}else {
					return 1;
				}
			}
		});
		//如果从FriendAddActivity(也就是发送朋友圈那个Acvtivity)传过来的数据不为空就添加到当前list中
		if (mMsgInfo!=null){
			mTfi.add(0,mMsgInfo);
		}

	}
	private void initView() {
		//这个是别人写的类似朋友圈那种可以下拉的listview
		mFrv = (FriendRefreshView) findViewById(R.id.frv);
		editTextBodyLl = (LinearLayout) findViewById(R.id.editTextBodyLl);
		circleEt = (EditText) findViewById(R.id.circleEt);
		btn_send_blue = (Button) findViewById(R.id.btn_send_blue);
		//这里面是把还没有发送的评论解析
		btn_send_blue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String message = circleEt.getText().toString();
				if (UIUtils.isEmpty(message)) {
					Toast.makeText(FriendActivity.this, "评论不能为空", Toast.LENGTH_SHORT).show();
				} else {
					//获取当前item的bean类（TestFriendInfo），这个mCountPos是点击评论或者弹出popupwindow那个imageView的时候赋的值
					TestFriendInfo tfi = mTfi.get(mCountPos);
					//获取当前item的bean类（TestFriendInfo）的总评论内容（commetnContent）
					String commetnContent = tfi.getCommetnContent();
					//这个mSplitCountInfo是被回复的评论，是在点别人的评论的时候赋的值
					if (!UIUtils.isEmpty(mSplitCountInfo)) {
						String[] splitComment = commetnContent.split("\\|");
						//如果长度为1，说明只有一条评论，就不能加“|”，因为只有一条评论的话，那么应该是这个格式（1~0~哥哥~你干哈啊|）
						//如果再加“|”的话，那么就会有两个"|""|",就会解析失败
						//评论的格式有两种：第一种为：（1~0~哥哥~你干哈啊|，这种是单独评论）第二种为（0~0~哥哥~你干哈啊~1~弟弟|，这种是回复别人的评论）。可以去DataUtil里面看
						//“|”为一条评论的分割符 “~”为这条评论的数据分割符，第一位为评论id（评论排序删除用的），第二位为评论用户ID（User的id）
						//第三位为评论用户名字(User的name)，第四为内容，第五六分别为被回复的用户id和name
						if (splitComment.length == 1) {
							tfi.setCommetnContent(commetnContent + splitComment.length + "~0~哥哥~" +message+"~"+ mSplitCountInfo[1]
									+ "~" + mSplitCountInfo[2]);
						} else {
							tfi.setCommetnContent(commetnContent + "|" + splitComment.length + "~0~哥哥~"
									 + message+"~"+ mSplitCountInfo[1] + "~" + mSplitCountInfo[2]);
						}
					} else {
						tfi.setCommentCount(1);
						if (UIUtils.isEmpty(commetnContent)) {
							tfi.setCommetnContent("0~0~哥哥~" + message + "|");
						} else {
							//如果长度为1，说明只有一条评论，就不能加“|”，因为只有一条评论的话，那么应该是这个格式（1~0~哥哥~你干哈啊|）
							//如果再加“|”的话，那么就会有两个"|""|",就会解析失败
							//评论的格式有两种：第一种为：（1~0~哥哥~你干哈啊|，这种是单独评论）第二种为（0~0~哥哥~你干哈啊~1~弟弟|，这种是回复别人的评论）。可以去DataUtil里面看
							//“|”为一条评论的分割符 “~”为这条评论的数据分割符，第一位为评论id（评论排序删除用的），第二位为评论用户ID（User的id）
							//第三位为评论用户名字(User的name)，第四为内容，第五六分别为被回复的用户id和name
							String[] splitComment = commetnContent.split("\\|");
							if (splitComment.length == 1) {
								tfi.setCommetnContent(commetnContent + splitComment.length + "~0~哥哥~" + message);
							} else {
								tfi.setCommetnContent(commetnContent + "|" + splitComment.length + "~0~哥哥~" + message);
							}
						}
					}
					circleEt.setText("");
					//隐藏评论框
					updateEditTextBodyVisible(View.GONE);
					mAdapter.notifyDataSetChanged();
				}
			}
		});
		mAdapter = new MyAdapter();
		mFrv.setAdapter(mAdapter);
		mFrv.setOnRefreshListener(new FriendRefreshView.OnRefreshListener() {
			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mFrv.stopRefresh();
						mAdapter.notifyDataSetChanged();
					}
				}, 2000);
			}
		});
		mFrv.setOnScrollListener(new OnDetectScrollListener() {
			@Override
			public void onUpScrolling() {
				updateEditTextBodyVisible(View.GONE);
			}

			@Override
			public void onDownScrolling() {
				updateEditTextBodyVisible(View.GONE);
			}
		});
	}
	private class MyAdapter extends BaseAdapter {
		ViewHolder mHolder = null;

		@Override
		public int getCount() {
			return mTfi.size();
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ArrayList<String> praiseItems = new ArrayList<>();
			final TestFriendInfo tfi = mTfi.get(position);
			String date = tfi.getDate();
			String praiseName = tfi.getPraiseName();
			if (convertView == null) {
				convertView = View.inflate(FriendActivity.this, R.layout.friend_item_test, null);
			}
			mHolder = ViewHolder.getInstence(convertView);
			mHolder.mIv_friend_photo.setImageDrawable(getResources().getDrawable(tfi.getPhoto()));
			mHolder.tv_friend_name.setText(tfi.getName());
			mHolder.mTv_friend_content.setText(tfi.getContent(), tfi.getContentIsExpand(), this);

			//解析时间
			String[] dataList = date.split("\\|");
			if (Integer.parseInt(dataList[0]) < mYear) {
				mHolder.tv_friend_time.setText(dataList[0] + "年" + dataList[1] + "月" + dataList[2] + "日");
			} else {
				if (Integer.parseInt(dataList[1]) < mMouth) {
					mHolder.tv_friend_time.setText(dataList[0] + "年" + dataList[1] + "月" + dataList[2] + "日");
				} else {
					if (Integer.parseInt(dataList[2]) < mDay) {
						int nearlyDay = mDay - Integer.parseInt(dataList[2]);
						switch (nearlyDay) {
							case 1 :
								mHolder.tv_friend_time.setText("昨天");
								break;
							case 2 :
								mHolder.tv_friend_time.setText("2天前");
								break;
							case 3 :
								mHolder.tv_friend_time.setText("3天前");
								break;
							default :
								mHolder.tv_friend_time
										.setText(dataList[0] + "年" + dataList[1] + "月" + dataList[2] + "日");
								break;
						}
					} else {
						if (Integer.parseInt(dataList[3]) < mHour) {
							int nearlyHour = mHour - Integer.parseInt(dataList[3]);
							mHolder.tv_friend_time.setText(nearlyHour + "小时前");
						} else {
							if (Integer.parseInt(dataList[4]) < mMinute) {
								int naerlyMinute = mMinute - Integer.parseInt(dataList[4]);
								mHolder.tv_friend_time.setText(naerlyMinute + "分钟前");
							} else {
								mHolder.tv_friend_time.setText("刚刚");
							}
						}
					}
				}
			}
			// pop
			mHolder.iv_friend_choose.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mHolder.mPopupWindow.isShowing()) {
						mHolder.mPopupWindow.dismiss();
					} else {
						v.getLocationOnScreen(mHolder.mLocation);
						mHolder.mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
								mHolder.mLocation[0] - mHolder.mPopupWindow.getWidth(), mHolder.mLocation[1]);
						if (tfi.getIsPraise() != -1) {
							mHolder.mDigBtn.setText("取消");
						} else {
							mHolder.mDigBtn.setText("赞");
						}
						mHolder.mLl_pou_choose_dianzan.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								//我是否赞
								if (tfi.getIsPraise() != -1) {
									//设为我没赞
									tfi.setIsPraise(-1);
									// praiseItems.remove(praiseItems.size()-1);
									//获取当前赞的人的名字
									String praiseName1 = tfi.getPraiseName();
									//如果等于自已|，说明只有我一个人赞了
									if (praiseName1.equals("自已|")) {
										//那么设为没有赞
										tfi.setPraiseCount(-1);
									}
									//把自已|删除掉
									String newPraise = praiseName1.substring(0, praiseName1.length() - 3);
									//再设置给当前条目的PraiseName
									tfi.setPraiseName(newPraise);
								} else {
									tfi.setIsPraise(1);
									tfi.setPraiseCount(1);
									String newPraiseName = null;
									if (!UIUtils.isEmpty(tfi.getPraiseName())) {
										newPraiseName = tfi.getPraiseName() + "|自已";
									} else {
										newPraiseName = "自已|";
									}
									tfi.setPraiseName(newPraiseName);
									// praiseItems.add("|自已");
								}
								notifyDataSetChanged();
								mHolder.mPopupWindow.dismiss();
								// Toast.makeText(FriendActivity.this, "点赞",
								// Toast.LENGTH_SHORT).show();
							}
						});
						mHolder.mLl_pou_choose_pinglun.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// Toast.makeText(FriendActivity.this, "评论",
								// Toast.LENGTH_SHORT).show();
								mHolder.mPopupWindow.dismiss();
								// mHolder.editTextBodyLl.setVisibility(View.VISIBLE);
								updateEditTextBodyVisible(View.VISIBLE,position);
								//将当前条目的位置赋给全局变量，以便评论的时候知道是给那一条评论评论
								mCountPos = position;
								mSplitCountInfo=null;
								Log.e("tag", mCountPos + "");
							}
						});
					}
				}
			});
			//这个是自定义TextView点击了全文或者收起按钮的回调
			mHolder.mTv_friend_content.setOntextStatuListener(new ExpandTextView.textStatuListener() {
				@Override
				public void open() {
					tfi.setContentIsExpand(1);
					// notifyDataSetChanged();
				}
				@Override
				public void close() {
					tfi.setContentIsExpand(-1);
					// notifyDataSetChanged();
				}
			});
			// image
			if (tfi.getImageCount() != -1) {
				mHolder.mGv_friend_content_photos.setVisibility(View.VISIBLE);
				final String imagePath = tfi.getImagePath();
				Log.e("tag",imagePath);
				String[] split = imagePath.split("\\|");
				final ArrayList<PhotoInfo> mPhotoInfos = new ArrayList<>();
				for (int i = 0; i < split.length; i++) {
					// mPhotoInfos.add(new
					// PhotoInfo(Integer.parseInt(split[i])));
					// int i1 = Integer.parseInt(split[i]);
					// Log.e("tag",i1+"");
					mPhotoInfos.add(new PhotoInfo(split[i]));
				}

				mHolder.mGv_friend_content_photos.setList(mPhotoInfos);
				mHolder.mGv_friend_content_photos.setOnItemClickListener(new MyGridView.OnItemClickListener() {
					@Override
					public void onItemClick(View view, int position) {
						Intent intent = new Intent(FriendActivity.this, ViewPagerActivity.class);
						intent.putExtra("imageInfo",imagePath);
						intent.putExtra("pos",position);
						startActivity(intent);
					}
				});
			} else {
				mHolder.mGv_friend_content_photos.setVisibility(View.GONE);
			}
			// 外链
			if (tfi.getWebContent() != -1) {
				mHolder.ll_friend_share.setVisibility(View.VISIBLE);
				/*mHolder.iv_friend_content_web_photo
						.setImageDrawable(getResources().getDrawable(tfi.getWebContent_photo()));*/
				mHolder.tv_friend_content_web_title.setText(tfi.getWebContent_content());
			} else {
				mHolder.ll_friend_share.setVisibility(View.GONE);
			}
			// 删除
			if (tfi.getIsMy() != -1) {
				mHolder.tv_friend_delete.setVisibility(View.VISIBLE);
				mHolder.tv_friend_delete.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mTfi.remove(position);
						notifyDataSetChanged();
					}
				});
			} else {
				mHolder.tv_friend_delete.setVisibility(View.GONE);
			}
			// 点赞
			if (tfi.getPraiseCount() != -1 || tfi.getCommentCount() != -1) {
				mHolder.ll_friend_pinglun.setVisibility(View.VISIBLE);
				// 点赞
				if (tfi.getPraiseCount() != -1) {
					mHolder.mTv_friend_dianzan_name.setVisibility(View.VISIBLE);
					String[] praiseList = praiseName.split("\\|");
					for (int i = 0; i < praiseList.length; i++) {
						praiseItems.add(praiseList[i]);
					}
					mHolder.mTv_friend_dianzan_name.setDatas(praiseItems);
				} else {
					mHolder.mTv_friend_dianzan_name.setVisibility(View.GONE);
				}
				// 评论
				if (tfi.getCommentCount() != -1) {
					mHolder.tv_friend_comment.setVisibility(View.VISIBLE);
					ArrayList<CommentItem> ci = new ArrayList<>();
					//总评论
					final String commetnGrossContent = tfi.getCommetnContent();
					//分割总评论
					final String[] commetnGrossContentSplit = commetnGrossContent.split("\\|");
					for (int i = 0; i < commetnGrossContentSplit.length; i++) {
						CommentItem mCommentItem;
						//每条评论
						String perCommentContent = commetnGrossContentSplit[i];
						//分割每条评论
						String[] perCommentContentSplit = perCommentContent.split("~");
						//长度为6，就是回复别人的评论
						if (perCommentContentSplit.length == 6) {
							User user = new User(perCommentContentSplit[1], perCommentContentSplit[2]);
							User replyUser = new User(perCommentContentSplit[4], perCommentContentSplit[5]);
							mCommentItem = new CommentItem(perCommentContentSplit[0], user, perCommentContentSplit[3], replyUser);
						} else {
							//自已单评论
							User user = new User(perCommentContentSplit[1], perCommentContentSplit[2]);
							mCommentItem = new CommentItem(perCommentContentSplit[0], user, perCommentContentSplit[3]);
						}
						ci.add(mCommentItem);
					}
					mHolder.tv_friend_comment.setDatas(ci);
					mHolder.tv_friend_comment.setOnItemClickListener(new CommentListView.OnItemClickListener() {
						@Override
						public void onItemClick(int countPos) {
							Log.e("tag", countPos + "");
							//赋值当前点击的位置
							mCountPos = position;
							//获到当前点击位置的那条评论
							String countInfo = commetnGrossContentSplit[countPos];
							//将这条评论赋给全局变量，以便回复这条评论的时候获取到内容和名字
							mSplitCountInfo = countInfo.split("~");
							updateEditTextBodyVisible(View.VISIBLE,position);
						}
					});
					mHolder.tv_friend_comment.setOnItemLongClickListener(new CommentListView.OnItemLongClickListener() {
						@Override
						public void onItemLongClick(final int longposition) {
							View mView = View.inflate(FriendActivity.this, R.layout.dialogview_longcount, null);
							TextView mTv_friend_dialog_longcount = (TextView) mView
									.findViewById(R.id.tv_friend_dialog_longcount);

							String countInfo = commetnGrossContentSplit[longposition];
							final String[] split11 = countInfo.split("~");
							//为0就是我自已评论的
							if (split11[1].equals("0")) {
								mTv_friend_dialog_longcount.setText("删除");
							}
							mTv_friend_dialog_longcount.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									if (split11[1].equals("0")) {
										if (commetnGrossContentSplit.length == 1) {
											tfi.setCommetnContent("");
											tfi.setCommentCount(-1);
										} else {
											StringBuffer sb = new StringBuffer();
											for (int i = 0; i < commetnGrossContentSplit.length; i++) {
												if (i!=longposition){
													sb.append(commetnGrossContentSplit[i]+"|");
												}
											}
											tfi.setCommetnContent(sb.toString());
										}
										notifyDataSetChanged();
									}else {
										Toast.makeText(FriendActivity.this, "复制了", Toast.LENGTH_SHORT).show();
									}
									mDialog.dismiss();
								}
							});
							mMBuilder = new AlertDialog.Builder(FriendActivity.this);
							mMBuilder.setView(mView);
							mDialog = mMBuilder.show();
						}
					});
				} else {
					mHolder.tv_friend_comment.setVisibility(View.GONE);
				}
			} else {
				mHolder.ll_friend_pinglun.setVisibility(View.GONE);
			}
			return convertView;
		}
	}

	static class ViewHolder {
		public final ExpandTextView mTv_friend_content;
		private final MyGridView mGv_friend_content_photos;
		private final ImageView mIv_friend_photo;
		private final ImageView iv_friend_choose;
		private final ImageView iv_friend_content_web_photo;
		private final TextView tv_friend_name;
		private final TextView tv_friend_content_web_title;
		private final TextView tv_friend_delete;
		private final TextView tv_friend_time;
		private final PopupWindow mPopupWindow;
		private final int[] mLocation;
		private final TextView mDigBtn;
		private final LinearLayout mLl_pou_choose_dianzan;
		private final LinearLayout mLl_pou_choose_pinglun;
		private final LinearLayout ll_friend_share;
		private final LinearLayout ll_friend_pinglun;

		private final MyPraiseTextView mTv_friend_dianzan_name;
		private final CommentListView tv_friend_comment;

		private ViewHolder(View convertView) {

			View view = UIUtils.inflate(R.layout.popupwindow_friend_choose);
			mDigBtn = (TextView) view.findViewById(R.id.digBtn);
			mLl_pou_choose_dianzan = (LinearLayout) view.findViewById(R.id.ll_pou_choose_dianzan);
			mLl_pou_choose_pinglun = (LinearLayout) view.findViewById(R.id.ll_pou_choose_pinglun);
			mPopupWindow = new PopupWindow(view, UIUtils.dip2px(180), UIUtils.dip2px(30));
			mPopupWindow.setOutsideTouchable(true);
			mPopupWindow.setTouchable(true);
			mPopupWindow.setFocusable(true);
			mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
			mPopupWindow.setAnimationStyle(R.style.anim_choose_pop);
			mLocation = new int[2];

			mTv_friend_content = (ExpandTextView) convertView.findViewById(R.id.tv_friend_content);
			mGv_friend_content_photos = (MyGridView) convertView.findViewById(R.id.gv_friend_content_photos);
			mTv_friend_dianzan_name = (MyPraiseTextView) convertView.findViewById(R.id.tv_friend_dianzan_name);
			tv_friend_comment = (CommentListView) convertView.findViewById(R.id.tv_friend_comment);

			mIv_friend_photo = (ImageView) convertView.findViewById(R.id.iv_friend_photo);
			iv_friend_choose = (ImageView) convertView.findViewById(R.id.iv_friend_choose);
			iv_friend_content_web_photo = (ImageView) convertView.findViewById(R.id.iv_friend_content_web_photo);

			tv_friend_name = (TextView) convertView.findViewById(R.id.tv_friend_name);
			tv_friend_delete = (TextView) convertView.findViewById(R.id.tv_friend_delete);
			tv_friend_content_web_title = (TextView) convertView.findViewById(R.id.tv_friend_content_web_title);
			tv_friend_time = (TextView) convertView.findViewById(R.id.tv_friend_time);

			ll_friend_share = (LinearLayout) convertView.findViewById(R.id.ll_friend_share);
			ll_friend_pinglun = (LinearLayout) convertView.findViewById(R.id.ll_friend_pinglun);

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
	public void updateEditTextBodyVisible(int visibility,int position) {
		mFrv.setSelectoin(position+1);
		editTextBodyLl.setVisibility(visibility);
		if (View.VISIBLE == visibility) {
			circleEt.requestFocus();
			// 弹出键盘
			CommonUtils.showSoftInput(circleEt.getContext(), circleEt);
		} else if (View.GONE == visibility) {
			// 隐藏键盘
			CommonUtils.hideSoftInput(circleEt.getContext(), circleEt);
		}
	}
	public void updateEditTextBodyVisible(int visibility) {
		editTextBodyLl.setVisibility(visibility);
		if (View.VISIBLE == visibility) {
			circleEt.requestFocus();
			// 弹出键盘
			CommonUtils.showSoftInput(circleEt.getContext(), circleEt);
		} else if (View.GONE == visibility) {
			// 隐藏键盘
			CommonUtils.hideSoftInput(circleEt.getContext(), circleEt);
		}
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
				//startActivity(new Intent(FriendActivity.this, MainActivity.class));
				FriendDb.getInstence(this).saveData(mTfi);
				finish();
				overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
				break;
			case 789 :
				View mView = View.inflate(this, R.layout.dialogview, null);
				RelativeLayout mRl_dialog_camera = (RelativeLayout) mView.findViewById(R.id.rl_dialog_camera);
				RelativeLayout mRl_dialog_form_photo = (RelativeLayout) mView.findViewById(R.id.rl_dialog_form_photo);
				mRl_dialog_camera.setOnClickListener(this);
				mRl_dialog_form_photo.setOnClickListener(this);
				mMBuilder = new AlertDialog.Builder(this);
				mMBuilder.setView(mView);
				mDialog = mMBuilder.show();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode){
			case KeyEvent.KEYCODE_BACK:
				FriendDb.getInstence(this).saveData(mTfi);
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
				startActivity(new Intent(FriendActivity.this, AlbumListActivity.class));
				//finish();
				overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
				mDialog.dismiss();
				break;
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mMyBroadCast);
	}
}

