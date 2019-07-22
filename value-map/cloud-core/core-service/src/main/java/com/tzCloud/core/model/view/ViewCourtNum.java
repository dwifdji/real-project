
package com.tzCloud.core.model.view;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年04月22日 12时40分08秒
 */
public class ViewCourtNum implements java.io.Serializable{

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
	 * 省份
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
