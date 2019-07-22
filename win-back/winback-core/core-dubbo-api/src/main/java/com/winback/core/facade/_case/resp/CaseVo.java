package com.winback.core.facade._case.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author RuQ
 * @Title: CaseVo
 * @ProjectName winback
 * @Description:
 * @date 2019/1/22 11:26
 */
@Setter
@Getter
public class CaseVo implements Serializable {

    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     * 案件编号
     */
    private java.lang.String caseId;
    /**
     * 发布者账户id
     */
    private java.lang.Integer accountId;
    /**
     * 原告
     */
    private java.lang.String plaintiff;
    /**
     * 被告
     */
    private java.lang.String defendant;
    /**
     * 承接律师账户id
     */
    private java.lang.Integer lawyerAccountId;
    /**
     * 涉案金额
     */
    private String caseAmount;
    /**
     * 案情描述
     */
    private java.lang.String caseDesc;
    /**
     * 申请人手机号,针对后台录入案件
     */
    private java.lang.String applyMobile;
    /**
     * 申请人姓名
     */
    private java.lang.String applyName;
    /**
     * 身份证号
     */
    private java.lang.String idCardNo;
    /**
     * 申请人类型
     */
    private java.lang.String applyerType;
    /**
     * 合作模式、案件类型id
     */
    private java.lang.Integer caseTypeId;
    /**
     * 案件主状态
     */
    private java.lang.String mainStatus;
    /**
     * 案件子状态，针对诉讼、执行
     */
    private java.lang.String subStatus;
    /**
     * 案件主来源
     */
    private java.lang.String mainSource;
    /**
     * 案件子来源
     */
    private java.lang.String subSource;
    /**
     * 财产线索
     */
    private java.lang.String assetClue;
    /**
     * 签约时间
     */
    private String signContractTime;
    /**
     * 案件阶段
     */
    private java.lang.String casePhase;
    /**
     * 案号
     */
    private java.lang.String caseNo;
    /**
     * 案由id
     */
    private java.lang.Integer caseBriefId;
    /**
     * 案件需求
     */
    private java.lang.String caseDemand;
    /**
     * 管辖法院
     */
    private java.lang.String caseCourt;
    /**
     * 审判长
     */
    private java.lang.String judgePresident;
    /**
     * 审判员
     */
    private java.lang.String judgeOfficer;
    /**
     * 代理审判员
     */
    private java.lang.String proxyJudgeOfficer;
    /**
     * 案件受理费
     */
    private java.math.BigDecimal caseAcceptFee;
    /**
     * 财产保全费
     */
    private java.math.BigDecimal assetProtectFee;
    /**
     * 保全担保费
     */
    private java.math.BigDecimal protectWarrantFee;
    /**
     * 律师费
     */
    private java.math.BigDecimal lawyerFee;
    /**
     * 公证费
     */
    private java.math.BigDecimal notarialFee;
    /**
     * 审计费
     */
    private java.math.BigDecimal auditFee;
    /**
     * 鉴定费
     */
    private java.math.BigDecimal appraiseFee;
    /**
     * 公告费
     */
    private java.math.BigDecimal noticeFee;
    /**
     * 执行费
     */
    private java.math.BigDecimal executeFee;
    /**
     * 调查费
     */
    private java.math.BigDecimal investigateFee;
    /**
     * 其它费用
     */
    private java.math.BigDecimal otherFee;
    /**
     * 总费用
     */
    private java.math.BigDecimal totalFee;
    /**
     * 省code
     */
    private java.lang.String provinceCode;
    /**
     * 市code
     */
    private java.lang.String cityCode;
    /**
     * 区县code
     */
    private java.lang.String areaCode;
    /**
     * 删除标志0 不删除 1删除
     */
    private java.lang.Boolean deleteFlag;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;

    private List<CaseAttachmentVo> attachmentMap;

    private List<CaseRecordVo> caseRecordList;

    private List<CaseRoleVo> caseRoleList;

    /**
     * 合作模式、案件类型
     */
    private String cooperateWay;

    /**
     * 案由
     */
    private String caseReason;

    private String nextStatusName;

    private String nextStatusDesc;

    private Integer nextStatusId;

    private String provinceName;
    private String cityName;
    private String areaName;

    private boolean updateStepButtonFlag;

    private boolean endFlag;

    private String statusDesc;

    private boolean applyAcceptFlag;



    private String unreadNum;//案件沟通的未读字段

    private boolean signContractFlag; // 是否已签合同

}
