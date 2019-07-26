package com.liuhaolei.dreamer.conf;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.github.wxpay.sdk.WXPayConfig;

/**
 * 自定义微信支付初始化类
 * @author L1553
 *
 */
public class MyWXPayConfig implements WXPayConfig{

	 private WXPayProperties wxPayProperties;
	
	 private byte[] certData;
	
	//初始化支付参数
	public MyWXPayConfig(WXPayProperties wxPayProperties) throws IOException {
		this.wxPayProperties = wxPayProperties;
		String wxPayCertPath = wxPayProperties.getWxPayCertPath();
		
//		URL resource = MyWXPayConfig.class.getClassLoader().getResource(wxPayCertPath);//获取

        File file = new File(wxPayCertPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
	}

	@Override
	public String getAppID() {
		return wxPayProperties.getWxPayAppID();
	}

	@Override
	public String getMchID() {
		return wxPayProperties.getWxPayMchID();
	}

	@Override
	public String getKey() {
		return wxPayProperties.getWxPayKey();
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
