
package com._360pai.core.model.account;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年11月01日 12时43分39秒
 */
public class TCityPartner implements java.io.Serializable{

	/**
	 * 自增主键
	 */
	private Integer id;
	/**
	 * 
	 */
	private Integer cityId;
	/**
	 * 
	 */
	private String officeSize;
	/**
	 * 
	 */
	private String contactName;
	/**
	 * 
	 */
	private String contactPhone;
	/**
	 * 
	 */
	private String position;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private String reservedField;
	
	/**
	 * 自增主键
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 自增主键
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 
	 */
	public Integer getCityId(){
		return cityId;
	}
	
	/**
	 * 
	 */
	public void setCityId(Integer cityId){
		this.cityId = cityId;
	}
	
	/**
	 * 
	 */
	public String getOfficeSize(){
		return officeSize;
	}
	
	/**
	 * 
	 */
	public void setOfficeSize(String officeSize){
		this.officeSize = officeSize;
	}
	
	/**
	 * 
	 */
	public String getContactName(){
		return contactName;
	}
	
	/**
	 * 
	 */
	public void setContactName(String contactName){
		this.contactName = contactName;
	}
	
	/**
	 * 
	 */
	public String getContactPhone(){
		return contactPhone;
	}
	
	/**
	 * 
	 */
	public void setContactPhone(String contactPhone){
		this.contactPhone = contactPhone;
	}
	
	/**
	 * 
	 */
	public String getPosition(){
		return position;
	}
	
	/**
	 * 
	 */
	public void setPosition(String position){
		this.position = position;
	}
	
	/**
	 * 
	 */
	public java.util.Date getCreatedAt(){
		return createdAt;
	}
	
	/**
	 * 
	 */
	public void setCreatedAt(java.util.Date createdAt){
		this.createdAt = createdAt;
	}
	
	/**
	 * 
	 */
	public String getReservedField(){
		return reservedField;
	}
	
	/**
	 * 
	 */
	public void setReservedField(String reservedField){
		this.reservedField = reservedField;
	}

}
