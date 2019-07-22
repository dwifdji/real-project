
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年05月08日 13时10分26秒
 */
public class AssetLeaseTypeImageCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 租赁权类型
	 */
	private String leaseType;
	/**
	 * 租赁权类型对应图片
	 */
	private String leaseImage;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date updateTime;
	/**
	 * 删除标识
	 */
	private Boolean deleteFlag;
	
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
	 * 租赁权类型
	 */
	public String getLeaseType(){
		return leaseType;
	}
	
	/**
	 * 租赁权类型
	 */
	public void setLeaseType(String leaseType){
		this.leaseType = leaseType;
	}
	
	/**
	 * 租赁权类型对应图片
	 */
	public String getLeaseImage(){
		return leaseImage;
	}
	
	/**
	 * 租赁权类型对应图片
	 */
	public void setLeaseImage(String leaseImage){
		this.leaseImage = leaseImage;
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
	 * 修改时间
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 修改时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}
	
	/**
	 * 删除标识
	 */
	public Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标识
	 */
	public void setDeleteFlag(Boolean deleteFlag){
		this.deleteFlag = deleteFlag;
	}

}