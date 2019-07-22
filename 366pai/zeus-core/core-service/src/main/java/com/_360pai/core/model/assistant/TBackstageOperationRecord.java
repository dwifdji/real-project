
package com._360pai.core.model.assistant;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年10月19日 13时21分31秒
 */
@Getter
@Setter
public class TBackstageOperationRecord implements java.io.Serializable{

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 * 类型（ 1：拍卖活动，2：预招商活动）
	 */
	private java.lang.String type;
	/**
	 * 关联外部id
	 */
	private java.lang.Long refId;
	/**
	 * 内容
	 */
	private java.lang.String content;
	/**
	 * 操作员
	 */
	private java.lang.Integer operatorId;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
}
