package com._360pai.core.facade.activity.vo;

import com._360pai.core.facade.account.vo.AgencyVo;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.account.vo.StaffVo;
import com._360pai.core.facade.asset.vo.AssetVo;
import com._360pai.core.facade.payment.vo.AuctionOrderVo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author xdrodger
 * @Title: ActivityVo
 * @ProjectName zeus-parent
 * @Description:
 * @date 04/09/2018 15:52
 */
@Getter
@Setter
public class AuctionActivityVo implements Serializable {
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
    private String statusDesc;
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
    private String modeDesc;
    /**
     *
     */
    private String assetType;
    /**
     *
     */
    private String subStatus;
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
    private String startingPriceDesc;
    /**
     *
     */
    private java.math.BigDecimal refPrice;
    /**
     *
     */
    private java.math.BigDecimal deposit;

    private String depositDesc;
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
    private String operator;
    /**
     *
     */
    private String visibilityLevel;

    private AgencyVo agency;

    private PartyAccount auctioneer;

    private FileInfo dealAgreement;

    private FileInfo sentAgreement;

    private FileInfo additionalAgreement;

    private PartyAccount staff;

    private PartyAccount seller;

    private PartyAccount buyer;

    private String orderId;

    private AssetVo asset;

    private Integer viewCount = 0;
    private Integer myViewCount = 0;
    private Integer unionParticipantNumber = 0;

    private String unitedStatus;

    private String cityName;
    private String categoryName;
    /**
     * 包含图片的JSON数据
     */
    private com.alibaba.fastjson.JSONObject extra;

    private String onlineStatus;
    private String onlineStatusDesc;
    /**
     * 签署类型 0 无 1 委托协议 2 委托补充协议
     */
    private String needToSign;
    /**
     * 尽调报告协议授权状态  00:未授权协议  10：已授权协议  20: 不可授权协议
     */
    private String reportProtocolStatus;

    private String imageUrl;

    private String agencyName;
    private String sellerName;
}
