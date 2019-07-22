
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年11月07日 13时21分53秒
 */
@Getter
@Setter
public class TAssetAuthorizationCondition implements DaoCondition{
	/**
	 * 主键id
	 */
	private java.lang.Integer id;
	/**
	 * 拍品id
	 */
	private java.lang.Integer activityId;
	/**
	 * 签约人
	 */
	private java.lang.Integer partyId;
	/**
	 * 协议主类型
	 */
	private java.lang.String protocolType;
	/**
	 * 协议类型
	 */
	private java.lang.String protocolSubtype;
	/**
	 *
	 */
	private java.lang.Boolean signed;
	/**
	 *
	 */
	private java.lang.Boolean allSigned;
	/**
	 *
	 */
	private java.lang.String contractId;
	/**
	 *
	 */
	private java.lang.String downloadUrl;
	/**
	 *
	 */
	private java.lang.String viewpdfUrl;
	/**
	 * 业务no
	 */
	private java.lang.String surveyNo;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date updateTime;

}