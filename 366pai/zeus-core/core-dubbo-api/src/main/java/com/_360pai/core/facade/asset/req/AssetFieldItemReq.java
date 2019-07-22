
package com._360pai.core.facade.asset.req;

import com._360pai.arch.common.RequestModel;


public class AssetFieldItemReq extends RequestModel {

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Integer categoryId;
    /**
     *
     */
    private Integer groupId;
    /**
     *
     */
    private Integer fieldId;
    /**
     *
     */
    private Boolean required;

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
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     *
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     *
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     *
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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
    public Boolean getRequired() {
        return required;
    }

    /**
     *
     */
    public void setRequired(Boolean required) {
        this.required = required;
    }

}
