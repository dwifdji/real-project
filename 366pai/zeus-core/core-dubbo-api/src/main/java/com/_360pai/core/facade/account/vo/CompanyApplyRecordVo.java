package com._360pai.core.facade.account.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: CompanyVerifyApplicationVo
 * @ProjectName zeus
 * @Description:
 * @date 11/09/2018 15:06
 */
@Getter
@Setter
public class CompanyApplyRecordVo implements Serializable {
    /**
     * 主键
     */
    private java.lang.Long id;
    /**
     * 公司名称
     */
    private java.lang.String name;
    /**
     * 手机号
     */
    private java.lang.String mobile;
    /**
     * 申请状态(PENDING = 1,APPROVED = 2,REJECT = 3)
     */
    private java.lang.String status;
    private java.lang.String statusDesc;
    /**
     * 办公地址
     */
    private java.lang.String address;
    /**
     * 注册地址
     */
    private java.lang.String registerAddress;
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
     * 注册城市Id
     */
    private java.lang.String registerCityId;
    /**
     * 注册省id
     */
    private java.lang.String registerProvinceId;
    /**
     * 注册区县id
     */
    private java.lang.String registerAreaId;
    private java.lang.String registerCityName;
    private java.lang.String registerProvinceName;
    private java.lang.String registerAreaName;
    /**
     * 营业执照
     */
    private java.lang.String license;
    /**
     * 营业执照图片
     */
    private java.lang.String licenseImg;
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
     * 营业起始
     */
    private java.util.Date qualifiedBegin;
    /**
     * 营业结束
     */
    private java.util.Date qualifiedEnd;
    /**
     * 原因
     */
    private java.lang.String reason;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 默认送拍机构
     */
    private AgencyVo defaultAgency;

    private StaffVo operator;
    /**
     * 认证来源（PLATFORM：平台，APPLET：小程序）
     */
    private java.lang.String applySource;
    private java.lang.String applySourceDesc;

    /**
     * 开户人员
     */
    private java.lang.String openAccountOperator;
    /**
     * 业务对接人
     */
    private java.lang.String businessOperator;
}
