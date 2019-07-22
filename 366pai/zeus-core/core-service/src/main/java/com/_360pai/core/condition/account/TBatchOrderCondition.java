
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年11月29日 15时57分11秒
 */
@Data
public class TBatchOrderCondition implements DaoCondition{

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