package com.xinbob.curable.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.xinbob.curable.db.Trouble;
import com.xinbob.curable.utils.NotificationUtils;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CheckTodayRecordReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String today = sdf.format(new Date());

        List<Trouble> troubles = DataSupport.where("timestamp=?", today).find(Trouble.class);

        if (troubles == null || troubles.size() < 1) {
            NotificationUtils.showNotification(context, "今天一个记录都没有", "随便写点什么");
        } else {
            for (Trouble trouble : troubles) {
                if (trouble.getMood() == -1 || TextUtils.isEmpty(trouble.getResolve()) ||
                        TextUtils.isEmpty(trouble.getTenTypes())) {
                    NotificationUtils.showNotification(context, "还有没写完的", "明天就不能写了");
                    break;
                }
            }
        }
    }

}
