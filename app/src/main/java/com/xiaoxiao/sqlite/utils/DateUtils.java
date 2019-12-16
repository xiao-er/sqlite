package com.xiaoxiao.sqlite.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: 潇潇
 * @create on:  2019/12/16
 * @describe:DOTO
 */

public class DateUtils {


    public static String getDate() {
        SimpleDateFormat simpled = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取当前时间
        Date sdate = new Date(System.currentTimeMillis());
        return simpled.format(sdate);
    }

}
