package com._360pai.core.facade.applet.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xdrodger
 * @Title: DebtCalculator
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-29 10:36
 */
@Data
public class DebtCalculator implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 债权本金
     */
    private java.math.BigDecimal debtPrincipal;
    /**
     * 债权利息
     */
    private java.math.BigDecimal debtInterest;
    /**
     * 债权最高额
     */
    private java.math.BigDecimal maximumDebt;
    /**
     * 清收目标金额
     */
    private java.math.BigDecimal liquidationAmount;
    /**
     * 转让价格
     */
    private java.math.BigDecimal transferAmount;
    /**
     * 自有资金GP
     */
    private java.math.BigDecimal gpAmount;
    /**
     * 需要配资LP金额
     */
    private java.math.BigDecimal lpAmount;
    /**
     * 配资年化费率
     */
    private java.math.BigDecimal withFundingAnnualizedRate;
//    /**
//     * 配资周期开始
//     */
//    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
//    private java.util.Date withFundingPeriodStart;
//    @JSONField(format= DateUtil.DOT_YYYY_DATE_PATTERN)
//    private java.util.Date withFundingPeriodStartShow;
//    /**
//     * 配资周期结束
//     */
//    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
//    private java.util.Date withFundingPeriodEnd;
//    @JSONField(format= DateUtil.DOT_YYYY_DATE_PATTERN)
//    private java.util.Date withFundingPeriodEndShow;
    private BigDecimal withFundingPeriod;
    /**
     * 处置成本-年化费率
     */
    private java.math.BigDecimal disposalAnnualizedRate;
    /**
     * 处置周期
     */
    private java.math.BigDecimal disposalPeriod;
    /**
     * 投资年化收益率
     */
    private java.math.BigDecimal annualizedReturn;
    /**
     * 预估收益
     */
    private java.math.BigDecimal estimatedIncome;
    /**
     * 配资成本
     */
    private java.math.BigDecimal withFundingAmount;
    /**
     * 处置成本
     */
    private java.math.BigDecimal disposalAmount;
    /**
     * 尽调成本
     */
    private java.math.BigDecimal dueDiligenceAmount;
    /**
     * 杂费
     */
    private java.math.BigDecimal miscAmount;
    /**
     * 投入成本总计
     */
    private java.math.BigDecimal totalCost;
    /**
     * 是否支付（0 否 1 是）
     */
    private Boolean payFlag;

    private String province;
    private String city;
    private String area;
    private String projectManager;
    private Boolean closedFlag;
}
