
package com._360pai.core.condition.activity;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 *
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 *
 */
@Getter
@Setter
public class AuctionActivityCondition implements DaoCondition {

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String status;
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
     *
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
     * 优先购买人
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

    private String q;
    private String createdAtFrom;
    private String createdAtTo;
    private String previewAtFrom;
    private String previewAtTo;
    private String beginAtFrom;
    private String beginAtTo;


}