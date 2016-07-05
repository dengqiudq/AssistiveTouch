package com.leon.assistivetouch.main;


import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.leon.assistivetouch.main.ui.LockReceiver;

public class AuxiliaryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        lockScreen();
    }

    private void lockScreen() {
        DevicePolicyManager mDevicePolicyManager;
        ComponentName mComponentName;

        mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(this, LockReceiver.class);

        // 判断是否有权限
        if (mDevicePolicyManager.isAdminActive(mComponentName)) {
            mDevicePolicyManager.lockNow();
            finish();
        } else {
            activeManager(mComponentName);
        }
    }

    /**
     * 激活设备管理器获取权限
     */
    private void activeManager(ComponentName componentName) {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "一键锁屏");
        startActivity(intent);
        finish();
    }

}
