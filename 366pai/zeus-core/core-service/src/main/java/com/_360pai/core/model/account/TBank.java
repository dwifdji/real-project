
package com._360pai.core.model.account;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年11月29日 15时57分11秒
 */
@Data
public class TBank implements java.io.Serializable{
	/**
	 *
	 */
	private java.lang.Integer id;
	/**
	 * 银行代码
	 */
	private java.lang.String code;
	/**
	 * 银行名称
	 */
	private java.lang.String name;
	/**
	 * 银行图片
	 */
	private java.lang.String imgUrl;
	/**
	 *
	 */
	private java.lang.Boolean isDelete;
	/**
	 *
	 */
	private java.util.Date createTime;
	/**
	 *
	 */
	private java.util.Date updateTime;
}
