package com._360pai.gateway.controller.req.fdd;

import java.io.Serializable;

/**
 * 描述：银行类委托模板参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/31 14:30
 */
public class GenerateBankDelegationReq implements Serializable {

    private String activityCode;//合同编号
    private String seller;//委托方
    private String auctionFirm;//送拍机构
    private String lotName;//标的名称
    private String auctionMethod;//拍卖方式
    private String reservePrice;//保留价
     private String beginTime;//开拍时间
    private String endTime;//开拍结束时间
    private String sellerCommissionRate;//分佣的比例
    private String paymentDuration;//佣金付款天数
    private String deliveryDuration;//解锁天数
    private String bankAccountName;//账号名
    private String bankName;//银行名称
    private String bankAccount;//银行账号
    private String sellerIdNumber;//委托方证件及号码
    private String sellerAddress;//委托方住址
    private String sellerLegalRep;//法定代表人
    private String sellerPhone;//联系电话
    private String auctionFirmAddress;//拍卖方地址
    private String auctionFirmLegalRep;//拍卖方姓名
    private String auctionFirmPhone;//拍卖方联系电话


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

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public String getAuctionMethod() {
        return auctionMethod;
    }

    public void setAuctionMethod(String auctionMethod) {
        this.auctionMethod = auctionMethod;
    }

    public String getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(String reservePrice) {
        this.reservePrice = reservePrice;
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

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
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