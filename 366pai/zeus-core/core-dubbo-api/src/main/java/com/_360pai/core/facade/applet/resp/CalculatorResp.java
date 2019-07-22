package com._360pai.core.facade.applet.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.arch.common.RequestModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xdrodger
 * @Title: NumberJumpResp
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-28 19:25
 */
public class CalculatorResp {

    @Getter
    @Setter
    public static class LoginResp extends BaseResp {
        private Integer loginId;
        private String openId;
    }

    @Data
    public static class CalculatorBroadcastPayResp extends RequestModel {
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

}
