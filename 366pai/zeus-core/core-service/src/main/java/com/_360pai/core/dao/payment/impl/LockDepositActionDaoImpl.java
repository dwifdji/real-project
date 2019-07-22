
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.LockDepositActionCondition;
import com._360pai.core.dao.payment.mapper.LockDepositActionMapper;
import com._360pai.core.model.payment.LockDepositAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.LockDepositActionDao;

@Service
public class LockDepositActionDaoImpl extends AbstractDaoImpl<LockDepositAction, LockDepositActionCondition, BaseMapper<LockDepositAction,LockDepositActionCondition>> implements LockDepositActionDao{
	
	@Resource
	private LockDepositActionMapper lockDepositActionMapper;
	
	@Override
	protected BaseMapper<LockDepositAction, LockDepositActionCondition> daoSupport() {
		return lockDepositActionMapper;
	}

	@Override
	protected LockDepositActionCondition blankCondition() {
		return new LockDepositActionCondition();
	}

}
