package ttentau.weixin.uitls;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * 
* @ClassName: CommonUtils 
* @Description: 
* @author yiw
* @date 2015-12-28 下午4:16:01 
*
 */
public class CommonUtils {

	public static void showSoftInput(Context context, View view){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		//imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}
	
	public static void hideSoftInput(Context context, View view){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
	}
	
	public static boolean isShowSoftInput(Context context){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
		//获取状态信息
		return imm.isActive();//true 打开
	}
	public void hideInputManager(Context ct) {
		try {
			((InputMethodManager)ct.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) ct)
					.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
			Log.e("tag", "hideInputManager Catch error,skip it!", e);
		}
	}
}
