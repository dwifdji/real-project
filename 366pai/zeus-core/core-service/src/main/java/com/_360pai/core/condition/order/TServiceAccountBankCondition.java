
package com._360pai.core.condition.order;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年10月06日 23时43分10秒
 */
@Data
public class TServiceAccountBankCondition implements DaoCondition{

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer accountId;
	private Integer partyId;
	/**
	 * 银行卡号
	 */
	private String bankNo;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 开户名称
	 */
	private String userName;
	/**
	 * 支行地址
	 */
	private String bankAddress;
	/**
	 * 当前绑定 0:未绑定  1:绑定
	 */
	private Boolean currentBiding;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date updateTime;
	/**
	 * 删除标识 0:未删除 1：删除
	 */
	private Boolean delFlag;


}