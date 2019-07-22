package com.winback.arch.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述 全局上下文工具类,用于储存一些东西
 *
 * @author : whisky_vip
 * @date : 2018/9/18 11:23
 */
public class ThreadLocalContextUtil {
    private static final ThreadLocal<Map<Object, Object>> MAP_THREAD_LOCAL = ThreadLocal.withInitial(HashMap::new);
 
    /**
     * 根据key获取值
     * @param key
     * @return
     */
    public static Object getValue(Object key) {
        if(MAP_THREAD_LOCAL.get() == null) {
            return null;
        }
        return MAP_THREAD_LOCAL.get().get(key);
    }
 
    /**
     * 存储
     * @param key
     * @param value
     * @return
     */
    public static Object setValue(Object key, Object value) {
        Map<Object, Object> cacheMap = MAP_THREAD_LOCAL.get();
        if(cacheMap == null) {
            cacheMap = new HashMap<>();
            MAP_THREAD_LOCAL.set(cacheMap);
        }
        return cacheMap.put(key, value);
    }
 
    /**
     * 根据key移除值
     * @param key
     */
    public static void removeValue(Object key) {
        Map<Object, Object> cacheMap = MAP_THREAD_LOCAL.get();
        if(cacheMap != null) {
            cacheMap.remove(key);
        }
    }
 
    /**
     * 重置
     */
    public static void reset() {
        if(MAP_THREAD_LOCAL.get() != null) {
            MAP_THREAD_LOCAL.get().clear();
        }
    }
 
}
