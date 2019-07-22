package com.tzCloud.gateway.controller.req.pay;


import lombok.Data;

import java.io.Serializable;

/**
 *微信app支付返回
 */
@Data
public class WxAppletPayVo implements Serializable {

    private String timeStamp;//支付时间戳
    private String nonceStr;//支付随机字符串
    private String payPackage;//支付参数值
    private String signType;//签名方式
    private String paySign;//签名
    private String appId;//支付id
    private String amount;//支付金额
    private String payDesc;//支付描述
    private String orderId;//支付订单id


}
