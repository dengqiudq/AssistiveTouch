package com.leon.assistivetouch.main.util;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/** 
 * 类名      VibratorHelper.java
 * 说明   调用系统震动效果
*/
public class VibratorHelper { 
	
	/**
	 * final Context context
	 * long milliseconds ：震动的时长，单位是毫秒
	 * long[] pattern  ：自定义震动模式 。数组中数字的含义依次是静止的时长，震动时长，静止时长，震动时长。。。时长的单位是毫秒
	 * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
	 * */
	
	public static void Vibrate(final Context context, long milliseconds) {
		Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds);
	}
	
	public static void Vibrate(final Context context, long[] pattern,boolean isRepeat) {
		Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(pattern, isRepeat ? 1 : -1);
	}
	
	public static void KeyVibrate (final Context context) {
		Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(100);
	}
}
