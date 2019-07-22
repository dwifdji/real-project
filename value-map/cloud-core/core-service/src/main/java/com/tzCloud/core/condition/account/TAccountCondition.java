
package com.tzCloud.core.condition.account;

import com.tzCloud.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月19日 13时33分42秒
 */
@Data
public class TAccountCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 头像
	 */
	private String headImgUrl;
	/**
	 * 姓名
	 */
	private java.lang.String name;
	/**
	 * 性别（U 未知 F 女 M 男）
	 */
	private java.lang.String gender;
	/**
	 * 公司
	 */
	private java.lang.String company;
	/**
	 * 行业
	 */
	private java.lang.String industry;
	/**
	 * 地址
	 */
	private java.lang.String address;
	/**
	 * 职务
	 */
	private java.lang.String title;
	/**
	 * 注册来源
	 */
	private String registerSource;
	/**
	 * 渠道来源
	 */
	private String source;
	/**
	 * 邀请码
	 */
	private String inviteCode;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

}