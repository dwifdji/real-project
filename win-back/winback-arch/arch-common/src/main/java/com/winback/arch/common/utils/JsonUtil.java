package com.winback.arch.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

;

/**
 * 描述: json转换工具
 *
 * @author: whisky_vip
 * @date: 2018/8/15 16:23
 */
public class JsonUtil {
    private static SerializeConfig mapping = new SerializeConfig();
    private static String          dateFormat;

    static {
        dateFormat = "yyyy-MM-dd HH:mm:ss";
    }

    /**
     * 默认的处理时间
     *
     * @param jsonText
     * @return
     */
    public static String toJSON(Object jsonText, PropertyFilter propertyFilter) {
        return JSON.toJSONString(jsonText, propertyFilter,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteNonStringValueAsString,
                SerializerFeature.WriteMapNullValue);
    }

    /**
     * 默认的处理时间
     *
     * @param jsonText
     * @return
     */
    public static String toJSON(Object jsonText) {
        return JSON.toJSONString(jsonText,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteNonStringValueAsString,
                SerializerFeature.WriteMapNullValue);
    }

    /**
     * 默认的处理时间 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static JSONObject toJSONObject(Object object, PropertyFilter propertyFilter) {
        String json = toJSON(object, propertyFilter);
        JSONArray.parse(json);
        return JSONObject.parseObject(json);
    }

    /**
     * 默认的处理时间 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static JSONObject toJSONObject(Object object) {
        String json = toJSON(object);
        JSONArray.parseObject(json);
        return JSONObject.parseObject(json);
    }


    /**
     * 自定义时间格式
     *
     * @param jsonText
     * @return
     */
    public static String toJSON(String dateFormat, String jsonText) {
        mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
        return JSON.toJSONString(jsonText, mapping);
    }

    private static final Logger       logger       = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @SuppressWarnings("unchecked")
    public static final Map<String, Object> jsonToMap(String jsonStr) {
        return (Map<String, Object>) json2Map(jsonStr);
    }

    public static String mapToJson(Map<String, Object> map) {
        return object2JSON(map);
    }

    public static final Map json2Map(String jsonStr) {
        if (StringUtils.isBlank(jsonStr)) {
            return null;
        }

        try {
            return objectMapper.readValue(jsonStr, Map.class);
        } catch (Exception e) {
            logger.error("Json转换异常", e);
            return null;
        }
    }


    public static final SortedMap json2SortedMap(String jsonStr) {
        if (StringUtils.isBlank(jsonStr)) return null;

        try {
            return objectMapper.readValue(jsonStr, SortedMap.class);
        } catch (Exception e) {
            logger.error("Json转换异常", e);
            return null;
        }
    }

    public static String object2JSON(Object obj) {
        if (obj == null) {
            return "{}";
        }

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("转换异常", e);
            return "";
        }
        //return JSON.toJSONString(obj,SerializerFeature.WriteDateUseDateFormat);
    }

    public static String map2Json(Map map) {
        return object2JSON(map);
    }

    public static final <T> T json2Bean(String content, Class<T> valueType) {
        if (StringUtils.isBlank(content)) {
            return null;
        }

        try {
            return objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            logger.error("Json转换异常", e);
            return null;
        }
    }

    public static final Map beanToMap(Object obj) {
        try {
            Map map = objectMapper.convertValue(obj, Map.class);
            return map;
        } catch (Exception e) {
            logger.error("Json转换异常", e);
            return null;
        }
    }


    /**
     * List<objcet> 转为List<Map>
     *
     * @param object
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> beanListToMapList(List object){
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (Object o : object) {
            String s = JSON.toJSONString(o);
            Map<String, Object> map = JSONObject.parseObject(s, Map.class);
            maps.add(map);
        }
        return maps;
    }
}
