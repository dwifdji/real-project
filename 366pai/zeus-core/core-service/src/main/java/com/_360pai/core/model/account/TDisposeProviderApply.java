
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
public class TDisposeProviderApply implements java.io.Serializable{

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
	 * 注册地址
	 */
	private java.lang.String registerAddress;
	/**
	 * 注册资本
	 */
	private java.math.BigDecimal registerCapital;
	/**
	 * 联系人姓名
	 */
	private java.lang.String contactName;
	/**
	 * 联系方式
	 */
	private java.lang.String contactMobile;
	/**
	 * 资质证书
	 */
	private java.lang.String qualificationUrl;
	/**
	 * 工作年限
	 */
	private java.math.BigDecimal workYear;
	/**
	 * 自我介绍
	 */
	private java.lang.String introduction;
	/**
	 * 过往案例
	 */
	private java.lang.String caseUrl;
	/**
	 * 可提供服务
	 */
	private java.lang.String provideService;
	/**
	 * 处置类型
	 */
	private java.lang.String disposeType;
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
	 *
	 */
	private java.util.Date createTime;
	/**
	 *
	 */
	private java.util.Date updateTime;

	private String region;

	private String lawOffice;
	/**
	 * 业务对接人
	 */
	private java.lang.Integer businessOperatorId;
	/**
	 * 开户人员
	 */
	private java.lang.Integer openAccountOperatorId;
	/**
	 * 省区域id
	 */
	private java.lang.String regionProvince;
	/**
	 * 区域-区县id
	 */
	private java.lang.String regionArea;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private java.lang.Boolean deleteFlag;

}
