
package com._360pai.core.condition.numberJump;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月28日 17时06分48秒
 */
@Data
public class TDebtCalculatorCondition implements DaoCondition{
	/**
	 *
	 */
	private Integer              id;
	/**
	 * 外部绑定表id
	 */
	private Integer              extBindId;
	/**
	 * 项目名称
	 */
	private String               projectName;
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
//	/**
//	 * 配资周期开始
//	 */
//	private java.util.Date       withFundingPeriodStart;
	/**
	 * 配资周期结束
	 */
	private BigDecimal           withFundingPeriod;
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
	/**
	 * 支付金额
	 */
	private java.math.BigDecimal payAmount;
	/**
	 * 是否删除（0 否 1 是）
	 */
	private Boolean isDelete;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

	private String projectManager;
	private String province;
	private String city;
	private String area;
	private Boolean closedFlag;
}