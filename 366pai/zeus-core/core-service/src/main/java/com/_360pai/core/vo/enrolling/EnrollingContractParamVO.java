package com._360pai.core.vo.enrolling;

import java.io.Serializable;

public class EnrollingContractParamVO implements Serializable{

	private String code;// 预招商活动编号
	private String name;// 预招商活动名称
	private String refPrice;// 市场参考价/债权本金
	private String beginAt;// 报名开始时间
	private String endAt;// 报名结束时间
	private String cost;// 发布服务费

    private String commissionPercent;// 预招商成交佣金
    private String agencyName;// 送拍机构
	private String agencyPhone;// 送拍方联系方式
    private String agencyAddress;// 送拍联系地址
    private String userName;//用户名称
    private String userPhone;//用户联系电话
    private String userAddress;//用户地址
	private String comName;//公司名称
	private String comPhone;// 公司联系电话
	private String comAddress;// 公司地址
    private String userAgencyName;//上拍机构名称
    private String userAgencyPhone;// 上拍机构联系电话
    private String userAgencyAddress;// 上拍机构联系地址

    private String userAgencylicense;//证件号码
    private String deposit;//保证金

    private String userIdNumber;//
    private String comIdNumber;//


    public String getUserAgencylicense() {
        return userAgencylicense;
    }

    public void setUserAgencylicense(String userAgencylicense) {
        this.userAgencylicense = userAgencylicense;
    }

    public String getUserAgencyName() {
        return userAgencyName;
    }

    public void setUserAgencyName(String userAgencyName) {
        this.userAgencyName = userAgencyName;
    }

    public String getUserAgencyPhone() {
        return userAgencyPhone;
    }

    public void setUserAgencyPhone(String userAgencyPhone) {
        this.userAgencyPhone = userAgencyPhone;
    }

    public String getUserAgencyAddress() {
        return userAgencyAddress;
    }

    public void setUserAgencyAddress(String userAgencyAddress) {
        this.userAgencyAddress = userAgencyAddress;
    }

    public String getComIdNumber() {
        return comIdNumber;
    }

    public void setComIdNumber(String comIdNumber) {
        this.comIdNumber = comIdNumber;
    }

    public String getUserIdNumber() {
        return userIdNumber;
    }

    public void setUserIdNumber(String userIdNumber) {
        this.userIdNumber = userIdNumber;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRefPrice() {
        return refPrice;
    }

    public void setRefPrice(String refPrice) {
        this.refPrice = refPrice;
    }

    public String getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(String beginAt) {
        this.beginAt = beginAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(String commissionPercent) {
        this.commissionPercent = commissionPercent;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyPhone() {
        return agencyPhone;
    }

    public void setAgencyPhone(String agencyPhone) {
        this.agencyPhone = agencyPhone;
    }

    public String getAgencyAddress() {
        return agencyAddress;
    }

    public void setAgencyAddress(String agencyAddress) {
        this.agencyAddress = agencyAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getComPhone() {
        return comPhone;
    }

    public void setComPhone(String comPhone) {
        this.comPhone = comPhone;
    }

    public String getComAddress() {
        return comAddress;
    }

    public void setComAddress(String comAddress) {
        this.comAddress = comAddress;
    }
}
