
package com._360pai.core.model.asset;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月27日 12时42分56秒
 */
@Data
public class TAssetPropertyActivity implements java.io.Serializable{

	/**
	 * 自增
	 */
	private Integer id;
	/**
	 * 拍品属性ID
	 */
	private Integer assetPropertyId;
	/**
	 * 活动ID
	 */
	private Integer activityId;
	/**
	 * 活动类型（拍卖 auction，招商 enrolling）
	 */
	private java.lang.String activityType;
	/**
	 * 排序编号
	 */
	private Integer orderNum;
	/**
	 * 是否删除 0不删除  1删除
	 */
	private Integer delFlag;
}
