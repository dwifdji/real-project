
package com._360pai.core.model.numberJump;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年04月28日 17时59分07秒
 */
@Data
public class TDebtCalculatorBroadcast implements java.io.Serializable{
	/**
	 *
	 */
	private java.lang.Integer id;
	/**
	 * 计算器表id
	 */
	private java.lang.Integer calculatorId;
	/**
	 * 播报日期
	 */
	private java.util.Date recordDate;
	/**
	 * 天数
	 */
	private java.lang.Integer days;
	/**
	 * 投资年化收益率
	 */
	private java.math.BigDecimal annualizedReturn;
	/**
	 * 预估收益
	 */
	private java.math.BigDecimal income;
	/**
	 * 已读标志（0 否 1 是）
	 */
	private java.lang.Boolean readFlag;
	/**
	 * 是否删除（0 否 1 是）
	 */
	private java.lang.Boolean isDelete;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
}
