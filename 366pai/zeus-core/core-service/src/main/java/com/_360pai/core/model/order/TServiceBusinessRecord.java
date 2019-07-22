
package com._360pai.core.model.order;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年10月08日 19时37分27秒
 */
@Data
public class TServiceBusinessRecord implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 买方partyId
	 */
	private Integer buyerAccountId;
	private Integer buyerPartyId;
	/**
	 * 卖方partyId
	 */
	private Integer sellerAccountId;
	private Integer sellerPartyId;
	/**
	 * 订单号
	 */
	private String orderNum;
	/**
	 * 订单类型 10 资产大买办 20 配资乐 30 处置服务 31 尽调报告
	 */
	private Integer orderType;
	/**
	 * assetId
	 */
	private Integer assetId;
	/**
	 * 名称
	 */
	private String assetName;
	/**
	 * 金额
	 */
	private java.math.BigDecimal amount;
	/**
	 * 补充内容
	 */
	private String additional;
	/**
	 * 支付状态 00:未支付 10:支付
	 */
	private String payStatus;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date updateTime;
	/**
	 * 删除标识 0:未删除 1：删除
	 */
	private Boolean delFlag;

}
