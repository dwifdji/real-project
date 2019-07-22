package com._360pai.core.facade.applet.vo;

import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.common.constants.ShopEnum;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class ShopEnrollingAndAuctionListVO implements Serializable {

    private String mortgageValue;//抵押情况真正的数值

    private String deposit;	//保证金

    private String status;//状态

    private String statusName;

    private String images;//图片地址

    private String enrollingId;

    private String name;

    private String refPrice;//市值估计

    private String type;//预招商类型

    private String typeName;

    private String choseFlag;



    private String activityId;//抵押情况真正的数值

    private String activityName;	//保证金

    private String acStatus;//状态

    private String acStatusName;//预招商类型

    private String acDeposit;

    private String mode;//图片地址

    private String modeName;

    private String startingPrice;

    private String imageUrl;

    private String acChoseFlag;//市值估计

    private String objectType;

    private String cityName;

    private String wallFlag;

    public String getWallFlag() {
        return wallFlag;
    }

    public void setWallFlag(String wallFlag) {
        this.wallFlag = wallFlag;
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        if(StringUtils.isNotBlank(cityName)) {
            String[] cityNames = cityName.split(",");
            this.cityName = cityNames[0];
        }else {
            this.cityName = cityName;
        }
    }

    public String getModeName() {
        return modeName;
    }


    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getAcStatus() {
        return acStatus;
    }

    public void setAcStatus(String acStatus) {
        this.acStatus = acStatus;
        this.acStatusName = ShopEnum.NewOnlineStatus.getKeyByValue(acStatus);
    }

    public String getAcStatusName() {
        return acStatusName;
    }

    public String getAcDeposit() {
        return acDeposit;
    }

    public void setAcDeposit(String acDeposit) {
        this.acDeposit = acDeposit;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
        this.modeName = AssetEnum.ExpectedMode.getKeyByValue(mode);
    }

    public String getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(String startingPrice) {
        this.startingPrice = startingPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAcChoseFlag() {
        return acChoseFlag;
    }

    public void setAcChoseFlag(String acChoseFlag) {
        this.acChoseFlag = acChoseFlag;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getChoseFlag() {
        return choseFlag;
    }

    public void setChoseFlag(String choseFlag) {
        this.choseFlag = choseFlag;
    }

    public String getTypeName() {
        return typeName;
    }


    public String getMortgageValue() {
        return mortgageValue;
    }

    public void setMortgageValue(String mortgageValue) {
        if(StringUtils.isNotBlank(mortgageValue)) {
            this.mortgageValue = EnrollingEnum.ENROLLING_GUARANTEE.getDesc(mortgageValue);
        }else {
            this.mortgageValue = mortgageValue;
        }
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
            this.deposit = deposit;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.statusName = EnrollingEnum.STATUS.getDesc(status);
        this.status = status;
     }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }


    public String getStatusName() {
        return statusName;
    }

    public String getEnrollingId() {
        return enrollingId;
    }

    public void setEnrollingId(String enrollingId) {
        this.enrollingId = enrollingId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.typeName = EnrollingEnum.ENROLLING_TYPE.getDesc(type);
        this.type = type;
    }

}
