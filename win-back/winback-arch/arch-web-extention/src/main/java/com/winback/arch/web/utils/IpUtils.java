package com.winback.arch.web.utils;

import com.winback.arch.common.HttpUtils;
import com.winback.arch.core.redis.RedisCachemanager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/10/31 13:41
 */
@Component
@Slf4j
public class IpUtils {
    private static final String REDIS_KEY_IP_INFO = "ipInfo";

    private static final String LOCALHOST  = "127.0.0.1";
    private static final String LOCALHOST1 = "localhost";
    private static final String LOCALHOST2 = "0.0.0.0";
    private static final String LOCALHOST3 = "0:0:0:0:0:0:0:1";

    private static final String INNER_NET_10  = "10";
    private static final String INNER_NET_172 = "172";
    private static final String INNER_NET_192 = "192";

    @Autowired
    private RedisCachemanager redisCachemanager;

    /**
     * * {
     * *     "status": 0,
     * *     "message": "query ok",
     * *     "result": {
     * *         "ip": "61.135.17.68",
     * *         "location": {
     * *             "lng": 116.407526,
     * *             "lat": 39.90403
     * *         },
     * *         "ad_info": {
     * *             "nation": "中国",
     * *             "province": "",
     * *             "city": "",
     * *             "district": "",
     * *             "adcode": 110000
     * *         }
     * *     }
     * * }
     *
     * @param ip
     * @return
     */
    public JSONObject getAddressByIPTencent(String ip) {
        JSONObject ipInfo = (JSONObject) redisCachemanager.hGet(REDIS_KEY_IP_INFO, ip);
        if (ipInfo != null) {
            return ipInfo;
        }

        JSONObject result = getStringStringMap();
        try {
            if (!LOCALHOST1.equals(ip) && !LOCALHOST2.equals(ip) && !LOCALHOST3.equals(ip) && !LOCALHOST.equals(ip) &&
                    !ip.startsWith(INNER_NET_10) && !ip.equals(INNER_NET_172) && !ip.equals(INNER_NET_192)) {
                String resultStr = HttpUtils.sendGet("https://apis.map.qq.com/ws/location/v1/ip?key=N7XBZ-NX764-OFOUH-D5LJY-KZ3QK-6WFNX&ip=" + ip);
                if (StringUtils.isNotBlank(resultStr)) {
                    JSONObject jsonResult = JSON.parseObject(resultStr);
                    Integer    status     = (Integer) jsonResult.get("status");
                    if (status == 0) {
                        Map                 m      = (Map) jsonResult.get("result");
                        Map<String, String> detail = (Map<String, String>) m.get("ad_info");
                        result.put("nation", detail.get("nation"));
                        result.put("province", detail.get("province"));
                        result.put("city", detail.get("city"));
                        result.put("district", detail.get("district"));

                        redisCachemanager.hSet(REDIS_KEY_IP_INFO, ip, result);
                        return result;
                    }
                }
            } else {
                redisCachemanager.hSet(REDIS_KEY_IP_INFO, ip, result);
                return result;
            }
        } catch (Exception e) {
            log.error("getAddressByIp错误：{}", e.getMessage());
        }
        return result;
    }

    private JSONObject getStringStringMap() {
        JSONObject result = new JSONObject();
        result.put("nation", "");
        result.put("province", "");
        result.put("city", "");
        result.put("district", "");
        return result;
    }

}

