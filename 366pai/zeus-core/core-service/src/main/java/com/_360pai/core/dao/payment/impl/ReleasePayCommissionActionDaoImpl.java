
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.ReleasePayCommissionActionCondition;
import com._360pai.core.dao.payment.mapper.ReleasePayCommissionActionMapper;
import com._360pai.core.model.payment.ReleasePayCommissionAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.ReleasePayCommissionActionDao;

@Service
public class ReleasePayCommissionActionDaoImpl extends AbstractDaoImpl<ReleasePayCommissionAction, ReleasePayCommissionActionCondition, BaseMapper<ReleasePayCommissionAction,ReleasePayCommissionActionCondition>> implements ReleasePayCommissionActionDao{
	
	@Resource
	private ReleasePayCommissionActionMapper releasePayCommissionActionMapper;
	
	@Override
	protected BaseMapper<ReleasePayCommissionAction, ReleasePayCommissionActionCondition> daoSupport() {
		return releasePayCommissionActionMapper;
	}

	@Override
	protected ReleasePayCommissionActionCondition blankCondition() {
		return new ReleasePayCommissionActionCondition();
	}

}
