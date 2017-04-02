package ttentau.weixin.adapter.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ttentau.weixin.R;
import ttentau.weixin.activity.found.ViewPagerActivity;
import ttentau.weixin.bean.CommentConfig;
import ttentau.weixin.bean.CommentItem;
import ttentau.weixin.bean.FavortItem;
import ttentau.weixin.bean.MyCircleItem;
import ttentau.weixin.bean.PhotoInfoMy;
import ttentau.weixin.bean.User;
import ttentau.weixin.mvp.presenter.CirclePresenter;
import ttentau.weixin.widgets.CommentDialog;
import ttentau.weixin.widgets.CommentListView;
import ttentau.weixin.widgets.ExpandTextView;
import ttentau.weixin.widgets.MyGridView;
import ttentau.weixin.uitls.Data2Data;
import ttentau.weixin.uitls.UIUtils;
import ttentau.weixin.uitls.UrlUtils;
import ttentau.weixin.viewholder.ImageViewHolder;
import ttentau.weixin.viewholder.MyViewHolder;
import ttentau.weixin.viewholder.URLViewHolder;


/**
 * Created by ttent on 2017/3/19.
 */

public class FriendCircleAdapter extends RecyclerView.Adapter<MyViewHolder> {

	private final Context mContext;
	private ArrayList<MyCircleItem> mListInfo;
	private int mYear;
	private int mHour;
	private int mMinute;
	private int mMouth;
	private int mDay;
	private CirclePresenter mCp;

	public FriendCircleAdapter(Context context) {
		mContext = context;
		Calendar instance = Calendar.getInstance();
		// instance.setTimeInMillis(time);
		mYear = instance.get(Calendar.YEAR);
		mMouth = instance.get(Calendar.MONTH) + 1;
		mDay = instance.get(Calendar.DAY_OF_MONTH);
		mHour = instance.get(Calendar.HOUR_OF_DAY);
		mMinute = instance.get(Calendar.MINUTE);
	}
	public void setDatas(ArrayList<MyCircleItem> datas){
		mListInfo=datas;
	}
	public void setPresenter(CirclePresenter cp){
		mCp = cp;
	}

	@Override
	public int getItemViewType(int position) {
		MyCircleItem myCircleItem = mListInfo.get(position);
		int itemType = 0;
		switch (myCircleItem.getType()){
			case MyCircleItem.TYPE_IMG:
				itemType= MyCircleItem.TYPE_IMG;
				break;
			case MyCircleItem.TYPE_URL:
				itemType=MyCircleItem.TYPE_URL;
				break;
			case MyCircleItem.TYPE_NORMAL:
				itemType=MyCircleItem.TYPE_NORMAL;
				break;
		}
		return itemType;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View inflate = UIUtils.inflate(R.layout.friend_item);
		MyViewHolder myViewHolder=null;
		switch (viewType){
			case MyCircleItem.TYPE_IMG:
				myViewHolder = new ImageViewHolder(inflate);
				break;
			case MyCircleItem.TYPE_URL:
				myViewHolder=new URLViewHolder(inflate);
				break;
			case MyCircleItem.TYPE_NORMAL:
				myViewHolder=new MyViewHolder(inflate) {
					@Override
					public void initSubView(ViewStub viewStub) {
					}
				};
		}
		return myViewHolder;
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int position) {
		final MyCircleItem tfi = mListInfo.get(position);
		int itemId = tfi.getItemId();
		User user = tfi.getUser();
		//Log.e("tag>>>Data2Data",user.getId());
		String content = tfi.getContent();
		boolean contentIsExpand = tfi.isContentIsExpand();
		boolean isPraise = tfi.isPraise();
		boolean isMy = tfi.isMy();
		int type = tfi.getType();
		String createTime = tfi.getCreateTime();
		final List<FavortItem> favorters = tfi.getFavorters();
		final List<CommentItem> comments = tfi.getComments();
		boolean hasComment = comments!=null?(comments.size()!=0?true:false):false;
		boolean hasPraise =  favorters!=null?(favorters.size()!=0?true:false):false;

		holder.tv_friend_name.setText(user.getName());
		if (!UIUtils.isEmpty(content)){
			holder.mTv_friend_content.setText(UrlUtils.formatUrlString(content),contentIsExpand);
		}
		holder.mTv_friend_content.setVisibility(UIUtils.isEmpty(content)?View.GONE:View.VISIBLE);

		Glide.with(mContext).load(user.getPhotoUrl()).into(holder.mIv_friend_photo);
		//解析时间
		String[] dataList = createTime.split("\\|");
		processDate(dataList,holder);

		// 点赞
		if (hasComment||hasPraise) {
			holder.ll_friend_pinglun.setVisibility(View.VISIBLE);
			// 点赞
			if (hasPraise) {
				holder.mTv_friend_dianzan_name.setVisibility(View.VISIBLE);

				holder.mTv_friend_dianzan_name.setDatas(favorters);
			} else {
				holder.mTv_friend_dianzan_name.setVisibility(View.GONE);
			}
			// 评论
			if (hasComment) {
				holder.tv_friend_comment.setVisibility(View.VISIBLE);
				holder.tv_friend_comment.setDatas(comments);
				holder.tv_friend_comment.setOnItemClickListener(new CommentListView.OnItemClickListener() {
					@Override
					public void onItemClick(int countPos) {
						CommentItem commentItem = comments.get(countPos);
						if(Data2Data.getCurrUser().getId().equals(commentItem.getUser().getId())){//复制或者删除自己的评论
							CommentDialog dialog = new CommentDialog(mContext, mCp, commentItem, position);
							dialog.show();
						}else{//回复别人的评论
							if(mCp != null){
								CommentConfig config = new CommentConfig();
								config.circlePosition = position;
								config.commentPosition = countPos;
								config.commentType = CommentConfig.Type.REPLY;
								config.replyUser = commentItem.getUser();
								mCp.showEditTextBody(config);
							}
						}
					}
				});
				holder.tv_friend_comment.setOnItemLongClickListener(new CommentListView.OnItemLongClickListener() {
					@Override
					public void onItemLongClick(final int longposition) {
						CommentItem commentItem = comments.get(longposition);
						CommentDialog dialog = new CommentDialog(mContext, mCp, commentItem, position);
						dialog.show();
					}
				});
			} else {
				holder.tv_friend_comment.setVisibility(View.GONE);
			}
		} else {
			holder.ll_friend_pinglun.setVisibility(View.GONE);
		}

		// pop
		holder.mDigBtn.setText(isPraise?"取消":"赞");

		holder.iv_friend_choose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (holder.mPopupWindow.isShowing()) {
					holder.mPopupWindow.dismiss();
				} else {
					v.getLocationOnScreen(holder.mLocation);
					holder.mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
							holder.mLocation[0] - holder.mPopupWindow.getWidth(), holder.mLocation[1]);

					holder.mLl_pou_choose_dianzan.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if(mCp != null){
								if ("赞".equals(holder.mDigBtn.getText().toString())) {
									mCp.addFavort(position,favorters==null?0:favorters.size());
								} else {//取消点赞
									mCp.deleteFavort(position, Data2Data.getCurrUser().getId());
								}
							}
							holder.mPopupWindow.dismiss();
						}
					});
					holder.mLl_pou_choose_pinglun.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							holder.mPopupWindow.dismiss();
							CommentConfig config = new CommentConfig();
							config.circlePosition = position;
							config.commentType = CommentConfig.Type.PUBLIC;
							mCp.showEditTextBody(config);
						}
					});
				}
			}
		});
		//这个是自定义TextView点击了全文或者收起按钮的回调
		holder.mTv_friend_content.setOntextStatuListener(new ExpandTextView.textStatuListener() {
			@Override
			public void statusChange(boolean isExpand) {
				if (isExpand)tfi.setContentIsExpand(true);
				else tfi.setContentIsExpand(false);
			}
		});
		// 删除
		holder.tv_friend_delete.setVisibility(user.getId().equals("q1")?View.VISIBLE:View.GONE);
		holder.tv_friend_delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCp.deleteCircle(position);
			}
		});

		switch (type){
			case MyCircleItem.TYPE_IMG:
				final List<PhotoInfoMy> photos = tfi.getPhotos();
					((ImageViewHolder)holder).gv_friend_content_photos.setVisibility(View.VISIBLE);
					((ImageViewHolder)holder).gv_friend_content_photos.setList(photos);
					((ImageViewHolder)holder).gv_friend_content_photos.setOnItemClickListener(new MyGridView.OnItemClickListener() {
						@Override
						public void onItemClick(View view, int position) {
					Intent intent = new Intent(UIUtils.getContext(), ViewPagerActivity.class);
					intent.putExtra("imageInfo", (Serializable) photos);
					intent.putExtra("pos",position);
					mContext.startActivity(intent);
						}
					});

				break;
			case MyCircleItem.TYPE_URL:
				// 外链
					((URLViewHolder)holder).ll_friend_share.setVisibility(View.VISIBLE);
					Glide.with(mContext).load(R.drawable.myic_3).into(((URLViewHolder)holder).iv_friend_content_web_photo);
					((URLViewHolder)holder).tv_friend_content_web_title.setText(tfi.getLinkTitle());
				break;
		}
	}

	@Override
	public int getItemCount() {
		return mListInfo.size();
	}

	public void processDate(String[] dataList, MyViewHolder holder){
		if (Integer.parseInt(dataList[0]) < mYear) {
			holder.tv_friend_time.setText(dataList[0] + "年" + dataList[1] + "月" + dataList[2] + "日");
		} else {
			if (Integer.parseInt(dataList[1]) < mMouth) {
				holder.tv_friend_time.setText(dataList[0] + "年" + dataList[1] + "月" + dataList[2] + "日");
			} else {
				if (Integer.parseInt(dataList[2]) < mDay) {
					int nearlyDay = mDay - Integer.parseInt(dataList[2]);
					switch (nearlyDay) {
						case 1 :
							holder.tv_friend_time.setText("昨天");
							break;
						case 2 :
							holder.tv_friend_time.setText("2天前");
							break;
						case 3 :
							holder.tv_friend_time.setText("3天前");
							break;
						default :
							holder.tv_friend_time
									.setText(dataList[0] + "年" + dataList[1] + "月" + dataList[2] + "日");
							break;
					}
				} else {
					if (Integer.parseInt(dataList[3]) < mHour) {
						int nearlyHour = mHour - Integer.parseInt(dataList[3]);
						holder.tv_friend_time.setText(nearlyHour + "小时前");
					} else {
						if (Integer.parseInt(dataList[4]) < mMinute) {
							int naerlyMinute = mMinute - Integer.parseInt(dataList[4]);
							holder.tv_friend_time.setText(naerlyMinute + "分钟前");
						} else {
							holder.tv_friend_time.setText("刚刚");
						}
					}
				}
			}
		}
	}
	public ArrayList<MyCircleItem> getDatas(){
		return mListInfo;
	}
}
