
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.ReleasePayOrderActionCondition;
import com._360pai.core.dao.payment.mapper.ReleasePayOrderActionMapper;
import com._360pai.core.model.payment.ReleasePayOrderAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.ReleasePayOrderActionDao;

@Service
public class ReleasePayOrderActionDaoImpl extends AbstractDaoImpl<ReleasePayOrderAction, ReleasePayOrderActionCondition, BaseMapper<ReleasePayOrderAction,ReleasePayOrderActionCondition>> implements ReleasePayOrderActionDao{
	
	@Resource
	private ReleasePayOrderActionMapper releasePayOrderActionMapper;
	
	@Override
	protected BaseMapper<ReleasePayOrderAction, ReleasePayOrderActionCondition> daoSupport() {
		return releasePayOrderActionMapper;
	}

	@Override
	protected ReleasePayOrderActionCondition blankCondition() {
		return new ReleasePayOrderActionCondition();
	}

}
