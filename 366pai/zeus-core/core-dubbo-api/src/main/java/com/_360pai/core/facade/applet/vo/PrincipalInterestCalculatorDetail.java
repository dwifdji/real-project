package com._360pai.core.facade.applet.vo;

import com._360pai.arch.common.utils.DateUtil;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: PrincipalInterestCalculatorDetail
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-29 18:32
 */
@Data
public class PrincipalInterestCalculatorDetail implements Serializable {
    /**
     *
     */
    private java.lang.Integer id;
    /**
     * 计算器表id
     */
    private java.lang.Integer calculatorId;
    /**
     *  0 逾期明细 1 延迟履行明细
     */
    private java.lang.String type;
    /**
     * 起始日期
     */
    @JSONField(format= DateUtil.DOT_YYYY_DATE_PATTERN)
    private java.util.Date startDate;
    /**
     * 终止日期
     */
    @JSONField(format= DateUtil.DOT_YYYY_DATE_PATTERN)
    private java.util.Date endDate;
    /**
     * 基准利率
     */
    private java.math.BigDecimal benchmarkInterestRate;
    /**
     * 实际利率
     */
    private java.math.BigDecimal actualInterestRate;
    /**
     * 利息
     */
    private java.math.BigDecimal interest;
}
