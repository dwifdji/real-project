
package com._360pai.core.condition.numberJump;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月28日 17时59分07秒
 */
@Data
public class TPrincipalInterestCalculatorBroadcastCondition implements DaoCondition{
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