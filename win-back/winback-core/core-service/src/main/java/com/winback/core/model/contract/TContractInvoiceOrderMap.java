
package com.winback.core.model.contract;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年02月18日 15时17分17秒
 */
public class TContractInvoiceOrderMap implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 合同id
	 */
	private Integer invoiceId;
	/**
	 * 订单id
	 */
	private Integer orderId;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	
	/**
	 * 
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 合同id
	 */
	public Integer getInvoiceId(){
		return invoiceId;
	}
	
	/**
	 * 合同id
	 */
	public void setInvoiceId(Integer invoiceId){
		this.invoiceId = invoiceId;
	}
	
	/**
	 * 订单id
	 */
	public Integer getOrderId(){
		return orderId;
	}
	
	/**
	 * 订单id
	 */
	public void setOrderId(Integer orderId){
		this.orderId = orderId;
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
