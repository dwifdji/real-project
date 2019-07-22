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
public class CaseAdminVo implements Serializable {

    /**
     * 主键
     */
    private Integer id;
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
     * 承接律师账户id
     */
    private Integer lawyerAccountId;
    /**
     * 涉案金额
     */
    private String caseAmount;
    /**
     * 案情描述
     */
    private String caseDesc;
    /**
     * 申请人手机号,针对后台录入案件
     */
    private String applyMobile;
    /**
     * 申请人姓名
     */
    private String applyName;
    /**
     * 身份证号
     */
    private String idCardNo;
    /**
     * 申请人类型
     */
    private String applyerType;
    /**
     * 合作模式、案件类型id
     */
    private Integer caseTypeId;
    /**
     * 案件主状态
     */
    private String mainStatus;
    /**
     * 案件子状态，针对诉讼、执行
     */
    private String subStatus;
    /**
     * 案件主来源
     */
    private String mainSource;
    /**
     * 案件子来源
     */
    private String subSource;
    /**
     * 财产线索
     */
    private String assetClue;
    /**
     * 签约时间
     */
    private String signContractTime;
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
     * 案由大类id
     */
    private Integer caseBigBriefId;
    /**
     * 案件需求
     */
    private String caseDemand;
    /**
     * 管辖法院
     */
    private String caseCourt;
    /**
     * 审判长
     */
    private String judgePresident;
    /**
     * 审判员
     */
    private String judgeOfficer;
    /**
     * 代理审判员
     */
    private String proxyJudgeOfficer;
    /**
     * 案件受理费
     */
    private String caseAcceptFee;
    /**
     * 财产保全费
     */
    private String assetProtectFee;
    /**
     * 保全担保费
     */
    private String protectWarrantFee;
    /**
     * 律师费
     */
    private String lawyerFee;
    /**
     * 公证费
     */
    private String notarialFee;
    /**
     * 审计费
     */
    private String auditFee;
    /**
     * 鉴定费
     */
    private String appraiseFee;
    /**
     * 公告费
     */
    private String noticeFee;
    /**
     * 执行费
     */
    private String executeFee;
    /**
     * 调查费
     */
    private String investigateFee;
    /**
     * 其它费用
     */
    private String otherFee;
    /**
     * 总费用
     */
    private String totalFee;
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
     * 删除标志0 不删除 1删除
     */
    private Boolean deleteFlag;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;

    //private Map<String,AdminCaseInfoVo> statusMap;

    private List<CaseRecordVo> caseRecordList;

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

    private String caseStep;

    private String caseType;

    private String statusDesc;

    private List<String> caseStepList;

    private String provinceName;
    private String cityName;
    private String areaName;
    /**
     * 案件子来源
     */
    private String subSourceDesc;
}
