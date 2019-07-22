
package com.tzCloud.core.condition.caseMatching;

import com.tzCloud.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年03月12日 11时53分17秒
 */
public class TLawyerInfoCondition implements DaoCondition{

	/**
	 * 
	 */
	private Long id;
	/**
	 * 律所id
	 */
	private String lsswsbs;
	/**
	 * id
	 */
	private String lsbs;
	/**
	 * 头像地址
	 */
	private String imgUrl;
	/**
	 * 姓名
	 */
	private String xm;
	/**
	 * 工作年限
	 */
	private Integer years;
	/**
	 * 律所名称
	 */
	private String lsswsmc;
	/**
	 * 专长
	 */
	private String ywzc;
	/**
	 * 
	 */
	private java.util.Date createTime;
	
	/**
	 * 
	 */
	public Long getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(Long id){
		this.id = id;
	}
	
	/**
	 * 律所id
	 */
	public String getLsswsbs(){
		return lsswsbs;
	}
	
	/**
	 * 律所id
	 */
	public void setLsswsbs(String lsswsbs){
		this.lsswsbs = lsswsbs;
	}
	
	/**
	 * id
	 */
	public String getLsbs(){
		return lsbs;
	}
	
	/**
	 * id
	 */
	public void setLsbs(String lsbs){
		this.lsbs = lsbs;
	}
	
	/**
	 * 头像地址
	 */
	public String getImgUrl(){
		return imgUrl;
	}
	
	/**
	 * 头像地址
	 */
	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}
	
	/**
	 * 姓名
	 */
	public String getXm(){
		return xm;
	}
	
	/**
	 * 姓名
	 */
	public void setXm(String xm){
		this.xm = xm;
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
	 * 律所名称
	 */
	public String getLsswsmc(){
		return lsswsmc;
	}
	
	/**
	 * 律所名称
	 */
	public void setLsswsmc(String lsswsmc){
		this.lsswsmc = lsswsmc;
	}
	
	/**
	 * 专长
	 */
	public String getYwzc(){
		return ywzc;
	}
	
	/**
	 * 专长
	 */
	public void setYwzc(String ywzc){
		this.ywzc = ywzc;
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

}