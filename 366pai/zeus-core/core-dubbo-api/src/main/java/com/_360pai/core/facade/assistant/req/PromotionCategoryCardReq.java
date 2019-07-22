
package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年08月10日 18时47分27秒
 */
public class PromotionCategoryCardReq extends RequestModel {

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String title;
    /**
     *
     */
        private String hint;
    /**
     *
     */
    private Integer assetCategoryGroupId;
    /**
     *
     */
    private Integer assetPropertyId;
    /**
     *
     */
    private Integer orderNo;
    /**
     *
     */
    private String img;
    /**
     *
     */
    private String link;

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
    public String getTitle() {
        return title;
    }

    /**
     *
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     */
    public String getHint() {
        return hint;
    }

    /**
     *
     */
    public void setHint(String hint) {
        this.hint = hint;
    }

    /**
     *
     */
    public Integer getAssetCategoryGroupId() {
        return assetCategoryGroupId;
    }

    /**
     *
     */
    public void setAssetCategoryGroupId(Integer assetCategoryGroupId) {
        this.assetCategoryGroupId = assetCategoryGroupId;
    }

    /**
     *
     */
    public Integer getAssetPropertyId() {
        return assetPropertyId;
    }

    /**
     *
     */
    public void setAssetPropertyId(Integer assetPropertyId) {
        this.assetPropertyId = assetPropertyId;
    }

    /**
     *
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     *
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    /**
     *
     */
    public String getImg() {
        return img;
    }

    /**
     *
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     *
     */
    public String getLink() {
        return link;
    }

    /**
     *
     */
    public void setLink(String link) {
        this.link = link;
    }

}