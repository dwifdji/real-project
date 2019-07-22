package com._360pai.core.facade.applet.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xdrodger
 * @Title: CalculatorBroadcast
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-29 09:00
 */
@Data
public class CalculatorBroadcast implements Serializable {
    private Integer id;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目经理
     */
    private String projectManager;

    private Boolean closedFlag;
    /**
     * 类型（0 本息计算器 1 债权计算器）
     */
    private String type;
    /**
     * 今日利息
     */
    private BigDecimal todayInterest;
    /**
     * 累计收益
     */
    private BigDecimal estimatedIncome;
    /**
     * 投资收益率
     */
    private BigDecimal annualizedReturn;
    /**
     * 剩余投资收益
     */
    private BigDecimal remainIncome;
    /**
     * 已读标志
     */
    private Boolean readFlag;

}
