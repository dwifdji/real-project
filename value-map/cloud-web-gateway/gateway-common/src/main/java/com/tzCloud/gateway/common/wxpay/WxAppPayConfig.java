package com.tzCloud.gateway.common.wxpay;

import com.github.wxpay.sdk.WXPayConfig;
import com.tzCloud.arch.core.sysconfig.properties.GatewayProperties;

import java.io.*;

public class WxAppPayConfig implements WXPayConfig {

    private GatewayProperties gatewayProperties;
    private byte[] certData;

    public WxAppPayConfig(GatewayProperties gatewayProperties) throws IOException {
        this.gatewayProperties = gatewayProperties;
        //读取退款证书
        String path = gatewayProperties.getWxPayCertPath();
        String filePath = WxPayConfig.class.getClassLoader().getResource(path).getPath();//获取

        File file = new File(filePath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    @Override
    public String getAppID() {
        return gatewayProperties.getWxPayAppID();
    }

    @Override
    public String getMchID() {
        return gatewayProperties.getWxPayMchID();
    }

    @Override
    public String getKey() {
        return gatewayProperties.getWxPayKey();
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
