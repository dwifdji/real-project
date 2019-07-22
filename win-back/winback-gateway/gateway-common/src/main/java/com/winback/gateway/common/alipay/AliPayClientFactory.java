package com.winback.gateway.common.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.winback.arch.core.sysconfig.properties.GatewayProperties;

/**
 * 描述：支付宝客户端工厂
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/16 9:24
 */
public class AliPayClientFactory {

	private static AlipayClient alipayClient = null;

	/**
	 * 封装公共请求参数
	 * 
	 * @return AlipayClient
	 */
	public static AlipayClient getAliPayClient(GatewayProperties gatewayProperties) {

		if (alipayClient != null) {
			return alipayClient;
		}

		// 网关
		String URL = gatewayProperties.getAliPayUrl();
		// 商户APP_ID
		String APP_ID = gatewayProperties.getAliPayAppId();
		// 商户RSA 私钥
		String APP_PRIVATE_KEY = gatewayProperties.getAliPayAppPrivateKey();
		// 请求方式 json
		String FORMAT = gatewayProperties.getAliPayFormat();
		// 编码格式，目前只支持UTF-8
		String CHARSET = gatewayProperties.getAliPayCharset();
		// 支付宝公钥
		String ALIPAY_PUBLIC_KEY = gatewayProperties.getAliPayPublicKey();
		// 签名方式
		String SIGN_TYPE = gatewayProperties.getAliPaySignType();

		return new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);


		//return new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", "2019010862816577", "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCQKlnhcREffh8SXtBCDY2ZhTtfdC8vx/OIr7gSnolTHX34B0iPv4Gj8DzW7NFFbknVWJOJjYgkX6pW3JpUaeWy71/0bdSrDE7yKg/3BHPPE1vJSmAFk0YUxVaOyldaF0Qj4QrvN1F4YWkob+4r2BoxktmdfdF4uc0mklh1hQrKN7nZL4KHWSkJyHDD0Q9qM6L03Unhy+L/z+UYoc9tKNTJcuI3Qal+UrZRF2s5a8LPBZZ0pyruYkW2TtMWsnbJUp5Fzl07az6dKExwOxcwPr0G+CySmnK9b1v1wE9Fhc9OSljXP5VZpznAqTZV0apUKRXQLe+nk4m5g7SGOgLuVhDrAgMBAAECggEARiOkuQ82gnjtCxYAArLvWmq8uTMgpgGFFdJQHxbsvvhRSjPY9L76htUh9HshUcNXvqmqlji6rN+yUzJfD4S6EXVu3M/2sQV6/0zBma+b3HqRpHw//VZquT5NX4lIOHerWj1qAXqLc6MI83iJiuFH1SiaIYqOOEtFaWe4C+0fk8QO46yEaKW8AMeBY4Ldbk+IHJf54InzSVhUkpCyg7SzVFEI200wqmA0Yd0UwvkLRunjcccqUxjAncg0durso7ta3pmfTHErSuBqmCv+kuTO0ScFbznS3C2kdSooZ59SoclW0VQusyVwhivlO/iD0XQ+aKdyrWpWcXU2Dlp4QBD5YQKBgQDWXnjG7wjxOWnURbsWClMWwYqXfu8mCVwAfjYiEYGYLtHG/ZGMGtMgKVL0Pqxhuwhfqd+KAKlTHRNqddND2a1tAUtJZLbGmq31KNzt7uTvFm6YOiTKnDgi+RhEkGenRazwYzb7QQ4uHRkj2a+ChmJ9oL7k1/DEeGqog6LaZF6TMQKBgQCsKaa27cuf0tiuPoLm7DzkY25kgI3piVoIX2Hdgwh3Qz+WJPcQ6EO5sMSg24hvjfAj4weys89JRhYxZ6vTbXk4vB0o+Fmh6XI+eyAHJD4+ZFzrFErk9uAi9RJ96EF5KAQVjMtp77a7FJ0bmUWrNlngelgX1cDxsAb6VYY4fOsG2wKBgEUdjH9HTnDH/BEOsnU2uvdZS1/w8xYmal1WVAnD7y6r2cL4ulabx3pVGIPbOSUBcn+1e3c5I17CvyHR/12e70V351gweqWp9XBXznOAVJupddrq1DXnDEpqWaKxlFngIxQWSQHVRcckZpiidO32egbbLvEFloEh+fF/D6CuP7fhAoGAZVBRip8f0Fr9JnUlBY4KO4EECuLYIJGyyPCQoBGnXr/VYJ86QdndWR5caCko/jTyF7xX4GMOXhl/n+1ZYZ+v0aJqKp0T6kN99qTwcSNz+bWINNMJS/q4ZfoAutV+CjVxSdlEL4Bg8XEtD3vAUwQ2GfMFemlRFSm4wYKo7P8eNDcCgYAIQQ6MvDAc1qqlip/Wu4cAfEvOq/f/M/fPeLABwclPtZRGE03tGyehQrbj97igDePyr6XAQb60RcMhWEd4FPC4UAwZlA8Zq3Q6ry2i1BpR8aqgIl6eB2HSnQ+E1z3jugWvRDpNczVFEsqhIQc7PuZd65e2mIFXZkBuTp5uK66IcA==", "json", "UTF-8", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh0Fqm4Pj4dZ/Ehm0XMEMxYHpT/DfcJmQtzN+a08kj6V9pZjcrkfyUBpdbOfWt/irca6L4FkIoWw3gXqMFfCa1wvqZEEaLQJnPSQ6GJ00+DVAznsi0TRQFga3rHBcfRaHPlZCEW+G2xGTZgYpw/s7kqjaA8l9/I6/XCOMH8VNr9AxbWC8dm6jUjblRiD84+98KFTU/RWIpAz5uqpm21IfzJhTLsxASQtuKbWvtAFk7vGjTX5h44EXGJIbm0B4bC11dJfAFMGkyi6kBGBOuEL9ClvnP/EpjLbJzDuGoRFKge/GDOaCA/rME2piFczV5XVado4cBNHD/tjsfNp7qSitrQIDAQAB", "RSA2");

	}
}