package com._360pai.core.facade.account.vo;

import com._360pai.arch.common.utils.DateUtil;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: UserVo
 * @ProjectName zeus
 * @Description:
 * @date 11/09/2018 15:04
 */
@Getter
@Setter
public class UserVo implements Serializable {
    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     * 个人认证时选的送拍机构
     */
    private AgencyVo defaultAgency;
    /**
     * 姓名
     */
    private java.lang.String name;
    /**
     * 身份证号码
     */
    private java.lang.String certificateNumber;
    /**
     * 身份证起始日期
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private java.util.Date certificateBegin;
    /**
     * 身份证结束日期
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private java.util.Date certificateEnd;
    /**
     * 省Id
     */
    private java.lang.String provinceId;
    /**
     * 城市Id
     */
    private java.lang.String cityId;
    /**
     * 区县id
     */
    private java.lang.String areaId;
    private java.lang.String provinceName;
    private java.lang.String cityName;
    private java.lang.String areaName;
    /**
     * 地址
     */
    private java.lang.String address;
    /**
     * 身份证正面照
     */
    private java.lang.String certificateFrontImg;
    /**
     * 身份证背面照
     */
    private java.lang.String certificateBackImg;
    /**
     * 手机号
     */
    private java.lang.String mobile;
    /**
     * 法大大Id
     */
    private java.lang.String fadadaId;
    /**
     * 东方付通ID
     */
    private java.lang.String dfftId;
    /**
     * 是否绑定东方付通
     */
    private java.lang.Integer payBind;
    /**
     * 0:无效,1:有效
     */
    private java.lang.Integer status;
    /**
     * 是否是代理商(0:否,1:是)
     */
    private java.lang.Integer isChannel;
    /**
     * 允许发布线下操作拍品 0 否 1 是
     */
    private java.lang.Boolean operOffline;
    /**
     * 是否允许未开通法大大下发布预招商 0 否 1 是
     */
    private java.lang.Boolean operWithoutFadada;
    /**
     * 创建时间
     */
    private java.util.Date createTime;

    private BigDecimal commissionPercentChannelAgent;
    private String myChannelAgentName;
    private Integer myChannelAgentId;

    private String isStaff;
    private String isInBlackList;
    /**
     * 拉黑时间
     */
    private java.util.Date latestInBlackListAt;
    /**
     * 开户人员
     */
    private java.lang.Integer openAccountOperatorId;
    /**
     * 业务对接人
     */
    private java.lang.Integer businessOperatorId;
    /**
     * 开户人员
     */
    private java.lang.String openAccountOperator;
    /**
     * 业务对接人
     */
    private java.lang.String businessOperator;
    /**
     * 认证来源（PLATFORM：平台，APPLET：小程序）
     */
    private java.lang.String applySource;
    private java.lang.String applySourceDesc;
}
