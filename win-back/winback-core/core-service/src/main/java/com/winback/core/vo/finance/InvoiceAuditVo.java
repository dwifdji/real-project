package com.winback.core.vo.finance;


import lombok.Data;

import java.io.Serializable;

@Data
public class InvoiceAuditVo implements Serializable {

    private String id;

    private String caseId;

    private String type;

    private String amount;

    private String comName;

    private String dutyNo;

    private String descInfo;

    private String auditor;

    private String status;

    private String statusDesc;

    private String invoiceDate;

    private String applyDate;

    private String finance;

    private String certificateUrl;

    private String phone;

}
