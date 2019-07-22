
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.TransferDepositActionCondition;
import com._360pai.core.dao.payment.mapper.TransferDepositActionMapper;
import com._360pai.core.model.payment.TransferDepositAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.TransferDepositActionDao;

@Service
public class TransferDepositActionDaoImpl extends AbstractDaoImpl<TransferDepositAction, TransferDepositActionCondition, BaseMapper<TransferDepositAction,TransferDepositActionCondition>> implements TransferDepositActionDao{
	
	@Resource
	private TransferDepositActionMapper transferDepositActionMapper;
	
	@Override
	protected BaseMapper<TransferDepositAction, TransferDepositActionCondition> daoSupport() {
		return transferDepositActionMapper;
	}

	@Override
	protected TransferDepositActionCondition blankCondition() {
		return new TransferDepositActionCondition();
	}

}
