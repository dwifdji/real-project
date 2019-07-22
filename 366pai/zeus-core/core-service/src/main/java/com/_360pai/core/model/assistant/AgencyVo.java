
package com._360pai.core.model.assistant;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分26秒
 */
@Getter
@Setter
public class AgencyVo implements java.io.Serializable{

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
	private String imageUrl;

	/**
	 *
	 */
	private String logoUrl;



	private String introduction;

	private String appletQrCode;
	private Boolean showAppletQrCode;

	/**
	 * 手机号
	 */
	private java.lang.String mobile;
	/**
	 * 机构编号
	 */
	private java.lang.String code;
	/**
	 * 营业执照图片
	 */
	private java.lang.String licenseImg;
	/**
	 * 许可证图片
	 */
	private java.lang.String qualificationImg;

}
