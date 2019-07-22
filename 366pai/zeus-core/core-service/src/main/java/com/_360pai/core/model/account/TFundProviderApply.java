
package com._360pai.core.model.account;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月19日 12时50分34秒
 */
@Getter
@Setter
public class TFundProviderApply implements java.io.Serializable{

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 *
	 */
	private java.lang.Integer accountId;
	/**
	 *
	 */
	private java.lang.Integer partyId;
	/**
	 * 公司名称
	 */
	private java.lang.String companyName;
	/**
	 * 公司类型
	 */
	private java.lang.String companyType;
	/**
	 * 自定义企业类型名称
	 */
	private java.lang.String customCompanyType;
	/**
	 * 注册地址
	 */
	private java.lang.String registerAddress;
	/**
	 * 注册资本
	 */
	private java.math.BigDecimal registerCapital;
	/**
	 * 配资区域
	 */
	private java.lang.String providerArea;
	/**
	 * 最小配资额度
	 */
	private java.math.BigDecimal providerMinAmount;
	/**
	 * 最大配资额度
	 */
	private java.math.BigDecimal providerMaxAmount;
	/**
	 * 配资级别
	 */
	private com.alibaba.fastjson.JSONObject providerLevel;
	/**
	 * 最小配资成本
	 */
	private java.math.BigDecimal providerMinCost;
	/**
	 * 最大配资成本
	 */
	private java.math.BigDecimal providerMaxCost;
	/**
	 * 服务费用
	 */
	private java.math.BigDecimal providerFee;
	/**
	 * 配资最短期限
	 */
	private java.math.BigDecimal providerMinMonth;
	/**
	 * 配资最长期限
	 */
	private java.math.BigDecimal providerMaxMonth;
	/**
	 * 配资比例
	 */
	private java.math.BigDecimal scale;
	/**
	 * 配资模式
	 */
	private java.lang.String providerPattern;
	/**
	 * 资产包要求
	 */
	private java.lang.String assetRequire;
	/**
	 * 资金服务商类型 user company
	 */
	private java.lang.String fundType;
	/**
	 * 描述
	 */
	private java.lang.String descrip;
	/**
	 * 申请状态(PENDING = 1,APPROVED = 2,REJECT = 3)
	 */
	private java.lang.String status;
	/**
	 * 原因
	 */
	private java.lang.String reason;
	/**
	 * 审核人Id
	 */
	private java.lang.Integer operatorId;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	/**
	 * 业务对接人
	 */
	private java.lang.Integer businessOperatorId;
	/**
	 * 开户人员
	 */
	private java.lang.Integer openAccountOperatorId;
	/**
	 * 联系方式（电话）
	 */
	private java.lang.String contactPhone;
	/**
	 * 联系人
	 */
	private java.lang.String contactPerson;
	/**
	 * 单笔配资额度开始
	 */
	private java.math.BigDecimal singleMinAmount;
	/**
	 * 单笔配资额度结束
	 */
	private java.math.BigDecimal singleMaxAmount;
	/**
	 * 年化收益率开始
	 */
	private java.lang.String annuaReturnMin;
	/**
	 * 年化收益率结束
	 */
	private java.lang.String annuaReturnMax;
	/**
	 * 其他费用
	 */
	private java.lang.String otherFee;
	/**
	 * 标的类型
	 */
	private java.lang.String assetType;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private java.lang.Boolean deleteFlag;

}
