
package com._360pai.core.model.account;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年11月29日 15时57分11秒
 */
@Data
public class TBatchOrder implements java.io.Serializable{
	/**
	 *
	 */
	private java.lang.Long id;
	/**
	 * INIT、LAST_VERIFY_SUCCESS/LAST_VERIFY_FAIL
	 */
	private java.lang.String status;
	/**
	 *
	 */
	private java.lang.String remark;
	/**
	 *
	 */
	private java.lang.Integer initOperatorId;
	/**
	 *
	 */
	private java.lang.Integer verifyOperatorId;
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
