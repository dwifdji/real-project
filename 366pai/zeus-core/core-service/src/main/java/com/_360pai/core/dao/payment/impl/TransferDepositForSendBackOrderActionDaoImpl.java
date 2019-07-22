
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.TransferDepositForSendBackOrderActionCondition;
import com._360pai.core.dao.payment.mapper.TransferDepositForSendBackOrderActionMapper;
import com._360pai.core.model.payment.TransferDepositForSendBackOrderAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.TransferDepositForSendBackOrderActionDao;

@Service
public class TransferDepositForSendBackOrderActionDaoImpl extends AbstractDaoImpl<TransferDepositForSendBackOrderAction, TransferDepositForSendBackOrderActionCondition, BaseMapper<TransferDepositForSendBackOrderAction,TransferDepositForSendBackOrderActionCondition>> implements TransferDepositForSendBackOrderActionDao{
	
	@Resource
	private TransferDepositForSendBackOrderActionMapper transferDepositForSendBackOrderActionMapper;
	
	@Override
	protected BaseMapper<TransferDepositForSendBackOrderAction, TransferDepositForSendBackOrderActionCondition> daoSupport() {
		return transferDepositForSendBackOrderActionMapper;
	}

	@Override
	protected TransferDepositForSendBackOrderActionCondition blankCondition() {
		return new TransferDepositForSendBackOrderActionCondition();
	}

}
