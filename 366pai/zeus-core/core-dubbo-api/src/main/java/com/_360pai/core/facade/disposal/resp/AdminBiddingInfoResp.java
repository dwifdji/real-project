package com._360pai.core.facade.disposal.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-09-21 10:42
 */
@Getter
@Setter
@ToString
public class AdminBiddingInfoResp implements Serializable {
    private static final long serialVersionUID = -5213858749178371376L;

    private Integer biddingId;
    private String bidNo;
    private String companyName;
    private String bidCost;
    private String bidStatus;
    private String specialDescription;
    private String requireDescription;
    private String contactName;
    private String contactMobile;
    private Date   createTime;
}
