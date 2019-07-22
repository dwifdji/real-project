
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.LockShareCommissionActionCondition;
import com._360pai.core.dao.payment.mapper.LockShareCommissionActionMapper;
import com._360pai.core.model.payment.LockShareCommissionAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.LockShareCommissionActionDao;

@Service
public class LockShareCommissionActionDaoImpl extends AbstractDaoImpl<LockShareCommissionAction, LockShareCommissionActionCondition, BaseMapper<LockShareCommissionAction,LockShareCommissionActionCondition>> implements LockShareCommissionActionDao{
	
	@Resource
	private LockShareCommissionActionMapper lockShareCommissionActionMapper;
	
	@Override
	protected BaseMapper<LockShareCommissionAction, LockShareCommissionActionCondition> daoSupport() {
		return lockShareCommissionActionMapper;
	}

	@Override
	protected LockShareCommissionActionCondition blankCondition() {
		return new LockShareCommissionActionCondition();
	}

}
