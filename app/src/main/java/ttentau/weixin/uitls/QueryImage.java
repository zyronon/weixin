package ttentau.weixin.uitls;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ttentau.weixin.bean.ImageModel;

/**
 * Created by ttent on 2017/2/19.
 */

public class QueryImage {

	private static ContentResolver mContentResolver;
	private final Context mContext;
//	private final ContentResolver mContentResolver;

	public QueryImage(Context context) {
		mContext = context;
		mContentResolver = mContext.getContentResolver();
	}

	public List<ImageModel> getImagesFileNameOfPhoto(final String name) {
		final List<ImageModel> list = new ArrayList<ImageModel>();

		final Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

		final String sortOrder = MediaStore.Images.Media.DATE_ADDED + " desc";

		final String[] projection = {MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DATA};

		new Thread(new Runnable() {
			private StringBuffer mStringBuffer;
			public Cursor mCursor;
			@Override
			public void run() {
				mCursor = mContentResolver.query(uri, projection, "bucket_display_name = ?", new String[]{name},
						sortOrder);
				mCursor.moveToFirst();
				while (!mCursor.isAfterLast()) {
					String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
					String id = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));
					 //Log.e(TAG, "id===="+id+"====path===="+path);
					ImageModel imageModel = new ImageModel(id, path);
					list.add(imageModel);
					mCursor.moveToNext();
				}
				mCursor.close();
			}
		}).start();
		return list;
	}

	public List<ImageModel> getImagesFileName() {
		final List<ImageModel> list = new ArrayList<ImageModel>();
		final Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

		final String[] projection = {MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

		new Thread(new Runnable() {
			private StringBuffer mStringBuffer;
			public Cursor mCursor;
			@Override
			public void run() {
				HashMap<String, Integer> map = new HashMap<>();
				int i = 1;

				mCursor = mContentResolver.query(uri, projection, null, null, null);
				int iId = mCursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
				mCursor.moveToFirst();
				while (!mCursor.isAfterLast()) {
					String id = mCursor.getString(iId);
					if (map.containsKey(id)) {
						i = i + 1;
						map.put(id, i);
					} else {
						map.put(id, 1);
						i = 1;
					}
					mCursor.moveToNext();
				}
				// Log.e(TAG, "FileName====" + map.toString());
				Iterator iter = map.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String key = (String) entry.getKey();
					int count = (int) entry.getValue();
					// Log.e(TAG,key+"====key++++count"+count);
					ImageModel imageModel = new ImageModel(count, key);
					list.add(imageModel);
					// getImagesFileNameOfPhoto(context,key);
				}
				mCursor.close();
			}
		}).start();
		return list;
	}

	public List<ImageModel> getImages() {
		final List<ImageModel> list = new ArrayList<ImageModel>();

		final Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

		final String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};

		final String sortOrder = MediaStore.Images.Media.DATE_ADDED + " desc";

		new Thread(new Runnable() {
			private StringBuffer mStringBuffer;
			public Cursor mCursor;
			@Override
			public void run() {
				mCursor = mContentResolver.query(uri, projection, null, null, sortOrder);
				int iId = mCursor.getColumnIndex(MediaStore.Images.Media._ID);
				int iPath = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);

				mCursor.moveToFirst();
				while (!mCursor.isAfterLast()) {
					String id = mCursor.getString(iId);
					String path = mCursor.getString(iPath);
					// Log.e(TAG, "id====" + id + "====path====" + path);
					ImageModel imageModel = new ImageModel(id, path);
					list.add(imageModel);
					mCursor.moveToNext();
				}
				mCursor.close();
			}
		}).start();

		return list;
	}
}
