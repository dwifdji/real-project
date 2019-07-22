
package com._360pai.core.dao.order.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.order.TServiceAccountMoneyCondition;
import com._360pai.core.dao.order.mapper.TServiceAccountMoneyMapper;
import com._360pai.core.model.order.TServiceAccountMoney;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.order.TServiceAccountMoneyDao;

@Service
public class TServiceAccountMoneyDaoImpl extends AbstractDaoImpl<TServiceAccountMoney, TServiceAccountMoneyCondition, BaseMapper<TServiceAccountMoney,TServiceAccountMoneyCondition>> implements TServiceAccountMoneyDao{
	
	@Resource
	private TServiceAccountMoneyMapper tServiceAccountMoneyMapper;
	
	@Override
	protected BaseMapper<TServiceAccountMoney, TServiceAccountMoneyCondition> daoSupport() {
		return tServiceAccountMoneyMapper;
	}

	@Override
	protected TServiceAccountMoneyCondition blankCondition() {
		return new TServiceAccountMoneyCondition();
	}

}
