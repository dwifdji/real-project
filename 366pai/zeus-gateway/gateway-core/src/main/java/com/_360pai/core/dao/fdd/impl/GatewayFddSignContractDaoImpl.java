
package com._360pai.core.dao.fdd.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.fdd.GatewayFddSignContractCondition;
import com._360pai.core.dao.fdd.GatewayFddSignContractDao;
import com._360pai.core.dao.fdd.mapper.GatewayFddSignContractMapper;
import com._360pai.core.model.fdd.GatewayFddSignContract;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GatewayFddSignContractDaoImpl extends AbstractDaoImpl<GatewayFddSignContract, GatewayFddSignContractCondition, BaseMapper<GatewayFddSignContract,GatewayFddSignContractCondition>> implements GatewayFddSignContractDao{
	
	@Resource
	private GatewayFddSignContractMapper gatewayFddSignContractMapper;
	
	@Override
	protected BaseMapper<GatewayFddSignContract, GatewayFddSignContractCondition> daoSupport() {
		return gatewayFddSignContractMapper;
	}

	@Override
	protected GatewayFddSignContractCondition blankCondition() {
		return new GatewayFddSignContractCondition();
	}

	@Override
	public List<GatewayFddSignContract> queryNotSignConTract() {
		return gatewayFddSignContractMapper.queryNotSignConTract();
	}
}
