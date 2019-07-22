
package com.winback.core.condition.assistant;

import com.winback.arch.core.abs.DaoCondition;
import lombok.Data;

import java.util.Date;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年02月15日 09时30分28秒
 */
@Data
public class TBuriedPointCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 埋点key
	 */
	private String pointKey;
	/**
	 * 埋点描述
	 */
	private String pointDesc;
	/**
	 * 埋点类型
	 */
	private String pointType;
	/**
	 * 业务id
	 */
	private String buzId;
	/**
	 * 业务参数
	 */
	private String buzParams;
	/**
	 * 设备标识
	 */
	private String deviceMark;
	/**
	 * 安卓：0，IOS：1，web：2
	 */
	private String deviceType;
	/**
	 * 设备token
	 */
	private String deviceToken;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户类型
	 */
	private String userType;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * ip
	 */
	private String ip;

}