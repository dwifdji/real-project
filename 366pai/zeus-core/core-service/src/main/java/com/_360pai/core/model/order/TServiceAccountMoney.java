
package com._360pai.core.model.order;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月30日 11时19分10秒
 */
@Data
public class TServiceAccountMoney implements java.io.Serializable{

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer accountId;
	private Integer partyId;
	/**
	 * 可用金额
	 */
	private java.math.BigDecimal availableAmount;
	/**
	 * 待转账金额
	 */
	private java.math.BigDecimal pendingAmount;
	/**
	 * 已提现金额
	 */
	private java.math.BigDecimal withdrawAmount;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date updateTime;
	/**
	 * 删除标识 0:未删除 1：删除
	 */
	private Boolean delFlag;

}
