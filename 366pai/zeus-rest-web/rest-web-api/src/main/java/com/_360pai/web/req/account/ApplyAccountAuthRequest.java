package com._360pai.web.req.account;

import com._360pai.arch.common.RequestModel;

/**
 * Created by RuQ on 2018/8/22 19:01
 */
public class ApplyAccountAuthRequest extends RequestModel {

    /**
     * 姓名
     */
    private java.lang.String name;
    /**
     * 身份证号
     */
    private java.lang.String certificateNumber;

    /**
     * 默认送拍机构
     */
    private java.lang.Integer defaultAgencyId;
    /**
     * 城市Id
     */
    private java.lang.String cityId;

    /**
     * 身份证正面图片
     */
    private java.lang.String certificateFrontImg;
    /**
     * 身份证背面图片
     */
    private java.lang.String certificateBackImg;
    /**
     * 办公地址
     */
    private java.lang.String address;


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

    public Integer getDefaultAgencyId() {
        return defaultAgencyId;
    }

    public void setDefaultAgencyId(Integer defaultAgencyId) {
        this.defaultAgencyId = defaultAgencyId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
