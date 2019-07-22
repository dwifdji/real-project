
package com._360pai.core.condition.numberJump;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月28日 14时58分37秒
 */
public class TBankBenchmarkInterestRateCondition implements DaoCondition{

	/**
	 *
	 */
	private java.lang.Integer id;
	/**
	 * 起始日期
	 */
	private java.util.Date startDate;
	/**
	 * 截止日期
	 */
	private java.util.Date endDate;
	/**
	 * 六个月内（含六个月）
	 */
	private java.math.BigDecimal firstSection;
	/**
	 * 六个月至一年内（含一年）
	 */
	private java.math.BigDecimal secondSection;
	/**
	 * 一年至三年（含三年）
	 */
	private java.math.BigDecimal thirdSection;
	/**
	 * 三年至五年（含五年）
	 */
	private java.math.BigDecimal fourthSection;
	/**
	 * 五年以上
	 */
	private java.math.BigDecimal fifthSection;
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
	 * 截止日期
	 */
	public java.util.Date getEndDate(){
		return endDate;
	}

	/**
	 * 截止日期
	 */
	public void setEndDate(java.util.Date endDate){
		this.endDate = endDate;
	}

	/**
	 * 六个月内（含六个月）
	 */
	public java.math.BigDecimal getFirstSection(){
		return firstSection;
	}

	/**
	 * 六个月内（含六个月）
	 */
	public void setFirstSection(java.math.BigDecimal firstSection){
		this.firstSection = firstSection;
	}

	/**
	 * 六个月至一年内（含一年）
	 */
	public java.math.BigDecimal getSecondSection(){
		return secondSection;
	}

	/**
	 * 六个月至一年内（含一年）
	 */
	public void setSecondSection(java.math.BigDecimal secondSection){
		this.secondSection = secondSection;
	}

	/**
	 * 一年至三年（含三年）
	 */
	public java.math.BigDecimal getThirdSection(){
		return thirdSection;
	}

	/**
	 * 一年至三年（含三年）
	 */
	public void setThirdSection(java.math.BigDecimal thirdSection){
		this.thirdSection = thirdSection;
	}

	/**
	 * 三年至五年（含五年）
	 */
	public java.math.BigDecimal getFourthSection(){
		return fourthSection;
	}

	/**
	 * 三年至五年（含五年）
	 */
	public void setFourthSection(java.math.BigDecimal fourthSection){
		this.fourthSection = fourthSection;
	}

	/**
	 * 五年以上
	 */
	public java.math.BigDecimal getFifthSection(){
		return fifthSection;
	}

	/**
	 * 五年以上
	 */
	public void setFifthSection(java.math.BigDecimal fifthSection){
		this.fifthSection = fifthSection;
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