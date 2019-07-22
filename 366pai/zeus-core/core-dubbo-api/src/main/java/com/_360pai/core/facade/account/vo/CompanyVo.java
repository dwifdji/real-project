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
 * @Title: CompanyVo
 * @ProjectName zeus
 * @Description:
 * @date 11/09/2018 15:04
 */
@Getter
@Setter
public class CompanyVo implements Serializable {
    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     * 公司名称
     */
    private java.lang.String name;
    /**
     * 账户Id
     */
    private java.lang.Integer accountId;
    /**
     * 企业认证送拍机构
     */
    private AgencyVo defaultAgency;
    /**
     * 手机号
     */
    private java.lang.String mobile;
    /**
     * 法人
     */
    private java.lang.String legalPerson;
    /**
     * 营业执照号码
     */
    private java.lang.String license;
    /**
     * 营业执照图片
     */
    private java.lang.String licenseImg;
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
     * 注册地址
     */
    private java.lang.String registerAddress;
    /**
     * 办公地址
     */
    private java.lang.String address;
    /**
     * 机构id
     */
    private AgencyVo agency;
    /**
     * 营业起始
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private java.util.Date qualifiedBegin;
    /**
     * 营业结束
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private java.util.Date qualifiedEnd;
    /**
     * 授权书
     */
    private java.lang.String authorizationImg;
    /**
     * 开户许可证
     */
    private java.lang.String accountLicense;
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
     * 状态(0:无效,1:有效)
     */
    private java.lang.Integer status;
    /**
     * 法大大Id
     */
    private java.lang.String fadadaId;
    /**
     * 法大大email
     */
    private java.lang.String fadadaEmail;
    /**
     * 东方付通Id
     */
    private java.lang.String dfftId;
    /**
     * 是否绑定东方付通
     */
    private java.lang.Integer payBind;
    /**
     * 是否为通道支付类企业
     */
    private java.lang.Integer channelPay;
    /**
     * 是否是代理商(0:否,1:是)
     */
    private java.lang.Integer isChannel;
    /**
     * 1:正常人，2:银行,3:AMC 资产管理公司,4:民营资管，5:普通公司,6:拍卖公司
     */
    private java.lang.String category;
    private java.lang.String categoryDesc;
    /**
     * 收款开户名
     */
    private java.lang.String bankAccountName;
    /**
     * 收款银行账户
     */
    private java.lang.String bankAccountNumber;
    /**
     * 银行Id
     */
    private java.lang.Integer bankId;

    private String bankName;
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
    /**
     * 拉黑时间
     */
    private java.util.Date latestInBlackListAt;
    /**
     * 是否在黑名单里
     */
    private java.lang.Boolean isInBlackList;
    /**
     * 是否内部企业
     */
    private String isStaff;
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

    private java.lang.Integer auditNum;

}
