package com.leon.assistivetouch.main;

import com.leon.assistivetouch.main.util.L;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.os.Message;

/** 
 * 类名      CheckUpdateReceiver.java
 * 说明   description of the class
 */
public class CheckUpdateReceiver extends BroadcastReceiver{

	private static final String TAG = "CheckUpdateReceiver";
	@Override
	public void onReceive(final Context context, Intent intent) {
		L.d(TAG, "onReceive 接收到命令去检测更新");
		new Thread(new Runnable() {
			public void run() {
				Looper.prepare();
				Message msg = AssistiveTouchApplication.getUpdateMessage (context);
				if (msg != null) {
					AssistiveTouchApplication.showUpdateNotify (context, msg);
				}
				Looper.loop();
			}
		}).start();
	}

}
