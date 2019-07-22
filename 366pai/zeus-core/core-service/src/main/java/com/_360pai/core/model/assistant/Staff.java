
package com._360pai.core.model.assistant;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分27秒
 */
@Getter
@Setter
public class Staff implements java.io.Serializable{

	/**
	 *
	 */
	private java.lang.Integer id;
	/**
	 * 名称
	 */
	private java.lang.String name;
	/**
	 * 是否管理员
	 */
	private java.lang.Boolean isAdmin;
	/**
	 * 手机号
	 */
	private java.lang.String mobile;
	/**
	 *
	 */
	private java.lang.String passwordHash;
	/**
	 * QQ号
	 */
	private java.lang.String qq;
	/**
	 * 部门
	 */
	private java.lang.String dept;
	/**
	 * 职位
	 */
	private java.lang.String job;
	/**
	 * 工号
	 */
	private java.lang.String empNo;
	/**
	 * 企业邮箱
	 */
	private java.lang.String companyEmail;
	/**
	 * 状态（0：禁用，1：启用）
	 */
	private java.lang.String status;
	/**
	 * 客户画像管理员
	 */
	private java.lang.Boolean personaAdmin;
	/**
	 *
	 */
	private java.util.Date createTime;
	/**
	 *
	 */
	private java.util.Date updateTime;

}
