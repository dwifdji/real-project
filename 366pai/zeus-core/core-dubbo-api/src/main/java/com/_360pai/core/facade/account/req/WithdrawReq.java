package com._360pai.core.facade.account.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author RuQ
 * @Title: WithdrawReq
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/12/3 15:21
 */
@Getter
@Setter
public class WithdrawReq implements Serializable {

    private Integer partyId;
    private String type;
    private BigDecimal amount;
    private Integer bankAccountId;


    private Long acctDetailId;
    private Integer operatorId;
    private String verifyRemark;
    private String status;

    private String invoiceType;
    private String imgUrl;
    private String logisticsCompany;
    private String logisticsNo;
    private String invoiceCode;
    private String invoiceNo;

    private String batchOrderNo;

    private Integer payBankAccountId;
    private String  bankDetailNo;
    private String payTime;

    private List<String> detailIdList;

    private Integer refId;

    private String smsCode;

}
