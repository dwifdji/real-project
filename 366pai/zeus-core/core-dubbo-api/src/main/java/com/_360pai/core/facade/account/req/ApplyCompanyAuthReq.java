package com._360pai.core.facade.account.req;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by RuQ on 2018/8/23 13:18
 */
@Data
public class ApplyCompanyAuthReq implements Serializable {
    /**
     * 主键
     */
    private java.lang.Long id;
    /**
     * 公司名称
     */
    private java.lang.String name;
    /**
     * 账户Id
     */
    private java.lang.Integer accountId;
    /**
     * 手机号
     */
    private java.lang.String mobile;
    /**
     * 默认送拍机构
     */
    private java.lang.Integer defaultAgencyId;
    /**
     * 申请状态(PENDING = 1,APPROVED = 2,REJECT = 3)
     */
    private java.lang.String status;
    /**
     * 办公地址
     */
    private java.lang.String address;
    /**
     * 注册地址
     */
    private java.lang.String registerAddress;
    /**
     * 城市Id
     */
    private java.lang.String cityId;
    /**
     * 注册城市Id
     */
    private java.lang.String registerCityId;
    /**
     * 省id
     */
    private java.lang.String provinceId;
    /**
     * 注册省id
     */
    private java.lang.String registerProvinceId;
    /**
     * 区县id
     */
    private java.lang.String areaId;
    /**
     * 注册区县id
     */
    private java.lang.String registerAreaId;
    /**
     * 营业执照
     */
    private java.lang.String license;
    /**
     * 营业执照图片
     */
    private java.lang.String licenseImg;
    /**
     * 营业起始
     */
    private java.util.Date qualifiedBegin;
    /**
     * 营业结束
     */
    private java.util.Date qualifiedEnd;
    /**
     * 法人
     */
    private java.lang.String legalPerson;
    /**
     * 身份证号
     */
    private java.lang.String idCard;
    /**
     * 身份证正面照
     */
    private java.lang.String idCardFrontImg;
    /**
     * 身份证背面照
     */
    private java.lang.String idCardBackImg;
    /**
     * 授权书
     */
    private java.lang.String authorizationImg;
    /**
     * 开户许可证
     */
    private java.lang.String accountLicense;
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

    private String category;
}
