
package com._360pai.core.model.account;

import com._360pai.core.facade.assistant.vo.CityVo;
import com._360pai.core.facade.disposal.req.City;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月17日 15时47分34秒
 */
@Getter
@Setter
public class TCompany implements java.io.Serializable{

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 * 公司名称
	 */
	private java.lang.String name;
	/**
	 * 账户Id
	 */
	private java.lang.Integer accountId;
	/**
	 * 企业认证送拍机构
	 */
	private java.lang.Integer defaultAgencyId;
	/**
	 * 手机号
	 */
	private java.lang.String mobile;
	/**
	 * 法人
	 */
	private java.lang.String legalPerson;
	/**
	 * 营业执照号码
	 */
	private java.lang.String license;
	/**
	 * 营业执照图片
	 */
	private java.lang.String licenseImg;
	/**
	 * 注册城市
	 */
	private java.lang.String registerCityId;
	/**
	 * 办公城市
	 */
	private java.lang.String cityId;
	/**
	 * 省id
	 */
	private java.lang.String provinceId;
	/**
	 * 注册省id
	 */
	private java.lang.String registerProvinceId;
	/**
	 * 区县id
	 */
	private java.lang.String areaId;
	/**
	 * 注册区县id
	 */
	private java.lang.String registerAreaId;
	/**
	 * 注册地址
	 */
	private java.lang.String registerAddress;
	/**
	 * 办公地址
	 */
	private java.lang.String address;
	/**
	 * 机构id
	 */
	private java.lang.Integer agencyId;
	/**
	 * 营业起始
	 */
	private java.util.Date qualifiedBegin;
	/**
	 * 营业结束
	 */
	private java.util.Date qualifiedEnd;
	/**
	 * 授权书
	 */
	private java.lang.String authorizationImg;
	/**
	 * 开户许可证
	 */
	private java.lang.String accountLicense;
	/**
	 * 身份证号
	 */
	private java.lang.String idCard;
	/**
	 * 身份证正面照
	 */
	private java.lang.String idCardFrontImg;
	/**
	 * 身份证背面照
	 */
	private java.lang.String idCardBackImg;
	/**
	 * 状态(0:无效,1:有效)
	 */
	private java.lang.Integer status;
	/**
	 * 法大大Id
	 */
	private java.lang.String fadadaId;
	/**
	 * 法大大email
	 */
	private java.lang.String fadadaEmail;
	/**
	 * 东方付通Id
	 */
	private java.lang.String dfftId;
	/**
	 * 是否绑定东方付通
	 */
	private java.lang.Integer payBind;
	/**
	 * 是否为通道支付类企业
	 */
	private java.lang.Integer channelPay;
	/**
	 * 是否是代理商(0:否,1:是)
	 */
	private java.lang.Integer isChannel;
	/**
	 * 1:正常人，2:银行,3:AMC 资产管理公司,4:民营资管，5:普通公司,6:拍卖公司
	 */
	private java.lang.String category;
	/**
	 * 收款开户名
	 */
	private java.lang.String bankAccountName;
	/**
	 * 收款银行账户
	 */
	private java.lang.String bankAccountNumber;
	/**
	 * 银行Id
	 */
	private java.lang.Integer bankId;
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
	/**
	 * 是否内部企业
	 */
	private String isStaff;

	private Integer virtual;
	private String agencyApplyStatus;
	private String fundApplyStatus;


	private Integer leaseNum;

	private CityVo cityVo;
}
