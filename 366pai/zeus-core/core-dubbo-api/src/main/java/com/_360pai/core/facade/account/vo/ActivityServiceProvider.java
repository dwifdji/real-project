package com._360pai.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: ActivityServiceProvider
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-05-21 15:47
 */
@Data
public class ActivityServiceProvider implements Serializable {
    /**
     *
     */
    private java.lang.Integer id;
    /**
     * 活动类型（拍卖 auction，招商 enrolling）
     */
    private java.lang.String activityType;
    /**
     * 活动id
     */
    private java.lang.Integer activityId;
    /**
     * 服务商类型（处置 dispose，资金 fund）
     */
    private java.lang.String providerType;
    /**
     * 服务商名称
     */
    private java.lang.String name;
    /**
     * 联系电话
     */
    private java.lang.String phone;
    /**
     * 服务商id
     */
    private java.lang.Integer providerId;
    /**
     * 城市合伙人标志
     */
    private java.lang.Boolean cityPartnerFlag;
    /**
     * 服务商区域
     */
    private java.lang.String region;
    /**
     * 排序号
     */
    private java.lang.Integer orderNum;
    /**
     * 删除标志
     */
    private java.lang.Boolean deleteFlag;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
}
