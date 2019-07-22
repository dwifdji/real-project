package com._360pai.core.facade.account.req;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by RuQ on 2018/8/22 18:25
 */
@Data
public class ApplyUserAuthReq implements Serializable {
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
     * 账户Id
     */
    private java.lang.Integer accountId;
    /**
     * 默认送拍机构
     */
    private java.lang.Integer defaultAgencyId;
    /**
     * 城市Id
     */
    private java.lang.String cityId;
    /**
     * 省市Id
     */
    private java.lang.String provinceId;
    /**
     * 区县Id
     */
    private java.lang.String areaId;
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
    private java.util.Date certificateBegin;
    /**
     * 身份证结束时间
     */
    private java.util.Date certificateEnd;
    /**
     * 原因
     */
    private java.lang.String reason;
    /**
     * 审核人Id
     */
    private java.lang.Integer operatorId;
    /**
     * 认证来源（PLATFORM：平台，APPLET：小程序）
     */
    private java.lang.String applySource;
    /**
     * 开户人员
     */
    private java.lang.Integer openAccountOperatorId;
    /**
     * 业务对接人
     */
    private java.lang.Integer businessOperatorId;
    /**
     * 删除标志(0:未删除,1:已删除)
     */
    private java.lang.Integer isDel;
    /**
     * 创建日期
     */
    private java.util.Date createTime;
    /**
     * 更新日期
     */
    private java.util.Date updateTime;

}
