package com.leon.assistivetouch.main.util;

import java.util.List;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

/** 
 * 类名      MemoryCache.java
 * 说明   description of the class
*/
public class MemoryCache {

	private static List<ResolveInfo> mAllapps = null;
	public static List<ResolveInfo> getAllApps (PackageManager packageManager) {
		if (mAllapps == null) {
			Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
			mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
			mAllapps = packageManager.queryIntentActivities(mainIntent, 0);
		}
		return mAllapps;
	}
	
	public static ResolveInfo getResolveInfoFromActivityName (PackageManager packageManager, String packegename) {
		List<ResolveInfo> infos = getAllApps(packageManager);
		for (ResolveInfo info : infos) {
			String temp = (String) info.activityInfo.name;
			if (temp.equals(packegename)) {
				return info;
			}
		}
		return null;
	}
	
	public static void clear () {
		if (mAllapps != null) {
			mAllapps.clear();
			mAllapps = null;
		}
		System.gc();
	}
}
