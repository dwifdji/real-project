package com.winback.core.vo.finance;


import lombok.Data;

import java.io.Serializable;

@Data
public class ReceivableAuditVo implements Serializable {

    private String id;

    private String caseId;

    private String amount;

    private String caseAmount;

    private String cost;

    private String descInfo;

    private String auditor;

    private String status;

    private String statusDesc;

    private String applyDate;

    private String finance;

    private String certificateUrl;

    private String type;




}
