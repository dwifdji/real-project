
package com.tzCloud.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.assistant.TConfigCondition;
import com.tzCloud.core.dao.assistant.mapper.TConfigMapper;
import com.tzCloud.core.model.assistant.TConfig;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.assistant.TConfigDao;

import java.util.List;

@Service
public class TConfigDaoImpl extends AbstractDaoImpl<TConfig, TConfigCondition, BaseMapper<TConfig,TConfigCondition>> implements TConfigDao{
	
	@Resource
	private TConfigMapper tConfigMapper;
	
	@Override
	protected BaseMapper<TConfig, TConfigCondition> daoSupport() {
		return tConfigMapper;
	}

	@Override
	protected TConfigCondition blankCondition() {
		return new TConfigCondition();
	}

	@Override
	public TConfig findBy(String configType) {
		TConfigCondition condition = new TConfigCondition();
		condition.setConfigType(configType);
		condition.setDeleteFlag(false);
		List<TConfig> list = tConfigMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public String getValue(String configType) {
		TConfig config = findBy(configType);
		if (config != null) {
			return config.getConfigValue();
		}
		return null;
	}
}
