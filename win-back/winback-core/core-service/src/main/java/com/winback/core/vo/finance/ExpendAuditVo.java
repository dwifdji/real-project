package com.winback.core.vo.finance;


import lombok.Data;

import java.io.Serializable;

@Data
public class ExpendAuditVo implements Serializable {

    private String id;

    private String caseId;

    private String amount;

    private String acctNo;

    private String acctBankName;

    private String descInfo;

    private String auditor;

    private String status;

    private String applyDate;

    private String loanDate;

    private String finance;

    private String certificateUrl;

    private String acctName;


}
