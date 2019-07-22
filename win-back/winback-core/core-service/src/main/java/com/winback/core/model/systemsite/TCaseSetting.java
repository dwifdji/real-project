
package com.winback.core.model.systemsite;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月23日 10时13分56秒
 */
public class TCaseSetting implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 1案件阶段2资金状态3案由
	 */
	private String type;
	/**
	 * 状态0开通1关闭
	 */
	private Boolean status;
	/**
	 * 删除标识
	 */
	private Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date updateTime;
	
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
	 * 编码
	 */
	public String getCode(){
		return code;
	}
	
	/**
	 * 编码
	 */
	public void setCode(String code){
		this.code = code;
	}
	
	/**
	 * 名称
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 名称
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 1案件阶段2资金状态3案由
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * 1案件阶段2资金状态3案由
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * 状态0开通1关闭
	 */
	public Boolean getStatus(){
		return status;
	}
	
	/**
	 * 状态0开通1关闭
	 */
	public void setStatus(Boolean status){
		this.status = status;
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

}
