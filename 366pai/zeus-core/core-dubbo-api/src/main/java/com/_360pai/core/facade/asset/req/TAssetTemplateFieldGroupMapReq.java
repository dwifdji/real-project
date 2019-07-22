
package com._360pai.core.facade.asset.req;

import com._360pai.arch.common.RequestModel;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public class TAssetTemplateFieldGroupMapReq extends RequestModel {

    /**
     * 自增ID
     */
    private Integer id;
    /**
     * 模板ID
     */
    private Integer assetTemplateCategoryId;
    /**
     * 字段分组ID
     */
    private Integer assetFieldGroupId;

    /**
     * 自增ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 自增ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 模板ID
     */
    public Integer getAssetTemplateCategoryId() {
        return assetTemplateCategoryId;
    }

    /**
     * 模板ID
     */
    public void setAssetTemplateCategoryId(Integer assetTemplateCategoryId) {
        this.assetTemplateCategoryId = assetTemplateCategoryId;
    }

    /**
     * 字段分组ID
     */
    public Integer getAssetFieldGroupId() {
        return assetFieldGroupId;
    }

    /**
     * 字段分组ID
     */
    public void setAssetFieldGroupId(Integer assetFieldGroupId) {
        this.assetFieldGroupId = assetFieldGroupId;
    }

}
