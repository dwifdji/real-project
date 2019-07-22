
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年11月23日 09时16分44秒
 */
@Data
public class TAccountExtBindCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 账户id
	 */
	private Integer accountId;
	/**
	 * 外部类型（APPLET：小程序）
	 */
	private String extType;
	/**
	 * 外部用户id
	 */
	private String extUserId;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 头像
	 */
	private String headImgUrl;
	/**
	 * 当前账户角色
	 */
	private Integer currentPartyId;
	/**
	 * 邀请码
	 */
	private Integer inviteCode;
	/**
	 * 邀请类型（DP：店铺，JG：机构）
	 */
	private String inviteType;
	/**
	 * 用户微信开放平台id
	 */
	private java.lang.String unionId;
	/**
	 * 是否删除（0 否 1 是）
	 */
	private Boolean isDelete;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

}