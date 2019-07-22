
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

import java.util.Date;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年11月29日 15时57分11秒
 */
@Data
public class TAcctDetailCondition implements DaoCondition{

	/**
	 *
	 */
	private java.lang.Long id;
	/**
	 * acctId
	 */
	private java.lang.Integer acctId;
	/**
	 * 类型 充值、其他、开店推荐费、成交佣金、提现
	 */
	private java.lang.String type;
	/**
	 * 金额
	 */
	private java.math.BigDecimal amount;
	/**
	 * 总金额
	 */
	private java.math.BigDecimal totalAmt;
	/**
	 * 锁定金额
	 */
	private java.math.BigDecimal lockAmt;
	/**
	 * 余额
	 */
	private java.math.BigDecimal availAmt;
	/**
	 * 备份金额,用于红冲、取消出款
	 */
	private java.math.BigDecimal backupAmt;
	/**
	 * 银行账户id
	 */
	private java.lang.Integer bankAccountId;
	/**
	 * 发票id
	 */
	private java.lang.Integer invoiceId;
	/**
	 * 合同id
	 */
	private java.lang.String contractId;
	/**
	 * 待初审、初审成功/失败、已签合同、已提供发票、发票审核成功/失败、终审成功/失败 、已出款、已标记红冲、红冲
	 */
	private java.lang.String status;
	/**
	 * 来源订单id
	 */
	private java.lang.String sourceOrderId;
	/**
	 * 初审人员id
	 */
	private java.lang.Integer firstVerifyOperatorId;
	/**
	 * 初审拒绝原因
	 */
	private java.lang.String firstVerifyRefuseReason;
	/**
	 * 初审时间
	 */
	private java.util.Date firstVerifyTime;
	/**
	 * 发票审核人员id
	 */
	private java.lang.Integer invoiceVerifyOperatorId;
	/**
	 * 发票审核拒绝原因
	 */
	private java.lang.String invoiceVerifyRefuseReason;
	/**
	 * 发票审核时间
	 */
	private java.util.Date invoiceVerifyTime;
	/**
	 * 出款人员
	 */
	private java.lang.Integer payOperatorId;
	/**
	 * 取消出款人员
	 */
	private java.lang.Integer cancelPayOperatorId;
	/**
	 * 取消出款原因
	 */
	private java.lang.String cancelPayReason;
	/**
	 * 取消出款时间
	 */
	private java.util.Date cancelTime;
	/**
	 * 红冲时间
	 */
	private java.util.Date hcTime;
	/**
	 * 红冲人员
	 */
	private java.lang.Integer hcOperatorId;
	/**
	 * 红冲原因
	 */
	private java.lang.String hcReason;
	/**
	 * 付款平台银行账户
	 */
	private java.lang.Integer payBankAccountId;
	/**
	 * 付款银行流水账户
	 */
	private java.lang.String payDetailNo;
	/**
	 * 出款日期
	 */
	private java.lang.String payTime;
	/**
	 * 推荐分润场景下，邀请的店铺id
	 */
	private java.lang.Integer inviteShopId;
	/**
	 *
	 */
	private java.util.Date createTime;
	/**
	 *
	 */
	private java.util.Date updateTime;
}