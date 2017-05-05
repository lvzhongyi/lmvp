package com.lvzhongyi.mvp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author lvzhongyi
 *         <p/>
 *         description
 *         date 16/4/19
 *         email lvzhongyiforchrome@gmail.com
 *         </p>
 */
public class DateUtils {
    /**
     * 格林威治时间转东八区时间,并略去小时分钟秒
     *
     * @param datetime
     * @return
     */
    public static String date2GMT(long datetime) {
        return date2GMT(new Date(datetime)); // 2014-1-31 21:20:50
    }

    public static String date2GMT(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String dateStrTmp = dateFormat.format(date);
        return dateStrTmp;
//        return "2016-04-29";
    }
}
