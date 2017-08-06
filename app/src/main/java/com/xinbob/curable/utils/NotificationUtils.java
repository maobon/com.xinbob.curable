package com.xinbob.curable.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.xinbob.curable.R;
import com.xinbob.curable.activity.MainActivity;

/**
 * Created by xinbob on 8/5/17.
 */

public class NotificationUtils {

    public static void showNotification(Context context, String title, String content) {
        Intent intent = new Intent(context, MainActivity.class);
        Notification notification = new NotificationCompat.Builder(context)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_today_white_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(context, 0, intent, 0))
                .build();

        getNotificationManager(context).notify(1, notification);
    }

    private static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
