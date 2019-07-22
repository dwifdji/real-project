package com._360pai.core.facade.disposal.resp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2018-09-20 20:22
 */
@Getter
@Setter
@ToString
public class BiddingProgramInfo implements Serializable {
    private static final long serialVersionUID = -6485641607853668787L;

    private Integer biddingId;
    private String bidNo;
    private String companyName;
    private String bidCost;
    private String bidStatus;
    private String specialDescription;
    private String requireDescription;
    @JSONField(serialize = false)
    private Integer partyId;
    @JSONField(serialize = false)
    private Integer accountId;
}
