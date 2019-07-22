
package com.winback.core.model.contract;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
@Data
public class TContractOrder implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 账户id
	 */
	private Integer accountId;
	/**
	 * 订单编号
	 */
	private java.lang.String orderNo;
	/**
	 * 支付的金额
	 */
	private java.math.BigDecimal amount;
	/**
	 * 支付类型（1 支付宝 2 微信）
	 */
	private java.lang.String payType;
	/**
	 * 支付的状态0 初始状态  1 支付成功 2 支付失败
	 */
	private Integer payStatus;
	/**
	 * 支付截止时间
	 */
	private java.util.Date payDeadline;
	/**
	 * 订单来源（APP：app应用，APPLET：小程序）
	 */
	private java.lang.String orderSource;
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
