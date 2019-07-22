
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
@Getter
@Setter
public class DepositCondition implements DaoCondition{

	/**
	 * 
	 */
	private Long id;
	/**
	 * 
	 */
	private Integer activityId;
	/**
	 * 
	 */
	private Integer partyId;
	/**
	 * 
	 */
	private java.math.BigDecimal amount;
	/**
	 * 
	 */
	private String status;
	/**
	 * 
	 */
	private Integer agencyId;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private String code;

	private Date updateTime;

	private String payType;

	private String remark;
	private String payId;
	/**
	 * 开户银行
	 */
	private java.lang.String bankName;
	/**
	 * 银行卡号
	 */
	private java.lang.String bankAccountNumber;
	/**
	 * 开户名称
	 */
	private java.lang.String bankAccountName;

	private String askResult;

	private Integer level;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private java.lang.Boolean deleteFlag;
}