package com.xinbob.curable.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.xinbob.curable.utils.AlarmUtil;

public class SetCheckTaskReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AlarmUtil.setCheckTodayRecordSchedule(context, 21, 30);
            }
        }, 1000);
    }

}
