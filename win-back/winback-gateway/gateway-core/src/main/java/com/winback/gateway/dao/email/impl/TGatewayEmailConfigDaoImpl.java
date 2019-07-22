
package com.winback.gateway.dao.email.impl;

import javax.annotation.Resource;

import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import org.springframework.stereotype.Service;

import com.winback.gateway.condition.email.TGatewayEmailConfigCondition;
import com.winback.gateway.dao.email.mapper.TGatewayEmailConfigMapper;
import com.winback.gateway.model.email.TGatewayEmailConfig;;
import com.winback.gateway.dao.email.TGatewayEmailConfigDao;

@Service
public class TGatewayEmailConfigDaoImpl extends AbstractDaoImpl<TGatewayEmailConfig, TGatewayEmailConfigCondition, BaseMapper<TGatewayEmailConfig,TGatewayEmailConfigCondition>> implements TGatewayEmailConfigDao{
	
	@Resource
	private TGatewayEmailConfigMapper tGatewayEmailConfigMapper;
	
	@Override
	protected BaseMapper<TGatewayEmailConfig, TGatewayEmailConfigCondition> daoSupport() {
		return tGatewayEmailConfigMapper;
	}

	@Override
	protected TGatewayEmailConfigCondition blankCondition() {
		return new TGatewayEmailConfigCondition();
	}

}
