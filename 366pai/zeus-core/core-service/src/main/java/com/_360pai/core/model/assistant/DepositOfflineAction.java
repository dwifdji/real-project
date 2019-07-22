
package com._360pai.core.model.assistant;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年10月03日 14时24分09秒
 */
@Getter
@Setter
public class DepositOfflineAction implements java.io.Serializable{

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
