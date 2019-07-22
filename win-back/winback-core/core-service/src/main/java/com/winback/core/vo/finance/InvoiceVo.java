package com.winback.core.vo.finance;


import lombok.Data;

import java.io.Serializable;

@Data
public class InvoiceVo implements Serializable {

    private String id;

    private String caseNo;

    private String caseId;


    private String accuser;

    private String accused;

    private String amount;

    private String desc;

    private String expendDate;

    private String auditor;

    private String status;

    private String statusDesc;

    private String type;

    private String invoiceNo;

    private String comName;

}
