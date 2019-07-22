package com._360pai.core.facade.finance.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-10-09 09:19
 */
@Getter
@Setter
@ToString
public class AdminWithdrawRecordDTO implements Serializable {

    private static final long serialVersionUID = 6721041104865245283L;

    private String accountName;
    private String bankNo;
    private Date createTime;
    private Integer withdrawId;
    private String withdrawNo;
    private BigDecimal withdrawAmount;
    private String verifyStatus;
    private String verifyStatusDesc;
    private String verifyContent;
    private String verifyOperatorName;
 }
