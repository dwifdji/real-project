
package com.tzCloud.core.condition.lawyerSearch;

import com.tzCloud.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月19日 09时15分09秒
 */
public class TParseLawyerInfoCondition implements DaoCondition{

	/**
	 * 主键id
	 */
	private Long id;
	/**
	 * 律师名称
	 */
	private String lawyerName;
	/**
	 * 律师事务所
	 */
	private String lawFirm;
	/**
	 * 事务所名称
	 */
	private String lawFirmShort;
	/**
	 * 事务所所在城市
	 */
	private String lawFirmCity;
	/**
	 * 事务所所在省份
	 */
	private String lawFirmProvince;
	/**
	 * 工作年限
	 */
	private Integer years;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	/**
	 * 删除标识
	 */
	private Boolean delFlag;
	
	/**
	 * 主键id
	 */
	public Long getId(){
		return id;
	}
	
	/**
	 * 主键id
	 */
	public void setId(Long id){
		this.id = id;
	}
	
	/**
	 * 律师名称
	 */
	public String getLawyerName(){
		return lawyerName;
	}
	
	/**
	 * 律师名称
	 */
	public void setLawyerName(String lawyerName){
		this.lawyerName = lawyerName;
	}
	
	/**
	 * 律师事务所
	 */
	public String getLawFirm(){
		return lawFirm;
	}
	
	/**
	 * 律师事务所
	 */
	public void setLawFirm(String lawFirm){
		this.lawFirm = lawFirm;
	}
	
	/**
	 * 事务所名称
	 */
	public String getLawFirmShort(){
		return lawFirmShort;
	}
	
	/**
	 * 事务所名称
	 */
	public void setLawFirmShort(String lawFirmShort){
		this.lawFirmShort = lawFirmShort;
	}
	
	/**
	 * 事务所所在城市
	 */
	public String getLawFirmCity(){
		return lawFirmCity;
	}
	
	/**
	 * 事务所所在城市
	 */
	public void setLawFirmCity(String lawFirmCity){
		this.lawFirmCity = lawFirmCity;
	}
	
	/**
	 * 事务所所在省份
	 */
	public String getLawFirmProvince(){
		return lawFirmProvince;
	}
	
	/**
	 * 事务所所在省份
	 */
	public void setLawFirmProvince(String lawFirmProvince){
		this.lawFirmProvince = lawFirmProvince;
	}
	
	/**
	 * 工作年限
	 */
	public Integer getYears(){
		return years;
	}
	
	/**
	 * 工作年限
	 */
	public void setYears(Integer years){
		this.years = years;
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
	
	/**
	 * 更新时间
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 更新时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}
	
	/**
	 * 删除标识
	 */
	public Boolean getDelFlag(){
		return delFlag;
	}
	
	/**
	 * 删除标识
	 */
	public void setDelFlag(Boolean delFlag){
		this.delFlag = delFlag;
	}

}