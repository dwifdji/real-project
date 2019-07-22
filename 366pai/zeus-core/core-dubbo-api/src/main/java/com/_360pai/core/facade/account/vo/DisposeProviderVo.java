package com._360pai.core.facade.account.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author xdrodger
 * @Title: DisposeProviderVo
 * @ProjectName zeus
 * @Description:
 * @date 18/09/2018 18:57
 */
@Getter
@Setter
public class DisposeProviderVo implements Serializable {
    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     *
     */
    private java.lang.Integer accountId;
    /**
     * 注册账号
     */
    private java.lang.String mobile;
    /**
     * 公司名称
     */
    private java.lang.String companyName;
    /**
     * 公司类型
     */
    private java.lang.String companyType;
    /**
     * 注册地址
     */
    private java.lang.String registerAddress;
    /**
     * 注册资本
     */
    private java.math.BigDecimal registerCapital;
    /**
     * 联系人姓名
     */
    private java.lang.String contactName;
    /**
     * 联系方式
     */
    private java.lang.String contactMobile;
    /**
     * 资质证书
     */
    private java.lang.String qualificationUrl;
    /**
     * 工作年限
     */
    private java.math.BigDecimal workYear;
    /**
     * 自我介绍
     */
    private java.lang.String introduction;
    /**
     * 过往案例
     */
    private java.lang.String caseUrl;
    /**
     * 可提供服务
     */
    private List<String> provideServices;
    /**
     * 处置类型
     */
    private java.lang.String disposeType;
    /**
     * 处置类型
     */
    private java.lang.String disposeTypeDesc;
    /**
     *
     */
    private java.util.Date createTime;
    /**
     *
     */
    private java.util.Date updateTime;
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

    private String lawOffice;

    private UserVo user;
    private CompanyVo company;
    /**
     * 区域市id
     */
    private String region;
    /**
     * 省区域id
     */
    private java.lang.String regionProvince;
    /**
     * 区域-区县id
     */
    private java.lang.String regionArea;
    /**
     * 区域市名称
     */
    private String regionName;
    /**
     * 省区域名称
     */
    private java.lang.String regionProvinceName;
    /**
     * 区域-区县名称
     */
    private java.lang.String regionAreaName;

}
