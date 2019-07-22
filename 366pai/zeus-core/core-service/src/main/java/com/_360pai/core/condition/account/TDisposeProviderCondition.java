
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年10月24日 17时54分20秒
 */
@Getter
@Setter
public class TDisposeProviderCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private Integer accountId;
	/**
	 *
	 */
	private java.lang.Integer partyId;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 公司类型
	 */
	private String companyType;
	/**
	 * 注册地址
	 */
	private String registerAddress;
	/**
	 * 区域
	 */
	private String region;
	/**
	 * 注册资本
	 */
	private java.math.BigDecimal registerCapital;
	/**
	 * 联系人姓名
	 */
	private String contactName;
	/**
	 * 联系方式
	 */
	private String contactMobile;
	/**
	 * 资质证书
	 */
	private String qualificationUrl;
	/**
	 * 工作期限
	 */
	private java.math.BigDecimal workYear;
	/**
	 * 自我介绍
	 */
	private String introduction;
	/**
	 * 过往案例
	 */
	private String caseUrl;
	/**
	 * 可提供服务
	 */
	private String provideService;
	/**
	 * 处置类型
	 */
	private String disposeType;

	/**
	 * 
	 */
	private java.util.Date createTime;
	/**
	 * 
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
	 * 删除标志（0 否 1 是）
	 */
	private java.lang.Boolean deleteFlag;
	/**
	 * 省区域id
	 */
	private java.lang.String regionProvince;
	/**
	 * 区域-区县id
	 */
	private java.lang.String regionArea;
}