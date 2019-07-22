
package com._360pai.core.dao.enrolling.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.enrolling.EnrollingActivityProgressCondition;
import com._360pai.core.dao.enrolling.mapper.EnrollingActivityProgressMapper;
import com._360pai.core.model.enrolling.EnrollingActivityProgress;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.enrolling.EnrollingActivityProgressDao;

@Service
public class EnrollingActivityProgressDaoImpl extends AbstractDaoImpl<EnrollingActivityProgress, EnrollingActivityProgressCondition, BaseMapper<EnrollingActivityProgress,EnrollingActivityProgressCondition>> implements EnrollingActivityProgressDao{
	
	@Resource
	private EnrollingActivityProgressMapper enrollingActivityProgressMapper;
	
	@Override
	protected BaseMapper<EnrollingActivityProgress, EnrollingActivityProgressCondition> daoSupport() {
		return enrollingActivityProgressMapper;
	}

	@Override
	protected EnrollingActivityProgressCondition blankCondition() {
		return new EnrollingActivityProgressCondition();
	}

}
