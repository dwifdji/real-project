
package com._360pai.core.model.assistant;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分26秒
 */
@Getter
@Setter
public class Deposit implements java.io.Serializable{

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
