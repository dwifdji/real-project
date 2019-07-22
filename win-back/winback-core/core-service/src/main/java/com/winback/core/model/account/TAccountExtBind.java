
package com.winback.core.model.account;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月16日 15时40分01秒
 */
@Data
public class TAccountExtBind implements java.io.Serializable{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 账户id
	 */
	private java.lang.Integer accountId;
	/**
	 * 外部类型（APPLET：小程序）
	 */
	private java.lang.String extType;
	/**
	 * 外部用户id
	 */
	private java.lang.String extUserId;
	/**
	 * 昵称
	 */
	private java.lang.String nickName;
	/**
	 * 头像
	 */
	private java.lang.String headImgUrl;
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

	public boolean hasBindAccount() {
		return this.accountId != null;
	}
}
