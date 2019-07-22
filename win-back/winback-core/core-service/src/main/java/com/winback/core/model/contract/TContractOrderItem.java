
package com.winback.core.model.contract;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
@Data
public class TContractOrderItem implements java.io.Serializable{

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
