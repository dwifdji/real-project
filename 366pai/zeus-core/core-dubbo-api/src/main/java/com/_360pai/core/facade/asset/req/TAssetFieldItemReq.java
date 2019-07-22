
package com._360pai.core.facade.asset.req;

import com._360pai.arch.common.RequestModel;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public class TAssetFieldItemReq extends RequestModel {

    /**
     *
     */
    private Integer id;
    /**
     * 债券类型ID
     */
    private Integer categoryId;
    /**
     * 模板ID
     */
    private Integer templateId;
    /**
     * 字段分组ID
     */
    private Integer fieldGroupId;
    /**
     * 字段ID
     */
    private Integer fieldId;
    /**
     * 是否必填
     */
    private Boolean required;
    /**
     * 二级筛选器ID
     */
    private Integer filterOptionId;
    /**
     * 三级筛选器ID
     */
    private Integer filterOptionItemId;

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
     * 债券类型ID
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 债券类型ID
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 模板ID
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * 模板ID
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * 字段分组ID
     */
    public Integer getFieldGroupId() {
        return fieldGroupId;
    }

    /**
     * 字段分组ID
     */
    public void setFieldGroupId(Integer fieldGroupId) {
        this.fieldGroupId = fieldGroupId;
    }

    /**
     * 字段ID
     */
    public Integer getFieldId() {
        return fieldId;
    }

    /**
     * 字段ID
     */
    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * 是否必填
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * 是否必填
     */
    public void setRequired(Boolean required) {
        this.required = required;
    }

    /**
     * 二级筛选器ID
     */
    public Integer getFilterOptionId() {
        return filterOptionId;
    }

    /**
     * 二级筛选器ID
     */
    public void setFilterOptionId(Integer filterOptionId) {
        this.filterOptionId = filterOptionId;
    }

    /**
     * 三级筛选器ID
     */
    public Integer getFilterOptionItemId() {
        return filterOptionItemId;
    }

    /**
     * 三级筛选器ID
     */
    public void setFilterOptionItemId(Integer filterOptionItemId) {
        this.filterOptionItemId = filterOptionItemId;
    }

}
