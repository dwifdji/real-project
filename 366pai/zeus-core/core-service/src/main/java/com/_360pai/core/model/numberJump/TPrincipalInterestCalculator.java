
package com._360pai.core.model.numberJump;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年04月28日 17时06分48秒
 */
@Data
public class TPrincipalInterestCalculator implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 外部绑定表id
	 */
	private Integer extBindId;
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
	private java.util.Date overdueStart;
	/**
	 * 逾期时间结束
	 */
	private java.util.Date overdueEnd;
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
	private java.util.Date delayPerformanceStart;
	/**
	 * 延迟履行期结束
	 */
	private java.util.Date delayPerformanceEnd;
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
