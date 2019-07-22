
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年10月19日 13时21分32秒
 */
@Getter
@Setter
public class TBackstageOperationRecordCondition implements DaoCondition{

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