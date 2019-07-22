
package com._360pai.core.dao.enrolling.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.enrolling.EnrollingActivityDataTestCondition;
import com._360pai.core.dao.enrolling.mapper.EnrollingActivityDataTestMapper;
import com._360pai.core.model.enrolling.EnrollingActivityDataTest;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.enrolling.EnrollingActivityDataTestDao;

@Service
public class EnrollingActivityDataTestDaoImpl extends AbstractDaoImpl<EnrollingActivityDataTest, EnrollingActivityDataTestCondition, BaseMapper<EnrollingActivityDataTest,EnrollingActivityDataTestCondition>> implements EnrollingActivityDataTestDao{
	
	@Resource
	private EnrollingActivityDataTestMapper enrollingActivityDataTestMapper;
	
	@Override
	protected BaseMapper<EnrollingActivityDataTest, EnrollingActivityDataTestCondition> daoSupport() {
		return enrollingActivityDataTestMapper;
	}

	@Override
	protected EnrollingActivityDataTestCondition blankCondition() {
		return new EnrollingActivityDataTestCondition();
	}

}
