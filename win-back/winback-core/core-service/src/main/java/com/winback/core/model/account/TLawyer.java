
package com.winback.core.model.account;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月16日 15时40分02秒
 */
@Data
public class TLawyer implements java.io.Serializable{

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 * 账户id
	 */
	private java.lang.Integer accountId;
	/**
	 * 邮箱
	 */
	private java.lang.String email;
	/**
	 * 姓名
	 */
	private java.lang.String name;
	/**
	 * 头像
	 */
	private java.lang.String headImgUrl;
	/**
	 * 身份证号码
	 */
	private java.lang.String certificateNumber;
	/**
	 * 身份证正面照
	 */
	private java.lang.String certificateFrontImg;
	/**
	 * 身份证背面照
	 */
	private java.lang.String certificateBackImg;
	/**
	 * 身份证起始日期
	 */
	private java.util.Date certificateBegin;
	/**
	 * 身份证结束日期
	 */
	private java.util.Date certificateEnd;
	/**
	 * 律师证
	 */
	private java.lang.String lawyerLicenseImg;
	/**
	 * 律师执业证号
	 */
	private java.lang.String lawyerLicenseNumber;
	/**
	 * 所属律所
	 */
	private java.lang.String lawFirm;
	/**
	 * 从业年限
	 */
	private java.lang.String workYear;
	/**
	 * 业务区域省code
	 */
	private java.lang.String businessProvinceCode;
	/**
	 * 业务区域市id
	 */
	private java.lang.String businessCityCode;
	/**
	 * 业务区区县code
	 */
	private java.lang.String businessAreaCode;
	/**
	 * 收款银行
	 */
	private java.lang.String bankName;
	/**
	 * 收款银行账户
	 */
	private java.lang.String bankAccountNumber;
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
