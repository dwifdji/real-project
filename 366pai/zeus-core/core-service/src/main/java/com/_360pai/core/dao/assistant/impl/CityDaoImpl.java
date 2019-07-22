
package com._360pai.core.dao.assistant.impl;

import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.condition.assistant.CityCondition;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.assistant.mapper.CityMapper;
import com._360pai.core.exception.DaoException;
import com._360pai.core.model.assistant.City;
import com._360pai.core.vo.assistant.ProvinceCityVo;
import com._360pai.core.vo.enrolling.web.EnrollingCityVO;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CityDaoImpl extends AbstractDaoImpl<City, CityCondition, BaseMapper<City,CityCondition>> implements CityDao{
	
	@Resource
	private CityMapper cityMapper;
	@Resource
	private RedisCachemanager redisCachemanager;

	private String tableKey = RedisKeyConstant.TABLE + "city";
	
	@Override
	protected BaseMapper<City, CityCondition> daoSupport() {
		return cityMapper;
	}

	@Override
	protected CityCondition blankCondition() {
		return new CityCondition();
	}

    @Override
    public Object pageCities() {
        return cityMapper.pageCities();
    }

	@Override
	public Object pageProvinces() {
		return cityMapper.pageProvinces();
	}

	@Override
	public List<EnrollingCityVO> getCityList() {
		return cityMapper.getCityList();
		
	}

	@Override
	public City selectById(Integer id) {
		String cacheStr = (String) redisCachemanager.hGet(tableKey, id + "");
		if (StringUtils.isEmpty(cacheStr)) {
			City model = super.selectById(id);
			if (model != null) {
				doCache(model);
			}
			return model;
		} else {
			return JSON.parseObject(cacheStr, City.class);
		}
	}

	@Override
	public List<ProvinceCityVo> getProvinceCityList(List<String> ids) {
		return cityMapper.getProvinceCityList(ids);
	}


	@Override
	public String getName(Integer id) {
		City city = selectById(id);
		if (city != null) {
			return city.getName();
		}
		return "";
	}

	@Override
	public String getName(String id) {
		if (StringUtils.isEmpty(id)) {
			return "";
		}
		return getName(Integer.parseInt(id));
	}

	@Override
	public Integer getProvinceId(Integer id) {
		City city = selectById(id);
		if (city != null) {
			return city.getProvinceId();
		}
		return null;
	}

	@Override
	public Integer getProvinceId(String id) {
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		return getProvinceId(Integer.parseInt(id));
	}

	private void doCache(City model) {
		try {
			redisCachemanager.hSet(tableKey, model.getId() + "", JSON.toJSONString(model));
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(ApiCallResult.DBERROR, String.format("%s,%d", tableKey, model.getId()));
		}
	}


	@Override
	public List<ProvinceCityVo> getAllProvinceCityList() {
		return cityMapper.getAllProvinceCityList();
	}

	@Override
	public List<ProvinceCityVo> getAllProvinceCityAreaList() {
		return cityMapper.getAllProvinceCityAreaList();
	}

	@Override
	public City findByName(String name) {
		CityCondition condition = new CityCondition();
		condition.setName(name);
		List<City> list = cityMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
