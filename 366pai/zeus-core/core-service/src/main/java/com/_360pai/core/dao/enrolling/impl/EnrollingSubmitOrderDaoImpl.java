
package com._360pai.core.dao.enrolling.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.enrolling.EnrollingSubmitOrderCondition;
import com._360pai.core.dao.enrolling.mapper.EnrollingSubmitOrderMapper;
import com._360pai.core.model.enrolling.EnrollingSubmitOrder;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.enrolling.EnrollingSubmitOrderDao;

@Service
public class EnrollingSubmitOrderDaoImpl extends AbstractDaoImpl<EnrollingSubmitOrder, EnrollingSubmitOrderCondition, BaseMapper<EnrollingSubmitOrder,EnrollingSubmitOrderCondition>> implements EnrollingSubmitOrderDao{
	
	@Resource
	private EnrollingSubmitOrderMapper enrollingSubmitOrderMapper;
	
	@Override
	protected BaseMapper<EnrollingSubmitOrder, EnrollingSubmitOrderCondition> daoSupport() {
		return enrollingSubmitOrderMapper;
	}

	@Override
	protected EnrollingSubmitOrderCondition blankCondition() {
		return new EnrollingSubmitOrderCondition();
	}

}
