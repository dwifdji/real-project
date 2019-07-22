
package com._360pai.core.dao.fdd.impl;

import javax.annotation.Resource;

import com._360pai.core.condition.fdd.GatewayFddSignAutoCondition;
import com._360pai.core.dao.fdd.GatewayFddSignAutoDao;
import com._360pai.core.dao.fdd.mapper.GatewayFddSignAutoMapper;
import com._360pai.core.model.fdd.GatewayFddSignAuto;
import org.springframework.stereotype.Service;


import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;

@Service
public class GatewayFddSignAutoDaoImpl extends AbstractDaoImpl<GatewayFddSignAuto, GatewayFddSignAutoCondition, BaseMapper<GatewayFddSignAuto,GatewayFddSignAutoCondition>> implements GatewayFddSignAutoDao {
	
	@Resource
	private GatewayFddSignAutoMapper gatewayFddSignAutoMapper;
	
	@Override
	protected BaseMapper<GatewayFddSignAuto, GatewayFddSignAutoCondition> daoSupport() {
		return gatewayFddSignAutoMapper;
	}

	@Override
	protected GatewayFddSignAutoCondition blankCondition() {
		return new GatewayFddSignAutoCondition();
	}

}
