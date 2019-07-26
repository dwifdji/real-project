package com.liuhaolei.dreamer.util;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.liuhaolei.dreamer.common.CommonString;
import com.liuhaolei.dreamer.conf.Audience;
import com.liuhaolei.dreamer.model.User;
import com.liuhaolei.dreamer.service.UserService;

@Service("redisUtil")
public class RedisUtil {
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Resource(name = "stringRedisTemplate")
	ValueOperations<String, String> valOpsStr;

	@Autowired
	RedisTemplate<Object, Object> redisTemplate;

	@Resource(name = "redisTemplate")
	ValueOperations<Object, Object> valOpsObj;
	@Autowired
	private UserService userService;
	@Autowired
	private Audience audience;

	
	/**
	 * 根据指定key获取String
	 * 
	 * @param key
	 * @return
	 */
	public String getStr(final String key) {
		try {
			return valOpsStr.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 删除
	 * @param key
	 * @return
	 */
	public Boolean delStr(final String key) {
		try {
			return stringRedisTemplate.delete(key);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 设置值并带设定超时时间
	 * @param key
	 * @param value
	 * @param expireTime
	 * @return
	 */
	public Boolean setStr(final String key, String value, Long expireTime) {

		Boolean result = false;
		try {
			valOpsStr.set(key, value, expireTime, TimeUnit.SECONDS);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}

	}
	
	/**
	 * redis中设置值不设置超时间
	 * @param key
	 * @param value
	 * @param expireTime
	 * @return
	 */
	public Boolean setStrNotime(final String key, String value) {

		Boolean result = false;
		try {
			valOpsStr.set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}

	}
	
	/**
	 * 
	 * @return
	 */
	public Boolean deleteKey(String key) {
		Boolean result = false;
		try {
			return stringRedisTemplate.delete(key);
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}
	
	/**
	 * 根据key获取对象
	 * @param key
	 * @return
	 */
	public Object getObj(final String key) {
		try {
			return valOpsObj.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 设置值并带设定超时时间
	 * @param key
	 * @param value
	 * @param expireTime
	 * @return
	 */
	public Boolean setObj(final Object key, Object value) {

		Boolean result = false;
		try {
			valOpsObj.set(key, value, 60, TimeUnit.SECONDS);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}

	}
	
	
	/**
	 * 设置值并带设定超时时间
	 * @param key
	 * @param value
	 * @param expireTime
	 * @return
	 */
	public Boolean setObjNotime(final Object key, Object value, Long expireTime) {

		Boolean result = false;
		try {
			valOpsObj.set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}

	}
	
	
	/**
	 * 根据key删除缓存
	 * @return
	 */
	public Boolean deleteObj(Object key) {
		Boolean result = false;
		try {
			redisTemplate.delete(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}

	/**
	 * 刷新token
	 * @param userId
	 * @return
	 */
	public String refashToken(String userIdSt, String oldToken) {
		//删除旧的token
		deleteKey(oldToken);
		
		String[] userIdSts = userIdSt.split("_");
		User userMdoel = userService.selectById(userIdSts[1]);
		
		String createJWT = JwtTokenUtil.createJWT(userMdoel.getUserName(), 
				String.valueOf(userMdoel.getId()), 
				null, 
				audience.getClientId(), 
				audience.getName(),
                audience.getExpiresSecond()*1000,
                audience.getBase64Secret());
		
		//设置新的token
		setStr(createJWT, CommonString.USERID + String.valueOf(userMdoel.getId()), 2400l);

		return createJWT;
	}
}
