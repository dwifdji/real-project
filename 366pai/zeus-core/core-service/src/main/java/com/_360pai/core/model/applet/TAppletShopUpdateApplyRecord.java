
package com._360pai.core.model.applet;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年11月29日 16时52分10秒
 */
@Data
public class TAppletShopUpdateApplyRecord implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 店铺id
	 */
	private Integer shopId;
	/**
	 *
	 */
	private Integer partyId;
	/**
	 * 店铺名称
	 */
	private String name;
	/**
	 * 店铺头像
	 */
	private String logoUrl;
	/**
	 * 原店铺名称
	 */
	private java.lang.String oldName;
	/**
	 * 原店铺头像
	 */
	private java.lang.String oldLogoUrl;
	/**
	 * 申请状态(PENDING = 1,APPROVED = 2,REJECT = 3)
	 */
	private String status;
	/**
	 * 审核人Id
	 */
	private Integer operatorId;
	/**
	 * 原因
	 */
	private String reason;
	/**
	 * 是否删除（0 否 1 是）
	 */
	private Boolean isDelete;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

}
