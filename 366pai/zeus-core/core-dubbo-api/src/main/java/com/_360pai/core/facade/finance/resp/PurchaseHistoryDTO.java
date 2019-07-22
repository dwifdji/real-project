package com._360pai.core.facade.finance.resp;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-10-03 07:14
 */
@Setter
@Getter
@ToString
public class PurchaseHistoryDTO implements Serializable {
    private String     orderNum;
    private String     assetName;
    private Integer    orderType;
    private String     orderTypeDesc;
    private BigDecimal amount;
    private Date       createTime;
    private String[]   tuneReportUrl;
}
