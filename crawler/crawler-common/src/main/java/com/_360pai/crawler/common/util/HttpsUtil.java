package com._360pai.crawler.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * @author yan
 * @date 2016-6-9 11:03:04
 * @version V1.0
 * @desc
 */
public class HttpsUtil {
	
	private static String paiLoginUrl = "https://passport.csdn.net/v1/register/pc/login/doLogin";
	
	private static String nikeLoginUrl = "https://unite.nike.com/login?appVersion=548&experienceVersion=446&uxid=com.nike.commerce.snkrs.web&locale=zh_CN&backendEnvironment=identity&browser=Google%20Inc.&os=undefined&mobile=true&native=false&visit=2&visitor=062ba01f-1b96-482f-87c0-f87596441512";
    public static final String get(final String url, final Map<String, Object> params) {
        StringBuilder sb = new StringBuilder("");

        if (null != params && !params.isEmpty()) {
            int i = 0;
            for (String key : params.keySet()) {
                if (i == 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(key).append("=").append(params.get(key));
                i++;
            }
        }

        CloseableHttpClient httpClient = createSSLClientDefault(); 


        CloseableHttpResponse response = null;
        HttpGet get = new HttpGet(url + sb.toString());
        
//        get.addHeader(":authority", "unite.nike.com");
//        get.addHeader(":method", "GET");
////        get.addHeader(":path", "/getUserService?appVersion=548&experienceVersion=446&uxid=com.nike.commerce.snkrs.web&locale=zh_CN&backendEnvironment=identity&browser=Google%20Inc.&os=undefined&mobile=true&native=false&visit=1&visitor=3bfeca5e-0e8d-4e23-909b-3db1e8a6e604&viewId=unite&atgSync=false");
//        get.addHeader(":scheme", "https");
//        get.addHeader("accept", "*/*");
//        get.addHeader("accept-encoding", "gzip, deflate, br");
//        get.addHeader("accept-language", "zh-CN,zh;q=0.9");
//        get.addHeader("authorization",  "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6Ijc2YWI1NThkLWMwZTMtNGVhYi05MTljLTJkYjA3YjFjN2NhMHNpZyJ9.eyJ0cnVzdCI6MTAwLCJpYXQiOjE1NTE3NzYxNjgsImV4cCI6MTU1MTc3OTc2OCwiaXNzIjoib2F1dGgyYWNjIiwianRpIjoiNzM5OGRhNTAtMjM4ZS00ZjZhLWI3ZjQtMWRlNTkxZmY4NDlmIiwibGF0IjoxNTUxNzc2MTY4LCJhdWQiOiJjb20ubmlrZS5kaWdpdGFsIiwic3ViIjoiY29tLm5pa2UuY29tbWVyY2Uuc25rcnMud2ViIiwic2J0IjoibmlrZTphcHAiLCJzY3AiOlsiY29tbWVyY2UiXSwicHJuIjoiMjVhOTNiNWMtODg2ZS00OTllLWFkZDItODMwMGYyNzdiNTBkIiwicHJ0IjoibmlrZTpwbHVzIn0.Oknzxmh30pcOK7UUyFGfzqB2VGT2gMyAb0nuU_AzvgMFTYpDpIZJYY55Rd9yMZv1t82jdQ9TS6whqp8c6NY9-u66iNR2TB_ckosVtiu5v41puSifcibgVSYTIHiCBbFpBWjHP4pRE7BT1IImWe7gkNk_gZWtzTbQk2OOWZyD7SkTb9CFm2xHPmZOU9YPMkxaxLm8l9jrwRPtbSNdgS-ma0hsF8b9y0sSOlcvdV3BRAvPEdoYpj8LEJl7fcIV-W7g9m0kNKa-QfBsEQZ4NoWwJzpRl1uxsoXvp9tamd6vicNiWR0kGSYeoXT3hbibuh7xQ-HnX_cdd40kuqDoiRm9kQ");
//        get.addHeader("cache-controlss", "no-cache");
//        get.addHeader("cookie", "anonymousId=0E6FE79B8E56630CD18B16C5F19ECE9B; NIKE_COMMERCE_COUNTRY=CN; NIKE_COMMERCE_LANG_LOCALE=zh_CN; guidU=7832413b-1973-4ddd-96e2-f872f435861a; RES_TRACKINGID=783320810044095; ResonanceSegment=1; _gscu_207448657=41575779k1sqo138; _smt_uid=5be29463.7f6ae10; CONSUMERCHOICE_SESSION=t; CONSUMERCHOICE=cn/zh_cn; ajs_user_id=null; ajs_group_id=null; ajs_anonymous_id=%220E6FE79B8E56630CD18B16C5F19ECE9B%22; siteCatalyst_sample=28; dreamcatcher_sample=20; neo_sample=33; neo.swimlane=37; _gcl_au=1.1.1292405866.1550552450; sq=3; cicIntercept=1; lls=3; guidA=642084b6ed200000aaed785c3c0200003a090000; dreams_sample=18; AMCVS_F0935E09512D2C270A490D4D%40AdobeOrg=1; Hm_lvt_ed406c6497cc3917d06fd572612b4bba=1551429039; _gscbrs_207448657=1; exp.swoosh.user=%7B%22granted%22%3A0%7D; nike_locale=cn/zh_cn; neo.experiments=%7B%22main%22%3A%7B%223333-interceptor-cn%22%3A%22a%22%7D%2C%22snkrs%22%3A%7B%7D%2C%22ocp%22%3A%7B%7D%2C%22thirdparty%22%3A%7B%7D%7D; AnalysisUserId=58.222.30.6.147951551617694403; clickedGetApp=true; utag_main=_st:1551768546655$ses_id:1551767457437%3Bexp-session; Hm_lpvt_ed406c6497cc3917d06fd572612b4bba=1551766748; s_sess=%20c51%3Dvertical%3B%20s_cc%3Dtrue%3B%20tp%3D1683%3B%20prevList2%3D%3B%20s_ppv%3Dnikecom%25253Ecart%25253Eview%252C100%252C99%252C1682%3B; AMCV_F0935E09512D2C270A490D4D%40AdobeOrg=793872103%7CMCIDTS%7C17960%7CMCMID%7C58525815428214604222388178590142175074%7CMCAAMLH-1552371562%7C11%7CMCAAMB-1552371562%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1551766702.718%7CNONE%7CMCAID%7CNONE%7CvVersion%7C3.4.0; s_cc=true; bm_sz=7A56AC50C67381EC30077FB9D4D5887A~YAAQJml7dmcKUS9pAQAAjX+CTAOXq1nxh6K+M+rXFvgKHC6oUwwj7kpAWXcI8zuBnZzJ1qIaHMa5vcBEonjLsiS3PpsWCuZQ+4tZZ3Xxhrw+S0rrKPTxVo0SK2qTiGjJIugmTlLzcQuyzH1DYC1Zfx0mXWaB+7CLbMYQYq0msQVfh4y4BoWt6oO41SaRCg==; _abck=9D1C229AD1A3042DE20426C664A65559~0~YAAQZCCEtvjcFDdpAQAAQOmDTAG9jf97pb69UIEOQlrMDc69tH3d9Eb6aKR5JMGbMZ8/MukBUcwRkcUr17uZo4Q4FEF8b2hHzXwGPEBbpOa1os6zsDkAyEQMhHBduM5kEJjX1Y1+yH5IlrUmywZan0wtKFP7hmxJ/BpFNzEMZ8O73IWpM3JpOwRi0sUurGV/F110B2Nx/SfjPlp15r67cWOTbN8AkwZ9T1b5MOuL0s5eSnpz99WQpJZN4VQjc1/KbP4FM077bM2lg7qRxP2MWpqU/+CErfUfrEoXkDu94W/uGGBKGQad~-1~-1~-1; gridViewSetting=true; llCheck=0fwqe0Ybw34g0I0SbOq/oK+gC+0fAbE0n6gPkWU/Bz1Zx/emCW3IHtk8i8cam2J8Dn2KoBAIZbTtQoLwBj1HVWHCvEaQEs+oOpwD7GtK6xjalq0vzvjaEpDdcM191J8WHHWhOhdyUmWnjUlKedCjQxWizONvcidh82GHf16j+c4=; bm_mi=75CA57404CEF1A1A3F3C68F77D08E2A8~1K2FAgjql3n2tDWhIK5wEUkG2QoFrHeLRyj2ev3xRwChGF7V3MSVl9DXRYbyxjGoB47gPRSe6U1jn2Kyv0ygEJ2dpi2pR6oDvk/kHhUCqP2S6FPWThHZWPcFQa5qLUpGurZdyu0L152hZSCjs554NHG/VvchTceN3O25pFohmKrdJH01W/bE/NyDDX4oLsNBvribLIJWF+sic8NkQxNr4axZzHVzqUWLGmx3sNJLLAIYhICbnJMC3zP5BWuJ0u+H+e2eleJNS4k0kXV5jqeBng==; guidS=3e86952c-f1a0-4119-9f8a-18da6e34fee9; guidSTimestamp=1551776073590|1551776073590; APID=088CA0267F5912C81E9C4AC3FC311AF5.sin-328-app-ap-0; RT=\"dm=nike.com&si=aa85fc29-751a-4538-89dd-cd94f1ccecce&ss=1551766758965&sl=1&tt=4137&obo=0&sh=1551776075159%3D1%3A0%3A4137&bcn=%2F%2F1288af19.akstat.io%2F&ld=1551776075159\"; NIKE_CART_SESSION={%22bucket%22:%22jcart%22%2C%22country%22:%22CN%22%2C%22id%22:0%2C%22merged%22:false}; ak_bmsc=6A74119B2B93EDD49FE1D0B0A52FE129B6842064ED20000047397E5CB478B663~plkD39hKb1ijgqy2ww3hWGz/IY9HbH1mNd4J/7zjKxVdDBlBm2WvXDcruiwTEg/P4/sjQA/q8Qx97JPk+XAgMgh3ilnCGtEfOhTA9QokSC2puTBUYc5sh36MpA7CiosqnC4y6bURryM8g9BTq7RYqw3Z9ejU/Ckv1K8NJvLqf0cR35R4hTU60TjC6HY23i7r2dYNJuluO4n0p6aUHDIcmDtRp0HxdAsCElZ7TFe0PwExxJIZzTrUM1vmWIpcVt4RHwwhZz5fnOf7JIMO81MW100g==; s_pers=%20c6%3Dno%2520value%7C1551777965209%3B%20c5%3Dno%2520value%7C1551777965213%3B; bm_sv=FB08D1911708F62602148FD7BBCD7EBB~OgXC6pjzMGr12tqZI8AeA7WLXcoCGFK+4T8zGEwLlhOlvUYzPAZgR/sQaEuS2XQSERjq/shLrirI7CsvWRFze8dPNUaCYgJzaA8xziiOhsgZa5FAP7UtlAf9zxbSl0Tny3xo0kC7UE5RFeIXtO87zQ==");
//        get.addHeader("origin", "https://www.nike.com");
//        get.addHeader("pragma", "no-cache");
////        get.addHeader("referer", "https://www.nike.com/cn/launch/");
//        get.addHeader("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");        
//       
        String result = "";

        try {
            response = httpClient.execute(get);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    result = EntityUtils.toString(entity, "UTF-8");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HttpsUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException ex) {
                    Logger.getLogger(HttpsUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return result;
    }

    public static final String post(final String url, final Map<String, Object> params) {
    	
        CloseableHttpClient httpClient = createSSLClientDefault();
        HttpPost post = new HttpPost(url);
        
        CloseableHttpResponse response = null;
        
         
        post.addHeader("content-type", "application/json");
        post.addHeader("accept-encoding", "gzip, deflate, br");
//        post.addHeader("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");

        if (null != params && !params.isEmpty()) {
            List<NameValuePair> nvpList = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                nvpList.add(nvp);
            }
            post.setEntity(new UrlEncodedFormEntity(nvpList, Charset.forName("UTF-8")));
        }

        String result = "";

        try {
            response = httpClient.execute(post);

             
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
             
        } catch (IOException ex) {
            Logger.getLogger(HttpsUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException ex) {
                    Logger.getLogger(HttpsUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return result;
    }

    private static CloseableHttpClient createSSLClientDefault() {

        SSLContext sslContext;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                @Override
                public boolean isTrusted(X509Certificate[] xcs, String string){
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyStoreException ex) {
            Logger.getLogger(HttpsUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HttpsUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(HttpsUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return HttpClients.createDefault();
    }

    public static void main(String[] args) throws IOException {
    	
    	Map<String, Object> params = new HashMap<String, Object>();
//      	params.put("email", "15538068782");
//    	params.put("pwd", "bit15538068782");
//    	params.put("userIdentification", "15538068782");
//    	params.put("fkid", "WC39ZUyXRgdH8LdCT1AEC1gpOCNOtGb3BN2LDyB6SOx4P08Xp4tjaiHTyCix2GsDZMn5uv0Fu0DWpq4bFNxwqYKdWjtHiszr3tYMtjdGCP/f/vQEmbrbDXUzA0CtbXuWTErzxpd9YAAdWsZcVfcrqYpMTBKZTURHG471YC9KiTqh8u3AqAZqHIX8gd4xaYnWr8KpmTxMqvGUKGa3GpO86gzIwwQmcULq/XHZbu54sXD79v5kY2VaGBy1MkTgJCHagUXSi7AOqpnbPzkIT4yg2ruRinzXzxrxK1487577677129");
//    	params.put("uaToken", "115#6tpqS51O1TaJdbxWTCCf1CsoE51GCpA11g2mOCJZRCOcAzNCjO01CCtuR59iFaUzlHaFJTpfu5qQASqfeHC8ykNQi/JJBr81Arhoa8ejMKsQys1z5KQziWI1i7zUhaPdAWIdaT3Vnazh9I2/ec78zrRdiupJhy889tG0arkjzWSd9NCPeVa8zrmQiR6+RH/z+XC0qLpb7aFdnIPzuxgz6MCdRQW+zzUzoHN0r8pbvdOd+jCzeK1GOMZpiQJRhUU89BhVD58E0zFQASfkF+T4ukdQwPVgHZz4A5lcaLBfsaFQASAyj+/4yWZQiQWR6UU8AWNDwLRiCKK5TivLDKA5Y5Cvv3edwN7lT3Spab9EGKY90l98EnzJqWNgqP/th16yUFTMsBKCPr3fzqyNkzot19xx3tolkEvANG05HlMOpVesZtmcI3RYoHA/g4aKWJt+N3eyPEUyTVLndR2+aaaHv/ybbb5ZvGVS0oO7JlE18LuggOkayd+wSmUUcQ1l7ocYmJeiv/XfGXHUXQTGhHjwABsU/ODE/L9LDfkzrnDc6hx3tKR63tCLp/srFVqZQn4BJ46NFKKGKvwgMuLaqjI4dzOjhoM5iXcHj8CYYNPUEXt5A1byE2e61YTo4B9Xd3mdk+7HbqU/joNLdvdtrph3BwF55xplQvcAw24k92/OF0TfEhG7g2kzu46QfOam+lYyrXbLD9osPL5uV6mzHf6Gv5bpGkA/uSNEtPDmi3W5Nl9dVbrRf6JuDSDKVSa/uMyb9jvJRpmls0moCr8jdfj9Dd9+Qq/+VCjalY5QD8Hey1W6Hwgym5fvEHTCIMYyT22dQe//LkP0Noh/s2VOkTUbKTEyPPR7a5tt7sI6j+14lUQCgvjxuGQeh5KvTkqnuXG9P3l9IYrfW4kmXF0fc0E70cjgNuC1F0k1tXTjzS9H6RUfg/BwBiA+LT4JNfwh96hqxP5vdRPIkoDvagtZ6/3ifcF83/8onpL46WWnZTvyQCfMZ6Iiuvv8iqy390iJAsTSWxvQHMJpJclAnHgniZwmzUiuvgVfPxS6ZFsC1ebcIZTa1mLOwMMKmrP6g/ZB6SbH8cneWa+bqtKf6YlbDdlAqDefBUkM0b+/wvTvGg+ZBxgI7hRCbPPZXcyX6Z+EvgquEtkjLwmsA0naKTzYD4Lk2qVhuzjAPp2vWx/aMQdpdqh0+Xr1TT/3BwAGg9GB06XvFT5U55QKC04K4xohuMNmfgFQDLhze8odR1leoPHW+dCdKIrB73iHzsfdc8y2MbNg/KVUuxOfIyETh91ylIpDhkLx0GCy/n2vGUuIjLgCruZO02LvZcucKGkOh+b4ctWERjYcjrNYFn06kSKvnaC1gBM4npfna5r80ifoSsouXtpkdTsHJdXD3QknKD/YoPCzJlWS8S4fwG2dDGCr+nKOw3EDH24mEflDtLpUTq+a5nELTJiIrNsuLfmOqTMv3Uggf5z5X+WWawwQcz/6Qn951DYyeG3lFwH7rDHQDmCjwsj4");
//    	params.put("webUmidToken", "T8133E29C57952DF14EA6597D5BE12853784A0D64E8D9DAC1273A7734D5");
//    	params.put("wyToken", "9ca17ae2e6ffcda170e2e6eeb6fb33bb89aaa5c973f38a8fb3d15f938b9fbabc68f1898491cf7da9939ca7aa2af0feaec3b92af29d96d8f069fbbf8296d04a939a9bb7c84aa1b1a59ac66bb1b58596e234958aee9e");

//    	
//    	params.put("username", "+8615538068782");
//    	params.put("password", "LhL1573621793");
//    	params.put("client_id", "PbCREuPr3iaFANEDjtiEzXooFl7mXGQ7");
//    	params.put("ux_id", "com.nike.commerce.snkrs.web");
//    	params.put("grant_type", "password");
//    	String loginResult = post(nikeLoginUrl , params);
//    	System.out.println("登陆的返回状态是" + loginResult);
    	
    	String orderResult = get("https://api.nike.com/product_feed/threads/v2/?filter=marketplace%28CN%29&filter=language%28zh-Hans%29&filter=channelId%28010794e5-35fe-4e32-aaff-cd2c74f89d61%29&filter=publishedContent.properties.seo.slug%28moon-racer-mountain-blue-sail-team-orange%29&filter=exclusiveAccess%28true%2Cfalse%29", null);
        System.out.println("调用下单接口返回的结果是" + orderResult);
    	
    	
     
    }
     
}
