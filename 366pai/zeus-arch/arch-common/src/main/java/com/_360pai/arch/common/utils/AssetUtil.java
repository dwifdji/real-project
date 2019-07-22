package com._360pai.arch.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AssetUtil {

    public static String getAssetCode(Integer id) {
        return ((char) (Math.random() * 26 + 'A')) + "" + id + "-" + RandomNumberGenerator.numberGenerator(6);
    }


    public static Date formatDate(Date beginAt, Integer reductionPeriod) {

        Calendar gc = new GregorianCalendar();
        gc.setTime(beginAt);
        gc.add(GregorianCalendar.MINUTE, reductionPeriod);

        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.NORM_DATETIME_PATTERN);
        String format = sdf.format(gc.getTime());
         return DateUtil.parse(format, DateUtil.NORM_DATETIME_PATTERN);
    }
}
