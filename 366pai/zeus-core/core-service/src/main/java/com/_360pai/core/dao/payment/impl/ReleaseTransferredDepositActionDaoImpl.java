
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.ReleaseTransferredDepositActionCondition;
import com._360pai.core.dao.payment.mapper.ReleaseTransferredDepositActionMapper;
import com._360pai.core.model.payment.ReleaseTransferredDepositAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.ReleaseTransferredDepositActionDao;

@Service
public class ReleaseTransferredDepositActionDaoImpl extends AbstractDaoImpl<ReleaseTransferredDepositAction, ReleaseTransferredDepositActionCondition, BaseMapper<ReleaseTransferredDepositAction,ReleaseTransferredDepositActionCondition>> implements ReleaseTransferredDepositActionDao{
	
	@Resource
	private ReleaseTransferredDepositActionMapper releaseTransferredDepositActionMapper;
	
	@Override
	protected BaseMapper<ReleaseTransferredDepositAction, ReleaseTransferredDepositActionCondition> daoSupport() {
		return releaseTransferredDepositActionMapper;
	}

	@Override
	protected ReleaseTransferredDepositActionCondition blankCondition() {
		return new ReleaseTransferredDepositActionCondition();
	}

}
