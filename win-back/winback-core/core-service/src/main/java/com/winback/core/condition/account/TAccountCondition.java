
package com.winback.core.condition.account;

import com.winback.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月16日 15时40分01秒
 */
@Data
public class TAccountCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 * 手机号
	 */
	private java.lang.String mobile;
	/**
	 * 密码
	 */
	private java.lang.String password;
	/**
	 * 昵称
	 */
	private java.lang.String nickName;
	/**
	 * 头像
	 */
	private java.lang.String headImgUrl;
	/**
	 * 注册来源
	 */
	private java.lang.String registerSource;
	/**
	 * 渠道来源
	 */
	private java.lang.String source;
	/**
	 * 邀请码
	 */
	private java.lang.String inviteCode;
	/**
	 * 项目经理标志
	 */
	private java.lang.Boolean projectManagerFlag;
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