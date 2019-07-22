package com._360pai.core.facade.applet.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/5/27 20:16
 */
@Data
public class CalculatorRelativeVo implements Serializable {
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
     * 利息总计
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