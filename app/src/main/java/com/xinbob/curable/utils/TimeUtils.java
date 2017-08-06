package com.xinbob.curable.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by xinbob on 7/20/17.
 * 时间工具类
 */

public class TimeUtils {

    /**
     * 根据日期取得周几
     *
     * @param date Date
     * @return 周几
     */
    public static String getWeek(Date date) {
        String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekIndex < 0) {
            weekIndex = 0;
        }
        return weeks[weekIndex];
    }

    /**
     * 整理"yyyy-MM-dd"日期字符串
     *
     * @param dashDate      yyyy-MM-dd
     * @param isContainYear 是否返回包含年的日期
     * @return 人类自然日期 2017年7月7日
     */
    public static String replaceDashInDateStr(String dashDate, boolean isContainYear) {
        String[] date = dashDate.split("-");
        String year = date[0];
        String month = date[1].substring(0, 1).equals("0") ? date[1].replace("0", "") : date[1];
        String day = date[2].substring(0, 1).equals("0") ? date[2].replace("0", "") : date[2];

        if (isContainYear) {
            return year + "年" + month + "月" + day + "日";
        }
        return month + "月" + day + "日";
    }
}
