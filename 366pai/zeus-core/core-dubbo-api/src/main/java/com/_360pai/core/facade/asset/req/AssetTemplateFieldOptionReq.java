
package com._360pai.core.facade.asset.req;

import com._360pai.arch.common.RequestModel;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年08月10日 18时47分25秒
 */
public class AssetTemplateFieldOptionReq extends RequestModel {

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
    private Integer fieldId;
    /**
     *
     */
    private Integer searchWeight;

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
    public Integer getFieldId() {
        return fieldId;
    }

    /**
     *
     */
    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    /**
     *
     */
    public Integer getSearchWeight() {
        return searchWeight;
    }

    /**
     *
     */
    public void setSearchWeight(Integer searchWeight) {
        this.searchWeight = searchWeight;
    }

}
