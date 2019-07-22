
package com.tzCloud.core.dao.order.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.order.TServiceConfigCondition;
import com.tzCloud.core.dao.order.mapper.TServiceConfigMapper;
import com.tzCloud.core.model.order.TServiceConfig;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.order.TServiceConfigDao;

import java.util.List;

@Service
public class TServiceConfigDaoImpl extends AbstractDaoImpl<TServiceConfig, TServiceConfigCondition, BaseMapper<TServiceConfig,TServiceConfigCondition>> implements TServiceConfigDao{
	
	@Resource
	private TServiceConfigMapper tServiceConfigMapper;
	
	@Override
	protected BaseMapper<TServiceConfig, TServiceConfigCondition> daoSupport() {
		return tServiceConfigMapper;
	}

	@Override
	protected TServiceConfigCondition blankCondition() {
		return new TServiceConfigCondition();
	}

	@Override
	public List<TServiceConfig> selectByLike(String configStart) {
		return tServiceConfigMapper.selectByLike(configStart);
	}
}
