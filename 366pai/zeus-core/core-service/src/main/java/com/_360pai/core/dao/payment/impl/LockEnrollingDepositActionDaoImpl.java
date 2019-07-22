
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.LockEnrollingDepositActionCondition;
import com._360pai.core.dao.payment.mapper.LockEnrollingDepositActionMapper;
import com._360pai.core.model.payment.LockEnrollingDepositAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.LockEnrollingDepositActionDao;

@Service
public class LockEnrollingDepositActionDaoImpl extends AbstractDaoImpl<LockEnrollingDepositAction, LockEnrollingDepositActionCondition, BaseMapper<LockEnrollingDepositAction,LockEnrollingDepositActionCondition>> implements LockEnrollingDepositActionDao{
	
	@Resource
	private LockEnrollingDepositActionMapper lockEnrollingDepositActionMapper;
	
	@Override
	protected BaseMapper<LockEnrollingDepositAction, LockEnrollingDepositActionCondition> daoSupport() {
		return lockEnrollingDepositActionMapper;
	}

	@Override
	protected LockEnrollingDepositActionCondition blankCondition() {
		return new LockEnrollingDepositActionCondition();
	}

}
