
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.PayEnrollingCommissionActionCondition;
import com._360pai.core.dao.payment.mapper.PayEnrollingCommissionActionMapper;
import com._360pai.core.model.payment.PayEnrollingCommissionAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.PayEnrollingCommissionActionDao;

@Service
public class PayEnrollingCommissionActionDaoImpl extends AbstractDaoImpl<PayEnrollingCommissionAction, PayEnrollingCommissionActionCondition, BaseMapper<PayEnrollingCommissionAction,PayEnrollingCommissionActionCondition>> implements PayEnrollingCommissionActionDao{
	
	@Resource
	private PayEnrollingCommissionActionMapper payEnrollingCommissionActionMapper;
	
	@Override
	protected BaseMapper<PayEnrollingCommissionAction, PayEnrollingCommissionActionCondition> daoSupport() {
		return payEnrollingCommissionActionMapper;
	}

	@Override
	protected PayEnrollingCommissionActionCondition blankCondition() {
		return new PayEnrollingCommissionActionCondition();
	}

}
