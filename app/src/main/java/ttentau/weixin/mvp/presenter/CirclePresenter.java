package ttentau.weixin.mvp.presenter;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ttentau.weixin.bean.CommentConfig;
import ttentau.weixin.bean.CommentItem;
import ttentau.weixin.bean.FavortItem;
import ttentau.weixin.bean.MyCircleItem;
import ttentau.weixin.db.FriendDb;
import ttentau.weixin.listener.IDataRequestListener;
import ttentau.weixin.mvp.contract.CircleContract;
import ttentau.weixin.mvp.modle.CircleModel;
import ttentau.weixin.uitls.Data2Data;

/**
 * Created by ttent on 2017/3/19.
 */

public class CirclePresenter implements CircleContract.Presenter{

    private final CircleContract.View mView;
    private final CircleModel mModel;
    private final Context mContext;
    private final FriendDb mDb;

    public CirclePresenter(Context context, CircleContract.View view){
        mModel = new CircleModel();
        mView = view;
        mContext = context;
        mDb = FriendDb.getInstence(context);
    }

    @Override
    public void loadData(int loadType) {
        ArrayList<MyCircleItem> data = Data2Data.getDataFormDataUtils(mDb.query());
        if (data==null||data.size()==0){
            data = Data2Data.getDataFormDataUtils(mContext);
        }
        Collections.sort(data, new Comparator<MyCircleItem>() {
            @Override
            public int compare(MyCircleItem o1, MyCircleItem o2) {
                if (o1.getCompareData()>o2.getCompareData()){
                    //	Log.e("tag",-1+"");
                    return -1;
                }else {
                    return 1;
                }
            }
        });
        if (mView!=null){
            mView.update2loadData(loadType,data);
        }
    }

    @Override
    public void upData(MyCircleItem mMsgcInfo) {
        if (mView!=null){
            mView.upData(mMsgcInfo);
        }
    }

    @Override
    public void deleteCircle(final int pos) {
        mModel.deleteCircle(new IDataRequestListener() {

            @Override
            public void loadSuccess(Object object) {
                if(mView!=null){
                    mView.update2DeleteCircle(pos);
                }
            }
        });

    }

    @Override
    public void addFavort(final int circlePosition, final int size) {
        mModel.addFavort(new IDataRequestListener() {
            @Override
            public void loadSuccess(Object object) {
                FavortItem favortItem = new FavortItem();
                favortItem.setId(size+"");
                favortItem.setUser(Data2Data.getCurrUser());
                 mView.update2AddFavorite(circlePosition,favortItem);
            }
        });

    }

    @Override
    public void deleteFavort(final int circlePosition, final String favortId) {

        mModel.deleteFavort(new IDataRequestListener() {
            @Override
            public void loadSuccess(Object object) {
                if (mView!=null){
                    mView.update2DeleteFavort(circlePosition,favortId);
                }
            }
        });
    }

    @Override
    public void deleteComment(final int circlePosition, final String commentId) {
        mModel.deleteComment(new IDataRequestListener() {
            @Override
            public void loadSuccess(Object object) {
                if (mView!=null){
                    mView.update2DeleteComment(circlePosition,commentId);
                }
            }
        });
    }
    public void showEditTextBody(CommentConfig commentConfig){
        if(mView != null){
            mView.updateEditTextBodyVisible(View.VISIBLE, commentConfig);
        }
    }

    public void addComment(final String content, final CommentConfig commentConfig) {
        if(commentConfig == null){
            return;
        }
        mModel.addComment(new IDataRequestListener() {

            @Override
            public void loadSuccess(Object object) {
                CommentItem newItem = null;
                if (commentConfig.commentType == CommentConfig.Type.PUBLIC) {
                    newItem = new CommentItem();
                    newItem.setContent(content);
                   // newItem.setToReplyUser(commentConfig.replyUser);
                    newItem.setUser(Data2Data.getCurrUser());
                } else if (commentConfig.commentType == CommentConfig.Type.REPLY) {
                    //newItem =TestFriendInfo.(commentConfig.replyUser, content);
                    newItem = new CommentItem();
                    newItem.setContent(content);
                    newItem.setToReplyUser(commentConfig.replyUser);
                    newItem.setUser(Data2Data.getCurrUser());
                }
                if(mView!=null){
                    mView.update2AddComment(commentConfig.circlePosition, newItem);
                }
            }

        });
    }
}
