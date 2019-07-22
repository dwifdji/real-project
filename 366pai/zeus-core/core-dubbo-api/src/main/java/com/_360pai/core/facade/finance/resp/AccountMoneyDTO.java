package com._360pai.core.facade.finance.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xiaolei
 * @create 2018-10-03 13:24
 */
@Getter
@Setter
@ToString
public class AccountMoneyDTO implements Serializable {
    private static final long serialVersionUID = -7562874689144660338L;
    // 可用金额
    private BigDecimal availableAmount;
    // 待转账金额
    private BigDecimal pendingAmount;
    // 提现金额
    private BigDecimal withdrawAmount;
}
