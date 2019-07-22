
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.TownCondition;
import com._360pai.core.dao.assistant.mapper.TownMapper;
import com._360pai.core.model.assistant.Town;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.TownDao;

@Service
public class TownDaoImpl extends AbstractDaoImpl<Town, TownCondition, BaseMapper<Town,TownCondition>> implements TownDao{
	
	@Resource
	private TownMapper townMapper;
	
	@Override
	protected BaseMapper<Town, TownCondition> daoSupport() {
		return townMapper;
	}

	@Override
	protected TownCondition blankCondition() {
		return new TownCondition();
	}

	@Override
	public String getAreaDetail(String townId) {
		return townMapper.getAreaDetail(townId);
	}

	@Override
	public String getAreaDetailByTownId(String townId) {
		return townMapper.getAreaDetailByTownId(townId);
	}

	@Override
	public String getAreaDetailByAreaId(String areaId) {
		return townMapper.getAreaDetailByAreaId(areaId);
	}
}
