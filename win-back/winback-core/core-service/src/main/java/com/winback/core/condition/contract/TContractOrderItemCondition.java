
package com.winback.core.condition.contract;

import com.winback.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
@Data
public class TContractOrderItemCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 订单id
	 */
	private Integer orderId;
	/**
	 * 合同id
	 */
	private Integer contractId;
	/**
	 * 支付的金额
	 */
	private java.math.BigDecimal amount;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
}