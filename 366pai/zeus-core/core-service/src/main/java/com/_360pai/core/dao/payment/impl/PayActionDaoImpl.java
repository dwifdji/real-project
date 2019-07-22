
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.PayActionCondition;
import com._360pai.core.dao.payment.mapper.PayActionMapper;
import com._360pai.core.model.payment.PayAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.PayActionDao;

@Service
public class PayActionDaoImpl extends AbstractDaoImpl<PayAction, PayActionCondition, BaseMapper<PayAction,PayActionCondition>> implements PayActionDao{
	
	@Resource
	private PayActionMapper payActionMapper;
	
	@Override
	protected BaseMapper<PayAction, PayActionCondition> daoSupport() {
		return payActionMapper;
	}

	@Override
	protected PayActionCondition blankCondition() {
		return new PayActionCondition();
	}

}
