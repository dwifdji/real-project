
package com._360pai.core.model.account;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月15日 19时32分10秒
 */
@Getter
@Setter
public class TAccount implements java.io.Serializable{

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 * 手机号
	 */
	private java.lang.String mobile;
	/**
	 * 密码
	 */
	private java.lang.String password;
	/**
	 * 注册来源
	 */
	private java.lang.String registerSource;
	/**
	 * 渠道来源
	 */
	private java.lang.String source;
	/**
	 * 状态(0:无效,1:有效)
	 */
	private java.lang.Integer status;
	/**
	 * 默认送拍机构
	 */
	private java.lang.Integer defaultAgencyId;
	/**
	 * 从属于某个机构的管理员
	 */
	private java.lang.Integer agencyId;
	/**
	 * 是否是机构的申请者
	 */
	private java.lang.Integer isAgencyAdmin;
	/**
	 * 是否可以查看保留价
	 */
	private java.lang.Boolean canCheckReservePrice;
	/**
	 *
	 */
	private java.lang.Integer currentPartyId;
	/**
	 * 店铺id
	 */
	private java.lang.Integer shopId;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private java.lang.Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

}
