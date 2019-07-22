package com._360pai.core.facade.activity.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: AuctionOfflineEnterAccountReq
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/4/28 15:57
 */
@Getter
@Setter
public class AuctionOfflineEnterAccountReq implements Serializable {
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
}
