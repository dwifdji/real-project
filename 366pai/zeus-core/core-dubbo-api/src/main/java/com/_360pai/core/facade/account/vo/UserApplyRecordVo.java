package com._360pai.core.facade.account.vo;

import com._360pai.arch.common.utils.DateUtil;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: UserVerifyApplicationVo
 * @ProjectName zeus
 * @Description:
 * @date 11/09/2018 15:05
 */
@Data
public class UserApplyRecordVo implements Serializable {
    /**
     * 主键
     */
    private java.lang.Long id;
    /**
     * 姓名
     */
    private java.lang.String name;
    /**
     * 身份证号
     */
    private java.lang.String certificateNumber;
    /**
     * 手机号
     */
    private java.lang.String mobile;
    /**
     * 默认送拍机构
     */
    private AgencyVo defaultAgency;
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
     * 用户申请状态(PENDING = 1,APPROVED = 2,REJECT = 3)
     */
    private java.lang.String status;
    /**
     * 身份证正面图片
     */
    private java.lang.String certificateFrontImg;
    /**
     * 身份证背面图片
     */
    private java.lang.String certificateBackImg;
    /**
     * 办公地址
     */
    private java.lang.String address;
    /**
     * 身份证开始时间
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private java.util.Date certificateBegin;
    /**
     * 身份证结束时间
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private java.util.Date certificateEnd;
    /**
     * 原因
     */
    private java.lang.String reason;

    private StaffVo operator;
    /**
     * 开户人员
     */
    private java.lang.String openAccountOperator;
    /**
     * 业务对接人
     */
    private java.lang.String businessOperator;
    /**
     * 创建日期
     */
    private java.util.Date createTime;
    /**
     * 认证来源（PLATFORM：平台，APPLET：小程序）
     */
    private java.lang.String applySource;
    private java.lang.String applySourceDesc;
}
