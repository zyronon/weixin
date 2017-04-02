package ttentau.weixin.uitls;

import android.content.Context;

import java.util.ArrayList;

import ttentau.weixin.R;
import ttentau.weixin.bean.CommentItem;
import ttentau.weixin.bean.FavortItem;
import ttentau.weixin.bean.MyCircleItem;
import ttentau.weixin.bean.TestFriendInfo;
import ttentau.weixin.bean.User;

/**
 * Created by ttent on 2017/3/21.
 */

public class Data2Data {
	public static ArrayList<MyCircleItem> getDataFormDataUtils(Context context) {
		ArrayList<TestFriendInfo> listInfo = DataUtil.getListInfo(context);
		ArrayList<MyCircleItem> myCircleItems = new ArrayList<>();
		for (int i = 0; i < listInfo.size(); i++) {

			MyCircleItem mi = new MyCircleItem();
			TestFriendInfo tfi = listInfo.get(i);

			mi.setItemId(i);
			int photo = tfi.getPhoto();
			String id = tfi.getId();
			// Log.e("tag>>>Data2Data",id);
			String name = tfi.getName();
			mi.setUser(new User(id, name, photo));

			String content = tfi.getContent();
			mi.setContent(content);

			int contentIsExpand = tfi.getContentIsExpand();
			mi.setContentIsExpand(contentIsExpand != -1 ? true : false);

			int isPraise = tfi.getIsPraise();
			mi.setIsPraise(isPraise != -1 ? true : false);

			int isMy = tfi.getIsMy();
			mi.setIsMy(isMy != -1 ? true : false);

			String date = tfi.getDate();
			mi.setCreateTime(date);

			long compareData = tfi.getCompareData();
            mi.setCompareData(compareData);

			int type = tfi.getType();
			mi.setType(type);

			switch (type) {
				case MyCircleItem.TYPE_URL :
					String webContent_content = tfi.getWebContent_content();
					String webContent_photo = tfi.getWebContent_photo();
					//mi.setType(type);
					mi.setLinkImg(webContent_photo);
					mi.setLinkTitle(webContent_content);
					break;
				case MyCircleItem.TYPE_IMG :
                    //mi.setType(type);
                    mi.setPhotos(tfi.getPhotoInfo());
					break;
			}
			int praiseCount = tfi.getPraiseCount();
			if (praiseCount != -1) {
				mi.setFavorters(tfi.getFavortItem());
			}
			int commentCount = tfi.getCommentCount();
			if (commentCount != -1) {
				mi.setComments(tfi.getCommentList());
			}
			myCircleItems.add(mi);
		}
		return myCircleItems;
	}
	public static User getCurrUser() {
		return new User("q1", "真是无奈", R.drawable.myphoto);
	}
	public static ArrayList<TestFriendInfo> TestBean2MyCiecle(ArrayList<MyCircleItem> list) {
		ArrayList<TestFriendInfo> tfiList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			MyCircleItem mi = list.get(i);
			TestFriendInfo tfi = new TestFriendInfo();
			User user = mi.getUser();
			tfi.setId(user.getId());
			tfi.setName(user.getName());
			tfi.setPhoto(user.getPhotoUrl());

			tfi.setContent(mi.getContent());

			tfi.setContentIsExpand(mi.isContentIsExpand() ? 1 : -1);

			tfi.setIsMy(mi.isMy() ? 1 : -1);

			tfi.setIsPraise(mi.isPraise() ? 1 : -1);

            int type = mi.getType();
            tfi.setType(type);
            switch (type){
                case MyCircleItem.TYPE_URL:
                    tfi.setWebContent_content(mi.getLinkTitle());
                    tfi.setWebContent_photo(mi.getLinkImg());
                    break;
                case MyCircleItem.TYPE_IMG:
                    tfi.setPhotos(mi.getPhotos());
                    break;
            }
            tfi.setDate(mi.getCreateTime());

            ArrayList<FavortItem> favortItem = mi.getFavorters();

            if (!UIUtils.isNull(favortItem)){
                tfi.setPraiseCount(1);
                tfi.setPraiseList(favortItem);
            }

            ArrayList<CommentItem> comments = mi.getComments();

            if (!UIUtils.isNull(comments)){
                tfi.setCommentCount(1);
                tfi.setCommentList(mi.getComments());
            }

            tfiList.add(tfi);
        }
		return tfiList;
	}
	public static ArrayList<MyCircleItem> getDataFormDataUtils(ArrayList<TestFriendInfo> listInfo){
		ArrayList<MyCircleItem> myCircleItems = new ArrayList<>();
		for (int i = 0; i < listInfo.size(); i++) {

			MyCircleItem mi = new MyCircleItem();
			TestFriendInfo tfi = listInfo.get(i);

			mi.setItemId(i);
			int photo = tfi.getPhoto();
			String id = tfi.getId();
			// Log.e("tag>>>Data2Data",id);
			String name = tfi.getName();
			mi.setUser(new User(id, name, photo));

			String content = tfi.getContent();
			mi.setContent(content);

			int contentIsExpand = tfi.getContentIsExpand();
			mi.setContentIsExpand(contentIsExpand != -1 ? true : false);

			int isPraise = tfi.getIsPraise();
			mi.setIsPraise(isPraise != -1 ? true : false);

			int isMy = tfi.getIsMy();
			mi.setIsMy(isMy != -1 ? true : false);

			String date = tfi.getDate();
			mi.setCreateTime(date);

			long compareData = tfi.getCompareData();
			mi.setCompareData(compareData);

			int type = tfi.getType();
			mi.setType(type);

			switch (type) {
				case MyCircleItem.TYPE_URL :
					String webContent_content = tfi.getWebContent_content();
					String webContent_photo = tfi.getWebContent_photo();
					//mi.setType(type);
					mi.setLinkImg(webContent_photo);
					mi.setLinkTitle(webContent_content);
					break;
				case MyCircleItem.TYPE_IMG :
					//mi.setType(type);
					mi.setPhotos(tfi.getPhotoInfo());
					break;
			}
			int praiseCount = tfi.getPraiseCount();
			if (praiseCount != -1) {
				mi.setFavorters(tfi.getFavortItem());
			}
			int commentCount = tfi.getCommentCount();
			if (commentCount != -1) {
				mi.setComments(tfi.getCommentList());
			}
			myCircleItems.add(mi);
		}
		return myCircleItems;
	}
}
