package com._360pai.core.facade.activity.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author RuQ
 * @Title: AuctionReq
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/6 9:55
 */
@Setter
@Getter
public class AuctionReq implements Serializable{
    private Integer partyId;
    private Integer activityId;
    private String agencyCode;
    private Long orderId;
    private BigDecimal bidAmount;
    private String contractId;
    private String bankName;
    private String bankAccountName;
    private String bankAccountNumber;
    private Boolean yxBuyFlag;
}
