package com.winback.core.facade._case.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: Case
 * @ProjectName winback
 * @Description:
 * @date 2019/1/28 13:19
 */
@Data
public class Case implements Serializable {
    /**
     * 案件编号
     */
    private String caseId;
    /**
     * 发布者账户id
     */
    private Integer accountId;
    /**
     * 原告
     */
    private String plaintiff;
    /**
     * 被告
     */
    private String defendant;
    /**
     * 涉案金额
     */
    private String caseAmount;
    /**
     * 案情描述
     */
    private String caseDesc;
    /**
     * 合作模式、案件类型id
     */
    private Integer caseTypeId;
    /**
     * 合作模式、案件类型id
     */
    private String caseTypeName;
    /**
     * 案件主状态
     */
    private String mainStatus;
    /**
     * 案件子状态，针对诉讼、执行
     */
    private String subStatus;
    /**
     * 案件阶段
     */
    private String casePhase;
    /**
     * 案号
     */
    private String caseNo;
    /**
     * 案由id
     */
    private Integer caseBriefId;
    /**
     * 案由id
     */
    private String caseBriefName;
    /**
     * 案件需求
     */
    private String caseDemand;
    /**
     * 管辖法院
     */
    private String caseCourt;
    /**
     * 省code
     */
    private String provinceCode;
    /**
     * 市code
     */
    private String cityCode;
    /**
     * 区县code
     */
    private String areaCode;
    /**
     * 创建时间
     */
    private java.util.Date createTime;

    private String caseReason;

    private String provinceName;
    private String cityName;
    private String areaName;

    private String statusDesc;

    private Date allocatedTime;

    private boolean endFlag;
}
