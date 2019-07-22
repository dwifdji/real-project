
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.LockPayCommissionActionCondition;
import com._360pai.core.dao.payment.mapper.LockPayCommissionActionMapper;
import com._360pai.core.model.payment.LockPayCommissionAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.LockPayCommissionActionDao;

@Service
public class LockPayCommissionActionDaoImpl extends AbstractDaoImpl<LockPayCommissionAction, LockPayCommissionActionCondition, BaseMapper<LockPayCommissionAction,LockPayCommissionActionCondition>> implements LockPayCommissionActionDao{
	
	@Resource
	private LockPayCommissionActionMapper lockPayCommissionActionMapper;
	
	@Override
	protected BaseMapper<LockPayCommissionAction, LockPayCommissionActionCondition> daoSupport() {
		return lockPayCommissionActionMapper;
	}

	@Override
	protected LockPayCommissionActionCondition blankCondition() {
		return new LockPayCommissionActionCondition();
	}

}
