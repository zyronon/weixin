package com.example.lenovo.myapplication.global;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;


import java.util.Iterator;
import java.util.List;

/**
 * 自定义application, 进行全局初始化
 * 
 * @author Kevin
 * @date 2015-10-27
 */
public class WeixinApplication extends Application {

	private static Context context;
	private static Handler handler;
	private static int mainThreadId;
	// 上下文菜单
	private Context mContext;

	// 记录是否已经初始化
	private boolean isInit = false;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		handler = new Handler();
		mainThreadId = android.os.Process.myTid();
		mContext = this;
	}

	/**
	 * 根据Pid获取当前进程的名字，一般就是当前app的包名
	 *
	 * @param pid 进程的id
	 * @return 返回进程的名字
	 */
	private String getAppName(int pid) {
		String processName = null;
		ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
		List list = activityManager.getRunningAppProcesses();
		Iterator i = list.iterator();
		while (i.hasNext()) {
			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
			try {
				if (info.pid == pid) {
					// 根据进程的信息获取当前进程的名字
					processName = info.processName;
					// 返回当前进程名
					return processName;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 没有匹配的项，返回为null
		return null;
	}

	public static Context getContext() {
		return context;
	}

	public static Handler getHandler() {
		return handler;
	}

	public static int getMainThreadId() {
		return mainThreadId;
	}
}
