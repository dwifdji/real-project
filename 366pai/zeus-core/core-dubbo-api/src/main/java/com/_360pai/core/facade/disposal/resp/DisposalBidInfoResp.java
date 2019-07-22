package com._360pai.core.facade.disposal.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2018-09-20 19:51
 */
@Getter
@Setter
@ToString
public class DisposalBidInfoResp implements Serializable {
    private static final long serialVersionUID = -4601522338854242710L;

    private Integer biddingId;
    private String  registerAddress;
    private String  companyName;
    private String  bidCost;
    private String  contactName;
    private String  bidStatus;
    private String  providerType;

}
