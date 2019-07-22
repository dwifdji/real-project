
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.DepositChannelPayActionCondition;
import com._360pai.core.dao.payment.mapper.DepositChannelPayActionMapper;
import com._360pai.core.model.payment.DepositChannelPayAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.DepositChannelPayActionDao;

@Service
public class DepositChannelPayActionDaoImpl extends AbstractDaoImpl<DepositChannelPayAction, DepositChannelPayActionCondition, BaseMapper<DepositChannelPayAction,DepositChannelPayActionCondition>> implements DepositChannelPayActionDao{
	
	@Resource
	private DepositChannelPayActionMapper depositChannelPayActionMapper;
	
	@Override
	protected BaseMapper<DepositChannelPayAction, DepositChannelPayActionCondition> daoSupport() {
		return depositChannelPayActionMapper;
	}

	@Override
	protected DepositChannelPayActionCondition blankCondition() {
		return new DepositChannelPayActionCondition();
	}

}
