
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年10月03日 14时24分09秒
 */
@Getter
@Setter
public class DepositOfflineActionCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 行为（ PAYMENT：付款，REFUND退款）
	 */
	private String action;
	/**
	 * 
	 */
	private Long depositId;
	/**
	 * 凭证
	 */
	private String voucher;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 操作员
	 */
	private Integer operatorId;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;

}