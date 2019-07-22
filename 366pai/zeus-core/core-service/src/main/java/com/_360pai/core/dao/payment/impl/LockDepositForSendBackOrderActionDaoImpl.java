
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.LockDepositForSendBackOrderActionCondition;
import com._360pai.core.dao.payment.mapper.LockDepositForSendBackOrderActionMapper;
import com._360pai.core.model.payment.LockDepositForSendBackOrderAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.LockDepositForSendBackOrderActionDao;

@Service
public class LockDepositForSendBackOrderActionDaoImpl extends AbstractDaoImpl<LockDepositForSendBackOrderAction, LockDepositForSendBackOrderActionCondition, BaseMapper<LockDepositForSendBackOrderAction,LockDepositForSendBackOrderActionCondition>> implements LockDepositForSendBackOrderActionDao{
	
	@Resource
	private LockDepositForSendBackOrderActionMapper lockDepositForSendBackOrderActionMapper;
	
	@Override
	protected BaseMapper<LockDepositForSendBackOrderAction, LockDepositForSendBackOrderActionCondition> daoSupport() {
		return lockDepositForSendBackOrderActionMapper;
	}

	@Override
	protected LockDepositForSendBackOrderActionCondition blankCondition() {
		return new LockDepositForSendBackOrderActionCondition();
	}

}
