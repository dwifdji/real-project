package com._360pai.core.facade.disposal.resp;

import com._360pai.core.facade.disposal.req.City;
import com._360pai.core.facade.disposal.req.DisposalRequirementReq;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-09-20 15:23
 */
@Getter
@Setter
@ToString
public class HotRequirementResp implements Serializable {
    private static final long serialVersionUID = -8393180190781643536L;

    private Integer disposalId;
    private String  disposalType;
    private String  disposalName;
    private String cityId;
    private Date    publishTime;
    private Integer viewNum;
    private Integer biddingNum;
    private City[] providerAreas;
    private String cityName;
}
