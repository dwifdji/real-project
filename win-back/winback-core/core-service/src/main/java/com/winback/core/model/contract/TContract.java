
package com.winback.core.model.contract;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
@Data
public class TContract implements java.io.Serializable{

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 * 名称
	 */
	private java.lang.String name;
	/**
	 * 金额
	 */
	private java.math.BigDecimal amount;
	/**
	 * 第一张图片
	 */
	private java.lang.String firstImage;
	/**
	 * 图片，逗号分隔
	 */
	private java.lang.String images;
	/**
	 * 下载链接
	 */
	private java.lang.String downloadUrl;
	/**
	 * 使用说明
	 */
	private java.lang.String manual;
	/**
	 * 提示
	 */
	private java.lang.String hint;
	/**
	 * 篇幅
	 */
	private java.lang.Integer length;
	/**
	 * 下载量
	 */
	private java.lang.Integer downloadCount;
	/**
	 * 收藏量
	 */
	private java.lang.Integer favoriteCount;
	/**
	 * 浏览量
	 */
	private java.lang.Integer viewCount;
	/**
	 * 购买量
	 */
	private java.lang.Integer purchaseCount;
	/**
	 * 推荐标志（0 否 1 是）
	 */
	private java.lang.Boolean recommendFlag;
	/**
	 * 是否上架（0 否 1 是）
	 */
	private java.lang.Boolean saleFlag;
	/**
	 * 排序号
	 */
	private java.lang.Integer orderNum;
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
