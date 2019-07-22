package com.winback.arch.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 随机数生成器
 * @ClassName: RandomNumberGenerator
 */
public class RandomNumberGenerator {

    /**
     * 随机种子
     */
    private static final Random RANDOM = new Random();

    /**
     * 取值范围
     */
    private static final char[] WORD = new char[]{
            '0','1','2','3','4','5','6','7','8','9',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
    };

    /**
     * 随机生成指定长度的纯数字组合(允许0开头)
     * @Title: numberGenerator
     * @Description: 随机生成指定长度的纯数字组合(允许0开头)
     * @param length 希望返回字符串的长度
     * @return String 结果字符串
     * @author g.yang@i-vpoints.com
     * @date 2017年7月3日 下午2:48:28
     */
    public static String numberGenerator(int length){
        return generator(length, 0, 10);
    }

    /**
     * 随机生成指定长度的字母-数字组合(允许0开头)
     * @Title: wordGenerator
     * @Description: 随机生成指定长度的字母-数字组合(允许0开头)
     * @param length 希望返回字符串长度
     * @return String 结果字符串
     * @author g.yang@i-vpoints.com
     * @date 2017年7月3日 下午2:49:52
     */
    public static String wordGenerator(int length){
        return generator(length, 0, WORD.length);
    }

    /**
     * 订单号生成器</br>
     * yyyyMMddHHmmss+wordGenerator(extLength)
     * @Title: generatorOrderNumber
     * @Description: 订单号生成器
     * @return String 20位长度订单号
     * @author g.yang@i-vpoints.com
     * @date 2017年7月4日 上午9:28:50
     */
    public static String generatorOrderNumber(int extlength){
        return DateFormatUtils.format(new Date(),"yyyyMMddHHmmss")+wordGenerator(extlength);
    }

    public static String generatorOrderNum(int extlength){
        return DateFormatUtils.format(new Date(),"yyyyMMddHHmmss")+numberGenerator(extlength);
    }

    public static String generatorContractOrderNum(int extlength){
        return DateFormatUtils.format(new Date(),"yyMMddHHmmss") + "-" + numberGenerator(extlength);
    }

    public static String generatorBatchOrderNum(){
        return DateFormatUtils.format(new Date(),"yyyyMMdd")+"001";

    }

    /**
     * 随机数生成器
     * @Title: generator
     * @Description: 随机数生成器
     * @param length 希望返回字符串长度
     * @param start 子串开始索引
     * @param end 子串结束索引
     * @return String 结果字符串
     * @author g.yang@i-vpoints.com
     * @date 2017年7月3日 下午2:51:08
     */
    private static String generator(int length, int start, int end){
        StringBuilder sb = new StringBuilder();
        int subLength = end - start;
        for(int i=0;i<length;i++){
            sb.append(WORD[start + RANDOM.nextInt(subLength)]);
        }
        return sb.toString();
    }

    /**
     * 订单号生成器</br>
     * yyyyMMddHHmmss+wordGenerator(extLength)
     * @Title: generatorOrderNumber
     * @Description: 订单号生成器
     * @return String 20位长度订单号
     * @author g.yang@i-vpoints.com
     * @date 2017年7月4日 上午9:28:50
     */
    public static String generatorMarkOrderNumber(String mark ,int extlength){
        return mark+DateFormatUtils.format(new Date(),"yyyyMMddHHmmss")+numberGenerator(extlength);
    }


    /**
     * 预招商code生成</br>
     * yyyyMMddHHmmss+wordGenerator(extLength)
     * @Title: generatorOrderNumber
     * @Description: 订单号生成器
     * @return String 20位长度订单号
     * @author g.yang@i-vpoints.com
     * @date 2017年7月4日 上午9:28:50
     */
    public static String generatorMarkEnrollingCode(String mark ,int extlength){
        return mark+DateFormatUtils.format(new Date(),"HHmmss")+numberGenerator(extlength);
    }




   /**
    *
    *获取去电 - 的 uuid
    */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
