
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
@Data
public class PartnerAgencyCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private String title;
	/**
	 * 
	 */
	private String url;
	/**
	 * 
	 */
	private String img;
	/**
	 * 
	 */
	private Integer orderNum;
	/**
	 *
	 */
	private Integer agencyId;
	
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
	 * 
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * 
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	 * 
	 */
	public String getUrl(){
		return url;
	}
	
	/**
	 * 
	 */
	public void setUrl(String url){
		this.url = url;
	}
	
	/**
	 * 
	 */
	public String getImg(){
		return img;
	}
	
	/**
	 * 
	 */
	public void setImg(String img){
		this.img = img;
	}
	
	/**
	 * 
	 */
	public Integer getOrderNum(){
		return orderNum;
	}
	
	/**
	 * 
	 */
	public void setOrderNum(Integer orderNum){
		this.orderNum = orderNum;
	}

}