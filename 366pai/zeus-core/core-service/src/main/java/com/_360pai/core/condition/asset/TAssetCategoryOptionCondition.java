
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月26日 16时17分16秒
 */
@Data
public class TAssetCategoryOptionCondition implements DaoCondition{

	/**
	 *  自增
	 */
	private Integer id;
	/**
	 * 拍品类型ID
	 */
	private Integer assetCategoryId;
	/**
	 * 子类型名称
	 */
	private String name;
	/**
	 * 是否启用  0：不启用  1：启用
	 */
	private Integer enable;
	private Integer orderNum;

	/**
	 *  自增
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 *  自增
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 拍品类型ID
	 */
	public Integer getAssetCategoryId(){
		return assetCategoryId;
	}
	
	/**
	 * 拍品类型ID
	 */
	public void setAssetCategoryId(Integer assetCategoryId){
		this.assetCategoryId = assetCategoryId;
	}
	
	/**
	 * 子类型名称
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 子类型名称
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 是否启用  0：不启用  1：启用
	 */
	public Integer getEnable(){
		return enable;
	}
	
	/**
	 * 是否启用  0：不启用  1：启用
	 */
	public void setEnable(Integer enable){
		this.enable = enable;
	}

}