
package com._360pai.core.dao.fdd.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.fdd.GatewayFddSignKeyworkCondition;
import com._360pai.core.dao.fdd.mapper.GatewayFddSignKeyworkMapper;
import com._360pai.core.model.fdd.GatewayFddSignKeywork;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.fdd.GatewayFddSignKeyworkDao;

@Service
public class GatewayFddSignKeyworkDaoImpl extends AbstractDaoImpl<GatewayFddSignKeywork, GatewayFddSignKeyworkCondition, BaseMapper<GatewayFddSignKeywork,GatewayFddSignKeyworkCondition>> implements GatewayFddSignKeyworkDao{
	
	@Resource
	private GatewayFddSignKeyworkMapper gatewayFddSignKeyworkMapper;
	
	@Override
	protected BaseMapper<GatewayFddSignKeywork, GatewayFddSignKeyworkCondition> daoSupport() {
		return gatewayFddSignKeyworkMapper;
	}

	@Override
	protected GatewayFddSignKeyworkCondition blankCondition() {
		return new GatewayFddSignKeyworkCondition();
	}

}
