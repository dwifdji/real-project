
package com._360pai.core.model.payment;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分27秒
 */
@Getter
@Setter
public class AuctionOrder implements java.io.Serializable{

	/**
	 * 
	 */
	private Long id;
	/**
	 * 
	 */
	private Integer activityId;
	/**
	 * 
	 */
	private String status;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private java.math.BigDecimal deposit;
	/**
	 * 
	 */
	private Integer bidId;
	/**
	 * 
	 */
	private Integer buyerAgencyId;
	/**
	 * 
	 */
	private Integer sellerAgencyId;
	/**
	 * 
	 */
	private Integer buyerId;
	/**
	 * 
	 */
	private Integer sellerId;
	/**
	 * 
	 */
	private java.math.BigDecimal amount;
	/**
	 * 
	 */
	private Boolean buyerPaidCommission;
	/**
	 * 
	 */
	private Boolean buyerPaidOrder;
	/**
	 * 
	 */
	private Boolean sellerPaidCommission;
	/**
	 * 
	 */
	private Boolean sellerPaidOrder;
	/**
	 * 
	 */
	private String assetCategory;
	/**
	 * 
	 */
	private java.math.BigDecimal serveBuyerPercent;
	/**
	 * 
	 */
	private java.math.BigDecimal serveSellerPercent;
	/**
	 * 
	 */
	private Boolean autoHandleDelay;

	private Date updateTime;

	private String buyerHasPayEnd;
	private String sellerHasPayEnd;

	/**
	 * 上拍来源：0 平台 1 机构
	 */
	private java.lang.String comeFrom;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private java.lang.Boolean deleteFlag;
}
