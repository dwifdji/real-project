
package com._360pai.core.dao.fdd.impl;

import javax.annotation.Resource;

import com._360pai.core.condition.fdd.GatewayFddGenerateContractCondition;
import com._360pai.core.dao.fdd.GatewayFddGenerateContractDao;
import com._360pai.core.dao.fdd.mapper.GatewayFddGenerateContractMapper;
import com._360pai.core.model.fdd.GatewayFddGenerateContract;
import org.springframework.stereotype.Service;


import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;


@Service
public class GatewayFddGenerateContractDaoImpl extends AbstractDaoImpl<GatewayFddGenerateContract, GatewayFddGenerateContractCondition, BaseMapper<GatewayFddGenerateContract,GatewayFddGenerateContractCondition>> implements GatewayFddGenerateContractDao {
	
	@Resource
	private GatewayFddGenerateContractMapper gatewayFddGenerateContractMapper;
	
	@Override
	protected BaseMapper<GatewayFddGenerateContract, GatewayFddGenerateContractCondition> daoSupport() {
		return gatewayFddGenerateContractMapper;
	}

	@Override
	protected GatewayFddGenerateContractCondition blankCondition() {
		return new GatewayFddGenerateContractCondition();
	}

}
