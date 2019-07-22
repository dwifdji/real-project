
package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年08月10日 18时47分26秒
 */
public class BannerReq extends RequestModel {

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String linkUrl;
    /**
     *
     */
    private String imgUrl;
    /**
     *
     */
    private Boolean isAgency;
    /**
     *
     */
    private Boolean isOnline;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private Integer orderNum;
    /**
     *
     */
    private Integer type;

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
    public String getLinkUrl() {
        return linkUrl;
    }

    /**
     *
     */
    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    /**
     *
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     *
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     *
     */
    public Boolean getIsAgency() {
        return isAgency;
    }

    /**
     *
     */
    public void setIsAgency(Boolean isAgency) {
        this.isAgency = isAgency;
    }

    /**
     *
     */
    public Boolean getIsOnline() {
        return isOnline;
    }

    /**
     *
     */
    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
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
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     *
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
