package com._360pai.core.facade.fastway.resp;

import com._360pai.core.facade.disposal.req.City;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-11-26 17:06
 */
@Data
public class DisposeLawyerVO implements Serializable {

    private static final long serialVersionUID = 1094621022271071163L;
    /**
     * 身份证姓名
     */
    private String cardName;
    /**
     * 	身份证号码
     */
    private String cardNo;
    /**
     * 	身份证正面照
     */
    private String cardImg1;
    /**
     * 	身份证反面照
     */
    private String cardImg2;
    /**
     * 常驻城市
     */
    private String residentCity;
    /**
     * 常驻城市
     */
    private City residentCity1;
    /**
     * 	联系地址
     */
    private String contactAddress;
    /**
     * 	手机号
     */
    private String contactMobile;
    /**
     * 	所属律所
     */
    private String lawOffice;
    /**
     * 	工作年限
     */
    private String workedYear;
    /**
     * 	自我介绍
     */
    private String introduction;
    /**
     * 	律师资格证
     */
    private String LawyerCredential;
    /**
     * 	过往案例
     */
    private String pastCases;
    /**
     * 10:h5   20:主站
     */
    private String source;
    private Date createTime;
    private String applyStatus;
    private String applyStatusDesc;
    private String cardType;
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
