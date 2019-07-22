
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月17日 15时47分34秒
 */
@Data
public class TAgencyCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 * 机构名
	 */
	private java.lang.String name;
	/**
	 * 名称拼音
	 */
	private java.lang.String pinyin;
	/**
	 * 手机号
	 */
	private java.lang.String mobile;
	/**
	 * 机构编号
	 */
	private java.lang.String code;
	/**
	 * 机构简称
	 */
	private java.lang.String shortName;
	/**
	 * 注册地址
	 */
	private java.lang.String registerAddress;
	/**
	 * 办公地址
	 */
	private java.lang.String address;
	/**
	 * 注册城市
	 */
	private java.lang.String registerCityId;
	/**
	 * 办公城市
	 */
	private java.lang.String cityId;
	/**
	 * 省id
	 */
	private java.lang.String provinceId;
	/**
	 * 注册省id
	 */
	private java.lang.String registerProvinceId;
	/**
	 * 区县id
	 */
	private java.lang.String areaId;
	/**
	 * 注册区县id
	 */
	private java.lang.String registerAreaId;
	/**
	 * 营业执照
	 */
	private java.lang.String license;
	/**
	 * 营业执照图片
	 */
	private java.lang.String licenseImg;
	/**
	 * 法人
	 */
	private java.lang.String legalPerson;
	/**
	 * 身份证号
	 */
	private java.lang.String idCard;
	/**
	 * 身份证正面照
	 */
	private java.lang.String idCardFrontImg;
	/**
	 * 身份证反面照
	 */
	private java.lang.String idCardBackImg;
	/**
	 * 许可证签发日期
	 */
	private java.util.Date qualifiedBegin;
	/**
	 * 许可证结束日期
	 */
	private java.util.Date qualifiedEnd;
	/**
	 * 许可证图片
	 */
	private java.lang.String qualificationImg;
	/**
	 * 许可证号
	 */
	private java.lang.String qualificationNumber;
	/**
	 * 授权书
	 */
	private java.lang.String authorizationImg;
	/**
	 * 开户许可证
	 */
	private java.lang.String accountLicense;
	/**
	 * 营业开始时间
	 */
	private java.util.Date businessBegin;
	/**
	 * 营业结束时间
	 */
	private java.util.Date businessEnd;
	/**
	 * 备注
	 */
	private java.lang.String remark;
	/**
	 * 成拍分成
	 */
	private java.lang.Integer serveBuyerPercent;
	/**
	 * 送拍分成
	 */
	private java.lang.Integer serveSellerPercent;
	/**
	 * 状态(0:无效,1:有效)
	 */
	private java.lang.Integer status;
	/**
	 * 机构自我介绍
	 */
	private java.lang.String selfIntroduction;
	/**
	 * 平台给机构的介绍
	 */
	private java.lang.String introduction;
	/**
	 * 法大大Id
	 */
	private java.lang.String fadadaId;
	/**
	 * 法大大邮箱
	 */
	private java.lang.String fadadaEmail;
	/**
	 * 东方付通Id
	 */
	private java.lang.String dfftId;
	/**
	 * 是否绑定东方付通
	 */
	private java.lang.Integer payBind;
	/**
	 * 是否是机构代理商
	 */
	private java.lang.Integer isChannelAgent;
	/**
	 * logo地址
	 */
	private java.lang.String logoUrl;
	/**
	 * 机构介绍图片
	 */
	private java.lang.String imgUrl;
	/**
	 * 站点状态(ONLINE、CLOSED)
	 */
	private java.lang.String websiteStatus;
	/**
	 * 是否允许未开通法大大下发布预招商 0 否 1 是
	 */
	private java.lang.Boolean operWithoutFadada;
	/**
	 * 开店返佣比例
	 */
	private java.math.BigDecimal shopCommissionPercent;
	/**
	 * 小程序二维码链接
	 */
	private java.lang.String appletQrCode;

	/**
	 * 开户人员
	 */
	private java.lang.Integer openAccountOperatorId;
	/**
	 * 业务对接人
	 */
	private java.lang.Integer businessOperatorId;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private java.lang.Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	/**
	 * 联排设置
	 */
	private java.lang.Integer isJoint;
	/**
	 * 受托人
	 */
	private java.lang.String trustee;
	/**
	 * 受托人联系方式
	 */
	private java.lang.String trusteePhone;
}