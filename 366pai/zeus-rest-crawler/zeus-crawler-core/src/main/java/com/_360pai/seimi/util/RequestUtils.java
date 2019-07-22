package com._360pai.seimi.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/10/15 21:14
 */
public class RequestUtils {

    public static String getDomain(String host) {
//定义好获取的域名后缀。如果还有要定义的	请添加 |(\\.域名的后缀) 。
        String regStr = "[0-9a-zA-Z]+((\\.com)|(\\.cn)|(\\.org)|(\\.net)|(\\.edu)|(\\.com.cn)|(\\.xyz)|(\\.xin)|(\\.club)|(\\.shop)|(\\.site)|(\\.wang)" +
                "|(\\.top)|(\\.win)|(\\.online)|(\\.tech)|(\\.store)|(\\.bid)|(\\.cc)|(\\.ren)|(\\.lol)|(\\.pro)|(\\.red)|(\\.kim)|(\\.space)|(\\.link)|(\\.click)|(\\.news)|(\\.news)|(\\.ltd)|(\\.website)" +
                "|(\\.biz)|(\\.help)|(\\.mom)|(\\.work)|(\\.date)|(\\.loan)|(\\.mobi)|(\\.live)|(\\.studio)|(\\.info)|(\\.pics)|(\\.photo)|(\\.trade)|(\\.vc)|(\\.party)|(\\.game)|(\\.rocks)|(\\.band)" +
                "|(\\.gift)|(\\.wiki)|(\\.design)|(\\.software)|(\\.social)|(\\.lawyer)|(\\.engineer)|(\\.org)|(\\.net.cn)|(\\.org.cn)|(\\.gov.cn)|(\\.name)|(\\.tv)|(\\.me)|(\\.asia)|(\\.co)|(\\.press)|(\\.video)|(\\.market)" +
                "|(\\.games)|(\\.science)|(\\.中国)|(\\.公司)|(\\.网络)|(\\.pub)" +
                "|(\\.la)|(\\.auction)|(\\.email)|(\\.sex)|(\\.sexy)|(\\.one)|(\\.host)|(\\.rent)|(\\.fans)|(\\.cn.com)|(\\.life)|(\\.cool)|(\\.run)" +
                "|(\\.gold)|(\\.rip)|(\\.ceo)|(\\.sale)|(\\.hk)|(\\.io)|(\\.gg)|(\\.tm)|(\\.com.hk)|(\\.gs)|(\\.us))";

        Pattern p      = Pattern.compile(regStr);
        Matcher m      = p.matcher(host);
        String  domain = null;
//获取一级域名
        while (m.find()) {
            domain = m.group();
        }
        return StringUtils.isBlank(domain) ? null : domain;
    }

    public static Map<String, String> toMap(String url) {
        Map<String, String> map = new HashMap<>();
        if (url.contains("?")) {
            String[] paraPairs = url.substring(url.indexOf("?") + 1).split("&");
            for (String paraPair : paraPairs) {
                String[] kv = paraPair.split("=");
                if (kv.length > 1) {
                    map.put(kv[0], kv[1]);
                } else {
                    map.put(kv[0], "");
                }
            }
        }
        return map;
    }

    public static void main(String[] args) {
        String domain = getDomain("http://locahost:8080/#/login");
        System.out.println(domain);

        String url = "http://www.gpai.net/sf/item2.do?Web_Item_ID=19349&b=1&c=&d=&n";
        System.out.println(url.substring(url.indexOf("?") + 1));
        System.out.println(toMap(url));
        System.out.println("--");
    }
}
