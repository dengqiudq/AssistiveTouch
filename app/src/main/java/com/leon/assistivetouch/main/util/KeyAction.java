package com.leon.assistivetouch.main.util;

import java.lang.reflect.Method;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.leon.assistivetouch.main.AssistiveTouchService;
import com.leon.assistivetouch.main.AuxiliaryActivity;
import com.leon.assistivetouch.main.MainActivity;
import com.leon.assistivetouch.main.ui.LockReceiver;

/** 
 * 类名      KeyAction.java
 * 说明   description of the class
 */
public class KeyAction {

	public static void doHomeAction(Context context) {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
		intent.addCategory(Intent.CATEGORY_HOME);
		context.startActivity(intent);

	}

	public static void doBackAction() {
//		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//
//		List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(100);
//
//		if (runningTaskInfos != null) {
//			ComponentName componentName = runningTaskInfos.get(0).topActivity;
//			Intent i = new Intent();
//			i.setComponent(componentName);
//			PackageManager packageManager = context.getPackageManager();
//			ResolveInfo resolveInfo = packageManager.resolveActivity(i, PackageManager.MATCH_DEFAULT_ONLY);
//
//		}
//		try{
//			Runtime runtime=Runtime.getRuntime();
//			runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
//		}catch(IOException e){
//			Log.e("Exception when doBack", e.toString());
//		}

	}

	public static void doSettingAction(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);

	}

	public static void doRecentAction() {
		try {
			Class ServiceManager = Class.forName("android.os.ServiceManager");
			Method getService = ServiceManager
					.getMethod("getService", new Class[]{String.class});
			Object[] statusbarObj = new Object[]{"statusbar"};
			IBinder binder = (IBinder) getService.invoke(ServiceManager,
					statusbarObj);
			Class IStatusBarService = Class.forName(
					"com.android.internal.statusbar.IStatusBarService")
					.getClasses()[0];
			Method asInterface = IStatusBarService.getMethod("asInterface",
					new Class[]{IBinder.class});
			Object obj = asInterface.invoke(null, new Object[]{binder});
			IStatusBarService.getMethod("toggleRecentApps", new Class[0]).invoke(
					obj, new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void doMenuAction(Context context) {

	}

	public static void doSearchAction(Context context) {

	}

	public static void doPowerAction(Context context) {
		Intent intent = new Intent(context,AuxiliaryActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);


	}

//	public static void onDisableRequested(Context context) {
//		Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.android.settings");
//
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//		context.startActivity(intent);
//	}



}
