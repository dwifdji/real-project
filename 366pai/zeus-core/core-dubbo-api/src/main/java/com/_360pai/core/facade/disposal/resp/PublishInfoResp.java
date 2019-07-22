package com._360pai.core.facade.disposal.resp;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-09-14 13:02
 */
public class PublishInfoResp implements Serializable {
    private static final long serialVersionUID = -2601670575380380166L;

    /**
     * 处置需求id
     */
    private Integer id;
    /**
     * 平台编号
     */
    private String platformNo;
    /**
     * 处置编号
     */
    private String disposalNo;
    /**
     * 处置类型
     */
    private String disposalType;
    /**
     * 处置周期
     */
    private Double period;
    /**
     * 平台类型
     */
    private Integer isPlatform;
    /**
     * 投标人数
     */
    private Integer biddingNum;
    /**
     * 发布时间
     */
    private Date publishTime;
    /**
     * 审核状态
     */
    private String disposalStatus;
    /**
     * 所在城市id
     */
    private Integer cityId;
    /**
     * 商品展示图
     */
    private String extra1;

    /**
     * 发布处置需求用户id
     */
    private Integer partyId;

    /**
     * 需求名称
     */
    private String disposalName;
    /**
     * 案件描述
     */
    private String caseDescription;
    /**
     * 需求描述
     */
    private String requireDescription;

    /**
     * 处置费用
     */
    private String cost;

    /**
     * 操作员id
     */
    private Integer operatorId;
    /**
     * 商品id
     */
    private Integer assetId;

    /**
     * 观看人数
     */
    private Integer viewNum;
    /**
     * 关注人数
     */
    private Integer followNum;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 更新时间
     */
    private java.util.Date updateTime;
    /**
     * 删除标识  0：未删除 1：删除
     */
    private Integer isDel;
    /**
     * 付款成功单号
     */
    private String payOrder;

    /**
     * 图片json
     */
    private JSONObject extra;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(String platformNo) {
        this.platformNo = platformNo;
    }

    public String getDisposalNo() {
        return disposalNo;
    }

    public void setDisposalNo(String disposalNo) {
        this.disposalNo = disposalNo;
    }

    public String getDisposalType() {
        return disposalType;
    }

    public void setDisposalType(String disposalType) {
        this.disposalType = disposalType;
    }

    public Double getPeriod() {
        return period;
    }

    public void setPeriod(Double period) {
        this.period = period;
    }

    public Integer getIsPlatform() {
        return isPlatform;
    }

    public void setIsPlatform(Integer isPlatform) {
        this.isPlatform = isPlatform;
    }

    public Integer getBiddingNum() {
        return biddingNum;
    }

    public void setBiddingNum(Integer biddingNum) {
        this.biddingNum = biddingNum;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getDisposalStatus() {
        return disposalStatus;
    }

    public void setDisposalStatus(String disposalStatus) {
        this.disposalStatus = disposalStatus;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getExtra1() {
        return extra1;
    }

    public void setExtra1(String extra1) {
        this.extra1 = extra1;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getDisposalName() {
        return disposalName;
    }

    public void setDisposalName(String disposalName) {
        this.disposalName = disposalName;
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public void setCaseDescription(String caseDescription) {
        this.caseDescription = caseDescription;
    }

    public String getRequireDescription() {
        return requireDescription;
    }

    public void setRequireDescription(String requireDescription) {
        this.requireDescription = requireDescription;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getFollowNum() {
        return followNum;
    }

    public void setFollowNum(Integer followNum) {
        this.followNum = followNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getPayOrder() {
        return payOrder;
    }

    public void setPayOrder(String payOrder) {
        this.payOrder = payOrder;
    }

    public JSONObject getExtra() {
        return extra;
    }

    public void setExtra(JSONObject extra) {
        this.extra = extra;
    }
}
