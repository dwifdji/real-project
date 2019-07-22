
package com.tzCloud.core.condition.legalEngine;

import com.tzCloud.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月19日 15时41分11秒
 */
@Data
public class TCourtCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 法院名称
	 */
	private String name;
	/**
	 * 法院名称
	 */
	private java.lang.String simpleName;
	/**
	 * 法院类型（基层法院，中层法院，高级法院，最高法院）
	 */
	private String type;
	/**
	 * 法院地址
	 */
	private String address;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 其他联系方式
	 */
	private String otherPhone;
	/**
	 * 工作时间
	 */
	private String workTime;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 图片链接
	 */
	private String imgUrl;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 删除标志
	 */
	private Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

}