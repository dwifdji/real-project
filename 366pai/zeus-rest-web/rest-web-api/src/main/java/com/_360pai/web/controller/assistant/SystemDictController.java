package com._360pai.web.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.common.SystemDict;
import com._360pai.core.facade.activity.ActivityFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 描述 数据字典
 *
 * @author : whisky_vip
 * @date : 2018/9/13 13:55
 */
@RestController
public class SystemDictController {
    @Reference(version = "1.0.0")
    ActivityFacade activityFacade;
    @Autowired
    private RedisCachemanager redisCachemanager;


    @Autowired
    private GatewayProperties gatewayProperties;

    @RequestMapping(value = "/open/assistant/systemDict")
    public ResponseModel systemDict() {
        Map<Object, Map<Object, Object>> map = SystemDict.instance.getSystemDict();
        map.putAll(getSystemDict());
        return ResponseModel.succ(map);
    }


    @RequestMapping(value = "/open/assistant/getConfigInfo")
    public ResponseModel getConfigInfo() {
        Map<String,String> map = new HashMap<>();
        map.put("mqAppId",gatewayProperties.getMqAppId());
        return ResponseModel.succ(map);
    }




    private Map<Object, Map<Object, Object>> getSystemDict() {
        Map<Object, Map<Object, Object>> map = new LinkedHashMap<>();
        String cacheAssetProperty = (String) redisCachemanager.get(RedisKeyConstant.SYSTEM_DICT_ASSET_PROPERTY);
        String cacheAssetCategory = (String) redisCachemanager.get(RedisKeyConstant.SYSTEM_DICT_ASSET_CATEGORY);
        if (StringUtils.isEmpty(cacheAssetProperty) || StringUtils.isEmpty(cacheAssetCategory)) {
            Map<Object, Map<Object, Object>> dynamicMap = activityFacade.getSystemDict();
            if (!dynamicMap.isEmpty()) {
                map.putAll(dynamicMap);
            }
        } else {
            Map<Object, Object> propertyMap  =  JSON.parseObject(cacheAssetProperty, Map.class);
            map.put("assetProperty", propertyMap);
            Map<Object, Object> categoryMap  =  JSON.parseObject(cacheAssetCategory, Map.class);
            map.put("assetCategory", categoryMap);
        }
        return map;
    }
}
