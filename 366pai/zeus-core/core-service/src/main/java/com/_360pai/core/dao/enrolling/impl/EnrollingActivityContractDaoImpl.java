
package com._360pai.core.dao.enrolling.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.enrolling.EnrollingActivityContractCondition;
import com._360pai.core.dao.enrolling.mapper.EnrollingActivityContractMapper;
import com._360pai.core.model.enrolling.EnrollingActivityContract;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.enrolling.EnrollingActivityContractDao;

@Service
public class EnrollingActivityContractDaoImpl extends AbstractDaoImpl<EnrollingActivityContract, EnrollingActivityContractCondition, BaseMapper<EnrollingActivityContract,EnrollingActivityContractCondition>> implements EnrollingActivityContractDao{
	
	@Resource
	private EnrollingActivityContractMapper enrollingActivityContractMapper;
	
	@Override
	protected BaseMapper<EnrollingActivityContract, EnrollingActivityContractCondition> daoSupport() {
		return enrollingActivityContractMapper;
	}

	@Override
	protected EnrollingActivityContractCondition blankCondition() {
		return new EnrollingActivityContractCondition();
	}

}
