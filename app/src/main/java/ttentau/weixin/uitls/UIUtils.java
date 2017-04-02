package ttentau.weixin.uitls;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

import ttentau.weixin.global.WeixinApplication;


public class UIUtils {

	public static Context getContext() {
		return WeixinApplication.getContext();
	}

	public static Handler getHandler() {
		return WeixinApplication.getHandler();
	}

	public static int getMainThreadId() {
		return WeixinApplication.getMainThreadId();
	}

	// /////////////////加载资源文件 ///////////////////////////


	public static final String ANDROID_RESOURCE = "android.resource://";
	public static final String FOREWARD_SLASH = "/";

	public static Uri resourceIdToUri(Context context, int resourceId) {
		return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
	}
	// 获取字符串
	public static String getString(int id) {
		return getContext().getResources().getString(id);
	}

	// 获取字符串数组
	public static String[] getStringArray(int id) {
		return getContext().getResources().getStringArray(id);
	}
	//获得状态栏的高度
	public static int getStatusHeight(Context context) {
		int statusHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}
	public static int[] getWidthAndHeight(Activity activity){
		WindowManager wm = activity.getWindowManager();
		int height = wm.getDefaultDisplay().getHeight();
		int width = wm.getDefaultDisplay().getWidth();
		int[] result={width,height};
		return result;
	}
	public static int getStatusBarHeight() {
		int result = 0;
		int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result =getContext().getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
	public static Bitmap getBitmapFromDrawable(Drawable drawable, int iconWidth, int iconHeight) {
		Bitmap bitmap = Bitmap.createBitmap(iconWidth, iconHeight, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		if (drawable instanceof BitmapDrawable) {
			drawable.draw(canvas);
			return bitmap;
		} else {
			throw new RuntimeException("The Drawable must be an instance of BitmapDrawable");
		}
	}

	/**
	 * 判断SDCard是否可用
	 */
	public static boolean existSDCard() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}


	// 获取图片
	public static Drawable getDrawable(int id) {
		return getContext().getResources().getDrawable(id);
	}

	public static void Toast(String value){
		Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();
	}
	public static void Toast(int value){
		Toast.makeText(getContext(),value+"",Toast.LENGTH_SHORT).show();
	}
	// 获取颜色
	public static int getColor(int id) {
		return getContext().getResources().getColor(id);
	}
	
	//根据id获取颜色的状态选择器
	public static ColorStateList getColorStateList(int id) {
		return getContext().getResources().getColorStateList(id);
	}

	// 获取尺寸
	public static int getDimen(int id) {
		return getContext().getResources().getDimensionPixelSize(id);// 返回具体像素值
	}

	// /////////////////dip和px转换//////////////////////////

	public static int dip2px(float dip) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return (int) (dip * density + 0.5f);
	}

	public static float px2dip(int px) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return px / density;
	}

	// /////////////////加载布局文件//////////////////////////
	public static View inflate(int id) {
		return View.inflate(getContext(), id, null);
	}

	// /////////////////判断是否运行在主线程//////////////////////////
	public static boolean isRunOnUIThread() {
		// 获取当前线程id, 如果当前线程id和主线程id相同, 那么当前就是主线程
		int myTid = android.os.Process.myTid();
		if (myTid == getMainThreadId()) {
			return true;
		}

		return false;
	}

	// 运行在主线程
	public static void runOnUIThread(Runnable r) {
		if (isRunOnUIThread()) {
			// 已经是主线程, 直接运行
			r.run();
		} else {
			// 如果是子线程, 借助handler让其运行在主线程
			getHandler().post(r);
		}
	}
	public static boolean isEmpty(String[] value){
		if (value!=null&&value.length!=0){
			return false;
		}
		return true;
	}
	public static boolean isEmpty(String value){
		if (value==null||value.equals("null")||value.equals("")||value.length()==0){
			return true;
		}
		return false;
	}
	public static boolean isNull(ArrayList list){
	    if (list==null||list.size()==0){
			return true;
		}
		return false;
	}

}
