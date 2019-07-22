
package com._360pai.core.model.applet;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年11月23日 09时16分48秒
 */
@Data
public class TAppletViewShop implements java.io.Serializable{

	/**
	 *
	 */
	private java.lang.Integer id;
	/**
	 * 店铺id
	 */
	private java.lang.Integer shopId;
	/**
	 * 小程序openid
	 */
	private java.lang.String openId;
	/**
	 * 标的id
	 */
	private java.lang.Integer auctionId;
	/**
	 * 1 首页 2 标的
	 */
	private java.lang.Integer type;
	/**
	 * 是否删除（0 否 1 是）
	 */
	private java.lang.Boolean isDelete;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

}
