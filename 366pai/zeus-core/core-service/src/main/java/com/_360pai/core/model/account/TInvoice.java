
package com._360pai.core.model.account;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年11月29日 15时57分11秒
 */
public class TInvoice implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 纸质/电子
	 */
	private String type;
	/**
	 * 物流公司
	 */
	private String logisticsCompany;
	/**
	 * 电子版url
	 */
	private String imgurl;
	/**
	 * 物流单号
	 */
	private String logisticsNo;
	/**
	 * 发票代码
	 */
	private String code;
	/**
	 * 发票号码
	 */
	private String num;
	/**
	 * 
	 */
	private java.util.Date createTime;
	/**
	 * 
	 */
	private java.util.Date updateTime;

	private Integer acctId;

	public Integer getAcctId() {
		return acctId;
	}

	public void setAcctId(Integer acctId) {
		this.acctId = acctId;
	}

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
	 * 纸质/电子
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * 纸质/电子
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * 物流公司
	 */
	public String getLogisticsCompany(){
		return logisticsCompany;
	}
	
	/**
	 * 物流公司
	 */
	public void setLogisticsCompany(String logisticsCompany){
		this.logisticsCompany = logisticsCompany;
	}
	
	/**
	 * 电子版url
	 */
	public String getImgurl(){
		return imgurl;
	}
	
	/**
	 * 电子版url
	 */
	public void setImgurl(String imgurl){
		this.imgurl = imgurl;
	}
	
	/**
	 * 物流单号
	 */
	public String getLogisticsNo(){
		return logisticsNo;
	}
	
	/**
	 * 物流单号
	 */
	public void setLogisticsNo(String logisticsNo){
		this.logisticsNo = logisticsNo;
	}
	
	/**
	 * 发票代码
	 */
	public String getCode(){
		return code;
	}
	
	/**
	 * 发票代码
	 */
	public void setCode(String code){
		this.code = code;
	}
	
	/**
	 * 发票号码
	 */
	public String getNum(){
		return num;
	}
	
	/**
	 * 发票号码
	 */
	public void setNum(String num){
		this.num = num;
	}
	
	/**
	 * 
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * 
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	
	/**
	 * 
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

}
