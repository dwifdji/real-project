
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月27日 12时42分56秒
 */
@Data
public class TAssetPropertyActivityCondition implements DaoCondition{

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