
package com.winback.gateway.dao.pay.impl;

import javax.annotation.Resource;

import com.winback.gateway.condition.pay.TGatewayPayWayCondition;
import com.winback.gateway.dao.pay.TGatewayPayWayDao;
import com.winback.gateway.model.pay.TGatewayPayWay;
import org.springframework.stereotype.Service;

import com.winback.gateway.condition.pay.TGatewayPayWayCondition;
import com.winback.gateway.dao.pay.mapper.TGatewayPayWayMapper;
import com.winback.gateway.model.pay.TGatewayPayWay;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.gateway.dao.pay.TGatewayPayWayDao;

@Service
public class TGatewayPayWayDaoImpl extends AbstractDaoImpl<TGatewayPayWay, TGatewayPayWayCondition, BaseMapper<TGatewayPayWay,TGatewayPayWayCondition>> implements TGatewayPayWayDao {
	
	@Resource
	private TGatewayPayWayMapper tGatewayPayWayMapper;
	
	@Override
	protected BaseMapper<TGatewayPayWay, TGatewayPayWayCondition> daoSupport() {
		return tGatewayPayWayMapper;
	}

	@Override
	protected TGatewayPayWayCondition blankCondition() {
		return new TGatewayPayWayCondition();
	}

}
