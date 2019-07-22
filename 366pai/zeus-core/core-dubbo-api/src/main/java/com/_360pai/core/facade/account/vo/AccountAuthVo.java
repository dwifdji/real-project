package com._360pai.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: AccountAuthVo
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/27 10:56
 */
@Data
public class AccountAuthVo implements Serializable {
    private String type;
    private String name;
    private String partyId;
    private Boolean isDefault;
    private Integer accountId;
    private String  authDisposerDesc;

    /**
     * 身份证号码
     */
    private java.lang.String certificateNumber;
    /**
     * 身份证正面照
     */
    private java.lang.String certificateFrontImg;
    /**
     * 身份证背面照
     */
    private java.lang.String certificateBackImg;
    /**
     * 省Id
     */
    private java.lang.String provinceId;
    /**
     * 城市Id
     */
    private java.lang.String cityId;
    /**
     * 城市名称
     */
    private java.lang.String cityName;
    /**
     * 地址
     */
    private java.lang.String address;


    /**
     * 营业执照号码
     */
    private java.lang.String license;
    /**
     * 注册省份
     */
    private java.lang.String registerProvinceId;
    /**
     * 注册城市
     */
    private java.lang.String registerCityId;
    /**
     * 注册城市名称
     */
    private java.lang.String registerCityName;
    /**
     * 注册地址
     */
    private java.lang.String registerAddress;
}
