
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分16秒
 */
public class CityCondition implements DaoCondition{

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
	private Integer provinceId;
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
	public Integer getProvinceId(){
		return provinceId;
	}
	
	/**
	 * 
	 */
	public void setProvinceId(Integer provinceId){
		this.provinceId = provinceId;
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