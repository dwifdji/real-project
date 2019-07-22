
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月25日 14时53分26秒
 */
public class TownCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private Integer areaId;
	/**
	 * 
	 */
	private String cityCode;
	/**
	 * 
	 */
	private Integer code;
	/**
	 * 
	 */
	private Float lat;
	/**
	 * 
	 */
	private Integer levelType;
	/**
	 * 
	 */
	private Float lng;
	/**
	 * 
	 */
	private String mergerName;
	/**
	 * 
	 */
	private String pinyin;
	/**
	 * 
	 */
	private String shortName;
	/**
	 * 
	 */
	private String zipCode;
	
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
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 
	 */
	public Integer getAreaId(){
		return areaId;
	}
	
	/**
	 * 
	 */
	public void setAreaId(Integer areaId){
		this.areaId = areaId;
	}
	
	/**
	 * 
	 */
	public String getCityCode(){
		return cityCode;
	}
	
	/**
	 * 
	 */
	public void setCityCode(String cityCode){
		this.cityCode = cityCode;
	}
	
	/**
	 * 
	 */
	public Integer getCode(){
		return code;
	}
	
	/**
	 * 
	 */
	public void setCode(Integer code){
		this.code = code;
	}
	
	/**
	 * 
	 */
	public Float getLat(){
		return lat;
	}
	
	/**
	 * 
	 */
	public void setLat(Float lat){
		this.lat = lat;
	}
	
	/**
	 * 
	 */
	public Integer getLevelType(){
		return levelType;
	}
	
	/**
	 * 
	 */
	public void setLevelType(Integer levelType){
		this.levelType = levelType;
	}
	
	/**
	 * 
	 */
	public Float getLng(){
		return lng;
	}
	
	/**
	 * 
	 */
	public void setLng(Float lng){
		this.lng = lng;
	}
	
	/**
	 * 
	 */
	public String getMergerName(){
		return mergerName;
	}
	
	/**
	 * 
	 */
	public void setMergerName(String mergerName){
		this.mergerName = mergerName;
	}
	
	/**
	 * 
	 */
	public String getPinyin(){
		return pinyin;
	}
	
	/**
	 * 
	 */
	public void setPinyin(String pinyin){
		this.pinyin = pinyin;
	}
	
	/**
	 * 
	 */
	public String getShortName(){
		return shortName;
	}
	
	/**
	 * 
	 */
	public void setShortName(String shortName){
		this.shortName = shortName;
	}
	
	/**
	 * 
	 */
	public String getZipCode(){
		return zipCode;
	}
	
	/**
	 * 
	 */
	public void setZipCode(String zipCode){
		this.zipCode = zipCode;
	}

}