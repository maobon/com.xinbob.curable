package com.xinbob.curable.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by xinbob on 8/6/17.
 * 使用系统的Alarm设定定时任务
 */

public class AlarmSettingUtils {

    /**
     * 设定检查本日记录定时任务
     *
     * @param context   Context
     * @param hourOfDay 几点
     * @param minutes   几分
     */
    public static void setCheckTodayRecordSchedule(Context context, int hourOfDay, int minutes) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minutes);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        // 将在预订时间发出广播 ACTION = ACTION_CHECK_TODAY_RECORD
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Constants.ACTION_CHECK_TODAY_RECORD);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (System.currentTimeMillis() < c.getTimeInMillis()) {
            // 当前时间 < 设定任务时间 才会完成设定
            manager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
        }
    }

}
