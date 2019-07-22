
package com._360pai.core.dao.enrolling.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.enrolling.TEnrollingActivityDataCondition;
import com._360pai.core.dao.enrolling.mapper.TEnrollingActivityDataMapper;
import com._360pai.core.model.enrolling.TEnrollingActivityData;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.enrolling.TEnrollingActivityDataDao;

@Service
public class TEnrollingActivityDataDaoImpl extends AbstractDaoImpl<TEnrollingActivityData, TEnrollingActivityDataCondition, BaseMapper<TEnrollingActivityData,TEnrollingActivityDataCondition>> implements TEnrollingActivityDataDao{
	
	@Resource
	private TEnrollingActivityDataMapper tEnrollingActivityDataMapper;
	
	@Override
	protected BaseMapper<TEnrollingActivityData, TEnrollingActivityDataCondition> daoSupport() {
		return tEnrollingActivityDataMapper;
	}

	@Override
	protected TEnrollingActivityDataCondition blankCondition() {
		return new TEnrollingActivityDataCondition();
	}

}
