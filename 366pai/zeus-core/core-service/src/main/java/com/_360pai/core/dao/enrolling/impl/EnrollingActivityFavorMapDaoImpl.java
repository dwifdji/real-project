
package com._360pai.core.dao.enrolling.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.enrolling.EnrollingActivityFavorMapCondition;
import com._360pai.core.dao.enrolling.mapper.EnrollingActivityFavorMapMapper;
import com._360pai.core.model.enrolling.EnrollingActivityFavorMap;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.enrolling.EnrollingActivityFavorMapDao;

@Service
public class EnrollingActivityFavorMapDaoImpl extends AbstractDaoImpl<EnrollingActivityFavorMap, EnrollingActivityFavorMapCondition, BaseMapper<EnrollingActivityFavorMap,EnrollingActivityFavorMapCondition>> implements EnrollingActivityFavorMapDao{
	
	@Resource
	private EnrollingActivityFavorMapMapper enrollingActivityFavorMapMapper;
	
	@Override
	protected BaseMapper<EnrollingActivityFavorMap, EnrollingActivityFavorMapCondition> daoSupport() {
		return enrollingActivityFavorMapMapper;
	}

	@Override
	protected EnrollingActivityFavorMapCondition blankCondition() {
		return new EnrollingActivityFavorMapCondition();
	}

}
