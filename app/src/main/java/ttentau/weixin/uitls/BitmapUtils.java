package ttentau.weixin.uitls;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by ttent on 2017/2/19.
 */

public class BitmapUtils {

	public BitmapUtils() {
		// TODO Auto-generated constructor stub
	}
	public static void scaleLoad(String path, PhotoView iv, int mHeight, int mWidth, Context context){
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path,opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		//Log.e("tag",width+"<<<<<<<qianqian?????"+height);
		float scale = (float) width / (float) height;
		if (width<mWidth/2){
			width=mWidth/2;
		}else {
			width=mWidth;
		}
		height=(int) (width / scale);
		if (height<mHeight){
			iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
		}else {
			iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		}
		Glide.with(context).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);
	}
	public static Bitmap getBitmapThumbnail(String path, int width, int height) {
		Bitmap bitmap = null;
		// 这里可以按比例缩小图片：
		/*
		 * BitmapFactory.Options opts = new BitmapFactory.Options();
		 * opts.inSampleSize = 4;//宽和高都是原来的1/4 bitmap =
		 * BitmapFactory.decodeFile(path, opts);
		 */

		/*
		 * 进一步的， 如何设置恰当的inSampleSize是解决该问题的关键之一。BitmapFactory.
		 * Options提供了另一个成员inJustDecodeBounds。
		 * 设置inJustDecodeBounds为true后，decodeFile并不分配空间，但可计算出原始图片的长度和宽度，即opts.
		 * width和opts.height。 有了这两个参数，再通过一定的算法，即可得到一个恰当的inSampleSize。
		 */
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);
		opts.inSampleSize = Math.max((int) (opts.outHeight / (float) height), (int) (opts.outWidth / (float) width));
		opts.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(path, opts);
		return bitmap;
	}

	public static Bitmap getBitmapThumbnail(Bitmap bmp, int width, int height) {
		Bitmap bitmap = null;
		if (bmp != null) {
			int bmpWidth = bmp.getWidth();
			int bmpHeight = bmp.getHeight();
			if (width != 0 && height != 0) {
				Matrix matrix = new Matrix();
				float scaleWidth = ((float) width / bmpWidth);
				float scaleHeight = ((float) height / bmpHeight);
				matrix.postScale(scaleWidth, scaleHeight);
				bitmap = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix, true);
			} else {
				bitmap = bmp;
			}
		}
		return bitmap;
	}

}
