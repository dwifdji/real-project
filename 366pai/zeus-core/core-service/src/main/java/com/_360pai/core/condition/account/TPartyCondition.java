
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月12日 17时03分04秒
 */
@Data
public class TPartyCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 * user or company
	 */
	private java.lang.String type;
	/**
	 * 1:正常人，2:银行,3:AMC 资产管理公司,4:民营资管，5:普通公司,6:拍卖公司
	 */
	private java.lang.String category;
	/**
	 * 是否渠道代理商
	 */
	private Boolean isChannelAgent;
	/**
	 * 是否在黑名单里
	 */
	private java.lang.Boolean isInBlackList;
	/**
	 * 认证来源（PLATFORM：平台，APPLET：小程序）
	 */
	private java.lang.String applySource;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 是否删除
	 */
	private java.lang.Integer isDel;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

}