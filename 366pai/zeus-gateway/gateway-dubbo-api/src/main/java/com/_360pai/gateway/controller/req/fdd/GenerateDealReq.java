package com._360pai.gateway.controller.req.fdd;

import java.io.Serializable;

/**
 * 描述：成交协议合同请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/31 14:49
 */
public class GenerateDealReq implements Serializable {

    private String activityCode;//合同编号

    private String seller;//委托人

    private String auctionFirm;//送拍机构

    private String buyer;//买受人

    private String unionAuctionFirm;//连拍机构

    private String auctionPeriod;//竞拍时间

    private String orderTime;//成交时间

    private String lotCode;//拍品编号

    private String hammerPrice;//成交金额

    private String lotName;//拍品名称

    private String sellerCommissionRate;//委托人佣金比例

    private String sellerCommissionFee;//委托人定额佣金

    private String sellerCommission;//应收佣金

    private String buyerCommissionRate;//买受人佣金比例

    private String buyerCommissionFee;//买受人定额佣金

    private String buyerCommission;//买受人应收佣金

    private String totalAmountChn;//合计金额

    private String totalAmount;//合计金额

    private String paymentPeriod;//预计支付时间


    public String getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(String paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getAuctionFirm() {
        return auctionFirm;
    }

    public void setAuctionFirm(String auctionFirm) {
        this.auctionFirm = auctionFirm;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getUnionAuctionFirm() {
        return unionAuctionFirm;
    }

    public void setUnionAuctionFirm(String unionAuctionFirm) {
        this.unionAuctionFirm = unionAuctionFirm;
    }

    public String getAuctionPeriod() {
        return auctionPeriod;
    }

    public void setAuctionPeriod(String auctionPeriod) {
        this.auctionPeriod = auctionPeriod;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getLotCode() {
        return lotCode;
    }

    public void setLotCode(String lotCode) {
        this.lotCode = lotCode;
    }

    public String getHammerPrice() {
        return hammerPrice;
    }

    public void setHammerPrice(String hammerPrice) {
        this.hammerPrice = hammerPrice;
    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public String getSellerCommissionRate() {
        return sellerCommissionRate;
    }

    public void setSellerCommissionRate(String sellerCommissionRate) {
        this.sellerCommissionRate = sellerCommissionRate;
    }

    public String getSellerCommissionFee() {
        return sellerCommissionFee;
    }

    public void setSellerCommissionFee(String sellerCommissionFee) {
        this.sellerCommissionFee = sellerCommissionFee;
    }

    public String getSellerCommission() {
        return sellerCommission;
    }

    public void setSellerCommission(String sellerCommission) {
        this.sellerCommission = sellerCommission;
    }

    public String getBuyerCommissionRate() {
        return buyerCommissionRate;
    }

    public void setBuyerCommissionRate(String buyerCommissionRate) {
        this.buyerCommissionRate = buyerCommissionRate;
    }

    public String getBuyerCommissionFee() {
        return buyerCommissionFee;
    }

    public void setBuyerCommissionFee(String buyerCommissionFee) {
        this.buyerCommissionFee = buyerCommissionFee;
    }

    public String getBuyerCommission() {
        return buyerCommission;
    }

    public void setBuyerCommission(String buyerCommission) {
        this.buyerCommission = buyerCommission;
    }

    public String getTotalAmountChn() {
        return totalAmountChn;
    }

    public void setTotalAmountChn(String totalAmountChn) {
        this.totalAmountChn = totalAmountChn;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
