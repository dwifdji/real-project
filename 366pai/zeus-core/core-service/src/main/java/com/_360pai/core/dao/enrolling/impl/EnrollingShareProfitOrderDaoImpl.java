
package com._360pai.core.dao.enrolling.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.enrolling.EnrollingShareProfitOrderCondition;
import com._360pai.core.dao.enrolling.mapper.EnrollingShareProfitOrderMapper;
import com._360pai.core.model.enrolling.EnrollingShareProfitOrder;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.enrolling.EnrollingShareProfitOrderDao;

@Service
public class EnrollingShareProfitOrderDaoImpl extends AbstractDaoImpl<EnrollingShareProfitOrder, EnrollingShareProfitOrderCondition, BaseMapper<EnrollingShareProfitOrder,EnrollingShareProfitOrderCondition>> implements EnrollingShareProfitOrderDao{
	
	@Resource
	private EnrollingShareProfitOrderMapper enrollingShareProfitOrderMapper;
	
	@Override
	protected BaseMapper<EnrollingShareProfitOrder, EnrollingShareProfitOrderCondition> daoSupport() {
		return enrollingShareProfitOrderMapper;
	}

	@Override
	protected EnrollingShareProfitOrderCondition blankCondition() {
		return new EnrollingShareProfitOrderCondition();
	}

}
