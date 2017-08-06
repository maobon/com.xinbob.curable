package com.xinbob.curable.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by xinbob on 8/6/17.
 */

public class AlarmUtil {

    public static void setCheckTodayRecordSchedule(Context context, int hourOfDay, int minutes) {
        // 设定定时任务
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minutes);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Constants.ACTION_CHECK_TODAY_RECORD);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (System.currentTimeMillis() < c.getTimeInMillis()) {
            manager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
        }
    }

}
