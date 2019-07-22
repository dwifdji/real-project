package com._360pai.core.facade.applet.vo;

import com._360pai.arch.common.utils.DateUtil;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: PrincipalInterestCalculatorBroadcast
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-30 15:29
 */
@Data
public class PrincipalInterestCalculatorBroadcast implements Serializable {
    /**
     *
     */
    private java.lang.Integer id;
    /**
     * 计算器表id
     */
    private java.lang.Integer calculatorId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 播报日期
     */
    @JSONField(format= DateUtil.DOT_YYYY_DATE_PATTERN)
    private java.util.Date recordDate;
    /**
     * 天数
     */
    private java.lang.Integer days;
    /**
     * 今日利息
     */
    private java.math.BigDecimal todayInterest;
    /**
     * 累计收益
     */
    private java.math.BigDecimal income;
    /**
     * 已读标志（0 否 1 是）
     */
    private java.lang.Boolean readFlag;
}
