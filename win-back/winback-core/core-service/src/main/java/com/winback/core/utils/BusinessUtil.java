package com.winback.core.utils;

import com.winback.arch.common.utils.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: BusinessUtil
 * @ProjectName winback
 * @Description:
 * @date 2019-03-22 15:48
 */
public class BusinessUtil {

    public static String formatAppMessageTime(Date createTime) {
        if (DateUtil.isToday(createTime)) {
            String s = DateUtil.format(createTime, "ahh:mm");
            return s.replace("AM", "上午").replace("PM", "下午");
        } else {
            return DateUtil.formatNormDate(createTime);
        }
    }
}
