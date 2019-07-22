package com._360pai.arch.core.redis;


import org.omg.CORBA.SystemException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 依赖redis实现的缓存管理器
 *
 * @author zhouling
 *
 */
public interface RedisCachemanager extends Cachemanager {

    /**
     * 模糊查询
     *
     * @param pattern
     * @return values
     * @throws SystemException
     */
    public List<Object> getValues(final String pattern) throws SystemException;

    /**
     * 保存至缓存中的map
     *
     * @param key
     * @param fieldKey
     * @param value
     * @return result
     * @throws SystemException
     */
    public boolean hSet(String key, String fieldKey, Serializable value) throws SystemException;

    /**
     * 批量保存至缓存中的
     *
     * @param key
     * @param fieldMap
     * @return result
     * @throws SystemException
     */
    public <T extends Serializable> void hMSet(String key, Map<String, T> fieldMap) throws SystemException;

    /**
     * 从缓存中的map中获取值
     *
     * @param key
     * @param fieldKey
     * @return value
     * @throws SystemException
     */
    public Object hGet(String key, String fieldKey) throws SystemException;

    /**
     * 删除
     *
     * @param key
     * @param fields
     * @return result
     * @throws SystemException
     */
    public long hDel(String key, String... fieldKeys) throws SystemException;

    /**
     * 从缓存中获取map
     *
     * @param key
     * @param clazz
     * @return map
     * @throws SystemException
     */
    public <T> Map<String, T> hGetAll(String key, Class<T> clazz) throws SystemException;
}
