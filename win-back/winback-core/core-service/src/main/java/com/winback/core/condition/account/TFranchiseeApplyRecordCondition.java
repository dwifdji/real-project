
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
public class TFranchiseeApplyRecordCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 * 账户id
	 */
	private java.lang.Integer accountId;
	/**
	 * 姓名
	 */
	private java.lang.String name;
	/**
	 * 加盟商类型(user：个人，company：企业)
	 */
	private java.lang.String type;
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
	 * 营业执照图片
	 */
	private java.lang.String licenseImg;
	/**
	 * 营业执照号
	 */
	private java.lang.String licenseNumber;
	/**
	 * 自我介绍
	 */
	private java.lang.String selfIntroduction;
	/**
	 * 申请状态(PENDING：审核中,APPROVED：审核通过,REJECT：审核拒绝)
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