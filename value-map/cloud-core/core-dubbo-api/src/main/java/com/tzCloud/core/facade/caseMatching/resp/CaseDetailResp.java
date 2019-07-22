package com.tzCloud.core.facade.caseMatching.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/2/26 10:48
 */
@Data
public class CaseDetailResp implements Serializable {
    private String docId;

    private String caseTitle;
    /**
     * 审理法院
     */
    private String courtName;
    /**
     * 案号
     */
    private String caseNo;
    /**
     * 案件类型
     */
    private String caseType;
    /**
     * 案由
     */
    private String brief;
    /**
     * 裁判日期
     */
    private String judgementDate;
    /**
     * 审理程序--二审
     */
    private String trialProcessType;
    /**
     * 上诉人
     */
    private String prosecutor;

    /**
     * 被上诉人
     */
    private String defendant;
    /**
     * 上诉人代理律师
     */
    private String prosecutorLawyer;
    /**
     * 被上诉人代理律师
     */
    private String defendantLawyer;
    /**
     * 文书性质--裁定
     */
    private String documentNature;

    /**
     * 正文
     */
    private String caseMainBody;

    /**
     * 合议庭
     */
    private String collegialPanel;

    private List<Map<String,Object>> prosecutorLawyerList;
    private List<Map<String,Object>> defendantLawyerList;
    private Map<String, List<String>> container;
}
