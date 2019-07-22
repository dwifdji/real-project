
package com._360pai.core.condition.numberJump;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月28日 14时58分37秒
 */
public class TPrincipalInterestCalculatorDetailCondition implements DaoCondition{

	/**
	 *
	 */
	private java.lang.Integer id;
	/**
	 * 计算器表id
	 */
	private java.lang.Integer calculatorId;
	/**
	 *  0 逾期明细 1 延迟履行明细
	 */
	private java.lang.String type;
	/**
	 * 起始日期
	 */
	private java.util.Date startDate;
	/**
	 * 终止日期
	 */
	private java.util.Date endDate;
	/**
	 * 基准利率
	 */
	private java.math.BigDecimal benchmarkInterestRate;
	/**
	 * 实际利率
	 */
	private java.math.BigDecimal actualInterestRate;
	/**
	 * 利息
	 */
	private java.math.BigDecimal interest;
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

	/**
	 *
	 */
	public java.lang.Integer getId(){
		return id;
	}

	/**
	 *
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}

	/**
	 * 计算器表id
	 */
	public java.lang.Integer getCalculatorId(){
		return calculatorId;
	}

	/**
	 * 计算器表id
	 */
	public void setCalculatorId(java.lang.Integer calculatorId){
		this.calculatorId = calculatorId;
	}

	/**
	 *  0 逾期明细 1 延迟履行明细
	 */
	public java.lang.String getType(){
		return type;
	}

	/**
	 *  0 逾期明细 1 延迟履行明细
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}

	/**
	 * 起始日期
	 */
	public java.util.Date getStartDate(){
		return startDate;
	}

	/**
	 * 起始日期
	 */
	public void setStartDate(java.util.Date startDate){
		this.startDate = startDate;
	}

	/**
	 * 终止日期
	 */
	public java.util.Date getEndDate(){
		return endDate;
	}

	/**
	 * 终止日期
	 */
	public void setEndDate(java.util.Date endDate){
		this.endDate = endDate;
	}

	/**
	 * 基准利率
	 */
	public java.math.BigDecimal getBenchmarkInterestRate(){
		return benchmarkInterestRate;
	}

	/**
	 * 基准利率
	 */
	public void setBenchmarkInterestRate(java.math.BigDecimal benchmarkInterestRate){
		this.benchmarkInterestRate = benchmarkInterestRate;
	}

	/**
	 * 实际利率
	 */
	public java.math.BigDecimal getActualInterestRate(){
		return actualInterestRate;
	}

	/**
	 * 实际利率
	 */
	public void setActualInterestRate(java.math.BigDecimal actualInterestRate){
		this.actualInterestRate = actualInterestRate;
	}

	/**
	 * 利息
	 */
	public java.math.BigDecimal getInterest(){
		return interest;
	}

	/**
	 * 利息
	 */
	public void setInterest(java.math.BigDecimal interest){
		this.interest = interest;
	}

	/**
	 * 是否删除（0 否 1 是）
	 */
	public java.lang.Boolean getIsDelete(){
		return isDelete;
	}

	/**
	 * 是否删除（0 否 1 是）
	 */
	public void setIsDelete(java.lang.Boolean isDelete){
		this.isDelete = isDelete;
	}

	/**
	 * 创建时间
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}

	/**
	 * 创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	/**
	 * 更新时间
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}

	/**
	 * 更新时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}
}