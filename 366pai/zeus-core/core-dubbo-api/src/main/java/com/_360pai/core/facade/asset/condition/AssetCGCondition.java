
package com._360pai.core.facade.asset.condition;

import com._360pai.core.facade.asset.condition.base.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 *
 * @author Generator
 * @date 2018年08月08日 14时24分16秒
 */
public class AssetCGCondition implements DaoCondition {

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