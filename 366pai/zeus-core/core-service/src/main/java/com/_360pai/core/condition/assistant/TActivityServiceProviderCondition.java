
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年05月21日 12时56分21秒
 */
@Data
public class TActivityServiceProviderCondition implements DaoCondition{
	/**
	 *
	 */
	private java.lang.Integer id;
	/**
	 * 活动类型（拍卖 auction，招商 enrolling）
	 */
	private java.lang.String activityType;
	/**
	 * 活动id
	 */
	private java.lang.Integer activityId;
	/**
	 * 服务商类型（处置 dispose，资金 fund）
	 */
	private java.lang.String providerType;
	/**
	 * 服务商名称
	 */
	private java.lang.String name;
	/**
	 * 联系电话
	 */
	private java.lang.String phone;
	/**
	 * 服务商id
	 */
	private java.lang.Integer providerId;
	/**
	 * 城市合伙人标志
	 */
	private java.lang.Boolean cityPartnerFlag;
	/**
	 * 来源（管理后台：admin， 主站：web）
	 */
	private java.lang.String source;
	/**
	 * 排序号
	 */
	private java.lang.Integer orderNum;
	/**
	 * 删除标志
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