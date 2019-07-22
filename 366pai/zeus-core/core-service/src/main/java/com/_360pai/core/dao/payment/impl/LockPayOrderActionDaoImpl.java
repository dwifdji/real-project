
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.LockPayOrderActionCondition;
import com._360pai.core.dao.payment.mapper.LockPayOrderActionMapper;
import com._360pai.core.model.payment.LockPayOrderAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.LockPayOrderActionDao;

@Service
public class LockPayOrderActionDaoImpl extends AbstractDaoImpl<LockPayOrderAction, LockPayOrderActionCondition, BaseMapper<LockPayOrderAction,LockPayOrderActionCondition>> implements LockPayOrderActionDao{
	
	@Resource
	private LockPayOrderActionMapper lockPayOrderActionMapper;
	
	@Override
	protected BaseMapper<LockPayOrderAction, LockPayOrderActionCondition> daoSupport() {
		return lockPayOrderActionMapper;
	}

	@Override
	protected LockPayOrderActionCondition blankCondition() {
		return new LockPayOrderActionCondition();
	}

}
