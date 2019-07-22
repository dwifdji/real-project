
package com.tzCloud.arch.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zxiao
 * @Title: NumberValidationUtils
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/23 11:16
 */
public class NumberValidationUtils {

    private static boolean isMatch(String regex, String orginal) {
        if (orginal == null || "".equals(orginal.trim())) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    /**
     * 功能描述:
     *
     * @param: 1 .对于正整数而言，可以带+号，第一个数字不能为0
     * @return:
     * @auther: zxiao
     * @date: 2018/9/23 11:19
     */
    public static boolean isPositiveInteger(String orginal) {
        return isMatch("^\\+{0,1}[0-9]\\d*", orginal);
    }

    /**
     * 功能描述:     2. 对于负整数而言，必须带负号，第一个数字也不能为0
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/23 11:19
     */
    public static boolean isNegativeInteger(String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }

    /**
     * 功能描述:  3.对于整数而言，实际是由0，正整数和负整数组成的，所以前两个方法一起判断
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/23 11:20
     */
    public static boolean isWholeNumber(String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
    }

    /**
     * 功能描述:  4 .对于正小数而言，可以考带+号，并考虑两种情况，
     * 第一个数字为0和第一个数字不为0，第一个数字为0时，则小数点后面应该不为0，
     * 第一个数字不为0时，小数点后可以为任意数字
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/23 11:20
     */
    public static boolean isPositiveDecimal(String orginal) {
        return isMatch("\\+{0,1}[0]\\.[0-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
    }

    /**
     * 功能描述:  5 . 对于负小数而言，必须带负号，其余都同上
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/23 11:22
     */
    public static boolean isNegativeDecimal(String orginal) {
        return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
    }

    /**
     * 功能描述:  6.对于小数，可以带正负号，并且带小数点就行了，但是至少保证小数点有一边不为空，
     * 所以这里还是分左边不为空和右边不为空的情况
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/23 11:22
     */
    public static boolean isDecimal(String orginal) {
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
    }

    /**
     * 功能描述:  7 . 实数比较简单，，要么是整数，要么是小数
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/23 11:22
     */
    public static boolean isRealNumber(String orginal) {
        return isWholeNumber(orginal) || isDecimal(orginal);
    }

    /**
     * 功能描述:  8. 实判断是否是 正小数和正整数
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/23 11:22
     */
    public static boolean isPositiveDecimalOrInteger(String orginal) {
        return isPositiveDecimal(orginal) || isPositiveInteger(orginal);
    }

    /**
     * 将价格格式化三位一逗号格式
     *
     * @param price
     * @return
     */
    public static String formatPrice(Object price) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String formatPrice = "";
        if (null == price) {
            return formatPrice;
        }
        if ("java.math.BigDecimal".equals(price.getClass().getTypeName())) {
            formatPrice = df.format(price);
        } else {
            formatPrice = df.format(new BigDecimal(price.toString()));
        }
        return formatPrice;
    }

    /**
     * 判断字符串是否是数字
     *
     * @param str
     * @return boolean
     */
    public static boolean isNumeric(String str) {
        try {
            String bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            //异常 说明包含非数字。
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.println("args = " + isPositiveDecimalOrInteger("-0.01111"));

        Float integer = Float.valueOf("1500.1");

        boolean numeric = isNumeric("-11111");
        System.out.println("numeric = " + numeric);

        if (integer < 100) {
            System.out.println("尽调报告价格不能小于100");
        }
        if (integer > 1500) {
            System.out.println("尽调报告价格不能大于1500");
        }
        System.out.println("integer = " + integer);
    }


}

