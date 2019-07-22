
package com._360pai.core.model.asset;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年09月06日 19时24分48秒
 */
@Data
public class TAssetFieldModel implements java.io.Serializable {

    /**
     * 主键自增
     */
    private Integer id;
    /**
     * 是否可扩展
     */
    private Integer extensible;
    /**
     * 模块标题
     */
    private String modelTitle;
    /**
     * 模板ID
     */
    private Integer templateId;
    /**
     * 分组ID
     */
    private Integer fieldGroupId;
    /**
     * 分组Key
     */
    private String modelKey;
    /**
     * 是否隐藏标题
     */
    private Integer showTitle;

    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    /**
     * 主键自增
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键自增
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 是否可扩展
     */
    public Integer getExtensible() {
        return extensible;
    }

    /**
     * 是否可扩展
     */
    public void setExtensible(Integer extensible) {
        this.extensible = extensible;
    }

    /**
     * 模块标题
     */
    public String getModelTitle() {
        return modelTitle;
    }

    /**
     * 模块标题
     */
    public void setModelTitle(String modelTitle) {
        this.modelTitle = modelTitle;
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
     * 分组ID
     */
    public Integer getFieldGroupId() {
        return fieldGroupId;
    }

    /**
     * 分组ID
     */
    public void setFieldGroupId(Integer fieldGroupId) {
        this.fieldGroupId = fieldGroupId;
    }

}
