
package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;
/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年08月10日 18时47分27秒
 */
public class PlatformAnnouncementReq extends RequestModel {

    public static final String NEWS = "NEWS"; //新闻类
    public static final String ANNOUNCEMENT = "ANNOUNCEMENT"; //平台公告

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
    private String url;
    /**
     *
     */
    private String detail;
    /**
     *
     */
    private java.util.Date createdAt;
    /**
     *
     */
    private java.util.Date expiredAt;
    /**
     *
     */
    private java.util.Date publicAt;
    /**
     *
     */
    private String category;
    /**
     * "1" 平台
     * "2" 后台管理
     */
    private String typeFlag;

    public String getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(String typeFlag) {
        this.typeFlag = typeFlag;
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
    public String getUrl() {
        return url;
    }

    /**
     *
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     */
    public String getDetail() {
        return detail;
    }

    /**
     *
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     *
     */
    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    /**
     *
     */
    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     */
    public java.util.Date getExpiredAt() {
        return expiredAt;
    }

    /**
     *
     */
    public void setExpiredAt(java.util.Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    /**
     *
     */
    public java.util.Date getPublicAt() {
        return publicAt;
    }

    /**
     *
     */
    public void setPublicAt(java.util.Date publicAt) {
        this.publicAt = publicAt;
    }

    /**
     *
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     */
    public void setCategory(String category) {
        this.category = category;
    }

}
