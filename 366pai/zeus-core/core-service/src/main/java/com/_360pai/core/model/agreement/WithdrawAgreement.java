
package com._360pai.core.model.agreement;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年12月06日 10时46分51秒
 */
@Data
public class WithdrawAgreement implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private Long acctDetailId;
	/**
	 * 是否签署
	 */
	private Boolean signed;
	/**
	 * 
	 */
	private Boolean allSigned;
	/**
	 * 下载链接
	 */
	private String downloadUrl;
	/**
	 * 模版id
	 */
	private Integer templateId;
	/**
	 * 查看链接
	 */
	private String viewpdfUrl;
	/**
	 * 合同id
	 */
	private String contractId;
	/**
	 * 创建时间
	 */
	private java.util.Date createdAt;
	/**
	 * 签署时间
	 */
	private java.util.Date allSignedAt;

}
