package com._360pai.web.controller.account.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

/**
 * Created by RuQ on 2018/8/22 19:01
 */
@Data
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
     * 省市Id
     */
    private java.lang.String provinceId;
    /**
     * 区县Id
     */
    private java.lang.String areaId;
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
}
