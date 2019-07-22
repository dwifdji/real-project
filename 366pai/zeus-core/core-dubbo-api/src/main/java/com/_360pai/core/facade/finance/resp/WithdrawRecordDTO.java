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
public class WithdrawRecordDTO implements Serializable {
    private static final long serialVersionUID = 8371290998937230910L;

    private Integer withdrawId;
    private String withdrawNo;
    private BigDecimal withdrawAmount;
    private Date verifyTime;
    private String verifyStatus;
    private String verifyStatusDesc;
    private String verifyContent;
 }
