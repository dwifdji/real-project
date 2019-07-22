package com._360pai.core.facade.finance.resp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-10-03 14:24
 */
@Getter
@Setter
@ToString
public class AdjustedRecordDTO implements Serializable {
    private static final long serialVersionUID = 982369249934469975L;
    private Integer adjustedId;
    private BigDecimal adjustedAmount;
    private BigDecimal amount;
    private String orderNum;
    private String partyName;
    private Integer orderType;
    private String orderTypeDesc;
    private Date orderTime;
}
