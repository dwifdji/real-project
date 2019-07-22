package com._360pai.core.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: OfflineEnterAccountDto
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/4/28 15:38
 */
@Getter
@Setter
public class OfflineEnterAccountDto implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * t_auction_offline_finance id
     */
    private Integer financeId;
    /**
     * 入账金额
     */
    private String amount;
    /**
     * 银行流水单号
     */
    private String bankOrderNo;
    /**
     * 公司银行账户id
     */
    private Integer bankAccountId;
    /**
     * 入账日期
     */
    private String enterDate;

    private String bankAccountNo;
}
