
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.ReleaseDepositActionCondition;
import com._360pai.core.dao.payment.mapper.ReleaseDepositActionMapper;
import com._360pai.core.model.payment.ReleaseDepositAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.ReleaseDepositActionDao;

@Service
public class ReleaseDepositActionDaoImpl extends AbstractDaoImpl<ReleaseDepositAction, ReleaseDepositActionCondition, BaseMapper<ReleaseDepositAction,ReleaseDepositActionCondition>> implements ReleaseDepositActionDao{
	
	@Resource
	private ReleaseDepositActionMapper releaseDepositActionMapper;
	
	@Override
	protected BaseMapper<ReleaseDepositAction, ReleaseDepositActionCondition> daoSupport() {
		return releaseDepositActionMapper;
	}

	@Override
	protected ReleaseDepositActionCondition blankCondition() {
		return new ReleaseDepositActionCondition();
	}

}
