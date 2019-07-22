package com.tzCloud.gateway.controller.req.pay;


import lombok.Data;

import java.io.Serializable;

/**
 *微信app支付返回
 */
@Data
public class WxAppPayVo implements Serializable {

    private String appid;

    private String partnerid;

    private String prepayid;

    private String payPackage;

    private String noncestr;

    private String timestamp;

    private String sign;



}
