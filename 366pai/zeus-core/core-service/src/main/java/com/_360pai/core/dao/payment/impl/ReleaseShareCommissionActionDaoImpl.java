
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.ReleaseShareCommissionActionCondition;
import com._360pai.core.dao.payment.mapper.ReleaseShareCommissionActionMapper;
import com._360pai.core.model.payment.ReleaseShareCommissionAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.ReleaseShareCommissionActionDao;

@Service
public class ReleaseShareCommissionActionDaoImpl extends AbstractDaoImpl<ReleaseShareCommissionAction, ReleaseShareCommissionActionCondition, BaseMapper<ReleaseShareCommissionAction,ReleaseShareCommissionActionCondition>> implements ReleaseShareCommissionActionDao{
	
	@Resource
	private ReleaseShareCommissionActionMapper releaseShareCommissionActionMapper;
	
	@Override
	protected BaseMapper<ReleaseShareCommissionAction, ReleaseShareCommissionActionCondition> daoSupport() {
		return releaseShareCommissionActionMapper;
	}

	@Override
	protected ReleaseShareCommissionActionCondition blankCondition() {
		return new ReleaseShareCommissionActionCondition();
	}

}
