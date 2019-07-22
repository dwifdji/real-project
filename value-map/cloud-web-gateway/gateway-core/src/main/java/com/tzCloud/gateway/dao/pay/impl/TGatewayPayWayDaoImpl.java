
package com.tzCloud.gateway.dao.pay.impl;

import javax.annotation.Resource;

import com.tzCloud.gateway.dao.pay.TGatewayPayWayDao;
import org.springframework.stereotype.Service;

import com.tzCloud.gateway.condition.pay.TGatewayPayWayCondition;
import com.tzCloud.gateway.dao.pay.mapper.TGatewayPayWayMapper;
import com.tzCloud.gateway.model.pay.TGatewayPayWay;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.gateway.dao.pay.TGatewayPayWayDao;

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
