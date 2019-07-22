
package com._360pai.core.dao.enrolling.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.enrolling.EnrollingActivityViewCountCondition;
import com._360pai.core.dao.enrolling.mapper.EnrollingActivityViewCountMapper;
import com._360pai.core.model.enrolling.EnrollingActivityViewCount;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.enrolling.EnrollingActivityViewCountDao;

@Service
public class EnrollingActivityViewCountDaoImpl extends AbstractDaoImpl<EnrollingActivityViewCount, EnrollingActivityViewCountCondition, BaseMapper<EnrollingActivityViewCount,EnrollingActivityViewCountCondition>> implements EnrollingActivityViewCountDao{
	
	@Resource
	private EnrollingActivityViewCountMapper enrollingActivityViewCountMapper;
	
	@Override
	protected BaseMapper<EnrollingActivityViewCount, EnrollingActivityViewCountCondition> daoSupport() {
		return enrollingActivityViewCountMapper;
	}

	@Override
	protected EnrollingActivityViewCountCondition blankCondition() {
		return new EnrollingActivityViewCountCondition();
	}

}
