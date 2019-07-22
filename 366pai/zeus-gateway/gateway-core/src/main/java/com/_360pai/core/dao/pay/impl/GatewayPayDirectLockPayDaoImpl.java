
package com._360pai.core.dao.pay.impl;

import javax.annotation.Resource;

import com._360pai.core.condition.pay.GatewayPayDirectLockPayCondition;
import com._360pai.core.dao.pay.GatewayPayDirectLockPayDao;
import com._360pai.core.dao.pay.mapper.GatewayPayDirectLockPayMapper;
import com._360pai.core.model.pay.GatewayPayDirectLockPay;
import org.springframework.stereotype.Service;


import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;

@Service
public class GatewayPayDirectLockPayDaoImpl extends AbstractDaoImpl<GatewayPayDirectLockPay, GatewayPayDirectLockPayCondition, BaseMapper<GatewayPayDirectLockPay,GatewayPayDirectLockPayCondition>> implements GatewayPayDirectLockPayDao {
	
	@Resource
	private GatewayPayDirectLockPayMapper gatewayPayDirectLockPayMapper;
	
	@Override
	protected BaseMapper<GatewayPayDirectLockPay, GatewayPayDirectLockPayCondition> daoSupport() {
		return gatewayPayDirectLockPayMapper;
	}

	@Override
	protected GatewayPayDirectLockPayCondition blankCondition() {
		return new GatewayPayDirectLockPayCondition();
	}

}
