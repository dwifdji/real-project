
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月17日 15时47分34秒
 */
@Getter
@Setter
public class TUserCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 * 账户Id
	 */
	private java.lang.Integer accountId;
	/**
	 * 个人认证时选的送拍机构
	 */
	private java.lang.Integer defaultAgencyId;
	/**
	 * 姓名
	 */
	private java.lang.String name;
	/**
	 * 身份证号码
	 */
	private java.lang.String certificateNumber;
	/**
	 * 身份证起始日期
	 */
	private java.util.Date certificateBegin;
	/**
	 * 身份证结束日期
	 */
	private java.util.Date certificateEnd;
	/**
	 * 省Id
	 */
	private java.lang.String provinceId;
	/**
	 * 城市Id
	 */
	private java.lang.String cityId;
	/**
	 * 区县id
	 */
	private java.lang.String areaId;
	/**
	 * 地址
	 */
	private java.lang.String address;
	/**
	 * 身份证正面照
	 */
	private java.lang.String certificateFrontImg;
	/**
	 * 身份证背面照
	 */
	private java.lang.String certificateBackImg;
	/**
	 * 手机号
	 */
	private java.lang.String mobile;
	/**
	 * 法大大Id
	 */
	private java.lang.String fadadaId;
	/**
	 * 东方付通ID
	 */
	private java.lang.String dfftId;
	/**
	 * 是否绑定东方付通
	 */
	private java.lang.Integer payBind;
	/**
	 * 0:无效,1:有效
	 */
	private java.lang.Integer status;
	/**
	 * 是否是代理商(0:否,1:是)
	 */
	private java.lang.Integer isChannel;
	/**
	 * 允许发布线下操作拍品 0 否 1 是
	 */
	private java.lang.Boolean operOffline;
	/**
	 * 是否允许未开通法大大下发布预招商 0 否 1 是
	 */
	private java.lang.Boolean operWithoutFadada;
	/**
	 * 开户人员
	 */
	private java.lang.Integer openAccountOperatorId;
	/**
	 * 业务对接人
	 */
	private java.lang.Integer businessOperatorId;
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