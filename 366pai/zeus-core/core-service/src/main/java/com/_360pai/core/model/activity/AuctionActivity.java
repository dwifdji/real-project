
package com._360pai.core.model.activity;

import com._360pai.core.common.constants.ActivityEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分25秒
 */
@Getter
@Setter
public class AuctionActivity implements java.io.Serializable{
	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private ActivityEnum.Status status;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private java.util.Date updatedAt;
	/**
	 * 
	 */
	private Integer assetId;
	/**
	 * 
	 */
	private String mode;
	/**
	 * 
	 */
	private String assetType;
	/**
	 * 
	 */
	private String assetName;
	/**
	 * 
	 */
	private Integer subCategoryId;
	/**
	 * 
	 */
	private Integer categoryId;
	/**
	 * 
	 */
	private Integer agencyId;
	/**
	 * 
	 */
	private java.util.Date previewAt;
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
	private java.math.BigDecimal startingPrice;
	/**
	 * 
	 */
	private java.math.BigDecimal reservePrice;
	/**
	 * 
	 */
	private java.math.BigDecimal refPrice;
	/**
	 * 
	 */
	private java.math.BigDecimal deposit;
	/**
	 * 
	 */
	private java.math.BigDecimal commissionSeller;
	/**
	 * 
	 */
	private java.math.BigDecimal commissionBuyer;
	/**
	 * 
	 */
	private java.math.BigDecimal commissionPercentSeller;
	/**
	 * 
	 */
	private java.math.BigDecimal commissionPercentBuyer;
	/**
	 * 
	 */
	private Integer quantity;
	/**
	 * 
	 */
	private Integer bidCount;
	/**
	 * 
	 */
	private String code;
	/**
	 * 
	 */
	private Integer participantNumber;
	/**
	 * 增加拍延时周期，默认5分钟
	 */
	private Integer biddingExtension;
	/**
	 * 
	 */
	private java.math.BigDecimal increment;
	/**
	 * 
	 */
	private Boolean lockEndAt;
	/**
	 * 
	 */
	private java.math.BigDecimal reduction;
	/**
	 * 
	 */
	private Integer reductionPeriod;
	/**
	 * 
	 */
	private Boolean paidBySeller;
	/**
	 * 
	 */
	private Boolean extended;
	/**
	 * 
	 */
	private java.math.BigDecimal currentPrice;
	/**
	 * 
	 */
	private java.util.Date reducedAt;
	/**
	 *
	 */
	private java.util.Date incrementAt;
	/**
	 * 
	 */
	private String restrict;
	/**
	 * 
	 */
	private java.util.Date finishAt;
	/**
	 * 
	 */
	private Boolean isForceFinished;
	/**
	 * 
	 */
	private Integer assetCategoryGroupId;
	/**
	 * 
	 */
	private Integer assetCategoryId;
	/**
	 * 
	 */
	private Integer assetPropertyId;
	/**
	 * 
	 */
	private Integer referenceActivityId;
	/**
	 * 
	 */
	private String auctioneerName;
	/**
	 * 
	 */
	private String auctioneerPhone;
	/**
	 * 
	 */
	private String auctioneerQq;
	/**
	 * 
	 */
	private String displayName;
	/**
	 * 
	 */
	private Boolean beginNotified;
	/**
	 * 
	 */
	private Boolean endNotified;
	/**
	 * 
	 */
	private java.math.BigDecimal commissionChannelAgent;
	/**
	 * 
	 */
	private Integer staffId;
	/**
	 * 
	 */
	private java.util.Date operatorAt;
	/**
	 * 
	 */
	private Integer operatorId;
	/**
	 * 
	 */
	private String visibilityLevel;
	/**
	 *  优先购买人
	 */
	private String preemptivePerson;
	/**
	 *
	 */
	private java.lang.Integer currentLevel;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private java.lang.Boolean deleteFlag;
	/**
	 * 商品分类名称
	 */
	private java.lang.String busTypeName;

	/**
	 * 拍卖类型名称
	 */
	private String categoryName;
	/**
	 * 平台浏览量
	 */
	private int viewCount;

	private String imageUrl;

	private String subStatus;


	// 需隐藏的活动状态
	public static Set<ActivityEnum.Status> HIDDEN_STATUS = new HashSet<>();

	// Agency 可联拍
	public static Set<ActivityEnum.Status> UNIONABLE_STATUS = new HashSet<>();

	// 有效的活动状态
	public static Set<ActivityEnum.Status> VALID_STATUS = new HashSet<>();

	static  {
		HIDDEN_STATUS.add(ActivityEnum.Status.AGENCY_PENDING);
		HIDDEN_STATUS.add(ActivityEnum.Status.AGENCY_REJECT);
		HIDDEN_STATUS.add(ActivityEnum.Status.PLATFORM_REVIEWING);
		HIDDEN_STATUS.add(ActivityEnum.Status.PLATFORM_REJECTED);
		HIDDEN_STATUS.add(ActivityEnum.Status.PLATFORM_APPROVED);
		HIDDEN_STATUS.add(ActivityEnum.Status.CANCELLED);

		UNIONABLE_STATUS.add(ActivityEnum.Status.ONLINE);


		VALID_STATUS.add(ActivityEnum.Status.PLATFORM_REVIEWING);
		VALID_STATUS.add(ActivityEnum.Status.PLATFORM_REJECTED);
		VALID_STATUS.add(ActivityEnum.Status.PLATFORM_APPROVED);
		VALID_STATUS.add(ActivityEnum.Status.ONLINE);
		VALID_STATUS.add(ActivityEnum.Status.CANCELLED);
		VALID_STATUS.add(ActivityEnum.Status.UNCONFIRMED);
		VALID_STATUS.add(ActivityEnum.Status.SUCCESS);
		VALID_STATUS.add(ActivityEnum.Status.FAILED);
	}

}
