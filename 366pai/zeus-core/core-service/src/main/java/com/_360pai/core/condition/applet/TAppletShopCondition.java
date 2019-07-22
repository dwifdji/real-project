
package com._360pai.core.condition.applet;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
@Data
public class TAppletShopCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 店铺名称
	 */
	private String name;
	/**
	 * 店铺头像
	 */
	private String logoUrl;
	/**
	 * 
	 */
	private Integer partyId;
	/**
	 * 手机号
	 */
	private java.lang.String mobile;
	/**
	 * 关注数量
	 */
	private Integer favoriteCount;
	/**
	 * 浏览量
	 */
	private Integer viewCount;
	/**
	 * 商品数量
	 */
	private Integer productCount;
	/**
	 * 邀请码
	 */
	private Integer inviteCode;
	/**
	 * 邀请类型（SHOP：店铺，AGENCY：机构）
	 */
	private String inviteType;
	/**
	 * 开店返佣比例
	 */
	private java.math.BigDecimal shopCommissionPercent;
	/**
	 * 小程序二维码链接
	 */
	private java.lang.String appletQrCode;
	/**
	 * 店铺邀请码
	 */
	private java.lang.String inviteQrCode;
	/**
	 * 开店服务费
	 */
	private java.math.BigDecimal serviceCharge;
	/**
	 * 联系人电话
	 */
	private java.lang.String contactPhone;
	/**
	 * 是否隐藏联系电话中间4位
	 */
	private java.lang.Boolean isHideContactPhone;
	/**
	 * 是否删除（0 否 1 是）
	 */
	private Boolean isDelete;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
}