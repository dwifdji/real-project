package com._360pai.core.facade.fastway.resp;

import com._360pai.core.facade.disposal.req.City;
import com._360pai.core.facade.fastway.vo.FundBasisVO;
import lombok.Data;

import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-12-07 10:03
 */
@Data
public class UserFundDetailVO extends FundBasisVO {
    private static final long serialVersionUID = -5828948477003212144L;
    /**
     * 注册手机号码
     */
    private String registerMobile;
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号码
     */
    private String certificateNumber;
    /**
     * 身份证正面照
     */
    private String certificateFrontImg;
    /**
     * 身份证反面照
     */
    private String certificateBackImg;
    /**
     * 常驻城市
     */
    private String cityId;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 申请状态描述
     */
    private String applyStatusDesc;
    /**
     * 城市对象
     */
    private City cityBean;
    /**
     * 手机号码
     */
    private String mobile;
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
     * 默认送拍机构
     */
    private String defaultAgency;
    /**
     * 默认送拍机构id
     */
    private Integer defaultAgencyId;
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
}
