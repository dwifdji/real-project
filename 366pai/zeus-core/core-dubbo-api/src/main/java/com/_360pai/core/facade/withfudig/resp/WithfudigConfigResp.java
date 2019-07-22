package com._360pai.core.facade.withfudig.resp;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/8/30 14:12
 */
public class WithfudigConfigResp implements Serializable {

    /**
     * 成交日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date    createdTime;
    /**
     * 成交信息
     */
    private String  description;
    /**
     * 排序
     */
    private Integer rank;

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
