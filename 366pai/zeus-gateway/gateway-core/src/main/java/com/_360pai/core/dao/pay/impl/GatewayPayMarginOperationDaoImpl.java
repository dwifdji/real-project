
package com._360pai.core.dao.pay.impl;

import javax.annotation.Resource;

import com._360pai.core.condition.pay.GatewayPayMarginOperationCondition;
import com._360pai.core.dao.pay.GatewayPayMarginOperationDao;
import com._360pai.core.dao.pay.mapper.GatewayPayMarginOperationMapper;
import com._360pai.core.model.pay.GatewayPayMarginOperation;
import org.springframework.stereotype.Service;


import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;

@Service
public class GatewayPayMarginOperationDaoImpl extends AbstractDaoImpl<GatewayPayMarginOperation, GatewayPayMarginOperationCondition, BaseMapper<GatewayPayMarginOperation,GatewayPayMarginOperationCondition>> implements GatewayPayMarginOperationDao {
	
	@Resource
	private GatewayPayMarginOperationMapper gatewayPayMarginOperationMapper;
	
	@Override
	protected BaseMapper<GatewayPayMarginOperation, GatewayPayMarginOperationCondition> daoSupport() {
		return gatewayPayMarginOperationMapper;
	}

	@Override
	protected GatewayPayMarginOperationCondition blankCondition() {
		return new GatewayPayMarginOperationCondition();
	}

}
