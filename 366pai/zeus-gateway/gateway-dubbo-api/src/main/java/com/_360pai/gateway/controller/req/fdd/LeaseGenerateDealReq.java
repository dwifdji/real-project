package com._360pai.gateway.controller.req.fdd;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：租赁合同成交确认书
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/31 14:49
 */
@Data
public class LeaseGenerateDealReq implements Serializable {

    private String activityCode;//合同编号

    private String seller;//委托人

    private String auctionFirm;//送拍机构

    private String buyer;//买受人

    private String unionAuctionFirm;//连拍机构

    private String auctionPeriod;//竞拍时间

    private String orderTime;//成交时间

    private String lotCode;//拍品编号

    private String hammerPrice;//成交金额

    private String lotName;//拍品名称

    //private String commission;//应收佣金

    private String sellerPercent;//委托人佣金比例

    private String buyerPercent;//买受人佣金比例


    private String sellerCommission;//委托人佣金

    private String buyerCommission;//委托人佣金

    private String commission;//佣金 废弃字段



    private String totalAmountChn;//合计金额

    private String totalAmount;//合计金额



    private String dealAmount;//首次实际支付成 交价金额

    private String deposit;//租赁保证金

    private String paymentPeriod;//付款日期

    private String depositCode;//竞买排好


    private String discount;//折扣率





}
