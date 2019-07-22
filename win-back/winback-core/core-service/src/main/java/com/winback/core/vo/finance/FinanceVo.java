package com.winback.core.vo.finance;


import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.List;

@Data
public class FinanceVo implements Serializable {

    //总记录数
    private long    total;
    //结果集
    private List<T> list;

    private String totalAmount;


}
