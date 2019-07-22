package com._360pai.core.facade.applet.vo;

import com._360pai.arch.common.utils.DateUtil;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * @author xdrodger
 * @Title: PrincipalInterestCalculator
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-29 18:28
 */
@Data
public class PrincipalInterestCalculator implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 贷款期限
     */
    private java.math.BigDecimal loanPeriod;
    /**
     * 剩余本金
     */
    private java.math.BigDecimal remainPrincipal;
    /**
     * 浮动利率
     */
    private java.math.BigDecimal fluctuationRate;
    /**
     * 欠息
     */
    private java.math.BigDecimal debtInterest;
    /**
     * 逾期时间开始
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private java.util.Date overdueStart;
    @JSONField(format= DateUtil.DOT_YYYY_DATE_PATTERN)
    private java.util.Date overdueStartShow;
    /**
     * 逾期时间结束
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private java.util.Date overdueEnd;
    @JSONField(format= DateUtil.DOT_YYYY_DATE_PATTERN)
    private java.util.Date overdueEndShow;
    /**
     * 罚息比例
     */
    private java.math.BigDecimal penaltyRate;
    /**
     * 基准利率来源
     */
    private String benchmarkInterestRateSource;
    /**
     * 延迟履行期开始
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private java.util.Date delayPerformanceStart;
    @JSONField(format= DateUtil.DOT_YYYY_DATE_PATTERN)
    private java.util.Date delayPerformanceStartShow;
    /**
     * 延迟履行期结束
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private java.util.Date delayPerformanceEnd;
    @JSONField(format= DateUtil.DOT_YYYY_DATE_PATTERN)
    private java.util.Date delayPerformanceEndShow;
    /**
     * 逾期利息
     */
    private java.math.BigDecimal overdueInterest;
    /**
     * 延迟履行金
     */
    private java.math.BigDecimal delayPerformanceAmount;
    /**
     * 总费用合计
     */
    private java.math.BigDecimal totalCost;
    /**
     * 是否支付（0 否 1 是）
     */
    private Boolean payFlag;

    /**
     * 本息总计
     */
    private BigDecimal principalAndInterestTotal;

    private String province;
    private String city;
    private String area;
    private String projectManager;
    private Boolean closedFlag;

    private List<PrincipalInterestCalculatorDetail> list = Collections.EMPTY_LIST;
}
