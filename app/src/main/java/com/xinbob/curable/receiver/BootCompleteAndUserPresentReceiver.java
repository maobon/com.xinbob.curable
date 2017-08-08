package com.xinbob.curable.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.xinbob.curable.utils.AlarmSettingUtils;
import com.xinbob.curable.utils.Constants;

/**
 * 监听设备启动完成与用户解锁广播
 */
public class BootCompleteAndUserPresentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AlarmSettingUtils.setCheckTodayRecordSchedule(context, Constants.HOUR_OF_DAY, Constants.MINUTES);
            }
        }, 1000);
    }

}
