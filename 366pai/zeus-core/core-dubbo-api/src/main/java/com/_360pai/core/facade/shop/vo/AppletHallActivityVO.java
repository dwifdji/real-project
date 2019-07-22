package com._360pai.core.facade.shop.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: AppletHallActivityVO
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/2/26 14:46
 */
@Data
public class AppletHallActivityVO implements Serializable {

    /**
     * 活动ID
     */
    private Integer activityId;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 活动类型（0 拍品 1抵押物预招商 2 债权招商 3 物权招商）
     */
    private String activityType;
    /**
     * 金额
     */
    private String amount;
    /**
     * 金额描述
     */
    private String amountDesc;
    /**
     * 状态（UPCOMING 即将开始，SALE 正在进行，，SUCCESS 成功 FAILED 结束）
     */
    private String status;
    /**
     * 状态描述
     */
    private String statusDesc;
    /**
     * 拍卖竞拍方式
     */
    private String mode;
    /**
     * 拍卖竞拍方式描述
     */
    private String modeDesc;
    /**
     * 招商抵押物情况描述
     */
    private String mortgageDesc;
    /**
     * 类型名称
     */
    private String categoryName;
    /**
     * 预展时间
     */
    private Date previewAt;
    /**
     * 开始时间
     */
    private Date beginAt;
    /**
     * 结束时间
     */
    private Date endAt;
    /**
     * 增价拍延时结束时间
     */
    private Date incrementAt;

    private Long previewAtTimestamp;
    private Long beginAtTimestamp;
    private Long endAtTimestamp;
    private Long incrementAtTimestamp;
    private Long currentTimestamp;

    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 图片URL
     */
    private String imageUrl;
    /**
     * 长城资产标志
     */
    private Boolean greatWallFlag;
}
