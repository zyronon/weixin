package ttentau.weixin.mvp.contract;

import java.util.ArrayList;

import ttentau.weixin.bean.CommentConfig;
import ttentau.weixin.bean.CommentItem;
import ttentau.weixin.bean.FavortItem;
import ttentau.weixin.bean.MyCircleItem;

/**
 * Created by ttent on 2017/3/19.
 */

public interface CircleContract {
    public interface Presenter{
        void loadData(int loadType);
        void upData(MyCircleItem mMsgcInfo);
        void deleteCircle(final int pos);
        void addFavort(final int circlePosition, final int size);
        void deleteFavort(final int circlePosition, final String favortId);
        void deleteComment(final int circlePosition, final String commentId);
    }
    interface View {
        void upData(MyCircleItem mMsgcInfo);
        void update2DeleteCircle(int pos);
        void update2AddFavorite(int circlePosition, FavortItem addItem);
        void update2DeleteFavort(int circlePosition, String favortId);
        void update2AddComment(int circlePosition, CommentItem addItem);
        void update2DeleteComment(int circlePosition, String commentId);
        void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig);
        void update2loadData(int loadType, ArrayList<MyCircleItem> datas);
    }
}
