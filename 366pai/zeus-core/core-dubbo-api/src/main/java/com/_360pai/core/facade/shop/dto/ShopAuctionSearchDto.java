package com._360pai.core.facade.shop.dto;

import com._360pai.arch.common.RequestModel;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.common.constants.ShopEnum;
import lombok.Getter;
import lombok.Setter;

public class ShopAuctionSearchDto extends RequestModel {

    /**
     *拍品类型
     */
    private String categoryId;

    /**
     *城市
     */
    private Integer cityId;
    /**
     * 省
     */
    private Integer provinceId;

    /**
     * 起始价格
     */
    private String beginPrice;

    /**
     * 结束价格
     */
    private String endPrice;

    /**
     * 搜索人openId
     */
    private String openId;

    /**
     * 拍品状态
     */
    private String activityStatus;

    /**
     * 搜索人openId
     */
    private String shopId;

    /**
     * 删除标识
     */
    private String deleteFlag;

    /**
     * 搜索条件
     */
    private String query;

    /**
     * 上下架标识
     */
    private String outFlag;

    public String getOutFlag() {
        return outFlag;
    }

    public void setOutFlag(String outFlag) {
        this.outFlag = outFlag;
    }

    private String partyCategoryId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getBeginPrice() {
        return beginPrice;
    }

    public void setBeginPrice(String beginPrice) {
        this.beginPrice = beginPrice;
    }

    public String getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(String endPrice) {
        this.endPrice = endPrice;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getPartyCategoryId() {
        return partyCategoryId;
    }

    public void setPartyCategoryId(String partyCategoryId) {
        this.partyCategoryId = ShopEnum.PartyCategoryEnum.getKeyByValue(partyCategoryId);
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }
}
