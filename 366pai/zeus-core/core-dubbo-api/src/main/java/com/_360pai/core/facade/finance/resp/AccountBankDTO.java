package com._360pai.core.facade.finance.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2018-10-08 13:16
 */
@Getter
@Setter
@ToString
public class AccountBankDTO implements Serializable {
    private static final long serialVersionUID = 8547082054045283932L;

    private Integer bankId;
    private String bankName;
    private String bankNo;
    private String bankNoSuffix;
}
