
package com._360pai.core.dao.disposal.impl;

import javax.annotation.Resource;

import com._360pai.core.model.disposal.TDisposalRequirement;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.disposal.TDisposeLevelCondition;
import com._360pai.core.dao.disposal.mapper.TDisposeLevelMapper;
import com._360pai.core.model.disposal.TDisposeLevel;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.disposal.TDisposeLevelDao;

import java.util.List;
import java.util.Map;

@Service
public class TDisposeLevelDaoImpl extends AbstractDaoImpl<TDisposeLevel, TDisposeLevelCondition, BaseMapper<TDisposeLevel,TDisposeLevelCondition>> implements TDisposeLevelDao{
	
	@Resource
	private TDisposeLevelMapper tDisposeLevelMapper;
	
	@Override
	protected BaseMapper<TDisposeLevel, TDisposeLevelCondition> daoSupport() {
		return tDisposeLevelMapper;
	}

	@Override
	protected TDisposeLevelCondition blankCondition() {
		return new TDisposeLevelCondition();
	}

	@Override
	public TDisposeLevel getRegionLevelProvider(Map<String, Object> map) {
		return tDisposeLevelMapper.getRegionLevelProvider(map);
	}

	@Override
	public List<TDisposeLevel> getFirstLevelCityPartnerByParam(Map<String, Object> params) {
		return tDisposeLevelMapper.getFirstLevelCityPartnerByParam(params);
	}

	@Override
	public List<TDisposeLevel> getCityPartnerByParam(Map<String, Object> params) {
		return tDisposeLevelMapper.getCityPartnerByParam(params);
	}

	@Override
	public List<TDisposeLevel> getCityPartnerByParamWithoutFirstLevel(Map<String, Object> params) {
		return tDisposeLevelMapper.getCityPartnerByParamWithoutFirstLevel(params);
	}

	@Override
	public TDisposeLevel getValidByProviderId(Integer providerId) {
		TDisposeLevelCondition condition = new TDisposeLevelCondition();
		condition.setProviderId(providerId);
		condition.setReplaceTime("0");
		List<TDisposeLevel> list = tDisposeLevelMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageInfo getListByPage(int page, int perPage) {
		PageHelper.startPage(page, perPage);
		List<TDisposeLevel> list = tDisposeLevelMapper.selectAll();
		return new PageInfo<>(list);
	}
}
