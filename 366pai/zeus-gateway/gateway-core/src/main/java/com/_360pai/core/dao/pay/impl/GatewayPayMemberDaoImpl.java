
package com._360pai.core.dao.pay.impl;

import javax.annotation.Resource;

import com._360pai.core.condition.pay.GatewayPayMemberCondition;
import com._360pai.core.dao.pay.GatewayPayMemberDao;
import com._360pai.core.dao.pay.mapper.GatewayPayMemberMapper;
import com._360pai.core.model.pay.GatewayPayMember;
import org.springframework.stereotype.Service;


import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;

@Service
public class GatewayPayMemberDaoImpl extends AbstractDaoImpl<GatewayPayMember, GatewayPayMemberCondition, BaseMapper<GatewayPayMember,GatewayPayMemberCondition>> implements GatewayPayMemberDao {
	
	@Resource
	private GatewayPayMemberMapper gatewayPayMemberMapper;
	
	@Override
	protected BaseMapper<GatewayPayMember, GatewayPayMemberCondition> daoSupport() {
		return gatewayPayMemberMapper;
	}

	@Override
	protected GatewayPayMemberCondition blankCondition() {
		return new GatewayPayMemberCondition();
	}

}
