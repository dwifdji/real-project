package com._360pai.core.facade.applet.vo;

import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.NumberValidationUtils;
import com._360pai.core.common.constants.EnrollingEnum;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class ShopEnrollingListVO implements Serializable {

    private String cityName;//城市名称

    private String mortgageValue;//抵押情况真正的数值

    private String deposit;	//保证金

    private String beginAt;//开始时间

    private String status;//状态

    private String statusName;

    private String imageUrl;//图片地址

    private String endAt;//结束时间

    private String activityId;

    private String activityName;

    private String refPrice;//市值估计

    private String type;//预招商类型

    private String typeName;

    private String focusNum;

    private String browseNum;

    private String depositNum;

    private String pushFlag;

    private String choseFlag;

    private Long beginTimestamp;

    private Long endTimestamp;

    private Long currentTimestamp = System.currentTimeMillis();

    private String bigType;

    private String wallFlag; //长城资产标识

    public String getWallFlag() {
        return wallFlag;
    }

    public void setWallFlag(String wallFlag) {
            this.wallFlag = wallFlag;
    }

    public Long getBeginTimestamp() {
        return beginTimestamp;
    }

    public Long getEndTimestamp() {
        return endTimestamp;
    }

    public Long getCurrentTimestamp() {
        return currentTimestamp;
    }

    public String getBigType() {
        return "2";
    }

    public String getChoseFlag() {
        return choseFlag;
    }

    public void setChoseFlag(String choseFlag) {
        this.choseFlag = choseFlag;
    }

    public String getPushFlag() {
        return pushFlag;
    }

    public void setPushFlag(String pushFlag) {
        this.pushFlag = pushFlag;
    }

    public String getFocusNum() {
        return focusNum;
    }

    public String getBrowseNum() {
        return browseNum;
    }

    public void setFocusNum(String focusNum) {
        if(focusNum == null) {
            this.focusNum = "0";
        }else {
            this.focusNum = focusNum;
        }

    }

    public void setBrowseNum(String browseNum) {
        if(browseNum == null) {
            this.browseNum = "0";
        }else {
            this.browseNum = browseNum;
        }

    }


    public String getDepositNum() {
        return depositNum;
    }

    public void setDepositNum(String depositNum) {
        if(depositNum == null) {
            this.depositNum = "0";
        }else {
            this.depositNum = depositNum;
        }
    }

    public String getTypeName() {
        return typeName;
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

    public String getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(String beginAt) {
        this.beginAt = beginAt;
        this.beginTimestamp = DateUtil.strToDateLong(beginAt).getTime();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.statusName = EnrollingEnum.STATUS.getDesc(status);
        this.status = status;
     }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getEndAt() {
        return endAt;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setEndAt(String endAt) {
        this.endTimestamp = DateUtil.strToDateLong(endAt).getTime();

        if(endAt != null && endAt.length() > 2) {
            this.endAt = endAt.substring(0, endAt.length() - 3);
        }else {
            this.endAt = endAt;
        }
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
