package com._360pai.gateway.common.dfftpay;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HttpUt
 * 
 * @author Sun.Tao
 */
public class HttpUt {

	private static HostnameVerifier hv = new HostnameVerifier() {
		public boolean verify(String urlHostName, SSLSession session) {
			System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
			return true;
		}
	};

	/**
	 * 支付HTTPS <br>
	 * Retrieves the response from the server by opening a connection and merely<br>
	 * reading the response.<br>
	 * <br>
	 * HttpURLConnection是基于HTTP协议的，其底层通过socket通信实现。如果不设置超时（timeout），在网络异常的情况下，<br>
	 * 可能会导致程序僵死而不继续往下执行。<br>
	 */
	public static String retrieveResponseFromServer(final String url, final String param) {
		HttpURLConnection connection = null;

		try {
			trustAllHttpsCertificates();
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			URL u = new URL(url);
			connection = (HttpURLConnection) u.openConnection();
  			connection.setRequestMethod("POST");// post与get的不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内
			connection.setDoOutput(true);// 设置是否向httpUrlConnection输出
			connection.setDoInput(true);// 设置是否从httpUrlConnection读入
			connection.setUseCaches(false);// Post 请求不能使用缓存
			connection.setConnectTimeout(25000);// 设置连接主机超时（单位：毫秒）6秒
			connection.setReadTimeout(60000);// 设置从主机读取数据超时（单位：毫秒）6秒
			connection.getOutputStream().write(param.getBytes("UTF-8"));
   			connection.getOutputStream().flush();
			connection.getOutputStream().close();
			InputStreamReader is = new InputStreamReader(connection.getInputStream(), "UTF-8");

			char[] bf = new char[888];
			StringBuffer buffer = new StringBuffer();
			int i;
			while ((i = is.read(bf)) != -1) {
				buffer.append(bf, 0, i);
			}
			return buffer.toString();
		} catch (final IOException e) {
			e.printStackTrace();

			throw new RuntimeException(e);
 		} catch (final Exception e1) {
			throw new RuntimeException(e1);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	private static void trustAllHttpsCertificates() throws Exception {
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			return;
		}

		public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			return;
		}
	}
}
