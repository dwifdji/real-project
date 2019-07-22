
package com._360pai.core.dao.assistant.impl;

import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.condition.assistant.ProvinceCondition;
import com._360pai.core.dao.assistant.ProvinceDao;
import com._360pai.core.dao.assistant.mapper.ProvinceMapper;
import com._360pai.core.exception.DaoException;
import com._360pai.core.model.assistant.Province;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProvinceDaoImpl extends AbstractDaoImpl<Province, ProvinceCondition, BaseMapper<Province,ProvinceCondition>> implements ProvinceDao{
	
	@Resource
	private ProvinceMapper provinceMapper;
	@Resource
	private RedisCachemanager redisCachemanager;

	private String tableKey = RedisKeyConstant.TABLE + "province";
	
	@Override
	protected BaseMapper<Province, ProvinceCondition> daoSupport() {
		return provinceMapper;
	}

	@Override
	protected ProvinceCondition blankCondition() {
		return new ProvinceCondition();
	}

	@Override
	public Province selectById(Integer id) {
		String cacheStr = (String) redisCachemanager.hGet(tableKey, id + "");
		if (StringUtils.isEmpty(cacheStr)) {
			Province model = super.selectById(id);
			if (model != null) {
				doCache(model);
			}
			return model;
		} else {
			return JSON.parseObject(cacheStr, Province.class);
		}
	}

	@Override
	public String getName(String id) {
		if (StringUtils.isEmpty(id)) {
			return "";
		}
		return getName(Integer.parseInt(id));
	}

	@Override
	public String getName(Integer id) {
		if (id == null) {
			return "";
		}
		Province province = selectById(id);
		if (province != null) {
			return province.getName();
		}
		return "";
	}

	private void doCache(Province model) {
		try {
			redisCachemanager.hSet(tableKey, model.getId() + "", JSON.toJSONString(model));
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(ApiCallResult.DBERROR, String.format("%s,%d", tableKey, model.getId()));
		}
	}
}
