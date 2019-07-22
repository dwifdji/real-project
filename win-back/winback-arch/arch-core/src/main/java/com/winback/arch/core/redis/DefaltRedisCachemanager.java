package com.winback.arch.core.redis;

import com.winback.arch.common.exception.ExceptionEnumImpl;
import com.winback.arch.common.exception.SystemException;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;
import java.util.*;

/**
 * 依赖redis实现的缓存管理器 的默认实现 序列化方式使用jdk自带方式，可以考虑利用第三方兼容序列化库
 *
 * @author zhouling
 *
 */
@SuppressWarnings("ALL")
@Service
public class DefaltRedisCachemanager implements RedisCachemanager {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaltRedisCachemanager.class);

	/**
	 * RedisTemplate
	 */
	private RedisTemplate<String, Serializable> redisTemplate;

	/**
	 * 设置 redisTemplate
	 *
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(RedisTemplate<String, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 *
	 * @param pattern
	 * @return values
	 * @throws SystemException
	 */
	@Override
	public List<Object> getValues(final String pattern) throws SystemException {
		return execute(new RedisCallback<List<Object>>() {
			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] patternBytes = getStringSerializer().serialize(pattern);
				Set<byte[]> keysBytes = connection.keys(patternBytes);
				if (keysBytes != null && !keysBytes.isEmpty()) {
					List<Object> values = new ArrayList<>(keysBytes.size());
					for (byte[] keyBytes : keysBytes) {
						byte[] valueBytes = connection.get(keyBytes);
						if (valueBytes != null) {
							Object value = getDefaultSerializer().deserialize(valueBytes);
							if (value != null) {
								values.add(value);
							}
						}
					}
					return values;
				}
				return Collections.emptyList();
			}
		});
	}

	/**
	 * 保存至缓存
	 *
	 * @param key
	 * @param value
	 * @param timeout
	 *            单位秒
	 */
	@Override
	public void set(final String key, final Serializable value, final long timeout) throws SystemException {
		if (value != null) {
			execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					connection.setEx(getStringSerializer().serialize(key), timeout,
							getDefaultSerializer().serialize(value));
					return null;
				}
			});
		}
	}

	/**
	 * 保存至缓存
	 *
	 * @param key
	 * @param value
	 */
	@Override
	public void set(final String key, final Serializable value) throws SystemException {
		if (value != null) {
			execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					connection.set(getStringSerializer().serialize(key), getDefaultSerializer().serialize(value));
					return null;
				}
			});
		}
	}

	/**
	 * 从缓存中读取
	 *
	 * @param key
	 * @return value
	 */
	@Override
	public Object get(final String key) throws SystemException {
		return execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyBytes = getStringSerializer().serialize(key);
				byte[] valueBytes = connection.get(keyBytes);
				if (valueBytes != null && valueBytes.length > 0) {
					return getDefaultSerializer().deserialize(valueBytes);
				}
				return null;
			}
		});
	}

	/**
	 * 从缓存中移除
	 *
	 * @param key
	 * @throws SystemException
	 */
	@Override
	public long del(final String key) throws SystemException {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.del(getStringSerializer().serialize(key));
			}
		});
	}


	@Override
	public boolean hSet(final String key, final String fieldKey, final Serializable value) throws SystemException {
		if (value != null) {
			return execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					return connection.hSet(getStringSerializer().serialize(key),
							getStringSerializer().serialize(fieldKey), getDefaultSerializer().serialize(value));
				}
			});
		}
		return false;
	}

	@Override
	public <T extends Serializable> void hMSet(final String key, final Map<String, T> fieldMap) throws SystemException {
		if (fieldMap != null && !fieldMap.isEmpty()) {
			execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					Map<byte[], byte[]> cacheValue = new HashMap<byte[], byte[]>(fieldMap.size());
					for (String fk : fieldMap.keySet()) {
						byte[] bk = getStringSerializer().serialize(fk);
						byte[] bv = getDefaultSerializer().serialize(fieldMap.get(fk));
						cacheValue.put(bk, bv);
					}
					connection.hMSet(getStringSerializer().serialize(key), cacheValue);
					return null;
				}
			});
		}
	}

	@Override
	public Object hGet(final String key, final String fieldKey) throws SystemException {
		return execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] valueBytes = connection.hGet(getStringSerializer().serialize(key), getStringSerializer()
						.serialize(fieldKey));
				if (valueBytes != null && valueBytes.length > 0) {
					return getDefaultSerializer().deserialize(valueBytes);
				}
				return null;
			}
		});
	}

	@Override
	public long hDel(final String key, final String... fieldKeys) throws SystemException {
		if (fieldKeys != null && fieldKeys.length > 0) {
			return execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					byte[][] fieldkeyBytes = new byte[fieldKeys.length][];
					for (int i = 0; i < fieldKeys.length; i++) {
						fieldkeyBytes[i] = getStringSerializer().serialize(fieldKeys[i]);
					}
					return connection.hDel(getStringSerializer().serialize(key), fieldkeyBytes);
				}
			});
		}
		return -1L;
	}

	@Override
	public <T> Map<String, T> hGetAll(final String key, Class<T> clazz) throws SystemException {
		return execute(new RedisCallback<Map<String, T>>() {
			@Override
			public Map<String, T> doInRedis(RedisConnection connection) throws DataAccessException {
				Map<byte[], byte[]> byteMap = connection.hGetAll(getStringSerializer().serialize(key));
				if (byteMap != null && !byteMap.isEmpty()) {
					Map<String, T> valueMap = new HashMap<String, T>();
					for (byte[] bk : byteMap.keySet()) {
						String vk = getStringSerializer().deserialize(bk);
						T vv = (T) getDefaultSerializer().deserialize(byteMap.get(bk));
						valueMap.put(vk, vv);
					}
					return valueMap;
				}
				return Collections.emptyMap();
			}
		});
	}

	/**
	 * 获取StringSerializer
	 *
	 * @return StringSerializer
	 */
	private RedisSerializer<String> getStringSerializer() {
		return redisTemplate.getStringSerializer();
	}

	/**
	 * 获取DefaultSerializer
	 *
	 * @return JdkSerializationRedisSerializer
	 */
	private RedisSerializer<Object> getDefaultSerializer() {
		return (JdkSerializationRedisSerializer) redisTemplate.getDefaultSerializer();
	}

	/**
	 * 执行redis
	 *
	 * @param action
	 * @return T
	 */
	private <T> T execute(RedisCallback<T> action) {
		try {
			return redisTemplate.execute(action);
		} catch (Exception e) {
			LOGGER.error("执行redis发生异常:" + e.getMessage(), e);
			if (e instanceof DataAccessException) {
				DataAccessException de = (DataAccessException) e;
				if (de.getCause() != null) {
					LOGGER.error("执行redis发生DataAccessException.Cause" + de.getCause().getMessage(), de.getCause());
				} else {
					LOGGER.error("执行redis发生DataAccessException:" + de.getMessage(), de);
				}
			}
			throw new SystemException(ExceptionEnumImpl.DO_CACHE_ERROR);
		}
	}

}
