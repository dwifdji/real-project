package com._360pai.web.controller.account.req;

import lombok.Data;

/**
 * Created by RuQ on 2018/8/23 13:55
 */
@Data
public class ApplyCompanyAuthRequest {

    /**
     * 公司名称
     */
    private java.lang.String name;

    /**
     * 默认送拍机构
     */
    private java.lang.Integer defaultAgencyId;

    /**
     * 办公地址
     */
    private java.lang.String address;
    /**
     * 注册地址
     */
    private java.lang.String registerAddress;
    /**
     * 城市Id
     */
    private java.lang.String cityId;
    /**
     * 注册城市Id
     */
    private java.lang.String registerCityId;
    /**
     * 省id
     */
    private java.lang.String provinceId;
    /**
     * 注册省id
     */
    private java.lang.String registerProvinceId;
    /**
     * 区县id
     */
    private java.lang.String areaId;
    /**
     * 注册区县id
     */
    private java.lang.String registerAreaId;
    /**
     * 营业执照
     */
    private java.lang.String license;
    /**
     * 营业执照图片
     */
    private java.lang.String licenseImg;
    /**
     * 法人
     */
    private java.lang.String legalPerson;
    /**
     * 身份证号
     */
    private java.lang.String idCard;
    /**
     * 身份证正面照
     */
    private java.lang.String idCardFrontImg;
    /**
     * 身份证背面照
     */
    private java.lang.String idCardBackImg;
    /**
     * 授权书
     */
    private java.lang.String authorizationImg;
    /**
     * 开户许可证
     */
    private java.lang.String accountLicense;

}
