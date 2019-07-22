
package com.winback.core.model.contract;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
public class TContractDownloadRecord implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 账户id
	 */
	private Integer accountId;
	/**
	 * 合同id
	 */
	private Integer contractId;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	
	/**
	 * 主键
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 主键
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 账户id
	 */
	public Integer getAccountId(){
		return accountId;
	}
	
	/**
	 * 账户id
	 */
	public void setAccountId(Integer accountId){
		this.accountId = accountId;
	}
	
	/**
	 * 合同id
	 */
	public Integer getContractId(){
		return contractId;
	}
	
	/**
	 * 合同id
	 */
	public void setContractId(Integer contractId){
		this.contractId = contractId;
	}
	
	/**
	 * 邮箱
	 */
	public String getEmail(){
		return email;
	}
	
	/**
	 * 邮箱
	 */
	public void setEmail(String email){
		this.email = email;
	}
	
	/**
	 * 删除标志（0 否 1 是）
	 */
	public Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志（0 否 1 是）
	 */
	public void setDeleteFlag(Boolean deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * 创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

}
