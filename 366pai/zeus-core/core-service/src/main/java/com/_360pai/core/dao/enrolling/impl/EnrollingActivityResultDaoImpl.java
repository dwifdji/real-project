
package com._360pai.core.dao.enrolling.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.enrolling.EnrollingActivityResultCondition;
import com._360pai.core.dao.enrolling.mapper.EnrollingActivityResultMapper;
import com._360pai.core.model.enrolling.EnrollingActivityResult;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.enrolling.EnrollingActivityResultDao;

@Service
public class EnrollingActivityResultDaoImpl extends AbstractDaoImpl<EnrollingActivityResult, EnrollingActivityResultCondition, BaseMapper<EnrollingActivityResult,EnrollingActivityResultCondition>> implements EnrollingActivityResultDao{
	
	@Resource
	private EnrollingActivityResultMapper enrollingActivityResultMapper;
	
	@Override
	protected BaseMapper<EnrollingActivityResult, EnrollingActivityResultCondition> daoSupport() {
		return enrollingActivityResultMapper;
	}

	@Override
	protected EnrollingActivityResultCondition blankCondition() {
		return new EnrollingActivityResultCondition();
	}

}
