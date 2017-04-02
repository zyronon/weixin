package ttentau.weixin.viewholder;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import ttentau.weixin.R;
import ttentau.weixin.widgets.CommentListView;
import ttentau.weixin.widgets.ExpandTextView;
import ttentau.weixin.widgets.MyPraiseTextView;
import ttentau.weixin.uitls.UIUtils;


/**
 * Created by ttent on 2017/3/19.
 */

public abstract class MyViewHolder extends RecyclerView.ViewHolder{
    public final ExpandTextView mTv_friend_content;
    public final ImageView mIv_friend_photo;
    public final ImageView iv_friend_choose;
    public final TextView tv_friend_name;
    public final TextView tv_friend_delete;
    public final TextView tv_friend_time;
    public final PopupWindow mPopupWindow;
    public final int[] mLocation;
    public final TextView mDigBtn;
    public final LinearLayout mLl_pou_choose_dianzan;
    public final LinearLayout mLl_pou_choose_pinglun;
    public final LinearLayout ll_friend_pinglun;

    public final MyPraiseTextView mTv_friend_dianzan_name;
    public final CommentListView tv_friend_comment;

    public MyViewHolder(View view) {
        super(view);
        View view1 = UIUtils.inflate(R.layout.popupwindow_friend_choose);
        mDigBtn = (TextView) view1.findViewById(R.id.digBtn);
        mLl_pou_choose_dianzan = (LinearLayout) view1.findViewById(R.id.ll_pou_choose_dianzan);
        mLl_pou_choose_pinglun = (LinearLayout) view1.findViewById(R.id.ll_pou_choose_pinglun);
        mPopupWindow = new PopupWindow(view1, UIUtils.dip2px(180), UIUtils.dip2px(30));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
       // mPopupWindow.setAnimationStyle(R.style.anim_choose_pop);
        mLocation = new int[2];
        ViewStub viewStub = (ViewStub) view.findViewById(R.id.viewStub);

        initSubView(viewStub);

       mTv_friend_content = (ExpandTextView) view.findViewById(R.id.tv_friend_content);
        mTv_friend_dianzan_name = (MyPraiseTextView) view.findViewById(R.id.tv_friend_dianzan_name);
        tv_friend_comment = (CommentListView) view.findViewById(R.id.tv_friend_comment);

        mIv_friend_photo = (ImageView) view.findViewById(R.id.iv_friend_photo);
        iv_friend_choose = (ImageView) view.findViewById(R.id.iv_friend_choose);

        tv_friend_name = (TextView) view.findViewById(R.id.tv_friend_name);
        tv_friend_delete = (TextView) view.findViewById(R.id.tv_friend_delete);
        tv_friend_time = (TextView) view.findViewById(R.id.tv_friend_time);

        ll_friend_pinglun = (LinearLayout) view.findViewById(R.id.ll_friend_pinglun);

    }

    public abstract void initSubView(ViewStub viewStub);
}