package com._360pai.core.facade.account.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author RuQ
 * @Title: AccountBaseDto
 * @ProjectName zeus-core
 * @Description:
 * @date 2018/9/4 10:41
 */
@Getter
@Setter
public class AccountBaseDto implements Serializable {
    private Integer accountId;
    private String mobile;
    private String name;
    private Integer defaultAgencyId;
    private String fadadaId;
    private String fadadaEmail;
    private String dfftId;
    private boolean isPayBind;
    private boolean isChannel;
    //party表主键 -1 不存在
    private Integer partyPrimaryId;
    private String type;
    private String accountAuthTime;
    private boolean isAccountAuth;
    private boolean isBank;
    private Integer bankId;
    private String bankCode;
    private String bankName;
    private String bankAccountNo;  //卡号
    private String bankAccountName;//开户名
    private String idOrLicenceNo;
    private boolean isFunder;
    private boolean isDisposer;

    private String disposerStatus;//处置服务商申请状态

    private boolean fromApplet;

    private Integer parentId;
    private String parentType;
    private Integer pparentId;
    private String pparentType;

    /**
     * 允许发布线下操作拍品 0 否 1 是
     */
    private java.lang.Boolean operOffline;
    /**
     * 是否允许未开通法大大下发布预招商 0 否 1 是
     */
    private java.lang.Boolean operWithoutFadada;
    private String address;
    private String legalPerson;

    private BigDecimal shopCommissionPercent;


    private String branchBankName;//银行的分支名称

}
