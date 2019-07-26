package com.liuhaolei.dreamer.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "wxpay")
@Component
public class WXPayProperties {
	
	private String wxPayCertPath;

    //AppId
    private String wxPayAppID;

    //转账的商户号码
    private String wxPayMchID;

    //秘钥key zhi
    private String wxPayKey;

    //微信支付通知地址
    private String wxPayNotifyUrl;

}
