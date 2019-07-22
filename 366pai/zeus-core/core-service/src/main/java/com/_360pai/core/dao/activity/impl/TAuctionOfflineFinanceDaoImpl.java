
package com._360pai.core.dao.activity.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.activity.TAuctionOfflineFinanceCondition;
import com._360pai.core.dao.activity.TAuctionOfflineFinanceDao;
import com._360pai.core.dao.activity.mapper.TAuctionOfflineFinanceMapper;
import com._360pai.core.model.activity.TAuctionOfflineFinance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TAuctionOfflineFinanceDaoImpl extends AbstractDaoImpl<TAuctionOfflineFinance, TAuctionOfflineFinanceCondition, BaseMapper<TAuctionOfflineFinance,TAuctionOfflineFinanceCondition>> implements TAuctionOfflineFinanceDao{
	
	@Resource
	private TAuctionOfflineFinanceMapper tAuctionOfflineFinanceMapper;
	
	@Override
	protected BaseMapper<TAuctionOfflineFinance, TAuctionOfflineFinanceCondition> daoSupport() {
		return tAuctionOfflineFinanceMapper;
	}

	@Override
	protected TAuctionOfflineFinanceCondition blankCondition() {
		return new TAuctionOfflineFinanceCondition();
	}

}
