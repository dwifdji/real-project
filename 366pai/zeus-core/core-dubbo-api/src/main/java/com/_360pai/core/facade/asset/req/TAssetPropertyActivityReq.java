
package com._360pai.core.facade.asset.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月18日 20时18分03秒
 */
@Data
public class TAssetPropertyActivityReq extends RequestModel {

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
	private java.lang.String activityType = "auction";
	/**
	 * 排序编号
	 */
	private Integer orderNum;


}
