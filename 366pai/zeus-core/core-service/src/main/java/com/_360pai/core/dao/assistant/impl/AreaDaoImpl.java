
package com._360pai.core.dao.assistant.impl;

import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.condition.assistant.AreaCondition;
import com._360pai.core.dao.assistant.AreaDao;
import com._360pai.core.dao.assistant.mapper.AreaMapper;
import com._360pai.core.exception.DaoException;
import com._360pai.core.model.assistant.Area;
import com._360pai.core.model.assistant.City;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AreaDaoImpl extends AbstractDaoImpl<Area, AreaCondition, BaseMapper<Area,AreaCondition>> implements AreaDao{
	
	@Resource
	private AreaMapper areaMapper;
	@Resource
	private RedisCachemanager redisCachemanager;

	private String tableKey = RedisKeyConstant.TABLE + "area";
	
	@Override
	protected BaseMapper<Area, AreaCondition> daoSupport() {
		return areaMapper;
	}

	@Override
	protected AreaCondition blankCondition() {
		return new AreaCondition();
	}

	@Override
	public Area selectById(Integer id) {
		String cacheStr = (String) redisCachemanager.hGet(tableKey, id + "");
		if (StringUtils.isEmpty(cacheStr)) {
			Area model = super.selectById(id);
			if (model != null) {
				doCache(model);
			}
			return model;
		} else {
			return JSON.parseObject(cacheStr, Area.class);
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
		Area area = selectById(id);
		if (area != null) {
			return area.getName();
		}
		return "";
	}

	private void doCache(Area model) {
		try {
			redisCachemanager.hSet(tableKey, model.getId() + "", JSON.toJSONString(model));
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(ApiCallResult.DBERROR, String.format("%s,%d", tableKey, model.getId()));
		}
	}
}
