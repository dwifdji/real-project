
package com._360pai.core.condition.agreement;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年12月06日 10时46分51秒
 */
@Data
public class WithdrawAgreementCondition implements DaoCondition{

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