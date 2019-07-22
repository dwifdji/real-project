package com._360pai.core.facade.account.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: WithdrawDetailVo
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/12/3 16:52
 */
@Getter
@Setter
public class WithdrawDetailVo implements Serializable {

    private String indexNo;
    private String detailOrderNo;
    private String userType;
    private String userName;
    private String registMobile;
    private String bankAccountNo;
    private String bankName;
    private String subBankName;
    private String invoiceCode;
    private String invoiceNum;
    private String amount;
    private Integer refId;

    private String payStatus;
    private String payAdminUserName;
    private String payTime;
    private String payBankDetailNo;
    private String payBankName;
    private String payBankAccountNo;

    private String hcAdmiUserName;
    private String hcTime;
    private String hcReason;

    private String downloadUrl;
    private String status;

    private String invoiceType;
    private String invoiceImgUrl;
    private String invoiceLogisticsCompany;
    private String invoiceLogisticsNo;

    private String applyTime;
    /**
     * 拒绝原因
     */
    private String refuseReason;


}
