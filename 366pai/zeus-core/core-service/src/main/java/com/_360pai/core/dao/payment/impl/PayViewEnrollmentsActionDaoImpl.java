
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.PayViewEnrollmentsActionCondition;
import com._360pai.core.dao.payment.mapper.PayViewEnrollmentsActionMapper;
import com._360pai.core.model.payment.PayViewEnrollmentsAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.PayViewEnrollmentsActionDao;

@Service
public class PayViewEnrollmentsActionDaoImpl extends AbstractDaoImpl<PayViewEnrollmentsAction, PayViewEnrollmentsActionCondition, BaseMapper<PayViewEnrollmentsAction,PayViewEnrollmentsActionCondition>> implements PayViewEnrollmentsActionDao{
	
	@Resource
	private PayViewEnrollmentsActionMapper payViewEnrollmentsActionMapper;
	
	@Override
	protected BaseMapper<PayViewEnrollmentsAction, PayViewEnrollmentsActionCondition> daoSupport() {
		return payViewEnrollmentsActionMapper;
	}

	@Override
	protected PayViewEnrollmentsActionCondition blankCondition() {
		return new PayViewEnrollmentsActionCondition();
	}

}
