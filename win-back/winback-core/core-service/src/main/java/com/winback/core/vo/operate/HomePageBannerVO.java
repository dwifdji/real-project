package com.winback.core.vo.operate;

import java.io.Serializable;

public class HomePageBannerVO implements Serializable {

    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     * 名称
     */
    private java.lang.String name;
    /**
     * 头像
     */
    private java.lang.String imgUrl;
    /**
     * 跳转url
     */
    private java.lang.String jumpUrl;

    private java.lang.String fixedJumpType;

    public String getFixedJumpType() {
        return fixedJumpType;
    }

    public void setFixedJumpType(String fixedJumpType) {
        this.fixedJumpType = fixedJumpType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }
}
