package ttentau.weixin.uitls;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by ttent on 2017/2/19.
 */

public class ImageCache {

	private LruCache<String, Bitmap> mMemoryCache;

	public ImageCache() {

		// 获取应用最大内存
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		//用最大内存的1/4来缓存图片
		final int cacheSize = maxMemory / 4;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			/**
			 * Measure item size in kilobytes rather than units which is more practical
			 * for a bitmap cache
			 */
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount() / 1024;
			}
		};
	}

	/**
	 * Adds a bitmap to both memory and disk cache.
	 * @param data Unique identifier for the bitmap to store
	 * @param value The bitmap drawable to store
	 */
	public void addBitmapToMemCache(String data, Bitmap value) {

//		Log.e("tag","addcache被调用了");
		if (getBitmapFromMemCache(data)!=value){
			mMemoryCache.put(data, value);
		}
	}

	/**
	 * Get from memory cache.
	 *
	 * @param data
	 *            Unique identifier for which item to get
	 * @return The bitmap drawable if found in cache, null otherwise
	 */
	public Bitmap getBitmapFromMemCache(String data) {

		Bitmap memValue = null;
		if (mMemoryCache != null) {
//			Log.e("tag","getcache被调用了");
			memValue = mMemoryCache.get(data);
		}
		return memValue;
	}

}