package com.tzCloud.arch.common.utils;

import com.tzCloud.arch.common.AccessToken;
import com.tzCloud.arch.common.JsApiTicket;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

public class WxCommonUtil {

    /**
     *
     * 功能描述: 凭证获取（GET）——access_token
     *
     */
    private final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     *
     * 功能描述: 微信JSSDK的ticket请求URL地址——jsapi_ticket
     *
     */
    private final static String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    /**
     * 发送https请求
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return rootNode(通过rootNode.get ( key)的方式获取json对象的属性值)
     */
    public static JsonNode httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        StringBuilder buffer = new StringBuilder();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");

            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if ("GET".equalsIgnoreCase(requestMethod)) {
                conn.connect();
            }
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            rootNode = mapper.readTree(buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootNode;
    }

    /**
     * 获取接口访问凭证
     *
     * @param appid     凭证
     * @param appsecret 密钥
     * @return
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;
        String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
        // 发起GET请求获取凭证
        JsonNode rootNode = httpsRequest(requestUrl, "GET", null);

        if (null != rootNode.get("access_token")) {
            accessToken = new AccessToken();
            accessToken.setAccessToken(rootNode.get("access_token").textValue());
            accessToken.setExpiresIn(toInt(rootNode.get("expires_in").toString()));
        }
        return accessToken;
    }

    /**
     * 调用微信JS接口的临时票据
     *
     * @param accessToken 接口访问凭证
     * @return
     */
    public static JsApiTicket getJsApiTicket(String accessToken) {
        String requestUrl = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", accessToken);
        // 发起GET请求获取凭证
        JsonNode rootNode = httpsRequest(requestUrl, "GET", null);
        JsApiTicket jsApiTicket = null;
        if (null != rootNode.get("ticket")) {
            jsApiTicket = new JsApiTicket();
            jsApiTicket.setTicket(rootNode.get("ticket").textValue());
            jsApiTicket.setExpiresIn(toInt(rootNode.get("expires_in").toString()));
        }
        return jsApiTicket;
    }

    private static Integer toInt(String str) {
        if (str == null || "".equals(str)) {
            return null;
        }
        return Integer.valueOf(str);
    }

}