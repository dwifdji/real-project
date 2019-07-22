
package com._360pai.core.model.numberJump;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年04月28日 14时58分37秒
 */
@Data
public class TBankBenchmarkInterestRate implements java.io.Serializable{

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


	public BigDecimal getRate(BigDecimal loanPeriod2) {
		double loanPeriod = Math.ceil((loanPeriod2).floatValue());
		if (loanPeriod <= 6) {
			return this.firstSection;
		} else if (loanPeriod > 6 && loanPeriod <= 12) {
			return secondSection;
		} else if (loanPeriod > 12 && loanPeriod <= 36) {
			return thirdSection;
		} else if (loanPeriod > 36 && loanPeriod <= 60) {
			return fourthSection;
		} else {
			return fifthSection;
		}
	}
}
