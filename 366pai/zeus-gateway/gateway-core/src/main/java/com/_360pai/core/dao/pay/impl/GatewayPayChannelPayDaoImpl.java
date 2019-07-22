
package com._360pai.core.dao.pay.impl;

import javax.annotation.Resource;

import com._360pai.core.condition.pay.GatewayPayChannelPayCondition;
import com._360pai.core.dao.pay.GatewayPayChannelPayDao;
import com._360pai.core.dao.pay.mapper.GatewayPayChannelPayMapper;
import com._360pai.core.model.pay.GatewayPayChannelPay;
import org.springframework.stereotype.Service;


import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;

@Service
public class GatewayPayChannelPayDaoImpl extends AbstractDaoImpl<GatewayPayChannelPay, GatewayPayChannelPayCondition, BaseMapper<GatewayPayChannelPay,GatewayPayChannelPayCondition>> implements GatewayPayChannelPayDao {
	
	@Resource
	private GatewayPayChannelPayMapper gatewayPayChannelPayMapper;
	
	@Override
	protected BaseMapper<GatewayPayChannelPay, GatewayPayChannelPayCondition> daoSupport() {
		return gatewayPayChannelPayMapper;
	}

	@Override
	protected GatewayPayChannelPayCondition blankCondition() {
		return new GatewayPayChannelPayCondition();
	}

}
