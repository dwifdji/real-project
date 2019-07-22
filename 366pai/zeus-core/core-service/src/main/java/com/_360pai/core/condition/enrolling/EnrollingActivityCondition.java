
package com._360pai.core.condition.enrolling;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月05日 15时14分21秒
 */
@Data
public class EnrollingActivityCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 
	 */
	private java.lang.String status;
	/**
	 * 
	 */
	private java.lang.String name;
	/**
	 * 省id
	 */
	private java.lang.String provinceId;
	/**
	 * 
	 */
	private java.lang.String cityId;
	/**
	 * 区县id
	 */
	private java.lang.String areaId;
	/**
	 * 
	 */
	private java.lang.Integer agencyId;
	/**
	 * 
	 */
	private java.lang.Integer propertyId;
	/**
	 * 
	 */
	private java.lang.Integer categoryId;
	/**
	 * 
	 */
	private java.math.BigDecimal refPrice;
	/**
	 * 
	 */
	private java.math.BigDecimal deposit;
	/**
	 * 
	 */
	private java.util.Date beginAt;
	/**
	 * 
	 */
	private java.util.Date endAt;
	/**
	 * 
	 */
	private java.lang.Integer partyId;
	/**
	 * 
	 */
	private java.util.Date expireAt;
	/**
	 * 
	 */
	private com.alibaba.fastjson.JSONObject extra;
	/**
	 * 
	 */
	private java.lang.String remark;
	/**
	 * 
	 */
	private java.lang.String descriptionDoc;
	/**
	 * 
	 */
	private java.lang.String detail;
	/**
	 * 
	 */
	private java.lang.String code;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private java.lang.Integer participantNumber;
	/**
	 * 
	 */
	private java.math.BigDecimal commissionPercent;
	/**
	 * 
	 */
	private java.lang.String rejectReason;
	/**
	 * 
	 */
	private java.lang.Integer assetCategoryGroupId;
	/**
	 * 
	 */
	private java.lang.String contactName;
	/**
	 * 
	 */
	private java.lang.String contactPhone;
	/**
	 * 
	 */
	private java.lang.String contactQq;
	/**
	 * 
	 */
	private java.lang.String options;
	/**
	 * 
	 */
	private java.lang.Boolean endNotified;
	/**
	 * 
	 */
	private java.lang.String visibilityLevel;
	/**
	 * 
	 */
	private java.lang.Integer browseNumber;
	/**
	 * 
	 */
	private java.lang.Integer focusNumber;
	/**
	 * 
	 */
	private java.lang.Integer reminderNumber;
	/**
	 * 
	 */
	private java.lang.Integer applyNumber;
	/**
	 * 
	 */
	private java.lang.String cityName;
	/**
	 * 
	 */
	private java.lang.String type;


	/**
	 *是否有抵押物
	 */
	private java.lang.Integer guarantee;


	/**
	 *资产亮点
	 */
	private java.lang.String brightSpot;
	/**
	 * 操作时间
	 */
	private java.util.Date operatorAt;
	/**
	 * 审核人
	 */
	private java.lang.Integer operatorId;

	/**
	 * 支付过期时间
	 */
	private java.util.Date endPayTime;



	/**
	 * 发布支付金额
	 */
	private java.math.BigDecimal releaseAmount;
	/**
	 * 0 平台 1 第三方渠道淘宝
	 */
	private Integer  thirdPartyStatus;
	/**
	 * 第三方渠道名称
	 */
	private String   thirdPartyTitle;
	/**
	 * 第三方渠道链接
	 */
	private String   thirdPartyUrl;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private java.lang.Boolean deleteFlag;


	/**
	 * 分公司名称
	 */
	private java.lang.String branchComName;


	private java.lang.String disposalService;


	private java.lang.String fundProvider;

}