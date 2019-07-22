package com._360pai.core.vo.applet;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：开店支付请求返回参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/26 13:23
 */
@Data
public class OpenShopPayVo implements Serializable {

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
