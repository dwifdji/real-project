
package com._360pai.core.dao.withfudig.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.withfudig.TWithfudigConfigCondition;
import com._360pai.core.dao.withfudig.mapper.TWithfudigConfigMapper;
import com._360pai.core.model.withfudig.TWithfudigConfig;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.withfudig.TWithfudigConfigDao;

@Service
public class TWithfudigConfigDaoImpl extends AbstractDaoImpl<TWithfudigConfig, TWithfudigConfigCondition, BaseMapper<TWithfudigConfig,TWithfudigConfigCondition>> implements TWithfudigConfigDao{
	
	@Resource
	private TWithfudigConfigMapper tWithfudigConfigMapper;
	
	@Override
	protected BaseMapper<TWithfudigConfig, TWithfudigConfigCondition> daoSupport() {
		return tWithfudigConfigMapper;
	}

	@Override
	protected TWithfudigConfigCondition blankCondition() {
		return new TWithfudigConfigCondition();
	}

	@Override
	public Integer selectMaxRank() {
		return tWithfudigConfigMapper.selectMaxRank();
	}
}
