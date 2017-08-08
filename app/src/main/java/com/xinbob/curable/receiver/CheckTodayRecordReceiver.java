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

/**
 * 检查本日记录
 */
public class CheckTodayRecordReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 本日日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String today = sdf.format(new Date());
        // 查询本日记录
        List<Trouble> troubles = DataSupport.where("timestamp=?", today).find(Trouble.class);

        if (troubles == null || troubles.size() < 1) {
            NotificationUtils.showNotification(context, "情绪日志", "今天不记录点什么吗?");
        } else {
            for (Trouble trouble : troubles) {
                if (trouble.getMood() == -1 || TextUtils.isEmpty(trouble.getResolve()) ||
                        TextUtils.isEmpty(trouble.getTenTypes())) {
                    NotificationUtils.showNotification(context, "情绪日志", "请及时完成本日未完成的条目");
                    break;
                }
            }
        }
    }

}
