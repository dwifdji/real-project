
package com._360pai.core.model.asset;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年11月16日 10时51分52秒
 */
public class AssetDataDrafts implements java.io.Serializable {

    public static final int notDel = 0;
    public static final int Del = 1;


    /**
     * 主键自增
     */
    private Integer id;
    /**
     * 认证用户ID
     */
    private String partyId;
    /**
     * 数据记录
     */
    private com.alibaba.fastjson.JSONObject content;
    /**
     * 类型 ：auction 活动，enrolling 预招商 ，information ：配资乐 ，comprador 资产大买办
     */
    private String type;
    /**
     * 创建时间
     */
    private java.util.Date createdTime;
    /**
     * 更新时间
     */
    private java.util.Date updatedTime;
    /**
     * 0 没删除 1删除
     */
    private Integer delFlag;

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
     * 认证用户ID
     */
    public String getPartyId() {
        return partyId;
    }

    /**
     * 认证用户ID
     */
    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    /**
     * 数据记录
     */
    public com.alibaba.fastjson.JSONObject getContent() {
        return content;
    }

    /**
     * 数据记录
     */
    public void setContent(com.alibaba.fastjson.JSONObject content) {
        this.content = content;
    }

    /**
     * 类型 ：auction 活动，enrolling 预招商 ，information ：配资乐 ，comprador 资产大买办
     */
    public String getType() {
        return type;
    }

    /**
     * 类型 ：auction 活动，enrolling 预招商 ，information ：配资乐 ，comprador 资产大买办
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 创建时间
     */
    public java.util.Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 创建时间
     */
    public void setCreatedTime(java.util.Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 更新时间
     */
    public java.util.Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 更新时间
     */
    public void setUpdatedTime(java.util.Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * 0 没删除 1删除
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * 0 没删除 1删除
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

}
