package com._360pai.core.facade.account.vo;

import com._360pai.arch.common.utils.DateUtil;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: AgencyApplyRecordVo
 * @ProjectName zeus
 * @Description:
 * @date 15/09/2018 19:45
 */
@Getter
@Setter
public class AgencyApplyRecordVo implements Serializable {
    /**
     * 主键
     */
    private java.lang.Long id;
    /**
     * 账户Id
     */
    private java.lang.Integer accountId;
    /**
     * 机构名
     */
    private java.lang.String name;
    /**
     * 手机号
     */
    private java.lang.String mobile;
    /**
     * 机构编号
     */
    private java.lang.String code;
    /**
     * 简称
     */
    private java.lang.String shortName;
    /**
     * 注册地址
     */
    private java.lang.String registerAddress;
    /**
     * 办公地址
     */
    private java.lang.String address;
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
     * 开户许可证
     */
    private java.lang.String accountLicense;
    /**
     * 授权书
     */
    private java.lang.String authorizationImg;
    /**
     * 营业开始时间
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private java.util.Date businessBegin;
    /**
     * 营业结束时间
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private java.util.Date businessEnd;
    /**
     * 身份证号
     */
    private java.lang.String idCard;
    /**
     * 身份证反面照
     */
    private java.lang.String idCardBackImg;
    /**
     * 身份证正面照
     */
    private java.lang.String idCardFrontImg;
    /**
     * 法人
     */
    private java.lang.String legalPerson;
    /**
     * 营业执照
     */
    private java.lang.String license;
    /**
     * 营业执照图片
     */
    private java.lang.String licenseImg;
    /**
     * logo图片
     */
    private java.lang.String logoUrl;
    /**
     * 许可证图片
     */
    private java.lang.String qualificationImg;
    /**
     * 许可证编号
     */
    private java.lang.String qualificationNumber;
    /**
     * 许可证签发日期
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private java.util.Date qualifiedBegin;
    /**
     * 许可证结束日期
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private java.util.Date qualifiedEnd;
    /**
     * 审核备注
     */
    private java.lang.String remark;
    /**
     * 成拍分成
     */
    private java.lang.Integer serveBuyerPercent;
    /**
     * 送拍分成
     */
    private java.lang.Integer serveSellerPercent;
    /**
     * 申请状态(PENDING = 1,APPROVED = 2,REJECT = 3)
     */
    private java.lang.String status;
    /**
     * 申请状态描述
     */
    private java.lang.String statusDesc;
    /**
     * 审核人员Id
     */
    private java.lang.Integer operatorId;

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
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 更新时间
     */
    private java.util.Date updateTime;
    /**
     * 受托人
     */
    private java.lang.String trustee;
}
