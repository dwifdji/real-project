package com._360pai.applet.controller.auction.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/26 13:23
 */
@Data
public class AuctionDetailVo implements Serializable {

    private String images;//展示图片
    private String assetName;//标的名称
    private String assetCode;//竞拍代码
    private String deposit;//保证金
    private String startingPrice;//起拍价
    private String bidPrice;//最新出价
    private String bidPerson;//最新出价人
    private String status;//标的状态
    private String statusDesc;//标的状态描述
    private String favorStatus;//关注状态
    private String favorCount;//关注数量
    private String viewCount;//浏览数量
    private String depositCount;//报名数量
    private String reservePrice;//	是否有保留价
    private String increment;//加价幅度
    private String commissionSeller;//卖家 佣金比例
    private String commissionBuyer;//买家 佣金比例
    private String agencyName;//送拍机构
    private String remarkStatus;//提醒状态
    private String pmPhone;//项目经理电话
    private String previewAt;//预展时间
    private String beginAt;//开始时间
    private String endAt;//结束时间
    private String competeUrl;//参拍跳转url

    private String detail;//商品详情
    private String assetId;//商品
    private String cityName;//城市名称
    private String assetCategoryName;//债权还是物权
    private String assetCategoryType;//1 债权 2 物权
    private String activityMode;//拍卖方式
    private String biddingExtension;//延迟
    private String modeStr;//延迟
    private String preemptivePerson;//优先购买人














}
