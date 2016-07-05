package com.leon.assistivetouch.main.util;

import java.lang.Thread.UncaughtExceptionHandler;

import com.leon.assistivetouch.main.AssistiveTouchService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;


/** 
 * 类名      CrashHandler.java
 * 说明   捕捉程序异常中止信息
 * 创建日期 2012-8-21
*/
public class CrashHandler implements UncaughtExceptionHandler {

	private static final String TAG = "CrashHandler";
	
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	private static CrashHandler mInstance;
	private Context mContext;
	
	private CrashHandler() {
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	static Object mLock = new Object();

	/** 获取CrashHandler实例 ,单例模式 */
	private static CrashHandler getInstance() {
		synchronized (mLock) {
			if (mInstance == null)
				mInstance = new CrashHandler();
		}
		return mInstance;
	}
	
	public static void init(Context context) {
		getInstance().setContext(context);
	}

	private void setContext(Context context) {
		this.mContext = context;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {
		if (!handleException(throwable) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, throwable);
		} else {
			restartApplication();
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(10);
		}
	}

	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return true;
		}
		// 生成日志
		L.e(TAG, "", ex);
		return true;
	}
	
	private void restartApplication () {
		// 使用时钟,在1秒后发送请求
		AlarmManager am = (AlarmManager) mContext.getApplicationContext()
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(AssistiveTouchService.ASSISTIVE_TOUCH_START_ACTION);
		PendingIntent pi = PendingIntent.getService(
				mContext.getApplicationContext(), 0, i,
				Intent.FLAG_ACTIVITY_NEW_TASK);
		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, pi);
	}

}
