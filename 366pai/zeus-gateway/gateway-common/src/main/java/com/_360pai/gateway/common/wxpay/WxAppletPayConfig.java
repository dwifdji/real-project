package com._360pai.gateway.common.wxpay;

import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.gateway.common.dfftpay.PayEnums;
import com.github.wxpay.sdk.WXPayConfig;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/27 17:51
 */
public class WxAppletPayConfig implements WXPayConfig {

    private GatewayProperties gatewayProperties;


    private byte[] certData;

    private String  channel;


    //初始化退款、撤销时的商户证书
    public WxAppletPayConfig(GatewayProperties gatewayProperties,String channel) throws Exception {

        this.gatewayProperties =gatewayProperties;
        this.channel =channel;


        String path = gatewayProperties.getWxPayCertPath();

        String filePath = WxAppletPayConfig.class.getClassLoader().getResource(path).getPath();//获取


        File file = new File(filePath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public String getAppID() {
        return PayEnums.PAY_BUS_CODE.APPLET_CALCULATOR_PAY.getType().equals(channel)?gatewayProperties.getCalculatorAppId():gatewayProperties.getAppletAppId();
    }

    public String getMchID() {
        return gatewayProperties.getWxPayMchID();
    }

    public String getKey() {
        return gatewayProperties.getWxPayKey();
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

}
