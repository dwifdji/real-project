
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
@Data
public class TAssetCategoryCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 债券类型名称
	 */
	private String name;
	/**
	 * 二级类型的标题
	 */
	private String optionTitle;
	/**
	 * 债券业务类型 AUCTION:拍卖  ENROLLING:预招商
	 */
	private String businessType;
	/**
	 * 拍卖模式 SELL:转让类 SERVICE:服务类
	 */
	private String dealMode;
	/**
	 * 是否启用 1:启用 0:不启用
	 */
	private Boolean enabled;


	private Integer orderNum;
	
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
	 * 债券类型名称
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 债券类型名称
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 债券业务类型 AUCTION:拍卖  ENROLLING:预招商
	 */
	public String getBusinessType(){
		return businessType;
	}
	
	/**
	 * 债券业务类型 AUCTION:拍卖  ENROLLING:预招商
	 */
	public void setBusinessType(String businessType){
		this.businessType = businessType;
	}
	
	/**
	 * 拍卖模式 SELL:转让类 SERVICE:服务类
	 */
	public String getDealMode(){
		return dealMode;
	}
	
	/**
	 * 拍卖模式 SELL:转让类 SERVICE:服务类
	 */
	public void setDealMode(String dealMode){
		this.dealMode = dealMode;
	}
	
	/**
	 * 是否启用 1:启用 0:不启用
	 */
	public Boolean getEnabled(){
		return enabled;
	}
	
	/**
	 * 是否启用 1:启用 0:不启用
	 */
	public void setEnabled(Boolean enabled){
		this.enabled = enabled;
	}

}