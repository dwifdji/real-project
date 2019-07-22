package com._360pai.core.facade.account.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.core.facade.account.vo.UserVo;
import com._360pai.core.facade.assistant.vo.CityVo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by RuQ on 2018/8/17 16:03
 */
public class UserResp implements Serializable {

    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     * 账户Id
     */
    private java.lang.Integer accountId;
    /**
     * 个人认证时选的送拍机构
     */
    private java.lang.Integer defaultAgencyId;
    /**
     * 姓名
     */
    private java.lang.String name;
    /**
     * 身份证号码
     */
    private java.lang.String certificateNumber;
    /**
     * 身份证起始日期
     */
    private java.util.Date certificateBegin;
    /**
     * 身份证结束日期
     */
    private java.util.Date certificateEnd;
    /**
     * 城市Id
     */
    private java.lang.String cityId;
    /**
     * 地址
     */
    private java.lang.String address;
    /**
     * 身份证正面照
     */
    private java.lang.String certificateFrontImg;
    /**
     * 身份证背面照
     */
    private java.lang.String certificateBackImg;
    /**
     * 手机号
     */
    private java.lang.String mobile;
    /**
     * 法大大Id
     */
    private java.lang.String fadadaId;
    /**
     * 东方付通ID
     */
    private java.lang.String dfftId;
    /**
     * 是否绑定东方付通
     */
    private java.lang.Integer payBind;
    /**
     * 0:无效,1:有效
     */
    private java.lang.Integer status;
    /**
     * 是否是代理商(0:否,1:是)
     */
    private java.lang.Integer isChannel;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 更新时间
     */
    private java.util.Date updateTime;

    private CityVo cityVo;

    public CityVo getCityVo() {
        return cityVo;
    }

    public void setCityVo(CityVo cityVo) {
        this.cityVo = cityVo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getDefaultAgencyId() {
        return defaultAgencyId;
    }

    public void setDefaultAgencyId(Integer defaultAgencyId) {
        this.defaultAgencyId = defaultAgencyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public Date getCertificateBegin() {
        return certificateBegin;
    }

    public void setCertificateBegin(Date certificateBegin) {
        this.certificateBegin = certificateBegin;
    }

    public Date getCertificateEnd() {
        return certificateEnd;
    }

    public void setCertificateEnd(Date certificateEnd) {
        this.certificateEnd = certificateEnd;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCertificateFrontImg() {
        return certificateFrontImg;
    }

    public void setCertificateFrontImg(String certificateFrontImg) {
        this.certificateFrontImg = certificateFrontImg;
    }

    public String getCertificateBackImg() {
        return certificateBackImg;
    }

    public void setCertificateBackImg(String certificateBackImg) {
        this.certificateBackImg = certificateBackImg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFadadaId() {
        return fadadaId;
    }

    public void setFadadaId(String fadadaId) {
        this.fadadaId = fadadaId;
    }

    public String getDfftId() {
        return dfftId;
    }

    public void setDfftId(String dfftId) {
        this.dfftId = dfftId;
    }

    public Integer getPayBind() {
        return payBind;
    }

    public void setPayBind(Integer payBind) {
        this.payBind = payBind;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsChannel() {
        return isChannel;
    }

    public void setIsChannel(Integer isChannel) {
        this.isChannel = isChannel;
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

    public static class DetailResp extends BaseResp {
        private UserVo user;

        public UserVo getUser() {
            return user;
        }

        public void setUser(UserVo user) {
            this.user = user;
        }
    }
}
