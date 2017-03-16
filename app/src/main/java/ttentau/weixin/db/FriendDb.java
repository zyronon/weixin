package ttentau.weixin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ttentau.weixin.bean.TestFriendInfo;


/**
 * Created by ttent on 2017/2/28.
 */

public class FriendDb {
	private final DbHelper mDb;
	private static FriendDb friendDb = null;

	private FriendDb(Context context) {
		mDb = new DbHelper(context);
	}
	public static FriendDb getInstence(Context context) {
		if (friendDb == null) {
			friendDb = new FriendDb(context);
		}
		return friendDb;
	}
	public ArrayList<TestFriendInfo> query() {
		SQLiteDatabase wdb = mDb.getWritableDatabase();
		Cursor query = wdb.query("friendinfo", null, null, null, null, null, "compareData desc");
		ArrayList<TestFriendInfo> filist = new ArrayList<>();
		while (query.moveToNext()) {
			TestFriendInfo mFi = new TestFriendInfo();
			int photo = query.getInt(1);
			String name = query.getString(2);
			String content = query.getString(3);

			mFi.setPhoto(photo);
			mFi.setName(name);
			mFi.setContent(content);

			int contentIsExpand = query.getInt(4);
			int isPraise = query.getInt(5);
			int isMy = query.getInt(6);
			mFi.setContentIsExpand(contentIsExpand);
			mFi.setIsPraise(isPraise);
			mFi.setIsMy(isMy);

			int webContent = query.getInt(7);
			mFi.setWebContent(webContent);
			if (webContent != -1) {
				String webContent_content = query.getString(8);
				String webContent_photo = query.getString(9);
				mFi.setWebContent_content(webContent_content);
				mFi.setWebContent_photo(webContent_photo);
			}

			String date = query.getString(10);
			int compareData = query.getInt(11);
			mFi.setDate(date);
			mFi.setCompareData(compareData);

			int praiseCount = query.getInt(12);
			mFi.setPraiseCount(praiseCount);
			if (praiseCount != -1) {
				String praiseName = query.getString(13);
				mFi.setPraiseName(praiseName);
			}

			int commentCount = query.getInt(14);
			mFi.setCommentCount(commentCount);
			if (commentCount != -1) {
				String commetnContent = query.getString(15);
				mFi.setCommetnContent(commetnContent);
			}

			int imageCount = query.getInt(16);
			mFi.setImageCount(imageCount);
			if (imageCount != -1) {
				String imagePath = query.getString(17);
				mFi.setImagePath(imagePath);
			}
			filist.add(mFi);
			/*
			 * String s = mFi.toString(); Log.e("tag",s);
			 */
		}
		query.close();
		wdb.close();
		return filist;
	}
	public void insert(TestFriendInfo tfi) {
		SQLiteDatabase wdb = mDb.getWritableDatabase();
		ContentValues values = new ContentValues();

		int photo = tfi.getPhoto();
		String name = tfi.getName();
		String content = tfi.getContent();
		values.put("photo", photo);
		values.put("name", name);
		values.put("content", content);

		int contentIsExpand = tfi.getContentIsExpand();
		int isMy = tfi.getIsMy();
		int isPraise = tfi.getIsPraise();
		values.put("contentIsExpand", contentIsExpand);
		values.put("isMy", isMy);
		values.put("isPraise", isPraise);

		int webContent = tfi.getWebContent();
		values.put("webContent", webContent);
		if (webContent != -1) {
			String webContent_content = tfi.getWebContent_content();
			String webContent_photo = tfi.getWebContent_photo();
			values.put("webContent_content", webContent_content);
			values.put("webContent_photo", webContent_photo);
		}
		String date = tfi.getDate();
		long compareData = tfi.getCompareData();
		values.put("date", date);
		values.put("compareData", compareData);

		int praiseCount = tfi.getPraiseCount();
		values.put("praiseCount", praiseCount);
		if (praiseCount != -1) {
			String praiseName = tfi.getPraiseName();
			values.put("praiseName", praiseName);
		}

		int commentCount = tfi.getCommentCount();
		values.put("commentCount", commentCount);
		if (commentCount != -1) {
			String commetnContent = tfi.getCommetnContent();
			values.put("commetnContent", commetnContent);
		}

		int imageCount = tfi.getImageCount();
		values.put("imageCount", imageCount);
		if (imageCount != -1) {
			String imagePath = tfi.getImagePath();
			values.put("imagePath", imagePath);
		}

		wdb.insert("friendinfo", null, values);
		wdb.close();
	}
	public void saveData(ArrayList<TestFriendInfo> al) {
		/*ArrayList<TestFriendInfo> query = query();
		for (int i = 0; i < al.size(); i++) {
			int oldsize = query.size();
			int newsize = al.size();
			if (oldsize < newsize) {
                int addList = newsize - oldsize;
                if (i<addList){
                    insert(al.get(i));
                }
            }
		}*/
		dalete();
		for (TestFriendInfo tfi : al) {
			insert(tfi);
		}
	}
	public void dalete(){
		SQLiteDatabase wdb = mDb.getWritableDatabase();
		wdb.delete("friendinfo",null,null);
		wdb.close();
	}
}
