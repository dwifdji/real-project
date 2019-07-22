
package com._360pai.core.facade.asset.req;

import com._360pai.arch.common.RequestModel;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public class TAssetCategoryReq extends RequestModel {

    public static final String SELL = "SELL";           //转让类
    public static final String SERVICE = "SERVICE";     //服务类
    public static final String AUCTION = "AUCTION";     //活动
    public static final String DISPOSALSERVICES = "DISPOSALSERVICES";     //处置服务
    public static final String DISTRIBUTION = "DISTRIBUTION";     //处置服务
    public static final String ENROLLING = "ENROLLING"; //预招商

    /**
     * 主键
     */
    private Integer id;
    /**
     * 字段分组ID
     */
    private Integer fieldGroupId;
    /**
     * 类型ID
     */
    private Integer categoryId;
    /**
     * 债券类型名称
     */
    private String name;
    /**
     * 债券业务类型 AUCTION:拍卖  ENROLLING:预招商
     */
    private String businessType;
    /**
     * 拍卖模式 SELL:转让类 SERVICE:服务类
     */
    private String dealMode;
    /**
     * 是否启用 1:启用 0:不启用
     */
    private Boolean enabled;

    public Integer getFieldGroupId() {
        return fieldGroupId;
    }

    public void setFieldGroupId(Integer fieldGroupId) {
        this.fieldGroupId = fieldGroupId;
    }

    /**
     * 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 债券类型名称
     */
    public String getName() {
        return name;
    }

    /**
     * 债券类型名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 债券业务类型 AUCTION:拍卖  ENROLLING:预招商
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * 债券业务类型 AUCTION:拍卖  ENROLLING:预招商
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    /**
     * 拍卖模式 SELL:转让类 SERVICE:服务类
     */
    public String getDealMode() {
        return dealMode;
    }

    /**
     * 拍卖模式 SELL:转让类 SERVICE:服务类
     */
    public void setDealMode(String dealMode) {
        this.dealMode = dealMode;
    }

    /**
     * 是否启用 1:启用 0:不启用
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * 是否启用 1:启用 0:不启用
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
