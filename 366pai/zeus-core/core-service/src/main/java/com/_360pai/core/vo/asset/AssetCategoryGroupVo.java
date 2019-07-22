
package com._360pai.core.vo.asset;

import com._360pai.core.model.asset.AssetTemplateField;
import com._360pai.core.model.asset.AssetTemplateFieldOption;

import java.util.List;

/**
 *
 */
public class AssetCategoryGroupVo implements java.io.Serializable {

    public static final String SELL = "SELL"; //销售类
    public static final String SERVICE = "SERVICE"; //服务类

    public static final String AUCTION = "AUCTION"; //拍卖
    public static final String ENROLLING = "ENROLLING"; //预招商

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String businessType;
    /**
     *
     */
    private String dealMode;
    /**
     *
     */
    private Boolean enabled;

    private List<AssetTemplateField> fields;

    public List<AssetTemplateField> getFields() {
        return fields;
    }

    public void setFields(List<AssetTemplateField> fields) {
        this.fields = fields;
    }

    /**
     *
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     *
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    /**
     *
     */
    public String getDealMode() {
        return dealMode;
    }

    /**
     *
     */
    public void setDealMode(String dealMode) {
        this.dealMode = dealMode;
    }

    /**
     *
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     *
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
