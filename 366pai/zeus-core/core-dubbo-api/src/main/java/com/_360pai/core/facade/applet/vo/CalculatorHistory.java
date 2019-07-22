package com._360pai.core.facade.applet.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xdrodger
 * @Title: CalculatorHistory
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-28 19:48
 */
@Data
public class CalculatorHistory implements Serializable {
    private Integer    id;
    /**
     * 项目名称
     */
    private String     projectName;
    /**
     * 项目经理
     */
    private String     projectManager;
    /**
     * 类型（0 本息计算器 1 债权计算器）
     */
    private String     type;
    /**
     * 成本小计/ 利息总计
     */
    private BigDecimal totalCost;
    /**
     * 实际收益金额
     */
    private BigDecimal estimatedIncome;
    /**
     * 劣后资金年收益率
     */
    private BigDecimal annualizedReturn;
    /**
     * 处置周期
     */
    private BigDecimal disposalPeriod;

    private Boolean closedFlag;
}
