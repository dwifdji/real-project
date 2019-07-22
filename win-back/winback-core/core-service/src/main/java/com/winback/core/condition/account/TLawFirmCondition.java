
package com.winback.core.condition.account;

import com.winback.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月16日 15时40分02秒
 */
@Data
public class TLawFirmCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 * 律所名称
	 */
	private java.lang.String name;
	/**
	 * 律所类型
	 */
	private java.lang.String type;
	/**
	 * 营业执照图片
	 */
	private java.lang.String licenseImg;
	/**
	 * 营业执照号
	 */
	private java.lang.String licenseNumber;
	/**
	 * 法人
	 */
	private java.lang.String legalPerson;
	/**
	 * 联系人
	 */
	private java.lang.String contactPerson;
	/**
	 * 联系人手机号
	 */
	private java.lang.String contactPhone;
	/**
	 * 律所规模
	 */
	private java.lang.String teamSize;
	/**
	 * 业务区域省code
	 */
	private java.lang.String businessProvinceCode;
	/**
	 * 业务区域市code
	 */
	private java.lang.String businessCityCode;
	/**
	 * 业务区区县code
	 */
	private java.lang.String businessAreaCode;
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