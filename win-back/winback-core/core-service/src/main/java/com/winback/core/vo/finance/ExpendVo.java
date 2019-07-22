package com.winback.core.vo.finance;



import lombok.Data;
import java.io.Serializable;

@Data
public class ExpendVo implements Serializable {

    private String id;

    private String caseId;

    private String caseNo;

    private String accuser;

    private String accused;

    private String amount;

    private String caseDesc;

    private String expendDate;

    private String auditor;

    private String status;

    private String statusDesc;


    private String acctBankName;
}
