package com.winback.core.vo.operate;


import java.io.Serializable;

public class AdvertisingSpaceListVO implements Serializable {

    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     * 类型 1 启动页 2 首页
     */
    private java.lang.String type;
    /**
     * 名称
     */
    private java.lang.String name;
    /**
     * 描述信息
     */
    private java.lang.String nameDesc;
    /**
     * 头像
     */
    private java.lang.String imgUrl;
    /**
     * 跳转url
     */
    private java.lang.String jumpUrl;
    /**
     * 有效开始时间
     */
    private String beginTime;
    /**
     * 有效结束时间
     */
    private String endTime;
    /**
     * 上线标志 0 没上线 1 上线
     */
    private Integer onlineFlage;

    private Integer sort;


    private String fixedJumpType;


    public String getFixedJumpType() {
        return fixedJumpType;
    }

    public void setFixedJumpType(String fixedJumpType) {
        this.fixedJumpType = fixedJumpType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameDesc() {
        return nameDesc;
    }

    public void setNameDesc(String nameDesc) {
        this.nameDesc = nameDesc;
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

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getOnlineFlage() {
        return onlineFlage;
    }

    public void setOnlineFlage(Integer onlineFlage) {
        this.onlineFlage = onlineFlage;
    }
}
