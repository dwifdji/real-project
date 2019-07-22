package com._360pai.core.facade.fastway.resp;

import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.facade.disposal.req.City;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-11-26 17:06
 */
@Data
public class DisposeLawOfficeVO implements Serializable {

    private static final long serialVersionUID = 8782015832714293699L;
    /**
     * 律所名称
     */
    private String lawOffice;
    /**
     * 社会信用代码
     */
    private String socialCreditCode;
    /**
     * 营业执照
     */
    private String businessLicense;
    /**
     * 注册城市
     */
    private String registeredCity;
    /**
     * 注册地址
     */
    private String registeredAddress;
    /**
     * 工作城市
     */
    private String workCity;
    /**
     * 工作城市
     */
    private City workCity1;
    /**
     * 工作地址
     */
    private String workAddress;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系人手机号
     */
    private String contactMobile;
    /**
     * 自我介绍
     */
    private String introduction;
    /**
     * 事务所资格证
     */
    private String LawOfficeCredential;
    /**
     * 事务所资格证副本
     */
    private String LawOfficeCredentialCopy;
    /**
     * 	是	string
     */
    private String adminAuthFile;
    /**
     * 10:h5   20:主站
     */
    private String source;
    private Date createTime;
    private String applyStatus;
    private String applyStatusDesc;
    private String remark;
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private Date beginDate;
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private Date endDate;
    private String mobile;
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
}
