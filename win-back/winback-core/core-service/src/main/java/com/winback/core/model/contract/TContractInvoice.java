
package com.winback.core.model.contract;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年02月13日 19时36分43秒
 */
@Data
public class TContractInvoice implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 账户id
	 */
	private Integer accountId;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 纸质/电子
	 */
	private String type;
	/**
	 * 抬头
	 */
	private String title;
	/**
	 * 抬头类型（user：ger，company：企业）
	 */
	private String titleType;
	/**
	 * 发票内容
	 */
	private String content;
	/**
	 * 纳税人识别号
	 */
	private String taxpayerIdentificationNumber;
	/**
	 * 金额
	 */
	private java.math.BigDecimal amount;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 联系电话
	 */
	private String contactPhone;
	/**
	 * 收款银行
	 */
	private String bankName;
	/**
	 * 收款银行账户
	 */
	private String bankAccountNumber;
	/**
	 * 备注信息
	 */
	private String memo;
	/**
	 * 地址和电话
	 */
	private java.lang.String addressPhone;
	/**
	 * 开户行和账号
	 */
	private java.lang.String bankAccount;
	/**
	 * 	发票编号
	 */
	private String invoiceNo;
	/**
	 * 发票图片
	 */
	private String invoiceImgUrl;
	/**
	 * 申请状态(PENDING：审核中,APPROVED：审核通过,REJECT：审核拒绝)
	 */
	private String status;
	/**
	 * 原因
	 */
	private String reason;
	/**
	 * 审核人Id
	 */
	private Integer operatorId;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

}
