package com._360pai.core.facade.payment.vo;

import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;
import com._360pai.core.facade.activity.vo.FileInfo;
import com._360pai.core.facade.asset.vo.AssetVo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author xdrodger
 * @Title: AuctionOrderVo
 * @ProjectName zeus
 * @Description:
 * @date 05/09/2018 16:47
 */
@Getter
@Setter
public class AuctionOrderVo implements Serializable {
    private String id;
    private BigDecimal amount;
    private String amountDesc;
    private Boolean autoHandleDelay;
    private String buyerAgencyName;
    private PartyAccount buyer;
    private BigDecimal commission;
    private BigDecimal deposit;
    private String depositDesc;
    private Date createdAt;
    private String sellerAgencyName;
    private PartyAccount seller;
    private PartyAccount sellerContactPerson;
    private String status;
    private String statusDesc;
    private FileInfo dealAgreement;
    private List<FeeInfo> feeInfos;
    private AuctionActivityVo activity;
    private AssetVo asset;
    /**
     * 买家是否支付佣金
     */
    private Boolean buyerPaidCommission;
    /**
     * 买家是否支付订单
     */
    private Boolean buyerPaidOrder;
    /**
     * 卖家是否支付佣金
     */
    private Boolean sellerPaidCommission;
    /**
     * 卖家是否支付订单
     */
    private Boolean sellerPaidOrder;
    /**
     * 模板名称
     */
    private String assetCategory;
    /**
     * 承拍分润比例
     */
    private java.math.BigDecimal serveBuyerPercent;
    /**
     * 送拍分润比例
     */
    private java.math.BigDecimal serveSellerPercent;
    /**
     * 委托人佣金
     */
    private BigDecimal sellerCommission;
    private String sellerCommissionDesc;
    /**
     * 买受人佣金
     */
    private BigDecimal buyerCommission;
    private String buyerCommissionDesc;
    /**
     * 委托人需支付金额
     */
    private BigDecimal sellerNeedPayAmount;
    private String sellerNeedPayAmountDesc;
    /**
     * 买受人需支付金额
     */
    private BigDecimal buyerNeedPayAmount;
    private String buyerNeedPayAmountDesc;
    /**
     * 送拍机构佣金收入
     */
    private BigDecimal serveSellerCommission;
    /**
     * 联拍机构佣金收入
     */
    private BigDecimal serveBuyerCommission;
    private String buyerHasPayEnd;
    private String sellerHasPayEnd;
    /**
     * 订单来源
     */
    private String orderSource;
}
