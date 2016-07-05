package com.leon.assistivetouch.main.ui;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by user on 16-6-24.
 */
public class LockReceiver extends DeviceAdminReceiver {

    private static final String TAG = "LockReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i(TAG, "onreceiver");
    }
    @Override
    public void onEnabled(Context context, Intent intent) {
        Log.i(TAG, "激活使用");
        super.onEnabled(context, intent);
    }
    @Override
    public void onDisabled(Context context, Intent intent) {
        Log.i(TAG, "取消激活");
        super.onDisabled(context, intent);
    }
}
