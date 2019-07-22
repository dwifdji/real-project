
package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分26秒
 */
@Data
public class CityReq extends RequestModel {

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

	private Integer cityId;


	private Integer areaId;

}
