package com.winback.core.dto.finance;


import lombok.Data;

import java.io.Serializable;

@Data
public class ReceivableDto implements Serializable {

    private String caseNo;
    private String customer;
    private String receivableBeginAt;
    private String receivableEndAt;
    private String mode;
    private String type;
    private String status;


}
