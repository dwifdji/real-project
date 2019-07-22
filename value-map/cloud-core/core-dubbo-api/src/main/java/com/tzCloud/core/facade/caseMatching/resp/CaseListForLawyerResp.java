package com.tzCloud.core.facade.caseMatching.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/3/13 13:47
 */
@Data
public class CaseListForLawyerResp implements Serializable {
    private String docId;
    private String caseTitle;
    /**
     * 审批程序
     */
    private String caseProgram;
    private String courtName;
    private String caseNo;
    private String caseDate;
    private String brief;
    private String type;
    private String judgementType;
}
