
package com._360pai.core.facade.enrolling.req;

import lombok.Data;
import sun.security.x509.SerialNumber;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年02月14日 10时04分51秒
 */
@Data
public class EnrollingActivityImportRealDataVo implements java.io.Serializable{

	/**
	 *
	 */
	private java.lang.Integer id;
	/**
	 *
	 */
	private java.lang.Integer activityId;
	/**
	 * 招商会名称
	 */
	private java.lang.String name;
	/**
	 * 所在地
	 */
	private java.lang.String pawnLocation;
	/**
	 * 省份
	 */
	private java.lang.String pawnPro;
	/**
	 * 城市
	 */
	private java.lang.String pawnCity;
	/**
	 * 地区
	 */
	private java.lang.String pawnArea;
	/**
	 * 省份id
	 */
	private java.lang.String pawnProId;
	/**
	 * 城市id
	 */
	private java.lang.String pawnCityId;
	/**
	 * 地区id
	 */
	private java.lang.String pawnAreaId;
	/**
	 * 市场参考价
	 */
	private java.lang.String refPrice;
	/**
	 * 商品类型
	 */
	private java.lang.String busTypeName;
	/**
	 * 权力人类型
	 */
	private java.lang.String powerType;
	/**
	 * 共有情况
	 */
	private java.lang.String ownedCondition;
	/**
	 * 共有数量
	 */
	private java.lang.String ownedNum;
	/**
	 * 所有权来源
	 */
	private java.lang.String ownedSource;
	/**
	 * 房地产权证号
	 */
	private java.lang.String realLicense;
	/**
	 * 土地使用权证号
	 */
	private java.lang.String landLicense;
	/**
	 * 建筑面积
	 */
	private java.lang.String buildArea;
	/**
	 * 登记日期
	 */
	private java.lang.String recordDate;
	/**
	 * 房屋类型
	 */
	private java.lang.String housingType;
	/**
	 * 套内面积
	 */
	private java.lang.String insideSpace;
	/**
	 * 装修程度
	 */
	private java.lang.String decorate;
	/**
	 * 竣工日期
	 */
	private java.lang.String completionDate;
	/**
	 * 总层数
	 */
	private java.lang.String totalNum;
	/**
	 * 所在层数
	 */
	private java.lang.String storyNum;
	/**
	 * 承担方式
	 */
	private java.lang.String bearWay;
	/**
	 * 公用事业欠费
	 */
	private java.lang.String publicOwe;
	/**
	 * 物业管理欠费
	 */
	private java.lang.String tenementOwe;
	/**
	 * 增值税及附加
	 */
	private java.lang.String vatAddition;
	/**
	 * 土地增值税
	 */
	private java.lang.String landVat;
	/**
	 * 个人所得税
	 */
	private java.lang.String personalVat;
	/**
	 * 备注
	 */
	private java.lang.String remark;
	/**
	 * 土地面积
	 */
	private java.lang.String landArea;
	/**
	 * 分摊面积
	 */
	private java.lang.String sharingArea;
	/**
	 * 土地用途
	 */
	private java.lang.String landUse;
	/**
	 * 使用权来源
	 */
	private java.lang.String useSource;
	/**
	 * 使用期限
	 */
	private java.lang.String usePeriod;
	/**
	 * 是否有抵押
	 */
	private java.lang.String ifMortgage;
	/**
	 * 权利人
	 */
	private java.lang.String holder;
	/**
	 * 权利种类
	 */
	private java.lang.String rightType;
	/**
	 * 他项产权号
	 */
	private java.lang.String otherEquity;
	/**
	 * 权利价值
	 */
	private java.lang.String rightValue;
	/**
	 * 使用情况
	 */
	private java.lang.String usageType;
	/**
	 * 户籍/工商注册
	 */
	private java.lang.String registration;
	/**
	 * 户籍数/工商注册数
	 */
	private java.lang.String registrationNum;
	/**
	 * 租赁情况
	 */
	private java.lang.String leaseCondition;
	/**
	 * 租赁期限
	 */
	private java.lang.String leaseDeadline;
	/**
	 * 使用人
	 */
	private java.lang.String leaseUser;
	/**
	 * 押金
	 */
	private java.lang.String leaseDeposit;
	/**
	 * 租金
	 */
	private java.lang.String leaseRent;
	/**
	 * 租金支付至
	 */
	private java.lang.String leasePayTo;
	/**
	 * 与当事人关系
	 */
	private java.lang.String relationship;
	/**
	 * 室内物品
	 */
	private java.lang.String indoorGoods;
	/**
	 * 联系电话
	 */
	private java.lang.String leasePhone;
	/**
	 * 备注
	 */
	private java.lang.String leaseRemark;
	/**
	 * 有无限制
	 */
	private java.lang.String unlimited;
	/**
	 * 限制人
	 */
	private java.lang.String limitOne;
	/**
	 * 限制方式
	 */
	private java.lang.String limitType;
	/**
	 * 其他限制
	 */
	private java.lang.String limitOther;
	/**
	 * 限制备注
	 */
	private java.lang.String limitRemark;
	/**
	 * 处置服务商
	 */
	private java.lang.String disposalService;
	/**
	 * 资金服务商
	 */
	private java.lang.String fundProvider;
	/**
	 * 上传业务人员ID
	 */
	private java.lang.Integer operatorId;
	/**
	 *
	 */
	private java.lang.String extra;
	/**
	 *
	 */
	private java.util.Date beginAt;
	/**
	 *
	 */
	private java.util.Date endAt;
	/**
	 *
	 */
	private java.lang.Boolean deleteFlag;
	/**
	 *
	 */
	private java.util.Date createTime;
	/**
	 *
	 */
	private java.util.Date updateTime;


	/**
	 *
	 */
	private java.lang.String partyId;


	private java.lang.String serialNumber;

	private java.lang.String contactPhone;

	private java.lang.String contactPerson;


	private java.lang.String contactName;


}
