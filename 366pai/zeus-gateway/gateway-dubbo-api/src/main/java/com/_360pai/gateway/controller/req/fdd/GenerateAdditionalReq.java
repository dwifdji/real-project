package com._360pai.gateway.controller.req.fdd;

import java.io.Serializable;

/**
 * 描述：委托补充协议生成请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/31 14:50
 */
public class GenerateAdditionalReq implements Serializable {
    private String seller;//委托方

    private String auctionFirm;//送拍机构

    private String signTime;//签订日期

    private String activityCode;//合同编号

    private String reservePrice;//原保留价

    private String newReservePrice;//新保留价

    private String sellerIdNumber;//委托人证件号

    private String sellerAddress;//委托人住址

    private String sellerLegalRep;//委托人法定代表人

    private String sellerPhone;//委托人联系电话

    private String auctionFirmAddress;//送拍机构地址

    private String auctionFirmLegalRep;//送拍机构法定代表人

    private String auctionFirmPhone;//送拍机构联系电话

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

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(String reservePrice) {
        this.reservePrice = reservePrice;
    }

    public String getNewReservePrice() {
        return newReservePrice;
    }

    public void setNewReservePrice(String newReservePrice) {
        this.newReservePrice = newReservePrice;
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
