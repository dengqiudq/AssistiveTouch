package com.leon.assistivetouch.main;

import com.leon.assistivetouch.main.util.Constan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/** 
 * 类名      BootReceiver.java
 * 说明  接收开机的消息广播
*/
public class BootReceiver extends BroadcastReceiver{

	private static final String TAG = "BootReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(context);
		boolean enable = spref.getBoolean(Constan.ENABLE_BOOT_START_KEY, true);
		if (enable) {
			spref.edit().putBoolean(Constan.ENABLE_ASSISTIVE_KEY, true).commit();
			Intent i = new Intent(AssistiveTouchService.ASSISTIVE_TOUCH_START_ACTION);
			context.getApplicationContext().startService(i);
		}
	}

}
