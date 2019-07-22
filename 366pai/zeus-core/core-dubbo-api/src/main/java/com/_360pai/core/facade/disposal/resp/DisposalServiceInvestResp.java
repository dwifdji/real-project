package com._360pai.core.facade.disposal.resp;

import com._360pai.core.facade.disposal.req.City;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-09-20 15:38
 */
@Setter
@Getter
@ToString
public class DisposalServiceInvestResp implements Serializable {
    private static final long serialVersionUID = 6376021995437248713L;

    private String disposalNo;
    private String[] cityId;
    private Date publishTime;
    private String disposalStatus;
    private String disposalStatusDesc;
    private String disposalType;
    private String disposalTypeDesc;
    private Double period;
    private String cost;
    private Integer biddingNum;
    private Integer disposalId;
    private String disposalName;
    private JSONObject extra;
    private String extra1;
    private City[] providerAreas;
    private Integer        viewNum;
    private BigDecimal debtInterest;
    private BigDecimal debtPrincipal;
    private String cityName;
}
