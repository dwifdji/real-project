
package com._360pai.core.dao.disposal.impl;

import javax.annotation.Resource;

import com._360pai.core.model.account.TDisposeProvider;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.disposal.TDisposeShowCondition;
import com._360pai.core.dao.disposal.mapper.TDisposeShowMapper;
import com._360pai.core.model.disposal.TDisposeShow;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.disposal.TDisposeShowDao;

import java.util.List;

@Service
public class TDisposeShowDaoImpl extends AbstractDaoImpl<TDisposeShow, TDisposeShowCondition, BaseMapper<TDisposeShow,TDisposeShowCondition>> implements TDisposeShowDao{
	
	@Resource
	private TDisposeShowMapper tDisposeShowMapper;
	
	@Override
	protected BaseMapper<TDisposeShow, TDisposeShowCondition> daoSupport() {
		return tDisposeShowMapper;
	}

	@Override
	protected TDisposeShowCondition blankCondition() {
		return new TDisposeShowCondition();
	}

	@Override
	public List<TDisposeProvider> getShowProvider(Integer activityId) {
		return tDisposeShowMapper.getShowProvider(activityId);
	}

	@Override
	public List<TDisposeProvider> getFirstLevelShowProvider(Integer cityId, Integer activityId) {
		return tDisposeShowMapper.getFirstLevelShowProvider(cityId, activityId);
	}
}
