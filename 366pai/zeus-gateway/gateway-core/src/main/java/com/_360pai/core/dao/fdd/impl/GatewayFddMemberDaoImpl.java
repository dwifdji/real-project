
package com._360pai.core.dao.fdd.impl;

import javax.annotation.Resource;

import com._360pai.core.condition.fdd.GatewayFddMemberCondition;
import com._360pai.core.dao.fdd.GatewayFddMemberDao;
import com._360pai.core.dao.fdd.mapper.GatewayFddMemberMapper;
import com._360pai.core.model.fdd.GatewayFddMember;
import org.springframework.stereotype.Service;


import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;


@Service
public class GatewayFddMemberDaoImpl extends AbstractDaoImpl<GatewayFddMember, GatewayFddMemberCondition, BaseMapper<GatewayFddMember,GatewayFddMemberCondition>> implements GatewayFddMemberDao {
	
	@Resource
	private GatewayFddMemberMapper gatewayFddMemberMapper;
	
	@Override
	protected BaseMapper<GatewayFddMember, GatewayFddMemberCondition> daoSupport() {
		return gatewayFddMemberMapper;
	}

	@Override
	protected GatewayFddMemberCondition blankCondition() {
		return new GatewayFddMemberCondition();
	}

}
