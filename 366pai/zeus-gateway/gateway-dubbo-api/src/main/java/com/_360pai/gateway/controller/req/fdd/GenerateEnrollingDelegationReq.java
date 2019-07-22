package com._360pai.gateway.controller.req.fdd;

import java.io.Serializable;

/**
 * 描述：预招商委托协议
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/31 14:51
 */
public class GenerateEnrollingDelegationReq implements Serializable {

    private String  merchantActivityCode;//合同编号
    private String  seller;//委托人
    private String  sellerAgency;//送拍机构
    private String  merchantObject;//标的名称
    private String  referencePrice;//参考价
    private String  beginTime;//开始时间
    private String  endTime;//结束时间
    private String  sellerAddress;//甲方联系地址
    private String  sellerContactName;//联系人
    private String  sellerContactPhone;//联系电话
    private String  sellerAgencyAddress;//送拍机构地址
    private String  sellerAgencyContactName;//机构名称
    private String  sellerAgencyContactPhone;//联系电话
    private String  sellerIdNumber;//委托人证件号码
    private String  sellerPhone;//地址
    private String  sellerLegalRep;//
    private String  sellerLegalRepName;//
    private String  sellerLegalRepPhone;//
    private String  sellerAgencyLegalRepName;//
    private String  sellerAgencyLegalRepPhone;//
    private String  signTime;//签署日期
    private String  cost;//金额

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getMerchantActivityCode() {
        return merchantActivityCode;
    }

    public void setMerchantActivityCode(String merchantActivityCode) {
        this.merchantActivityCode = merchantActivityCode;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getSellerAgency() {
        return sellerAgency;
    }

    public void setSellerAgency(String sellerAgency) {
        this.sellerAgency = sellerAgency;
    }

    public String getMerchantObject() {
        return merchantObject;
    }

    public void setMerchantObject(String merchantObject) {
        this.merchantObject = merchantObject;
    }

    public String getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(String referencePrice) {
        this.referencePrice = referencePrice;
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

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getSellerContactName() {
        return sellerContactName;
    }

    public void setSellerContactName(String sellerContactName) {
        this.sellerContactName = sellerContactName;
    }

    public String getSellerContactPhone() {
        return sellerContactPhone;
    }

    public void setSellerContactPhone(String sellerContactPhone) {
        this.sellerContactPhone = sellerContactPhone;
    }

    public String getSellerAgencyAddress() {
        return sellerAgencyAddress;
    }

    public void setSellerAgencyAddress(String sellerAgencyAddress) {
        this.sellerAgencyAddress = sellerAgencyAddress;
    }

    public String getSellerAgencyContactName() {
        return sellerAgencyContactName;
    }

    public void setSellerAgencyContactName(String sellerAgencyContactName) {
        this.sellerAgencyContactName = sellerAgencyContactName;
    }

    public String getSellerAgencyContactPhone() {
        return sellerAgencyContactPhone;
    }

    public void setSellerAgencyContactPhone(String sellerAgencyContactPhone) {
        this.sellerAgencyContactPhone = sellerAgencyContactPhone;
    }

    public String getSellerIdNumber() {
        return sellerIdNumber;
    }

    public void setSellerIdNumber(String sellerIdNumber) {
        this.sellerIdNumber = sellerIdNumber;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public String getSellerLegalRep() {
        return sellerLegalRep;
    }

    public void setSellerLegalRep(String sellerLegalRep) {
        this.sellerLegalRep = sellerLegalRep;
    }

    public String getSellerLegalRepName() {
        return sellerLegalRepName;
    }

    public void setSellerLegalRepName(String sellerLegalRepName) {
        this.sellerLegalRepName = sellerLegalRepName;
    }

    public String getSellerLegalRepPhone() {
        return sellerLegalRepPhone;
    }

    public void setSellerLegalRepPhone(String sellerLegalRepPhone) {
        this.sellerLegalRepPhone = sellerLegalRepPhone;
    }

    public String getSellerAgencyLegalRepName() {
        return sellerAgencyLegalRepName;
    }

    public void setSellerAgencyLegalRepName(String sellerAgencyLegalRepName) {
        this.sellerAgencyLegalRepName = sellerAgencyLegalRepName;
    }

    public String getSellerAgencyLegalRepPhone() {
        return sellerAgencyLegalRepPhone;
    }

    public void setSellerAgencyLegalRepPhone(String sellerAgencyLegalRepPhone) {
        this.sellerAgencyLegalRepPhone = sellerAgencyLegalRepPhone;
    }
}
