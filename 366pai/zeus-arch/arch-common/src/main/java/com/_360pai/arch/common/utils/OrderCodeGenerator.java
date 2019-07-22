package com._360pai.arch.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单编码码生成器，生成18位数字编码，
 *
 * @author : whisky_vip
 * @date : 2018/9/1 12:22
 */
public enum OrderCodeGenerator {


    /**
     * 实例
     */
    INSTANCE;

    /**
     * 资产大买办需求单 单号前缀
     */
    public static String COMPRADOR_REQUIREMENT_NO             = "DR";
    /**
     * 资产大买办需求单 对应的我有资产 单号前缀
     */
    public static String COMPRADOR_REQUIREMENT_RECOMMENDER_NO = "DRT";
    /**
     * 我有资产 单号前缀
     */
    public static String COMPRADOR_RECOMMEND_NO               = "DMR";
    /**
     * 配资乐 需求单
     */
    public static String WITHFUDIG_REQUIREMENT_NO             = "PR";
    /**
     * 配资乐 我要投标
     */
    public static String WITHFUDIG_REQUIREMENT_INVEST_NO      = "PRT";
    /**
     * 第三方尽调服务 我要投标
     */
    public static String THIRD_SURVEY_NO                      = "TS";


    /**
     * 格式化时间 + 2位随机数字
     *
     * @param prefix 规则 参考本类的常量
     * @return orderCode
     */
    public String getOrderCode(String prefix) {
        SimpleDateFormat sdf     = new SimpleDateFormat("yyMMddHHMMssSSS");
        String           newDate = sdf.format(new Date());
        return prefix + newDate + RandomNumberGenerator.numberGenerator(1);
    }

    public static void main(String[] args) {
        String orderId = OrderCodeGenerator.INSTANCE.getOrderCode("PRT");
        System.out.println(orderId);
    }
}
