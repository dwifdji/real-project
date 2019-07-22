package com._360pai.core.facade.account.vo;

import com._360pai.arch.common.utils.DateUtil;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: AgencyVo
 * @ProjectName zeus-parent
 * @Description:
 * @date 04/09/2018 15:29
 */
@Getter
@Setter
public class AgencyVo implements Serializable {
    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     * 机构名
     */
    private java.lang.String name;
    /**
     * 名称拼音
     */
    private java.lang.String pinyin;
    /**
     * 手机号
     */
    private java.lang.String mobile;
    /**
     * 机构编号
     */
    private java.lang.String code;
    /**
     * 机构简称
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
     * 身份证反面照
     */
    private java.lang.String idCardBackImg;
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
     * 许可证图片
     */
    private java.lang.String qualificationImg;
    /**
     * 许可证号
     */
    private java.lang.String qualificationNumber;
    /**
     * 授权书
     */
    private java.lang.String authorizationImg;
    /**
     * 开户许可证
     */
    private java.lang.String accountLicense;
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
     * 备注
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
     * 状态(0:无效,1:有效)
     */
    private java.lang.Integer status;
    /**
     * 机构自我介绍
     */
    private java.lang.String selfIntroduction;
    /**
     * 平台给机构的介绍
     */
    private java.lang.String introduction;
    /**
     * 法大大Id
     */
    private java.lang.String fadadaId;
    /**
     * 法大大邮箱
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
     * 是否是机构代理商
     */
    private java.lang.Integer isChannelAgent;
    /**
     * logo地址
     */
    private java.lang.String logoUrl;
    /**
     * 机构介绍图片地址
     */
    private java.lang.String imgUrl;
    /**
     * 站点状态(PENDING、ONLINE、CLOSED)
     */
    private java.lang.String websiteStatus;
    /**
     * 站点状态描述
     */
    private java.lang.String websiteStatusDesc;
    /**
     * 创建时间
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private java.util.Date createTime;
    /**
     * 我的机构代理商名称
     */
    private String myChannelAgentName;
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
     * 受托人
     */
    private java.lang.String trustee;
    /**
     * 受托人联系方式
     */
    private java.lang.String trusteePhone;
}
