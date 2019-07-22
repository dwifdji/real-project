package com.tzCloud.core.utils;


import com.tzCloud.arch.common.utils.RandomNumberGenerator;

/**
 * @author xdrodger
 * @Title: BusinessUtil
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/31 11:05
 */
public class BusinessUtil {
    public static String genDfftId() {
        return RandomNumberGenerator.wordGenerator(24).toLowerCase();
    }

    public static String genFadadaEmail() {
        return RandomNumberGenerator.wordGenerator(10).toLowerCase() + "@360pai.com";
    }
}
