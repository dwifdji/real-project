package com.winback.core.vo.finance;


import lombok.Data;

import java.io.Serializable;

@Data
public class ReceivableVo implements Serializable {

    private String id;

    private String caseNo;

    private String accuser;

    private String accused;

    private String amount;

    private String descInfo;

    private String receivableDate;

    private String auditor;

    private String status;

    private String statusDesc;

    private String type;

    private String caseId;



}
