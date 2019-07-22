package com._360pai.core.facade.fastway.resp;

import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.facade.disposal.req.City;
import com._360pai.core.facade.fastway.vo.FundBasisVO;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-12-07 10:03
 */
@Data
public class CompanyFundDetailVO extends FundBasisVO {
    private static final long serialVersionUID = -1199962142375185205L;

    /**
     * 企业名称
     */
    private String name;
    /**
     * 企业类型
     */
    private String companyType;
    /**
     * 联系人
     */
    private String contactName;
    /**
     * 联系方式
     */
    private String mobile;
    /**
     * 信用代码
     */
    private String license;
    /**
     * 营业执照
     */
    private String licenseImg;
    /**
     * 注册地址
     */
    private String registerAddress;
    /**
     * 注册资本
     */
    private BigDecimal registerCapital;
    /**
     * 办公城市
     */
    private String cityId;
    /**
     * 办公地址
     */
    private String address;
    /**
     * 管理员委托书
     */
    private String authorizationImg;
    /**
     * 申请状态描述
     */
    private String applyStatusDesc;
    /**
     * 城市对象
     */
    private City cityBean;
    /**
     * 注册手机号码
     */
    private String registerMobile;
    /**
     * 申请时间
     */
    private Date createTime;
    /**
     * 申请状态
     */
    private String applyStatus;
    /**
     * 审核备注
     */
    private String remark;
    /**
     * 营业开始时间
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private Date qualifiedBegin;
    /**
     * 营业结束时间
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private Date qualifiedEnd;
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
     * 选择其他，公司类型
     */
    private String customCompanyType;

    private String contactPhone;
    private String contactPerson;

    private Integer auditNum;
}
