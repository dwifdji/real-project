
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分12秒
 */
@Getter
@Setter
public class AccountCompanyMapCondition implements DaoCondition{
	/**
	 *
	 */
	private java.lang.Integer id;
	/**
	 *
	 */
	private java.lang.Integer accountId;
	/**
	 *
	 */
	private java.lang.Integer companyId;
	/**
	 *
	 */
	private java.lang.String name;
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