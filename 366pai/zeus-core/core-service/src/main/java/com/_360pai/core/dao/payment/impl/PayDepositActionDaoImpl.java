
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.PayDepositActionCondition;
import com._360pai.core.dao.payment.mapper.PayDepositActionMapper;
import com._360pai.core.model.payment.PayDepositAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.PayDepositActionDao;

@Service
public class PayDepositActionDaoImpl extends AbstractDaoImpl<PayDepositAction, PayDepositActionCondition, BaseMapper<PayDepositAction,PayDepositActionCondition>> implements PayDepositActionDao{
	
	@Resource
	private PayDepositActionMapper payDepositActionMapper;
	
	@Override
	protected BaseMapper<PayDepositAction, PayDepositActionCondition> daoSupport() {
		return payDepositActionMapper;
	}

	@Override
	protected PayDepositActionCondition blankCondition() {
		return new PayDepositActionCondition();
	}

}
