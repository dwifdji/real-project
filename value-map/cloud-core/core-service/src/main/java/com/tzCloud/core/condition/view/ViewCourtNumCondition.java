
package com.tzCloud.core.condition.view;

import com.tzCloud.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月22日 12时40分08秒
 */
public class ViewCourtNumCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 法院名称
	 */
	private String courtName;
	/**
	 * 法院文书统计数量
	 */
	private Integer courtNum;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 城市
	 */
	private String cityName;
	
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
	 * 法院名称
	 */
	public String getCourtName(){
		return courtName;
	}
	
	/**
	 * 法院名称
	 */
	public void setCourtName(String courtName){
		this.courtName = courtName;
	}
	
	/**
	 * 法院文书统计数量
	 */
	public Integer getCourtNum(){
		return courtNum;
	}
	
	/**
	 * 法院文书统计数量
	 */
	public void setCourtNum(Integer courtNum){
		this.courtNum = courtNum;
	}
	
	/**
	 * 省份
	 */
	public String getProvince(){
		return province;
	}
	
	/**
	 * 省份
	 */
	public void setProvince(String province){
		this.province = province;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}