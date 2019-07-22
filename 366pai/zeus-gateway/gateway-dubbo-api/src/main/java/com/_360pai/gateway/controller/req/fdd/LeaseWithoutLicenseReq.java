package com._360pai.gateway.controller.req.fdd;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：租赁协议 无证版
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/31 14:49
 */
@Data
public class LeaseWithoutLicenseReq implements Serializable {

    private String assetCode;//资产编号

    private String activityCode;//合同编号

    private String seller;//甲方

    private String buyer;//买受人

    private String address;//房屋地址

    private String structure;//房屋结构

    private String area;//建筑面积

    private String use;//用途

    private String businessProject;//经营项目

    private String leaseYears;//租赁期

    private String leaseStartTime;//租赁开始时间

    private String leaseEndTime;//租赁结束时间

    private String deal_amount;//成交价

    private String increase;//租金年递增比例


    private String payWay;//按时间支付
    private String rent;//租金
    private String deposit;//押金
    private String totalAmount;//合计
    private String rentChn;//租金大写
    private String depositChn;//押金大写
    private String totalAmountChn;//合计大写
    private String side;//支付方
    private String supplement;//补充
    private String sellerAddress;//甲方地址
    private String buyerAddress;//买方地址
    private String user;//代表
    private String buyerNum;//买方身份证
    private String sellerPhone;//甲方电话
    private String buyerPhone;//乙方电话




}
