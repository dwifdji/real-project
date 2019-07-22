
package com._360pai.core.dao.enrolling.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.enrolling.EnrollingActivityTestCondition;
import com._360pai.core.dao.enrolling.mapper.EnrollingActivityTestMapper;
import com._360pai.core.model.enrolling.EnrollingActivityTest;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.enrolling.EnrollingActivityTestDao;

@Service
public class EnrollingActivityTestDaoImpl extends AbstractDaoImpl<EnrollingActivityTest, EnrollingActivityTestCondition, BaseMapper<EnrollingActivityTest,EnrollingActivityTestCondition>> implements EnrollingActivityTestDao{
	
	@Resource
	private EnrollingActivityTestMapper enrollingActivityTestMapper;
	
	@Override
	protected BaseMapper<EnrollingActivityTest, EnrollingActivityTestCondition> daoSupport() {
		return enrollingActivityTestMapper;
	}

	@Override
	protected EnrollingActivityTestCondition blankCondition() {
		return new EnrollingActivityTestCondition();
	}

}
