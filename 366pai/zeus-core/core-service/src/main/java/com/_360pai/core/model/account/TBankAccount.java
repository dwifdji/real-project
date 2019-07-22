
package com._360pai.core.model.account;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年11月29日 15时57分11秒
 */
@Data
public class TBankAccount implements java.io.Serializable{

	/**
	 *
	 */
	private java.lang.Integer id;
	/**
	 *
	 */
	private java.lang.Integer acctId;
	/**
	 *
	 */
	private java.lang.String bankCode;
	/**
	 * 用户自定义银行名称
	 */
	private java.lang.String bankName;
	/**
	 * 卡号
	 */
	private java.lang.String bankAccountNo;
	/**
	 * 开户人
	 */
	private java.lang.String bankAccountName;
	/**
	 * 支行名称
	 */
	private java.lang.String subBankName;
	/**
	 *
	 */
	private java.lang.Boolean isDelete;
	/**
	 *
	 */
	private java.util.Date createTime;
	/**
	 *
	 */
	private java.util.Date updateTime;
}
