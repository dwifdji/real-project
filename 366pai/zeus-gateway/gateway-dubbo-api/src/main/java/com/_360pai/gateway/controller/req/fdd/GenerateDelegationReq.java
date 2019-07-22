package com._360pai.gateway.controller.req.fdd;

import java.io.Serializable;

/**
 * 描述：委托协议请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/31 14:30
 */
public class GenerateDelegationReq implements Serializable {

    private String activityCode;//合同编号
    private String partyName;//委托人姓名
    private String agencyName;//送拍机构名称
    private String activityName;//标的名称
    private String auctionMethod;//拍卖方式
    private String refPrice;//拍卖市场参考价
    private String reservePrice;//拍卖保留价
    private String beginYear;//开拍年
    private String beginTime;//开拍时间
    private String endTime;//开拍结束时间
    private String sellerCommissionRate;//分佣的比例
    private String paymentDuration;//佣金付款天数
    private String deliveryDuration;//解锁天数
    private String sellerIdNumber;//委托方证件号码
    private String sellerAddress;//委托方住址
    private String sellerLegalRep;//法定代表人
    private String sellerPhone;//联系号码
    private String auctionFirmAddress;//拍卖方地址
    private String auctionFirmLegalRep;//拍卖方姓名
    private String auctionFirmPhone;//拍卖方联系电话

    public String getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(String reservePrice) {
        this.reservePrice = reservePrice;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getAuctionMethod() {
        return auctionMethod;
    }

    public void setAuctionMethod(String auctionMethod) {
        this.auctionMethod = auctionMethod;
    }

    public String getRefPrice() {
        return refPrice;
    }

    public void setRefPrice(String refPrice) {
        this.refPrice = refPrice;
    }

    public String getBeginYear() {
        return beginYear;
    }

    public void setBeginYear(String beginYear) {
        this.beginYear = beginYear;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSellerCommissionRate() {
        return sellerCommissionRate;
    }

    public void setSellerCommissionRate(String sellerCommissionRate) {
        this.sellerCommissionRate = sellerCommissionRate;
    }

    public String getPaymentDuration() {
        return paymentDuration;
    }

    public void setPaymentDuration(String paymentDuration) {
        this.paymentDuration = paymentDuration;
    }

    public String getDeliveryDuration() {
        return deliveryDuration;
    }

    public void setDeliveryDuration(String deliveryDuration) {
        this.deliveryDuration = deliveryDuration;
    }

    public String getSellerIdNumber() {
        return sellerIdNumber;
    }

    public void setSellerIdNumber(String sellerIdNumber) {
        this.sellerIdNumber = sellerIdNumber;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getSellerLegalRep() {
        return sellerLegalRep;
    }

    public void setSellerLegalRep(String sellerLegalRep) {
        this.sellerLegalRep = sellerLegalRep;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public String getAuctionFirmAddress() {
        return auctionFirmAddress;
    }

    public void setAuctionFirmAddress(String auctionFirmAddress) {
        this.auctionFirmAddress = auctionFirmAddress;
    }

    public String getAuctionFirmLegalRep() {
        return auctionFirmLegalRep;
    }

    public void setAuctionFirmLegalRep(String auctionFirmLegalRep) {
        this.auctionFirmLegalRep = auctionFirmLegalRep;
    }

    public String getAuctionFirmPhone() {
        return auctionFirmPhone;
    }

    public void setAuctionFirmPhone(String auctionFirmPhone) {
        this.auctionFirmPhone = auctionFirmPhone;
    }
}
